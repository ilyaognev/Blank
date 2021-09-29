package app.controller;

import app.model.User;
import app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegistrationController {
    @Autowired
    UserService userService;

    @GetMapping("/signup") //REDIRECT TO REGISTRATION
    public String showSignUpForm(User user) {
        return "add-user";
    }

    @PostMapping("/signup") //DO REGISTRATION + redirect to index
    public String addUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-user";
        }

        try {
            if (userService.loadUserByUsername(user.getLogin()).getClass() == User.class) {
                return "add-user";
            }
        } catch (UsernameNotFoundException ignored) {
        }

        userService.saveUser(user);
        return "redirect:/";
    }
}
