package jwt.auth.springboot.userauth.service;

import jwt.auth.springboot.userauth.dtos.LoginUser;
import jwt.auth.springboot.userauth.dtos.RegisterUserDto;
import jwt.auth.springboot.userauth.entity.User;
import jwt.auth.springboot.userauth.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(RegisterUserDto input) {
        final String hashedPassword = passwordEncoder.encode(input.password());
        final String email = input.email();
        final String fullName = input.fullName();
        User newUser = new User();
        newUser.setFullName(fullName);
        newUser.setPassword(hashedPassword);
        newUser.setEmail(email);
        return userRepository.save(newUser);
    }

    public User authenticate(LoginUser input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.email(),
                        input.password()
                )
        );
        return userRepository.findByEmail(input.email())
                .orElseThrow();
    }
}

