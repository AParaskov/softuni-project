package gamestore.UserTest;

import gamestore.model.entity.Role;
import gamestore.model.entity.User;
import gamestore.repository.UserRepository;
import gamestore.service.UserDetailService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceTest {
    private UserDetailService serviceToTest;

    private User user;

    private Role role;

    @Mock
    private UserRepository mockUserRepository;

    @BeforeEach
    public void setUp() {
        role = new Role();
        role.setName("ADMIN");

        user = new User();
        user.setUsername("user");
        user.setEmail("email");
        user.setPassword("abcdef");
        user.setRole(role);

        serviceToTest = new UserDetailService(mockUserRepository);
    }

    @Test
    public void testLoadUserByUsername() {
        when(mockUserRepository.findByUsername("user"))
                .thenReturn(Optional.of(user));

        UserDetails actualDetails = serviceToTest.loadUserByUsername("user");

        Assertions.assertEquals(user.getUsername(), actualDetails.getUsername());
    }

    @Test
    public void failTestLoadUserByUsername() {
        when(mockUserRepository.findByUsername("admin"))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            serviceToTest.loadUserByUsername("admin");
        });
    }


}
