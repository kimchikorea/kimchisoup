package book.login.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import book.login.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	  Optional<Member> findByUemail(String uemail);
	  Optional<Member> findByUid(String uid);

} 