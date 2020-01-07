package book.search.controller;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import book.login.entity.Member;
import book.login.entity.SecurityMember;
import book.search.dao.SearchHistoryRepository;
import book.search.entity.PopularKeword;
import book.search.entity.SearchHistory;
import book.search.service.SearchService;


@Controller
public class SearchController {
	
	@Resource
	private SearchService searchService;
	
	@Resource
	private SearchHistoryRepository searchHistoryRepository;
	
	// Get my search history
	@GetMapping("/search/myhistory") 
	public ModelAndView getMySearchHistory() {
		
		ModelAndView mv =new ModelAndView();
		List<SearchHistory> myhistory = searchService.findByUidOrderByRegdateDesc();
	    
	    mv.addObject("myhistorylist", myhistory);
        mv.setViewName("myhistory");
		
		return mv;
		
	}
	
	@GetMapping("/search/popularkeywords") 
	public ModelAndView getPopularkeywords() {
		
		ModelAndView mv =new ModelAndView();
		List<PopularKeword> popularKeywords = searchService.getPopularKeyowrds();
	    
	    mv.addObject("popularKeywords", popularKeywords);
        mv.setViewName("popularkeyword");
		
		return mv;
		
	}
	
	@GetMapping("/search")
	public ModelAndView search(String keyword, @RequestParam(value = "currentPage", required = false, defaultValue = "0") int currentPage) {
		System.out.println("#### currentPage " + currentPage);
		System.out.println("#### keyword " + keyword);
		ModelAndView mv =new ModelAndView();
		Map<String, Object> map = searchService.bookSearch(keyword, currentPage);
		
		mv.addObject("bookList", map.get("bookList"));
		mv.addObject("totalCnt", map.get("totalCnt"));
		mv.addObject("currentPage", currentPage);
		mv.addObject("keyword", keyword);
		mv.addObject("searchFrom", map.get("searchFrom"));
		mv.addObject("lastPage", map.get("lastPage"));
		mv.addObject("startPageNum", map.get("startPageNum"));
		mv.addObject("lastPageNum", map.get("lastPageNum")); 
			
		mv.setViewName("searchList");
		
		return mv;
	}
	

}
