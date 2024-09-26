package vpmLimp.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name ="user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserModel implements UserDetails {
    @Column(name = "id", nullable = false, unique = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "address", nullable = false )
    private String address;

    @Column(name = "city",  nullable = false)
    private String city;

    @Column(name = "state",  nullable = false)
    private String state;

    @Column(name = "zip_code",  nullable = false)
    private String zipCode;

    @Column(name = "phone", unique = true)
    private String phone;

    @Column(name = "cpf", unique = true, length = 11)
    private String cpf;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
