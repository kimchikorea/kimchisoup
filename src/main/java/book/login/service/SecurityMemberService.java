package book.login.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import book.login.dao.MemberRepository;
import book.login.entity.Member;
import book.login.entity.MemberRole;
import book.login.entity.SecurityMember;

@Service
public class SecurityMemberService implements UserDetailsService {

	@Resource(name = "memberRepository")
	private MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Member member = memberRepository.findByUid(username).orElseThrow(() -> new
		// UsernameNotFoundException("아이디 혹은 비밀번호를 잘 못 입력 하셨습니다."));
		// return new SecurityMember(member);
		Optional<Member> userEntityWrapper = memberRepository.findByUid(username);
		Member userEntity = userEntityWrapper.get();

		List<GrantedAuthority> authorities = new ArrayList<>();

		if (("admin").equals(username)) {
			authorities.add(new SimpleGrantedAuthority(MemberRole.ADMIN));
		} else {
			authorities.add(new SimpleGrantedAuthority(MemberRole.MEMBER));
		}

		return new User(userEntity.getUid(), userEntity.getUpw(), authorities);
	}
}
