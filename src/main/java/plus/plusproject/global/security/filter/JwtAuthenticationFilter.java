package plus.plusproject.global.security.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import plus.plusproject.global.security.jwt.JwtUtil;
import plus.plusproject.user.entity.UserRoleEnum;
import plus.plusproject.global.security.userdetails.UserDetailsImpl;
import plus.plusproject.user.dto.UserRequestDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final JwtUtil jwtUtil;

	public JwtAuthenticationFilter(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
		setFilterProcessesUrl("/api/auth/login");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		log.info("로그인 시도");
		try {
			var inputStream = request.getInputStream();
			UserRequestDto requestDto = new ObjectMapper().readValue( inputStream, UserRequestDto.class);

			return getAuthenticationManager().authenticate(
					new UsernamePasswordAuthenticationToken(
							requestDto.getUsername(),
							requestDto.getPassword(),
							null
					)
			);
		} catch (IOException e) {
			log.error(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
		log.info("로그인 성공 및 JWT 생성");
		long userId = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getId();
		UserRoleEnum role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();

		String token = jwtUtil.createToken(userId, role);
		jwtUtil.addJwtToCookie(token, response);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
		log.info("로그인 실패");
		response.setStatus(401);
	}
}
