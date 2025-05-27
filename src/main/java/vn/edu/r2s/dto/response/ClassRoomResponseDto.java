package vn.edu.r2s.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClassRoomResponseDto {

	private Integer id;
	private String name;
}
