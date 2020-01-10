package book.search.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import book.search.dao.SearchHistoryRepository;
import book.search.entity.PopularKeword;
import book.search.entity.SearchHistory;
import book.search.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {

	private static String token = "1b6f955bf84aeafbbe78c820cf205bd3";// 토큰 값";
	private static String header = "KakaoAK " + token;

	private static String NAVER_ID = "u46JnmukJEspmftfqH2a";
	private static String NAVER_SECRET = "OokbsbViiA";

	private static HttpURLConnection con;
	private static URL url;
	private static String apiURL;
	private static String queryString;
	private static BufferedReader br;
	private static Boolean fromKakao = true;
	//사용한 검색 API
	private static String searchFrom = "kakao";
	
	Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private SearchHistoryRepository searchHistoryRepository;

	@Override
	public List<SearchHistory> findByUidOrderByRegdateDesc() {
        String uid = currentUserId();
		return searchHistoryRepository.findByUidOrderByRegdateDesc(uid);
	}

	@Override
	public List<PopularKeword> getPopularKeyowrds() {

		return searchHistoryRepository.getPopularKeyowrds();
	}

	@Override
	@Cacheable("bookSearch")
	public Map<String, Object> bookSearch(String keyword, int currentPage) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		
		
		JSONArray docuArray = null;
		try {
			apiURL = "https://dapi.kakao.com/v3/search/book";
			queryString = "?query=" + URLEncoder.encode(keyword, "UTF-8");
			apiURL = apiURL + queryString;
			apiURL = apiURL +"&page=" + currentPage;
			url = new URL(apiURL);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Authorization", header);
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			con.setRequestProperty("charset", "utf-8");

			int responseCode = con.getResponseCode();

			// 200 O.K
			if (responseCode == HttpURLConnection.HTTP_OK) {
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			}
			// Kakao Book API Server Error  --> Naver Book API
			else if (responseCode == HttpURLConnection.HTTP_INTERNAL_ERROR) {
				con.disconnect();
				searchNaverBookAPI(queryString, currentPage);

			} else { // 에러 발생
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}
			br.close();

			JSONParser jsonParser = new JSONParser();
			String responseString = response.toString();
			
			// 네이버 검색의 경우 결과값에 HTML태그가 포함될 수 있어 제거
			if (!fromKakao) responseString = responseString.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>","");

			JSONObject jsonObject = (JSONObject) jsonParser.parse(responseString);

			long totalCnt = 0;
			if (fromKakao) {
				docuArray = (JSONArray) jsonObject.get("documents");	

				String jsonString = jsonObject.get("meta").toString();
				JSONObject jsonObjectMeta = (JSONObject) jsonParser.parse(jsonString);
				totalCnt = (long) jsonObjectMeta.get("pageable_count");
			}
			else {
				docuArray = (JSONArray) jsonObject.get("items");	
				totalCnt = (long) jsonObject.get("total");	
			}
			map = getPageInfo(map, currentPage, totalCnt);
			map.put("bookList", docuArray);
			map.put("totalCnt", totalCnt);

		} catch (Exception e) {
			System.out.println(e);
		}

		
		return map;
	}

	public void searchNaverBookAPI(String queryString, int currentPage) {
		searchFrom = "naver";
		try {
			// Naver API 사용
			apiURL = "https://openapi.naver.com/v1/search/book.json";
			apiURL = apiURL + queryString;
			
			// 네이버의 경우, 페이지가 아닌 시작 번호 우선이므로 (n-1)*10 +1 연산을한다.(1페이지에 10개 기준)
			apiURL = apiURL + "&start=" + ((currentPage-1)*10 + 1);
			url = new URL(apiURL);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("X-Naver-Client-Id", NAVER_ID);
			con.setRequestProperty("X-Naver-Client-Secret", NAVER_SECRET);
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			con.setRequestProperty("charset", "utf-8");

			int responseCode = con.getResponseCode();

			if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
				fromKakao = false;
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else { // 에러 발생
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

	}
	
    public Map<String, Object> getPageInfo(Map<String, Object> map, int currentPage, long totalCnt) {
    	
    	// 페이지에 보여줄 행의 개수 ROW_PER_PAGE = 10으로 고정
        final int ROW_PER_PAGE = 10; 
        
        // 페이지에 보여줄 첫번째 페이지 번호는 1로 초기화
        int startPageNum = 1;
        
        // 처음 보여줄 마지막 페이지 번호는 10
        int lastPageNum = ROW_PER_PAGE;
        
        // 현재 페이지가 ROW_PER_PAGE/2 보다 클 경우
        if(currentPage > (ROW_PER_PAGE/2)) {
            // 보여지는 페이지 첫번째 페이지 번호는 현재페이지 - ((마지막 페이지 번호/2) -1 )
            // ex 현재 페이지가 6이라면 첫번째 페이지번호는 2
            startPageNum = currentPage - ((lastPageNum/2)-1);
            // 보여지는 마지막 페이지 번호는 현재 페이지 번호 + 현재 페이지 번호 - 1 
            lastPageNum += (startPageNum-1);
        }
        
        // Map Data Type 객체 참조 변수 map 선언
        // HashMap() 생성자 메서드로 새로운 객체를 생성, 생성된 객체의 주소값을 객체 참조 변수에 할당
        // 한 페이지에 보여지는 첫번째 행은 (현재페이지 - 1) * 10
        int startRow = (currentPage - 1)*ROW_PER_PAGE;
        // 값을 map에 던져줌
        map.put("startRow", startRow);
        map.put("rowPerPage", ROW_PER_PAGE);
        
        // DB 행의 총 개수를 구하는 getBoardAllCount() 메서드를 호출하여 double Date Type의 boardCount 변수에 대입
        //double boardCount = boardMapper.getBoardAllCount();

        // 마지막 페이지번호를 구하기 위해 총 개수 / 페이지당 보여지는 행의 개수 -> 올림 처리 -> lastPage 변수에 대입
        int lastPage = (int)(Math.ceil((double)totalCnt/ROW_PER_PAGE));
        
        // 현재 페이지가 (마지막 페이지-4) 보다 같거나 클 경우
        if(currentPage >= (lastPage-4)) {
            // 마지막 페이지 번호는 lastPage
            lastPageNum = lastPage;
        }
        
        map.put("currentPage", currentPage);
        map.put("lastPage", lastPage);
        map.put("startPageNum", startPageNum);
        map.put("lastPageNum", lastPageNum);       
        map.put("searchFrom", searchFrom);       
    	
        return map;
    }

    //현재사용자 ID값 가져오기
	@Override
	public String currentUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		return user.getUsername();
	}

}
