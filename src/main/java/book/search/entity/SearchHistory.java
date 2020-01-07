package book.search.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Entity
public class SearchHistory {
	
	public SearchHistory(){}
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false, length=50)
	private String uid;
	
	@Column(nullable = false, length=1000)
	private String keyword;
	
	@CreationTimestamp
	@Column
    private Date regdate;

}
