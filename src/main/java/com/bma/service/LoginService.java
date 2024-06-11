package com.bma.service;

import com.bma.dao.UserDao;
import com.bma.exception.DatabaseException;
import com.bma.exception.InvalidUserDataException;
import com.bma.model.User;
import com.bma.util.HashUtil;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public class LoginService {
    HashUtil hashUtil = HashUtil.getInstance();
    UserDao userDao = UserDao.getInstance();

    public User authentication(String login, String password) throws InvalidUserDataException, NoSuchAlgorithmException, DatabaseException {
        validateUserData(login, password);
        String hashedPassword = hashUtil.hashPassword(password);

        Optional<User> user = userDao.getUserByLoginAndPassword(login, hashedPassword);

        if (user.isEmpty()) {
            throw new InvalidUserDataException("Incorrect login or password");

        }
        return user.get();
    }

    private void validateUserData(String login, String password) throws InvalidUserDataException {
        if (login.isEmpty() || password.isEmpty()) {
            throw new InvalidUserDataException("Fill in all the fields");
        }
    }

    private LoginService() {
    }

    private static final LoginService INSTANCE = new LoginService();

    public static LoginService getInstance() {
        return INSTANCE;
    }
}
