package org.zhezlov.documentarchive.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zhezlov.documentarchive.model.User;
import org.zhezlov.documentarchive.service.DocumentService;
import org.zhezlov.documentarchive.service.SecurityService;
import org.zhezlov.documentarchive.service.UserService;
import org.zhezlov.documentarchive.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


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

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        LOG.debug("start registration");
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(userForm); //toDo обработать ситуацию, когда первый сработает, а securityService не сработает (проверить сохранилось ли и затем залогинить)

        securityService.autoLogin(userForm.getUsername(), userForm.getConfirmPassword());
        LOG.debug("registration complete, redirect to welcome.jsp");
        return "redirect:/welcome";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "Username or password is incorrect.");
        }

        if (logout != null) {
            model.addAttribute("message", "Logged out successfully.");
        }

        return "login";
    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        LOG.debug("log in successful");
        model.addAttribute("documents", documentService.getAll());
        return "welcome";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin(Model model) {
        return "admin";
    }
}
