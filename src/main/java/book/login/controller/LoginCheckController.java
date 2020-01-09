package book.login.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import book.login.service.SecurityMemberService;

@RestController
public class LoginCheckController {
	
	@Autowired
	SecurityMemberService securityMemberService;

	@PostMapping("/idCheck")
    public Map<Object, Object> idCheck(@RequestBody String uid) {
        System.out.println("########");
        int count = 0;
        Map<Object, Object> map = new HashMap<Object, Object>();
 
        count = securityMemberService.idCheck(uid);
        map.put("cnt", count);
 
        return map;
    }

}
