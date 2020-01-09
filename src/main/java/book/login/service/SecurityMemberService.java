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
	public UserDetails loadUserByUsername(String uid) throws UsernameNotFoundException {
		memberRepository.findByUid(uid).orElseThrow(() -> new UsernameNotFoundException("아이디 혹은 비밀번호를 잘 못 입력 하셨습니다."));
		
		Optional<Member> userEntityWrapper = memberRepository.findByUid(uid);
		Member userEntity = userEntityWrapper.get();

		List<GrantedAuthority> authorities = new ArrayList<>();

		if (("admin").equals(uid)) {
			authorities.add(new SimpleGrantedAuthority(MemberRole.ADMIN));
		} else {
			authorities.add(new SimpleGrantedAuthority(MemberRole.MEMBER));
		}

		return new User(userEntity.getUid(), userEntity.getUpw(), authorities);
	}
	
	public int idCheck(String uid) {
		int cnt = 0;
		cnt = memberRepository.idCheck(uid);
		
		return cnt;
	}
}
