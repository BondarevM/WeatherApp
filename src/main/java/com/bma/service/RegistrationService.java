package com.bma.service;

import com.bma.dao.UserDao;
import com.bma.exception.DatabaseException;
import com.bma.exception.InvalidUserDataException;
import com.bma.model.User;
import com.bma.util.HashUtil;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RegistrationService {
    private static final UserDao userDao = UserDao.getInstance();
    private static final HashUtil hashUtil = HashUtil.getInstance();

    public void saveUser(String login, String password, String confirmedPassword) throws InvalidUserDataException, NoSuchAlgorithmException, DatabaseException {
        validateUserData(login,password,confirmedPassword);
        String hashedPassword = hashUtil.hashPassword(password);


        User user = User.builder()
                .login(login)
                .password(hashedPassword)
                .build();

        userDao.save(user);
    }

    public void validateUserData(String login, String password, String confirmedPassword) throws InvalidUserDataException {
        if (login.isEmpty() || password.isEmpty() || confirmedPassword.isEmpty()){
            throw new InvalidUserDataException("Fill in all the fields");
        }

        if (!password.equals(confirmedPassword)){
            throw new InvalidUserDataException("Password mismatch");
        }

        if (password.length() < 4){
            throw new InvalidUserDataException("Password must contain at least 4 characters");
        }
    }




    public static RegistrationService getInstance(){
        return INSTANCE;
    }

    private RegistrationService(){}
    private static final RegistrationService INSTANCE = new RegistrationService();
}
