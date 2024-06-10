package com.bma;

import com.bma.dao.UserDao;
import com.bma.exception.DatabaseException;
import com.bma.exception.InvalidUserDataException;
import com.bma.model.User;
import com.bma.service.RegistrationService;
import com.bma.util.HashUtil;
import com.bma.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public class RegistrationServiceTest {
    private static RegistrationService registrationService;
    private static UserDao userDao;
    private static HashUtil hashUtil;

    @BeforeAll
    public static void InitSources(){
        registrationService = RegistrationService.getInstance();
        userDao = UserDao.getInstance();
        hashUtil = HashUtil.getInstance();
    }


    @Test
    public void AfterRegistrationUserAddedToDatabase() throws InvalidUserDataException, NoSuchAlgorithmException, DatabaseException {
        String login = "login";
        String password = "password";
        String hashedPassword = hashUtil.hashPassword(password);

        registrationService.saveUser(login,password,password);

        Optional<User> user = userDao.getUserByLoginAndPassword(login, hashedPassword);
        Assertions.assertTrue(user.isPresent());
    }

    @Test
    public void RegistrationUsingNonUniqueLoginThrowsException() throws NoSuchAlgorithmException, InvalidUserDataException, DatabaseException {

        String login = "Login";
        String password = "password";

        registrationService.saveUser(login,password,password);

        String login2 = "Login";
        String password2 = "password2";

        Assertions.assertThrows(InvalidUserDataException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                registrationService.saveUser(login2, password2,password2);
            }
        });
    }
}
