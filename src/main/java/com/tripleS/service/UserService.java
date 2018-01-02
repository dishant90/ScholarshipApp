package com.tripleS.service;

import com.tripleS.model.User;

public interface UserService {
	public User findUserByEmailID(String emailID);
	public void saveUser(User user);
	public void createPasswordResetTokenForUser(User user, String token);
	public void changeUserPassword(User user, String password);
}
