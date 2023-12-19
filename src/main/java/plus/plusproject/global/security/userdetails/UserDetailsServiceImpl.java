package plus.plusproject.global.security.userdetails;

import plus.plusproject.user.entity.User;
import plus.plusproject.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	public UserDetailsServiceImpl( UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public UserDetails loadUserByUserId( long userId ) throws NoSuchElementException {
		User user = userRepository.findById( userId )
				.orElseThrow( () -> new NoSuchElementException( "user id : " + userId + " not exist" ) );

		return new UserDetailsImpl( user );
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Not Found " + username));

		return new UserDetailsImpl(user);
	}
}
