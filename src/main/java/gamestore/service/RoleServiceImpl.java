package gamestore.service;

import gamestore.model.entity.Role;
import gamestore.model.service.RoleServiceModel;
import gamestore.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public void init() {
        if (this.roleRepository.count() == 0) {
            Role admin = new Role("ROLE_ADMIN");
            Role user = new Role("ROLE_USER");

            this.roleRepository.save(admin);
            this.roleRepository.save(user);
        }
    }

    @Override
    public RoleServiceModel findByName(String name) {
        return this.roleRepository
                .findByName(name)
                .map(role -> this.modelMapper.map(role, RoleServiceModel.class))
                .orElse(null);
    }

    @Override
    public List<String> findAllRoles() {
        return this.roleRepository
                .findAll()
                .stream()
                .map(Role::getName)
                .collect(Collectors.toList());
    }
}
