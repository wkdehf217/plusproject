package plus.plusproject.user.dto;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Pattern;

@Getter
@Setter
public class UserRequestDto {
	@Pattern(regexp = "^[a-zA-Z0-9]{3,15}$")
	String username;

	@Pattern(regexp = "^[a-zA-Z0-9]{4,15}$")
	String password;

	@Pattern(regexp = "^[a-zA-Z0-9]{4,15}$")
	String passwordConfirm;

	private String email;
	private String introduce;
	private String role;
}