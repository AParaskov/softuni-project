package gamestore.web;


import gamestore.model.binding.UserAddBindingModel;
import gamestore.model.binding.UserLoginBindingModel;
import gamestore.model.service.UserServiceModel;
import gamestore.service.ProductService;
import gamestore.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final ProductService productService;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper, ProductService productService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.productService = productService;
    }


    @GetMapping("/login")
    public ModelAndView login(@Valid @ModelAttribute("userLoginBindingModel")
                                      UserLoginBindingModel userLoginBindingModel,
                              BindingResult bindingResult, ModelAndView modelAndView) {
        modelAndView.addObject("userLoginBindingModel", userLoginBindingModel);
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @PostMapping("/login-error")
    public ModelAndView loginError(@ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
                                           String username,
                                   ModelAndView modelAndView) {


        modelAndView.addObject("notFound", "bad.credentials");
        modelAndView.addObject("username", username);
        modelAndView.setViewName("login");


        return modelAndView;

    }

    @GetMapping("/register")
    public String register(@Valid @ModelAttribute("userAddBindingModel")
                                   UserAddBindingModel userAddBindingModel,
                           BindingResult bindingResult) {
        return "register";
    }

    @PostMapping("/register")
    public String registerConfirm(@Valid @ModelAttribute("userAddBindingModel")
                                          UserAddBindingModel userAddBindingModel,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors() || !userAddBindingModel
                .getPassword().equals(userAddBindingModel.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("userAddBindingModel", userAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userAddBindingModel", bindingResult);
            return "redirect:register";
        } else {
            this.userService
                    .registerUser(this.modelMapper.map(userAddBindingModel, UserServiceModel.class));
            return "redirect:login";
        }

    }

}
