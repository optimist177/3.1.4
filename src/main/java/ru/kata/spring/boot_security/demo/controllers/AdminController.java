package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;
import java.util.List;

@Controller
public class AdminController {


    private final UserService userService;

    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService,
                           RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public String getUserLsit(Model model) {
        List<User>  userList = userService.allUsers();
        model.addAttribute("userList",userList);
        return "/list";
    }


    @GetMapping("/{id}")
    public String getByID(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findUserBYId(id));
        return "userPage";
    }


    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/admin")
    public String adminPage(Principal principal,Model model) {
        User user  = userService.findByUsername(principal.getName());
        List<User>  users = userService.allUsers();
        model.addAttribute("newuser",new User());
        model.addAttribute("user",user);
        model.addAttribute("users",users);
        model.addAttribute("role",roleService.listRoles());
        return "/admin";
    }


    @GetMapping("/edit/{id}")
        public String edit(@PathVariable("id") Long id, Model model ) {
        model.addAttribute("user", userService.findUserBYId(id));
        return "/edit";
    }


    @PatchMapping("/update/{id}")
    public String update(@ModelAttribute User user, @PathVariable("id") Long id) {
        userService.updateUser(id,user);
        return "redirect:/admin";
    }

    @PostMapping("/newupdate/{id}")
    public String newUpdate(@ModelAttribute User user, @RequestParam("listRoles") List<Long> listRoles) {

        userService.newUpdateUser(user,roleService.getRolesListById(listRoles));
        return "redirect:/admin";
    }

        @PostMapping("/registration")
        public String performRegister(@ModelAttribute("newuser") User user,
                                      @RequestParam("listRoles") Long[] rolesId) {
            for  (Long id : rolesId ) {
                user.addRole(roleService.getRole(id));
            }
            userService.saveUser(user);
            return "redirect:/admin";
    }

}
