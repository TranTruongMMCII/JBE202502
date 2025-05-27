package vn.edu.r2s.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

	@JsonProperty(value = "displayName")
	private String displayName;
	
	private Integer age;
	
	private String email;
	
	private String userName;
	
	private String password;
	
	private String cccd;
}
