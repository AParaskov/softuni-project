package gamestore.web;

import gamestore.model.binding.RoleAddBindingModel;
import gamestore.service.RoleService;
import gamestore.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public RoleController(RoleService roleService, UserService userService, ModelMapper modelMapper) {
        this.roleService = roleService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/add")
    public ModelAndView add(@Valid @ModelAttribute("roleAddBindingModel") RoleAddBindingModel roleAddBindingModel,
                            ModelAndView modelAndView) {

        modelAndView.addObject("role", this.roleService
                .findAllRoles());
        modelAndView.addObject("usernames", this.userService
                .findAllUsernames());
        modelAndView.setViewName("add-role");
        return modelAndView;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public String addConfirm(@Valid @ModelAttribute("roleAddBindingModel") RoleAddBindingModel roleAddBindingModel) {

        this.userService
                .addRoleToUser(roleAddBindingModel.getUsername(), roleAddBindingModel.getRole());

        return "redirect:/home";
    }
}
