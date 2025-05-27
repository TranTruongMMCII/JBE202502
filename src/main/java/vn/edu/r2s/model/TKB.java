package vn.edu.r2s.model;

import java.time.OffsetDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TKB {

//	@EmbeddedId
//	private TKBKey key;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private Integer times;

	private OffsetDateTime startTime;

	private OffsetDateTime endTime;

	private String description;

	@ManyToOne
//	@MapsId(value = "userId")
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	@ManyToOne
//	@MapsId(value = "courseId")
	@JoinColumn(name = "course_id", referencedColumnName = "id")
	private Course course;
}

//@Embeddable
//class TKBKey {
//
//	@JoinColumn(name = "course_id")
//	private Integer courseId;
//
//	@JoinColumn(name = "user_id")
//	private Integer userId;
//
//	private Integer times;
//}