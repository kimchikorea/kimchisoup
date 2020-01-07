package book.search.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import book.search.entity.PopularKeword;
import book.search.entity.SearchHistory;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long>{
	List<SearchHistory> findByUid(String uid);
	List<SearchHistory> findByUidOrderByRegdateDesc(String uid);
	
	@Query(nativeQuery = true, value =
	           "select sh.keyword as keyword, count(sh.*) as cnt " +
	           "from SEARCH_HISTORY sh "+
	           "group by sh.keyword")
	List<PopularKeword> getPopularKeyowrds();

}
