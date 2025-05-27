package vn.edu.r2s.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnrollmentResponseDto {

	private UserResponseDto userResponse;
	private ClassRoomResponseDto clasRoomResponse;
}
