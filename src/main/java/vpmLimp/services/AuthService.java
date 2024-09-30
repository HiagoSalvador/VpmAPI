package vpmLimp.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import vpmLimp.DTO.*;
import vpmLimp.model.UserModel;
import vpmLimp.repositories.UserModelRepository;
import java.util.List;
import java.util.stream.Collectors;




@Service
@AllArgsConstructor
public class AuthService {

    private final JwtServices jwtServices;
    private final AuthenticationManager authenticationManager;
    private final UserModelRepository userModelRepository;
    private final PasswordEncoder passwordEncoder;


    public JwtAuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userModelRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        var jwt = jwtServices.generateToken(user);
        return new JwtAuthResponse(jwt);
    }

    public UserResponse signUp(SignUp signUp) {
        UserModel user = this.userModelRepository.save(
                UserModel.builder()
                        .name(signUp.getName())
                        .email(signUp.getEmail())
                        .password(passwordEncoder.encode(signUp.getPassword()))
                        .phone(signUp.getPhone())
                        .cpf(signUp.getCpf())
                        .address(signUp.getAddress())
                        .city(signUp.getCity())
                        .state(signUp.getState())
                        .zipCode(signUp.getZipCode())
                        .build()
        );
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

        user.setEmail(updateUser.getEmail());
        user.setPassword(passwordEncoder.encode(updateUser.getPassword()));
        user.setAddress(updateUser.getAddress());
        user.setCity(updateUser.getCity());
        user.setState(updateUser.getState());
        user.setZipCode(updateUser.getZipCode());
        user.setPhone(updateUser.getPhone());

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
