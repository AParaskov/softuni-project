package gamestore.service;


import gamestore.model.entity.Role;
import gamestore.model.entity.User;
import gamestore.repository.UserRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Primary
@Service
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;


    public UserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userEntity = userRepository
                .findByUsername(username);

        return userEntity.
                map(this::map)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found!"));
    }

    private org.springframework.security.core.userdetails.User map(User user1) {
        return new org.springframework.security.core.userdetails.User(
                user1.getUsername(),
                user1.getPassword(),
                map(user1.getRole())

        );
    }


    private List<GrantedAuthority> map(Role role) {
        return List.of(new SimpleGrantedAuthority(role.getName()));


    }
}
