package com.tripleS.service;

public interface UserSecurityService {

    String validatePasswordResetToken(long id, String token);

}
