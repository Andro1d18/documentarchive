package org.zhezlov.documentarchive.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.zhezlov.documentarchive.model.User;
import org.zhezlov.documentarchive.service.DocumentService;
import org.zhezlov.documentarchive.service.SecurityService;
import org.zhezlov.documentarchive.service.UserService;
import org.zhezlov.documentarchive.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;


@Controller
public class UserController {
    public static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private DocumentService documentService;

    @GetMapping("/registration")
    public String registration(Model model) {
        LOG.debug("start registration");
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        User savedUser = userService.save(userForm);
        User getUser = userService.getUser(savedUser);

        if (!savedUser.equals(getUser)) {
            bindingResult.rejectValue("username", "We have problem with create new user. We have already started solving. Please try again later.");
            return "registration";
        }

        securityService.autoLogin(userForm.getUsername(), userForm.getConfirmPassword());
        LOG.debug("registration complete, redirect to welcome.jsp");
        return "redirect:/welcome";


    }

    @GetMapping(value = "/login")
    public String login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "Username or password is incorrect.");
        }

        if (logout != null) {
            model.addAttribute("message", "Logged out successfully.");
        }

        return "login";
    }

    @GetMapping(value = {"/", "/welcome"})
    public String welcome(Model model) {
        LOG.debug("log in successful");
        model.addAttribute("documents", documentService.getAll());
        return "welcome";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        return "admin";
    }

    @GetMapping("/accessDenied")
    public String accessDenied(Model model){
        return "accessDenied";
    }
}
