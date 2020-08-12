package gamestore.UserTest;

import gamestore.model.entity.Role;
import gamestore.model.entity.User;
import gamestore.model.service.RoleServiceModel;
import gamestore.model.service.UserServiceModel;
import gamestore.repository.RoleRepository;
import gamestore.repository.UserRepository;
import gamestore.service.RoleService;
import gamestore.service.RoleServiceImpl;
import gamestore.service.UserService;
import gamestore.service.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    private UserService serviceToTest;
    private User user;
    private Role userRole;
    private Role adminRole;

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private RoleRepository mockRoleRepository;

    private ModelMapper modelMapper = new ModelMapper();

    private RoleService roleService;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


    @BeforeEach
    public void setUp() {

        user = new User();
        user.setId("SOME_UUID");
        user.setUsername("user");
        user.setPassword("12345");
        user.setEmail("abv@abv.bg");

        userRole = new Role();
        userRole.setName("USER");

        adminRole = new Role();
        adminRole.setName("ADMIN");


        roleService = new RoleServiceImpl(mockRoleRepository, modelMapper);
        serviceToTest = new UserServiceImpl(mockUserRepository, roleService, modelMapper, bCryptPasswordEncoder);
    }

    @Test
    public void testFindAllUsernames() {
        when(mockUserRepository.findAll()).
                thenReturn(List.of(user));

        List<String> usernames = serviceToTest.findAllUsernames();

        Assertions.assertEquals(1, usernames.size());
        String actualUsername = usernames.get(0);
        Assertions.assertEquals(user.getUsername(), actualUsername);

    }

    @Test
    public void testFindUserByUsername() {
        when(mockUserRepository.findByUsername("user")).
                thenReturn(Optional.of(user));

        UserServiceModel userByUsername = serviceToTest.findByUsername("user");

        Assertions.assertEquals(user.getUsername(), userByUsername.getUsername());

    }

    @Test
    public void testAddRoleToUser() {
        Role role1 = new Role();
        role1.setName("ADMIN");

        user = new User();
        user.setUsername("admin");
        user.setRole(role1);

        when(mockUserRepository.findByUsername("admin")).
                thenReturn(Optional.of(user));

        when(mockRoleRepository.findByName("USER"))
                .thenReturn(Optional.of(userRole));

        serviceToTest.addRoleToUser("admin", userRole.getName());

        Assertions.assertEquals(user.getRole().getName(), userRole.getName());

    }

    @Test
    public void testRegisterUser() {
        RoleServiceModel roleServiceModel = new RoleServiceModel();
        roleServiceModel.setName("ROLE_ADMIN");

        UserServiceModel userServiceModel = new UserServiceModel();
        userServiceModel.setUsername(user.getUsername());
        userServiceModel.setPassword(user.getPassword());
        userServiceModel.setEmail(user.getEmail());
        userServiceModel.setRole(roleServiceModel);

        when(mockRoleRepository.findByName("ROLE_ADMIN"))
                .thenReturn(Optional.of(adminRole));


        UserServiceModel actualUser = serviceToTest.registerUser(userServiceModel);

        Assertions.assertEquals(userServiceModel.getUsername(), actualUser.getUsername());
        Assertions.assertEquals(userServiceModel.getPassword(), actualUser.getPassword());

    }
}
