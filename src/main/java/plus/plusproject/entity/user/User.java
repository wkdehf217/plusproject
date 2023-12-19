package plus.plusproject.entity.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private long id;
	private String username;
	private String password;
	private String passwordConfirm;
	private String email;
	private String introduce;


	@Enumerated( value = EnumType.STRING )
	private UserRoleEnum role;
}
