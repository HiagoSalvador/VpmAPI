package vpmLimp.validations;

import org.springframework.stereotype.Component;
import vpmLimp.DTO.SignUp;
import vpmLimp.repositories.UserModelRepository;

@Component
public class UserValidation {


    private final UserModelRepository userModelRepository;

    public UserValidation(UserModelRepository userModelRepository) {
        this.userModelRepository = userModelRepository;
    }

    public void validateSignUp(SignUp signUp) {
        validateUniqueCpf(signUp.cpf());
        validateUniqueEmail(signUp.email());
    }

    private void validateUniqueCpf(String cpf) {
        if (userModelRepository.existsByCpf(cpf)) {
            throw new IllegalArgumentException("CPF already exists.");
        }
    }

    private void validateUniqueEmail(String email) {
        if (userModelRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists.");
        }
    }
}
