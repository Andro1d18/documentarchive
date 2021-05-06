package org.zhezlov.documentarchive.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.zhezlov.documentarchive.exception.UserAlreadyExistException;
import org.zhezlov.documentarchive.model.User;
import org.zhezlov.documentarchive.model.VerificationToken;
import org.zhezlov.documentarchive.registration.OnRegistrationCompleteEvent;
import org.zhezlov.documentarchive.service.DocumentService;
import org.zhezlov.documentarchive.service.SecurityService;
import org.zhezlov.documentarchive.service.UserService;
import org.zhezlov.documentarchive.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Locale;


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

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    @Qualifier("messageSource")
    private MessageSource messages;

    @GetMapping("/registration")
    public String registration(Model model) {
        LOG.debug("start registration");
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public ModelAndView  registration(
            @ModelAttribute("userForm") User userForm,
            BindingResult bindingResult,
            Model model, HttpServletRequest request) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return new ModelAndView("registration", "user", userForm);
//            return "registration";
        }
        try {
        User registered = userService.registerNewUserAccount(userForm);
// остановился тут. Изменил свой контроллер чтобы включить активацию по емэйлу. Спйчас ошибка с отсутствием русской локали. Либо захардкодь, либо сделай ru локаль
        String appUrl = request.getContextPath();
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered,
                request.getLocale(), appUrl));
        } catch (UserAlreadyExistException uaeEx) {
            ModelAndView mav = new ModelAndView("registration", "user", userForm);
            mav.addObject("message", "An account for that username/email already exists.");
            return mav;
//            return "redirect:/welcome";
        }
//        catch (RuntimeException ex) {
////            return new ModelAndView("emailError", "user", userForm);
//            return "redirect:/welcome";
//        }
        return new ModelAndView("registrationSuccess", "user", userForm);
//        return "registrationSuccess";

//        User savedUser = userService.save(userForm);
//        User getUser = userService.getUser(savedUser);
//
//        if (!savedUser.equals(getUser)) {
//            bindingResult.rejectValue("username", "We have problem with create new user. We have already started solving. Please try again later.");
//            return "registration";
//        }
//
//        securityService.autoLogin(userForm.getUsername(), userForm.getConfirmPassword());
//        LOG.debug("registration complete, redirect to welcome.jsp");
//        return "redirect:/welcome";
    }
    @GetMapping("/regitrationConfirm")
    public String confirmRegistration
            (WebRequest request, Model model, @RequestParam("token") String token) {

        Locale locale = request.getLocale();

        VerificationToken verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null) {
            String message = messages.getMessage("auth.message.invalidToken", null, locale);
            model.addAttribute("message", message);
            return "invalidUser";
        }

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            String messageValue = messages.getMessage("auth.message.expired", null, locale);
            model.addAttribute("message", messageValue);
            return "invalidUser";
        }

        user.setEnabled(true);
        userService.saveRegisteredUser(user);
        return "registrationConfirm";
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
        model.addAttribute("documents", documentService.findAllDocumentsWithAnyUserGrants());
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
