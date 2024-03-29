package app.controller;

import app.model.User;
import app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class AdminController {
    @Autowired
    UserService userService;

    @GetMapping("/register") //REDIRECT TO REGISTRATION
    public String showSignUpForm(User user) {
        return "add-user";
    }

    @PostMapping("/register") //DO REGISTRATION + redirect to index
    public String addUser(@Valid User user, BindingResult result) {
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

    @GetMapping("/list") //REDIRECT TO LIST OF USERS
    public String showList(Model model) {
        model.addAttribute("users", userService.allUsers());
        return "list";
    }

    @GetMapping("/edit/{id}") //REDIRECT TO EDIT FORM
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        User user = userService.findUserById(id);

        model.addAttribute("user", user);
        return "update-user";
    }

    @PutMapping("/update/{userId}")
    public String updateUser(@PathVariable("userId") long id, @Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            user.setId(id);
            return "redirect:/list";
        }

        userService.saveUser(user);
        return "redirect:/list";
    }

    @DeleteMapping("/delete/{userId}")
    public String deleteUser(@PathVariable("userId") long id) {
        userService.deleteUser(id);
        return "redirect:/list";
    }
}