package vpmLimp.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import vpmLimp.DTO.*;
import vpmLimp.model.UserModel;
import vpmLimp.model.enums.Roles;
import vpmLimp.repositories.RoleRepository;
import vpmLimp.repositories.UserModelRepository;

import javax.management.relation.RoleNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;




@Service
@AllArgsConstructor
public class AuthService {

    private final JwtServices jwtServices;
    private final AuthenticationManager authenticationManager;
    private final UserModelRepository userModelRepository;
    private final PasswordEncoder passwordEncoder;

    private RoleRepository roleRepository;




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

    public UserResponse makeAdmin(Long userId) throws RoleNotFoundException {
        UserModel user = userModelRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        Roles adminRole = roleRepository.findByName("ADMIN")
                .orElseThrow(() -> new RoleNotFoundException("Role 'ADMIN' not found."));

        user.getRoles().add(adminRole);

        userModelRepository.save(user);

        return new UserResponse(user);
    }





}
