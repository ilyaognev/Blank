package app.controller;

import app.model.User;
import app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.management.relation.RoleNotFoundException;
import javax.validation.Valid;

@Controller
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@Valid User user, BindingResult result, Model model) throws RoleNotFoundException {
        if (userService.findUserByUsername(user.getUsername()).isPresent()) {
            result.rejectValue("username", "error.username", "Username is already registered");
        }
        if (result.hasErrors()) {
            return "registration";
        }

        userService.saveUser(user);
        return "redirect:/";
    }
}
