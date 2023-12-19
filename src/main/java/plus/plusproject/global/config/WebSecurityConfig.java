package plus.plusproject.global.config;

import plus.plusproject.global.security.filter.JwtAuthenticationFilter;
import plus.plusproject.global.security.filter.JwtAuthorizationFilter;
import plus.plusproject.global.security.jwt.JwtUtil;
import plus.plusproject.global.security.userdetails.UserDetailsServiceImpl;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	private final JwtUtil jwtUtil;
	private final UserDetailsServiceImpl userDetailsService;
	private final AuthenticationConfiguration authenticationConfiguration;

	public WebSecurityConfig( JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService, AuthenticationConfiguration authenticationConfiguration ) {
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
		this.authenticationConfiguration = authenticationConfiguration;
	}

	@Bean
	public AuthenticationManager authenticationManager( AuthenticationConfiguration configuration ) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
		JwtAuthenticationFilter filter = new JwtAuthenticationFilter( jwtUtil );
		filter.setAuthenticationManager( authenticationManager( authenticationConfiguration ) );

		return filter;
	}

	@Bean
	public JwtAuthorizationFilter jwtAuthorizationFilter() {
		return new JwtAuthorizationFilter( jwtUtil, userDetailsService );
	}

	@Bean
	public SecurityFilterChain securityFilterChain( HttpSecurity http) throws Exception {
		// CSRF 설정
		http.csrf( AbstractHttpConfigurer::disable );

		http.authorizeHttpRequests((authorizeHttpRequests) ->
				authorizeHttpRequests
						.requestMatchers( PathRequest.toStaticResources().atCommonLocations()).permitAll() // resources 접근 허용 설정
						.requestMatchers("/api/auth/**").permitAll() // '/api/user/'로 시작하는 요청 모두 접근 허가
						.anyRequest().authenticated() // 그 외 모든 요청 인증처리
		);

		http.addFilterBefore( jwtAuthorizationFilter(), JwtAuthenticationFilter.class );
		http.addFilterBefore( jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class );

		return http.build();
	}
}
