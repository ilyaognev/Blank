package app.controller;

import app.model.Post;
import app.model.Role;
import app.model.User;
import app.service.PostService;
import app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;
import java.util.Optional;

@Controller
@SessionAttributes("post") //important
public class PostController {
    private final PostService postService;
    private final UserService userService;

    @Autowired
    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String displayAllPosts(Model model) {

        Collection<Post> posts = postService.getAll();
        model.addAttribute("posts", posts);
        return "index";
    }

    @GetMapping("/post/{id}")
    public String getPost(@PathVariable Long id, Model model, Principal principal) {

        Optional<Post> optionalPost = postService.getById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            model.addAttribute("post", post);
            if (isPrincipalOwnerOfPostOrAdmin(principal, post)) {
                model.addAttribute("isOwner", true);
            }
            return "post";
        } else {
            return "404";
        }
    }

    @Secured("ROLE_USER")
    @GetMapping("/postForm")
    public String createNewPost(Model model, Principal principal) {
        Optional<User> user = userService.findUserByUsername(principal.getName());
        if (user.isPresent()) {
            Post post = new Post();
            post.setUser(user.get());
            model.addAttribute("post", post);
            return "postForm";
        } else {
            return "error";
        }
    }

    @Secured("ROLE_USER")
    @PostMapping("/postForm")
    public String createNewPost(@Valid Post post, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "postForm";
        }
        postService.save(post);
        return "redirect:/post/" + post.getId();
    }

    @Secured("ROLE_USER")
    @GetMapping("editPost/{id}")
    public String editPost(@PathVariable Long id, Model model, Principal principal) {

        Optional<Post> optionalPost = postService.getById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            if (isPrincipalOwnerOfPostOrAdmin(principal, post)) {
                model.addAttribute("post", post);
                return "postForm";
            } else {
                return "403";
            }
        } else {
            return "error";
        }
    }

    @Secured("ROLE_USER")
    @GetMapping("/deletePost/{id}")
    public String deletePost(@PathVariable Long id, Principal principal) {

        Optional<Post> optionalPost = postService.getById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            if (isPrincipalOwnerOfPostOrAdmin(principal, post)) {
                postService.delete(post);
                return "redirect:/";
            } else {
                return "403";
            }
        } else {
            return "error";
        }
    }

    private boolean isPrincipalOwnerOfPostOrAdmin(Principal principal, Post post) {

        if (principal == null) {
            return false;
        }

        Optional<User> user = userService.findUserByUsername(principal.getName());
        if (user.isPresent() && user.get().getRoles().contains(Role.ADMIN)) {
            return true;
        } else {
            return principal.getName().equals(post.getUser().getUsername());
        }
    }
}
