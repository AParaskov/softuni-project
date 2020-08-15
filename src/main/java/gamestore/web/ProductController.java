package gamestore.web;

import gamestore.model.binding.ProductAddBindingModel;
import gamestore.model.binding.ProductUpdateBindingModel;
import gamestore.model.binding.ShoppingCartProductAddBindingModel;
import gamestore.model.service.ProductServiceModel;
import gamestore.model.view.ProductViewModel;
import gamestore.service.ProductService;
import gamestore.service.ReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final ModelMapper modelMapper;
    private ReviewService reviewService;

    public ProductController(ProductService productService, ModelMapper modelMapper) {
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping
    public ModelAndView products(ModelAndView modelAndView) {
        modelAndView.addObject("products", this.productService.findAllProducts());
        modelAndView.setViewName("products");

        return modelAndView;
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/add")
    public String add(Model model) {
        if (!model.containsAttribute("productAddBindingModel")) {
            model.addAttribute("productAddBindingModel", new ProductAddBindingModel());
        }
        return "add-product";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public String addConfirm(@Valid @ModelAttribute("productAddBindingModel") ProductAddBindingModel productAddBindingModel,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("productAddBindingModel", productAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.productAddBindingModel", bindingResult);
            return "redirect:add";
        } else {

            this.productService
                    .addProduct(this.modelMapper.map(productAddBindingModel, ProductServiceModel.class));

            return "redirect:/home";
        }

    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/details")
    public ModelAndView details(@RequestParam("id") String id, ModelAndView modelAndView) {
        ProductViewModel product = this.productService.findById(id);
        ShoppingCartProductAddBindingModel shoppingCartBindingModel = new ShoppingCartProductAddBindingModel();
        shoppingCartBindingModel.setProductId(product.getId());

        modelAndView.addObject("product", product);
//        modelAndView.addObject("reviews", this.reviewService.findByProductId(id));
        modelAndView.addObject("shoppingCartProductAddBindingModel", shoppingCartBindingModel);
        modelAndView.setViewName("details");
        return modelAndView;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id) {
        this.productService.delete(id);
        return "redirect:/home";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/update")
    public ModelAndView update(@Valid @ModelAttribute("productUpdateBindingModel")
                                       ProductUpdateBindingModel productUpdateBindingModel,
                               BindingResult bindingResult, ModelAndView modelAndView) {

        modelAndView.addObject("productUpdateBindingModel", productUpdateBindingModel);
        modelAndView.setViewName("update-product");
        return modelAndView;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/update")
    public String updateConfirm(@Valid @ModelAttribute("productUpdateBindingModel") ProductUpdateBindingModel productUpdateBindingModel,
                                BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("productUpdateBindingModel", productUpdateBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.productUpdateBindingModel", bindingResult);
            return "redirect:update";
        } else {

            this.productService
                    .updateProduct(productUpdateBindingModel.getName(), productUpdateBindingModel.getQuantity());

            return "redirect:/home";
        }

    }

}
