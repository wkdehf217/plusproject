package plus.plusproject.user.service;

import plus.plusproject.user.dto.UserRequestDto;
import plus.plusproject.user.entity.EmailAuth;
import plus.plusproject.global.exception.user.ContainUsernameAndPassword;
import plus.plusproject.global.exception.user.NotMatchPasswordAndPasswordConfirm;
import plus.plusproject.user.repository.EmailAuthRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

	private final JavaMailSender javaMailSender;
	private final EmailAuthRepository emailAuthRepository;
	private final PasswordEncoder passwordEncoder;

	public void sendEmailAuth( UserRequestDto userRequestDto ) {

		checkUsernameAndPassword(userRequestDto);
		String subject = "[ Plus Project ] 회원 가입 인증 메일입니다.";

		String id = passwordEncoder.encode( userRequestDto.getUsername()
				+ userRequestDto.getEmail()
				+ userRequestDto.getIntroduce()	);
		id = id.replace( "/", "." );
		String password = passwordEncoder.encode( userRequestDto.getPassword() );

		emailAuthRepository.save( EmailAuth.builder()
				.id( id )
				.username( userRequestDto.getUsername() )
				.password( password )
				.introduce( userRequestDto.getIntroduce() )
				.email( userRequestDto.getEmail() ).build()
		);

		String text = "http://localhost:8080/api/auth/signup/email/" + id;

		sendMail( userRequestDto.getEmail(), subject, text );
	}

	private void checkUsernameAndPassword(UserRequestDto userRequestDto) {

		if(userRequestDto.getPassword().contains(userRequestDto.getUsername())){
			throw new ContainUsernameAndPassword();
		}

		if(!userRequestDto.getPassword().equals(userRequestDto.getPasswordConfirm())){
			throw new NotMatchPasswordAndPasswordConfirm();
		}
	}

	private void sendMail( String to, String subject, String text ) {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();

		try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
			mimeMessageHelper.setTo( to ); // 메일 수신자
			mimeMessageHelper.setSubject( subject ); // 메일 제목
			mimeMessageHelper.setText( text , false); // 메일 본문 내용, HTML 여부

			javaMailSender.send(mimeMessage);

			log.info("Success");

		} catch ( MessagingException e ) {
			log.info("fail");
			throw new RuntimeException( e );
		}
	}
}
