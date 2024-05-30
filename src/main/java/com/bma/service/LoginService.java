package com.bma.service;

import com.bma.dao.UserDao;
import com.bma.exception.DatabaseException;
import com.bma.exception.InvalidUserDataException;
import com.bma.model.User;
import com.bma.util.HashUtil;

import java.security.NoSuchAlgorithmException;

public class LoginService {
    HashUtil hashUtil = HashUtil.getInstance();
    UserDao userDao = UserDao.getInstance();

    public void authentication(String login, String password) throws InvalidUserDataException, NoSuchAlgorithmException, DatabaseException {
        validateUserData(login, password);
        String hashedPassword = hashUtil.hashPassword(password);

        User user = userDao.getUserByLoginAndPassword(login, hashedPassword);

        if (user == null){
            throw new InvalidUserDataException("Incorrect login or password");
        }


    }

    private void validateUserData(String login, String password) throws InvalidUserDataException {
        if (login.isEmpty() || password.isEmpty()){
            throw new InvalidUserDataException("Fill in all the fields");
        }
    }


    private LoginService(){}
    private static final LoginService INSTANCE = new LoginService();
    public static LoginService getInstance(){
        return INSTANCE;
    }
}
