package book;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@EnableAutoConfiguration
public class mainController {


	
    @GetMapping("/")
    public String mainHome() {
    	return "/search/dashboard";
        
    }
    
    @GetMapping("/layout/default")
    public String goDefault() {
        
    	return "layout/default";
        
    }
    
    @GetMapping("/user/signup")
    public String signUp() {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	// 이미 로그인된 사용자 일 경우
    	if (!(auth instanceof AnonymousAuthenticationToken)) {
    	    /* The user is logged in :) */
    	    return "search/dashboard";
    	}else {

            return "signUp";	
    	}
        
    }
    
    //로그인 페이지
    @GetMapping("/user/login")
    public String dispLogin() {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    	// 이미 로그인된 사용자 일 경우
    	if (!(auth instanceof AnonymousAuthenticationToken)) {
    	    /* The user is logged in :) */
    	    return "search/dashboard";
    	}else {

            return "signin";	
    	}
    	
    	
    }
    
    // 로그인 결과 페이지
    @GetMapping("/user/login/result")
    public String dispLoginResult() {
        return "search/dashboard";
    }
    
   // 로그아웃 결과 페이지
    @GetMapping("/user/logout/result")
    public String dispLogout() {
        return "logout";
    }

    // 접근 거부 페이지
    @GetMapping("/user/denied")
    public String dispDenied() {
        return "error/401";
    }

    // 어드민 페이지
    @GetMapping("/admin")
    public String dispAdmin() {
        return "admin";
    }
    
}
