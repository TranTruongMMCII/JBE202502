package vn.edu.r2s.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnrollmentRequestDto {

	private Integer userId;
	private Integer classId;
}
