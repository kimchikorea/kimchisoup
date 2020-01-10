package book.login.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import book.login.service.SecurityMemberService;

@Controller
public class LoginCheckController {
	
	@Autowired
	SecurityMemberService securityMemberService;

	@RequestMapping(value = "/idCheck", method = RequestMethod.GET)
	@ResponseBody
    public int idCheck(@RequestParam("uid") String uid) {
        int count = 0;
        Map<String, Integer>  map = new HashMap<String, Integer> ();
 
        count = securityMemberService.idCheck(uid);
        map.put("cnt", count);
 
        return count;
    }

}
