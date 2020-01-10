package book.login.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import book.login.dao.MemberRepository;
import book.login.entity.Member;
import book.login.entity.MemberRole;

@Controller
public class MemberController implements WebMvcConfigurer{

	@Autowired
	MemberRepository memberRepository;	
	
	

	@PostMapping("/member")
	public String createMember(Member member) {
		
			MemberRole role = new MemberRole();
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			member.setUpw(passwordEncoder.encode(member.getUpw()));
			role.setRoleName("MEMBER");
			member.setRoles(Arrays.asList(role));
			memberRepository.save(member);
			
			return "signin";
		
	}
	
}
