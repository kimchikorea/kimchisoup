package book.search.controller;


import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import book.search.dao.SearchHistoryRepository;
import book.search.entity.PopularKeword;
import book.search.entity.SearchHistory;
import book.search.service.SearchService;


@Controller
public class SearchController implements Serializable{
	


	/**
	 * 
	 */
	private static final long serialVersionUID = -8375590693288675158L;

	@Resource
	private SearchService searchService;
	
	@Resource
	private SearchHistoryRepository searchHistoryRepository;
	
	// 내 검색기록 조회
	@GetMapping("/search/myhistory") 
	public ModelAndView getMySearchHistory() {
		ModelAndView mv =new ModelAndView();
		List<SearchHistory> myhistory = searchService.findByUidOrderByRegdateDesc();
	    
	    mv.addObject("myhistorylist", myhistory);
        mv.setViewName("search/myhistory");
		
		return mv;
		
	}
	
	// 인기검색어 상위 10개 조회
	@GetMapping("/search/popularkeywords") 
	public ModelAndView getPopularkeywords() {
		
		ModelAndView mv =new ModelAndView();
		List<PopularKeword> popularKeywords = searchService.getPopularKeyowrds();
	    
	    mv.addObject("popularKeywords", popularKeywords);
        mv.setViewName("search/popularkeyword");
		
		return mv;
		
	}
	
	// 책 검색 : 캐싱처리 - 1시간마다 삭제
	@GetMapping("/search")
	public ModelAndView search(@RequestParam(required = true) String keyword, @RequestParam(value = "currentPage", required = false, defaultValue = "0") int currentPage) {
		
		ModelAndView mv =new ModelAndView();
		
		// currentPage가 0일 경우, 검색해서 들어온 로직이기 때문에 검색결과 저장.
	    // 그 이외의 경우는 페이지 검색으로 검색결과에 저장하지는 않
		if(currentPage == 0) {
			saveSearchHistory(keyword);
			currentPage = 1;
		}
		
		Map<String, Object> map = searchService.bookSearch(keyword, currentPage);
		
		mv.addObject("bookList", map.get("bookList"));
		mv.addObject("totalCnt", map.get("totalCnt"));
		mv.addObject("currentPage", currentPage);
		mv.addObject("keyword", keyword);
		mv.addObject("searchFrom", map.get("searchFrom"));
		mv.addObject("lastPage", map.get("lastPage"));
		mv.addObject("startPageNum", map.get("startPageNum"));
		mv.addObject("lastPageNum", map.get("lastPageNum")); 
			
		mv.setViewName("search/searchList");
		return mv;
	}
	
	// 검색결과 저장
		@Async("threadPoolTaskExecutor")
		public void saveSearchHistory(String keyword) {
			SearchHistory searchHistory = new SearchHistory();
			String uid = searchService.currentUserId();
			searchHistory.setUid(uid);
			searchHistory.setKeyword(keyword);
			searchHistoryRepository.save(searchHistory);

		}
	

}
