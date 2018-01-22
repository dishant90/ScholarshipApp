package com.tripleS.controller;

import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.tripleS.util.GenericResponse;
import com.tripleS.service.NotificationService;
import com.tripleS.service.UserSecurityService;
import com.tripleS.dto.PasswordDto;
import com.tripleS.dto.UserDTO;
import com.tripleS.exception.UserNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tripleS.model.User;
import com.tripleS.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
    private Environment env;
	
	@Autowired
    private MessageSource messages;
	
	@Autowired
    private JavaMailSender mailSender;
	
	@Autowired
    private UserSecurityService userSecurityService;
	
	@Autowired
	private NotificationService notifyService;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView getUser() {
		ModelAndView modelAndView = new ModelAndView();
		final org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext()
	            .getAuthentication()
	            .getPrincipal();
		if(user != null) {
			logger.info("Logged in user's email id: " + user.getUsername());
			User loggedInUser = userService.findUserByEmailID(user.getUsername());
			UserDTO userDTO = new UserDTO();
			userDTO.setFirstName(loggedInUser.getFirstName());
			userDTO.setLastName(loggedInUser.getLastName());
			userDTO.setEmailID(loggedInUser.getEmailID());
			modelAndView.addObject("userDTO", userDTO);
			modelAndView.setViewName("userProfile");
		} else {
			SecurityContextHolder.getContext().setAuthentication(null);
			modelAndView.setViewName("redirect:/logout");
		}
		return modelAndView;
	}

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public ModelAndView registration() {
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("registration");
		return modelAndView;
	}
	
	// Reset password
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    @ResponseBody
    public GenericResponse resetPassword(final HttpServletRequest request, @RequestParam("email") final String userEmail) {
    	logger.info("Requested password reset for user with email id: " + userEmail);
        final User user = userService.findUserByEmailID(userEmail);
        if (user != null) {
            final String token = UUID.randomUUID()
                .toString();
            userService.createPasswordResetTokenForUser(user, token);
            mailSender.send(constructResetTokenEmail(getAppUrl(request), request.getLocale(), token, user));
            return new GenericResponse(messages.getMessage("message.resetPasswordEmail", null, request.getLocale()));
        } else {
        	throw new UserNotFoundException();
        }
        
    }
    
    @RequestMapping(value = "/updatePassword", method = RequestMethod.GET)
    //public String showChangePasswordPage(Locale locale, Model model,
    public String showChangePasswordPage(Locale locale, @RequestParam("id") long id, 
    		@RequestParam("token") String token, RedirectAttributes redirectAttributes) {
        String result = userSecurityService.validatePasswordResetToken(id, token);
        if (result != null) {
        	redirectAttributes.addFlashAttribute("message", 
              messages.getMessage("auth.message." + result, null, locale));
            logger.info("Token invalid message: " + messages.getMessage("auth.message." + result, null, locale));
            return "redirect:/login?lang=" + locale.getLanguage();
        }
        logger.info("Token validated successfully");
        return "updatePassword";
    }
    
    @RequestMapping(value = "/savePassword", method = RequestMethod.POST)
    @ResponseBody
    public GenericResponse savePassword(final Locale locale, @Valid PasswordDto passwordDto) {
    	logger.info("Passport save request");
        final User user = (User) SecurityContextHolder.getContext()
            .getAuthentication()
            .getPrincipal();
        if(user != null) {
        	logger.info("Password save request for " + user.getFirstName());
        	userService.changeUserPassword(user, passwordDto.getNewPassword());
        	return new GenericResponse(messages.getMessage("message.resetPasswordSuccessful", null, locale));
        } else {
        	return new GenericResponse(messages.getMessage("message.resetPasswordFailure", null, locale));
        }
    }

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		User userExists = userService.findUserByEmailID(user.getEmailID());
		if (userExists != null) {
			bindingResult.rejectValue("email", "error.user",
					"There is already a user registered with the email id provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("registration");
		} else {
			userService.saveUser(user);
			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("registration");

		}
		return modelAndView;
	}
	
	@RequestMapping(value = "/updateProfile", method = RequestMethod.POST)
	public ModelAndView updateUserProfile(@Valid UserDTO userDTO, BindingResult bindingResult, Locale locale, RedirectAttributes redirectAttributes) {
		ModelAndView modelAndView = new ModelAndView();
		if (bindingResult.hasErrors()) {
			logger.error("Found validation errors while updating logged in user's profile");
			modelAndView.addObject("userDTO", userDTO);
			modelAndView.setViewName("userProfile");
		} else {
			final org.springframework.security.core.userdetails.User currentUser = (org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext()
		            .getAuthentication()
		            .getPrincipal();
			if(!currentUser.getUsername().equals(userDTO.getEmailID())){
				logger.info("Logged in user wants to change the email id, verify if the new one already exists in the database");
				User userExists = userService.findUserByEmailID(userDTO.getEmailID());
				if (userExists != null && !userExists.getEmailID().equals(currentUser.getUsername())) {
					logger.error("Email ID already exists in the db, please provide different one");
					bindingResult.rejectValue("emailID", "error.user",
							"There is already a user registered with the email id provided");
					modelAndView.addObject("userDTO", userDTO);
					modelAndView.setViewName("userProfile");
					return modelAndView;
				} else {
					logger.info("New email id doesn't exist in the db so can be changed");
				}
			}
			User user = userService.findUserByEmailID(currentUser.getUsername());
			user.setFirstName(userDTO.getFirstName());
			user.setLastName(userDTO.getLastName());
			user.setEmailID(userDTO.getEmailID());
			userService.updateUser(user);
			if(!currentUser.getUsername().equals(userDTO.getEmailID())){
				logger.info("Email id has changed, user would be logged out to login again");
				redirectAttributes.addFlashAttribute("message", 
			              messages.getMessage("message.emailIDChanged", null, locale));
				logger.info(
						userDTO.getFirstName() + "'s profile updated successfully!! You have been logged out of the system. Please login with the updated email id.");
				SecurityContextHolder.getContext().setAuthentication(null);
				//modelAndView.setViewName("redirect:/logout");
				modelAndView.setViewName("redirect:/login");
			} else {
				logger.info("User details other than email id has been updated");
				notifyService.addInfoMessage(
					userDTO.getFirstName() + "'s profile updated successfully!!");
				modelAndView.setViewName("userProfile");
			}
		}
		return modelAndView;
	}
	
	private SimpleMailMessage constructResetTokenEmail(final String contextPath, final Locale locale, final String token, final User user) {
        final String url = contextPath + "/user/updatePassword?id=" + user.getId() + "&token=" + token;
        final String message = messages.getMessage("message.resetPassword", null, locale);
        return constructEmail("Reset Password", message + " \r\n" + url, user);
    }
	
	private SimpleMailMessage constructEmail(String subject, String body, User user) {
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getEmailID());
        email.setFrom(env.getProperty("support.email"));
        return email;
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
