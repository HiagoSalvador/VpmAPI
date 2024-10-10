package vpmLimp.services;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import vpmLimp.DTO.*;
import vpmLimp.model.UserModel;
import vpmLimp.model.enums.UserRole;
import vpmLimp.repositories.UserModelRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserModelRepository userModelRepository;
    private final PasswordEncoder passwordEncoder;


    public JwtAuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        var user = userModelRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        var jwt = jwtService.generateToken(user);
        return new JwtAuthResponse(jwt);
    }

    public UserResponse signUp(SignUp signUp) {
        UserRole role = Optional.ofNullable(signUp.role())
                .filter(r -> !r.isEmpty())
                .map(UserRole::valueOf)
                .orElse(UserRole.USER);

        UserModel user = UserModel.builder()
                .name(signUp.name())
                .email(signUp.email())
                .password(passwordEncoder.encode(signUp.password()))
                .phone(signUp.phone())
                .cpf(signUp.cpf())
                .address(signUp.address())
                .city(signUp.city())
                .state(signUp.state())
                .zipCode(signUp.zipCode())
                .role(role)
                .build();

        user = this.userModelRepository.save(user);

        return new UserResponse(user);
    }
    public UserResponse getUserById(Long id) {
        UserModel user = userModelRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));
        return new UserResponse(user);
    }


    public UserResponse updateUser(Long id, UpdateUser updateUser) {
        UserModel user = userModelRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        user.setEmail(updateUser.email());
        user.setPassword(passwordEncoder.encode(updateUser.password()));
        user.setAddress(updateUser.address());
        user.setCity(updateUser.city());
        user.setState(updateUser.state());
        user.setZipCode(updateUser.zipCode());
        user.setPhone(updateUser.phone());

        return new UserResponse(userModelRepository.save(user));
    }

    public void deleteUser(Long id) {
        if (!userModelRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found.");
        }
        userModelRepository.deleteById(id);
    }

    public List<UserResponse> getAllUsers() {
        List<UserModel> users = userModelRepository.findAll();
        return users.stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

}








