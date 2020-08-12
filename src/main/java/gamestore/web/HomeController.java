package gamestore.web;

import gamestore.service.ProductService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    private final ProductService productService;

    public HomeController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public ModelAndView index(ModelAndView modelAndView) {
        modelAndView.addObject("products", this.productService.findAllProducts());
        modelAndView.setViewName("/index");

        return modelAndView;
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/home")
    public ModelAndView home(ModelAndView modelAndView) {

        modelAndView.setViewName("/home");
        return modelAndView;
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/home")
    public String homePost() {
        return "redirect:/home";
    }


}
