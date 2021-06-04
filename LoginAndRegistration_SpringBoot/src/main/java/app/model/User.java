package app.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String surname;
    @NotBlank(message = "Login is mandatory")
    private String login;
    @NotBlank(message = "Password is mandatory")
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    //Особое внимание сюда. Этот метод позволяет увидеть роли
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getUsername() {
        return login;
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

    public String getRole() {
        StringBuilder result = new StringBuilder();
        for (Role role : roles){
            result.append(role.getName()).append(" ");
        }
        return result.toString().trim();
    }
}
