package gamestore.RoleTest;

import gamestore.model.entity.Role;
import gamestore.model.service.RoleServiceModel;
import gamestore.repository.RoleRepository;
import gamestore.service.RoleService;
import gamestore.service.RoleServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {
    private RoleService serviceToTest;
    private Role role;

    @Mock
    private RoleRepository roleRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    public void setUp() {

        role = new Role();
        role.setName("ADMIN");
        role.setId("UUID");


        serviceToTest = new RoleServiceImpl(roleRepository, modelMapper);

    }

    @Test
    public void testFindAllRoles() {
        when(roleRepository.findAll()).
                thenReturn(List.of(role));

        List<String> roles = serviceToTest.findAllRoles();

        Assertions.assertEquals(1, roles.size());
        String actualName = roles.get(0);
        Assertions.assertEquals(role.getName(), actualName);

    }

    @Test
    public void testFindRoleByName() {
        when(roleRepository.findByName("ADMIN")).
                thenReturn(Optional.of(role));

        RoleServiceModel roleByName = serviceToTest.findByName("ADMIN");

        Assertions.assertEquals(role.getName(), roleByName.getName());

    }

}
