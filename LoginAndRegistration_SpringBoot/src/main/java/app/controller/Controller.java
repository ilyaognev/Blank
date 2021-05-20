package app.controller;

import app.entity.User;
import app.service.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class Controller {
    UserServiceImpl userService = new UserServiceImpl();

    @RequestMapping("/")
    ModelAndView main() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index.html");
        return modelAndView;
    }

    @RequestMapping("/loginForm")
    ModelAndView loginForm() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("loginForm.html");
        return modelAndView;
    }

    @RequestMapping("/registrationForm")
    ModelAndView registrationForm() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("registrationForm.html");
        return modelAndView;
    }

    @GetMapping("/login")
    String login(@RequestParam String login, @RequestParam String password) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        return userService.login(user);
    }

    @GetMapping("/registration")
    String registration(@RequestParam String name,@RequestParam String surname, @RequestParam String login,@RequestParam String password) {
        User user = new User();
        user.setName(name);
        user.setSurname(surname);
        user.setLogin(login);
        user.setPassword(password);
        return userService.registration(user);
    }
}
