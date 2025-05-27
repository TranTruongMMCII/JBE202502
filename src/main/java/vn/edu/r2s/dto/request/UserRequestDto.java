package vn.edu.r2s.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
public class UserRequestDto {

	@NotBlank(message = "yeu cau phai nhap ten ne")
	private String name;

	@Email
	private String email;

	@Min(value = 1L)
	@Max(value = 130L, message = "cu song tho nhat chi co 121 tuoi ma thoi")
	private Integer age;

	private String cccd;
}
