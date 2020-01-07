package book.login.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import book.login.dao.MemberRepository;
import book.login.entity.Member;
import book.login.entity.MemberRole;

@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	MemberRepository memberRepository;

	@PostMapping("")
	public String createMember(Member member) {
		MemberRole role = new MemberRole();
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		member.setUpw(passwordEncoder.encode(member.getUpw()));
		role.setRoleName("MEMBER");
		member.setRoles(Arrays.asList(role));
		memberRepository.save(member);
		//return "redirect:/";
		return "signin";
	}
}
