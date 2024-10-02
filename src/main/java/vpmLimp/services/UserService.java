package vpmLimp.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vpmLimp.model.UserModel;
import vpmLimp.model.enums.Roles;
import vpmLimp.repositories.RoleRepository;
import vpmLimp.repositories.UserModelRepository;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService  {

    private final UserModelRepository userModelRepository;



    @Transactional
    public UserDetailsService userDetailsService(){
        return username -> userModelRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found with email -" + username));
    }



}
