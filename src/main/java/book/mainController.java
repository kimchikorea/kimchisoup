package book;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
    
    
    @GetMapping("/signin")
    public String signIn() {
        
        return "signin";
        
    }
    
    @GetMapping("/user/signup")
    public String signUp() {
        
        return "signUp";
        
    }
    
    //로그인 페이지
    @GetMapping("/user/login")
    public String dispLogin() {
        return "signin";
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
        return "denied";
    }

    // 어드민 페이지
    @GetMapping("/admin")
    public String dispAdmin() {
        return "admin";
    }
    
}
