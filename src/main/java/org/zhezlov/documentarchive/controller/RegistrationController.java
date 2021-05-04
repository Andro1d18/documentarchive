package org.zhezlov.documentarchive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.zhezlov.documentarchive.service.UserService;

@Controller
public class RegistrationController {
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private UserService userService;

//    @PostMapping("/user/registration")
//    public ModelAndView registerUserAccount(
//            @ModelAttribute("user") @Valid UserDto userDto,
//            HttpServletRequest request, Errors errors) {
//
//        try {
//            User registered = userService.registerNewUserAccount(userDto);
//
//            String appUrl = request.getContextPath();
//            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered,
//                    request.getLocale(), appUrl));
//        } catch (UserAlreadyExistException uaeEx) {
//            ModelAndView mav = new ModelAndView("registration", "user", userDto);
//            mav.addObject("message", "An account for that username/email already exists.");
//            return mav;
//        } catch (RuntimeException ex) {
//            return new ModelAndView("emailError", "user", userDto);
//        }
//
//        return new ModelAndView("successRegister", "user", userDto);
//    }
}
