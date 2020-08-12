package gamestore.service;

import gamestore.model.entity.Role;
import gamestore.model.entity.User;
import gamestore.model.service.UserServiceModel;
import gamestore.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleService roleService, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserServiceModel registerUser(UserServiceModel userServiceModel) {
        userServiceModel
                .setRole(this.roleService.findByName(this.userRepository.count() == 0 ? "ROLE_ADMIN" : "ROLE_USER"));

        userServiceModel.setPassword(bCryptPasswordEncoder.encode(userServiceModel.getPassword()));

        User user = this.modelMapper
                .map(userServiceModel, User.class);

        this.userRepository.saveAndFlush(user);


        return this.modelMapper
                .map(user, UserServiceModel.class);
    }

    @Override
    public UserServiceModel findByUsername(String username) {
        return this.userRepository
                .findByUsername(username)
                .map(user -> this.modelMapper
                        .map(user, UserServiceModel.class))
                .orElse(null);
    }

    @Override
    public List<String> findAllUsernames() {
        return this.userRepository
                .findAll()
                .stream()
                .map(User::getUsername)
                .collect(Collectors.toList());
    }


    @Override
    public void addRoleToUser(String username, String role) {
        User user = this.userRepository
                .findByUsername(username)
                .orElse(null);


        if (!user.getRole().getName().equals(role)) {
            Role roleEntity = this.modelMapper
                    .map(this.roleService.findByName(role), Role.class);

            user.setRole(roleEntity);
            this.userRepository.saveAndFlush(user);
        }
    }

}
