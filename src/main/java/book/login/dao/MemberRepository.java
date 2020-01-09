package book.login.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import book.login.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	  Optional<Member> findByUemail(String uemail);
	  Optional<Member> findByUid(String uid);
	  
	  @Query(nativeQuery = true, value =
	           "SELECT COUNT(*) FROM MEMBER " + 
	           "WHERE uid = :uid")
	int idCheck(@Param("uid") String uid);

} 