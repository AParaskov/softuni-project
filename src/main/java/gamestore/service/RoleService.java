package gamestore.service;

import gamestore.model.service.RoleServiceModel;

import java.util.List;

public interface RoleService {
    RoleServiceModel findByName(String name);

    List<String> findAllRoles();
}
