package vpmLimp.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vpmLimp.repositories.UserModelRepository;


@Service
@AllArgsConstructor
public class UserService {

    private final UserModelRepository userModelRepository;


    @Transactional
    public UserDetailsService userDetailsService() {
        return cpf -> userModelRepository.findByCpf(cpf)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with CPF - " + cpf));
    }


}
