package plus.plusproject.user.service;

import plus.plusproject.user.dto.UserRequestDto;
import plus.plusproject.user.dto.UserResponseDto;
import plus.plusproject.user.entity.EmailAuth;
import plus.plusproject.user.entity.User;
import plus.plusproject.user.entity.UserRoleEnum;
import plus.plusproject.user.repository.EmailAuthRepository;
import plus.plusproject.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final EmailAuthRepository emailAuthRepository;
	private final PasswordEncoder passwordEncoder;


	public UserResponseDto getUser( long userId ) {
		var user = this.userRepository.findById( userId )
				.orElseThrow( ()-> new NoSuchElementException( "user id : " + userId + " not exist." ) );

		UserResponseDto userResponseDto = new UserResponseDto();
		userResponseDto.setUsername( user.getUsername() );
		userResponseDto.setEmail( user.getEmail() );
		userResponseDto.setIntroduce( user.getIntroduce() );

		return userResponseDto;
	}

	@Transactional
	public void updateUser( long userId, UserRequestDto userRequestDto ) {
		User user = userRepository.findById(userId)
				.orElseThrow( ()-> new NoSuchElementException( "user id : " + userId + " not exist." ) );

		user.setUsername( userRequestDto.getUsername() );
		user.setIntroduce( userRequestDto.getIntroduce() );
		user.setEmail( userRequestDto.getEmail() );
		user.setPassword( passwordEncoder.encode( userRequestDto.getPassword() ) );
	}

	@Transactional
	public void signupEmailAuth( String id ) {
		EmailAuth emailAuth = this.emailAuthRepository.findById( id ).orElseThrow(
				()-> new NoSuchElementException( "일치하는 이메일 인증 ID를 찾을 수 없습니다." )
		);

		String username = emailAuth.getUsername();;
		var findUser = userRepository.findByUsername( username );
		if( findUser.isPresent() ){
			throw new DuplicateKeyException( "user name : " + username + " duplicated" );
		}

		User user = new User();
		user.setUsername( username );
		user.setPassword( emailAuth.getPassword() );
		user.setEmail(  emailAuth.getEmail() );
		user.setIntroduce( emailAuth.getIntroduce() );
		user.setRole( UserRoleEnum.USER );

		this.userRepository.save( user );

		this.emailAuthRepository.deleteById( id );
	}
}
