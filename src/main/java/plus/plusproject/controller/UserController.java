package plus.plusproject.controller;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import plus.plusproject.dto.user.UserRequestDto;
import plus.plusproject.etc.response.ApiResponse;
import plus.plusproject.security.UserDetailsImpl;
import plus.plusproject.service.EmailService;
import plus.plusproject.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping( "/api" )
public class UserController {
	private final UserService userService;
	private final EmailService emailService;

	@PostMapping( "/auth/signup" )
	public @ResponseBody ResponseEntity<ApiResponse> signup( @RequestBody UserRequestDto userRequestDto ) {

		this.emailService.sendEmailAuth( userRequestDto );

		return ResponseEntity.ok( ApiResponse.ok( userRequestDto.getEmail() + "으로 인증 메일을 발송하였습니다."  ) );
	}

	@GetMapping( "/auth/signup/email/{id}" )
	public ResponseEntity< ApiResponse > email_auth( @PathVariable String id ) {
		this.userService.signupEmailAuth( id );

		return ResponseEntity.ok( ApiResponse.ok( "회원 가입을 축하합니다. 이제부터 로그인 가능합니다." ) );
	}

	@GetMapping( "/users/{userId}" )
	public ResponseEntity< ApiResponse > getUser( @PathVariable long userId ) {
		var userResponseDto = this.userService.getUser( userId );

		return ResponseEntity.ok( ApiResponse.ok( userResponseDto ) );
	}

	@PatchMapping( "/users" )
	public ResponseEntity< ApiResponse > updateProfile( @AuthenticationPrincipal UserDetailsImpl userDetailsImpl, UserRequestDto userRequestDto ) {
		var userId = userDetailsImpl.getUser().getId();
		userService.updateUser( userId, userRequestDto );

		return ResponseEntity.ok( ApiResponse.ok( "update success" ) );
	}

}
