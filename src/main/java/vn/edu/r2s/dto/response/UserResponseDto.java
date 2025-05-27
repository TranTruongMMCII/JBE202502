package vn.edu.r2s.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

	private Integer id;

	@JsonProperty(value = "displayName")
	private String name;
	private String email;
	private Integer age;
	private String cccd;
}
