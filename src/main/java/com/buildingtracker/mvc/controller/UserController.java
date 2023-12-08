package com.buildingtracker.mvc.controller;

import com.buildingtracker.mvc.model.user.Role;
import com.buildingtracker.mvc.model.user.User;
import com.buildingtracker.mvc.service.user.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @GetMapping("/users")
    String getUsers(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "user/list_user.html";
    }

    @GetMapping("/user")
    String getUser(Model model, @RequestParam(required = false) Long id, @RequestParam(required = false) Boolean update) {
        User user = (id != null) ? userService.findById(id) : userService.getAuthUser();
        model.addAttribute("user", user);
        model.addAttribute("updated", update);
        return "user/details_user.html";
    }

    @GetMapping("/user/edit")
    String getEdit(Model model, @RequestParam(required = false) Long id) {
        User user = userService.findById(id);
        List<Role> roles = userService.findAllRoles();

        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return "user/edit_user.html";
    }

    @GetMapping("/user/add")
    String getEdit(Model model) {
        List<Role> roles = userService.findAllRoles();
        model.addAttribute("roles", roles);
        return "user/add_user.html";
    }

    @PostMapping("/user/save")
    String saveEdit(Model model, @RequestParam(required = false) Long id, @RequestParam int roleId, @RequestParam String login,
                    @RequestParam String name, @RequestParam String email, @RequestParam(required = false) String password) {
        Role role = userService.findRoleById(roleId);
        User user;
        if (id != null) {
            user = userService.findById(id);
            user.setLogin(login);
            user.setName(name);
            user.setEmail(email);
            user.setRole(role);
        } else {
            String passwordEncode = bCryptPasswordEncoder().encode(password);
            user = new User(login, name, passwordEncode, email, role);
        }
        boolean isUpdate = userService.update(user);

        model.addAttribute("user", user);
        return "redirect:/user?id=" + user.getId() + "&update=" + isUpdate;
    }

    @GetMapping("/login")
    String login() {
        return "user/login.html";
    }

    @GetMapping("/user/password")
    String editUserPassword(Model model, @RequestParam Long id) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "user/edit_password.html";
    }

    @PostMapping("/user/password/save")
    String saveUserPassword(@RequestParam Long id, @RequestParam String password) {
        User user = userService.findById(id);
        boolean isUpdate = false;
        if (user != null) {
            user.setPassword(bCryptPasswordEncoder().encode(password));
            isUpdate = userService.update(user);
        }
        return "redirect:/user?id=" + user.getId() + "&update=" + isUpdate;
    }

    @DeleteMapping("/user/delete")
    ResponseEntity<String> deleteUser(@RequestParam Long id) {
        User loggedUser = userService.getAuthUser();
        User user = userService.findById(id);
        boolean isDeleted = false;
        if (user != loggedUser)
            isDeleted = userService.delete(user);

        if (isDeleted) {
            return ResponseEntity.ok("Deleted success");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Deleted fail");
        }
    }
}
