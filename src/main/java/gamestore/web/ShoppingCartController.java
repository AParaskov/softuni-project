package gamestore.web;

import gamestore.model.binding.ShoppingCartProductAddBindingModel;
import gamestore.model.entity.ShoppingCart;
import gamestore.model.service.ShoppingCartProductServiceModel;
import gamestore.service.ShoppingCartService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/shopping-cart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;
    private final ModelMapper modelMapper;


    public ShoppingCartController(ShoppingCartService shoppingCartService, ModelMapper modelMapper) {
        this.shoppingCartService = shoppingCartService;
        this.modelMapper = modelMapper;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ModelAndView shopping_cart(ModelAndView modelAndView, HttpServletRequest httpServletRequest) {
        Principal principal = httpServletRequest.getUserPrincipal();
        ShoppingCart shoppingCart = shoppingCartService.findByUser(principal.getName());
        modelAndView.addObject("shoppingCartProducts", this.shoppingCartService.findAllShoppingCartProducts(shoppingCart));
        modelAndView.addObject("total", this.shoppingCartService.total(shoppingCart));
        modelAndView.setViewName("shopping-cart");
        return modelAndView;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("shoppingCartProductAddBindingModel") ShoppingCartProductAddBindingModel shoppingCartProductAddBindingModel,
                      BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("shoppingCartProductAddBindingModel", shoppingCartProductAddBindingModel);
            return "redirect:/details";
        }

        Principal principal = httpServletRequest.getUserPrincipal();
        ShoppingCart shoppingCart = shoppingCartService.findByUser(principal.getName());

        ShoppingCartProductServiceModel serviceModel = this.modelMapper.map(shoppingCartProductAddBindingModel, ShoppingCartProductServiceModel.class);
        serviceModel.setShoppingCart(shoppingCart);

        this.shoppingCartService.addProductToCart(serviceModel);
        return "redirect:/home";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/remove/{id}")
    public String delete(@PathVariable("id") String id) {
        this.shoppingCartService.remove(id);
        return "redirect:/shopping-cart";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/remove-all")
    public String delete() {
        this.shoppingCartService.removeAll();
        return "redirect:/home";
    }
}
