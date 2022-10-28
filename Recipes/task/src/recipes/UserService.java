package recipes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Resource
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    public void registerUser(UserRegisterInfo request) {
        Optional<User> byEmail = Optional.ofNullable(userRepository.findByEmail(request.getEmail()));
        if (byEmail.isEmpty()) {
            User user = new User();
            request.setPassword(encoder.encode(request.getPassword()));
            user.setPassword(request.getPassword());
            user.setEmail(request.getEmail());
            userRepository.save(user);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return new UserDetails(user);
    }
}
