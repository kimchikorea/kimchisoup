package book.search.service;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import book.search.entity.PopularKeword;
import book.search.entity.SearchHistory;

public interface SearchService {
	
	// 검색기록 조회 (날짜 최신순 정렬)
	List<SearchHistory> findByUidOrderByRegdateDesc();
	
	// 인기검색어 조회 (조회순 정렬)
	List<PopularKeword> getPopularKeyowrds();
	
	// 책검색 결과 조회
	Map<String, Object> bookSearch(String keyword, int currentPage);
	
	String currentUserId();
	
	
	
}
