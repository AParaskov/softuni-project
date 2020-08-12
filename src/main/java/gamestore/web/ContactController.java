package gamestore.web;

import gamestore.model.binding.ContactAddBindingModel;
import gamestore.model.service.ContactServiceModel;
import gamestore.service.ContactService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/contact")
public class ContactController {
    private final ContactService contactService;
    private final ModelMapper modelMapper;

    public ContactController(ContactService contactService, ModelMapper modelMapper) {
        this.contactService = contactService;
        this.modelMapper = modelMapper;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ModelAndView contact(@Valid @ModelAttribute("contactAddBindingModel")
                                        ContactAddBindingModel contactAddBindingModel, ModelAndView modelAndView, HttpServletRequest httpServletRequest) {
        Principal principal = httpServletRequest.getUserPrincipal();
        contactAddBindingModel.setUserName(principal.getName());
        modelAndView.setViewName("contact");
        return modelAndView;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/add")
    public String addConfirm(@Valid @ModelAttribute("contactAddBindingModel") ContactAddBindingModel contactAddBindingModel,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("contactAddBindingModel", contactAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.contactAddBindingModel", bindingResult);
            return "redirect:/home";
        } else {

            this.contactService.addContact(this.modelMapper.map(contactAddBindingModel, ContactServiceModel.class));

            return "redirect:/home";
        }
    }
}