package com.tripleS.service;

import java.util.Arrays;
import java.util.HashSet;

import com.tripleS.model.PasswordResetToken;

import com.tripleS.repository.PasswordResetTokenRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tripleS.enums.RoleEnum;
import com.tripleS.model.Role;
import com.tripleS.model.User;
import com.tripleS.repository.RoleRepository;
import com.tripleS.repository.UserRepository;

@Service("userService")
public class UserServiceImpl implements UserService{

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private PasswordResetTokenRepository passwordTokenRepository;
	
	@Override
	public User findUserByEmailID(String emailID) {
		return userRepository.findByEmailID(emailID);
	}

	@Override
	public void saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByName(RoleEnum.APPLICANT);
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		userRepository.save(user);
		logger.info("User saved in the db: " + user.toString());
	}
	
	@Override
	public void updateUser(User user) {
		userRepository.save(user);
		logger.info("User saved in the db: " + user.toString());
	}

	@Override
	public void createPasswordResetTokenForUser(User user, String token) {
		final PasswordResetToken myToken = new PasswordResetToken(token, user);
	    passwordTokenRepository.save(myToken);
	    logger.info("Password reset token saved in the db: " + myToken.toString());
	}

	@Override
    public void changeUserPassword(final User user, final String password) {
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userRepository.save(user);
        logger.info("Password changed successfully");
    }
	
	@Override
    public boolean checkIfValidOldPassword(final User user, final String oldPassword) {
        return bCryptPasswordEncoder.matches(oldPassword, user.getPassword());
    }
}
