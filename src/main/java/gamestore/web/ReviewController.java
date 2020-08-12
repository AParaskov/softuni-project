package gamestore.web;

import gamestore.model.binding.ReviewAddBindingModel;
import gamestore.model.service.ReviewServiceModel;
import gamestore.service.ReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;
    private final ModelMapper modelMapper;


    public ReviewController(ReviewService reviewService, ModelMapper modelMapper) {
        this.reviewService = reviewService;
        this.modelMapper = modelMapper;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/add")
    public ModelAndView add(@Valid @ModelAttribute("reviewAddBindingModel") ReviewAddBindingModel reviewAddBindingModel,
                            @RequestParam("product_id") String productId,
                            BindingResult bindingResult, ModelAndView modelAndView) {
        reviewAddBindingModel.setProductId(productId);
        modelAndView.addObject("reviewAddBindingModel", reviewAddBindingModel);
        modelAndView.setViewName("add-review");
        return modelAndView;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/add")
    public String addConfirm(@Valid @ModelAttribute("reviewAddBindingModel") ReviewAddBindingModel reviewAddBindingModel,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("reviewAddBindingModel", reviewAddBindingModel);
            return "redirect:/reviews/add";
        } else {
            this.reviewService
                    .addReview(this.modelMapper.map(reviewAddBindingModel, ReviewServiceModel.class));
            return "redirect:/home";
        }

    }
}
