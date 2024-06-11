package com.bma;

import com.bma.dao.SessionDao;
import com.bma.dao.UserDao;
import com.bma.exception.DatabaseException;
import com.bma.exception.InvalidUserDataException;
import com.bma.model.Session;
import com.bma.model.User;
import com.bma.service.RegistrationService;
import com.bma.service.SessionService;
import com.bma.util.HashUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class LoginServiceTest {
    private static RegistrationService registrationService;
    private static SessionDao sessionDao;
    private static  SessionService sessionService;
    private static UserDao userDao;
    private static HashUtil hashUtil;

    @BeforeAll
    public static void InitSources(){
        registrationService = RegistrationService.getInstance();
        userDao = UserDao.getInstance();
        hashUtil = HashUtil.getInstance();
        sessionService = SessionService.getInstance();
        sessionDao = SessionDao.getInstance();
    }

    @Test
    public void ExpireSessionIfCurrentTimeGreaterThanSessionExpiresTime() throws InvalidUserDataException, NoSuchAlgorithmException, DatabaseException, InterruptedException {
        String login = "login3";
        String password = "password";
        String hashedPassword = hashUtil.hashPassword(password);

        registrationService.saveUser(login,password,password);
        Optional<User> user = userDao.getUserByLoginAndPassword(login, hashedPassword);

        Session session = Session.builder()
                .id(UUID.randomUUID().toString())
                .user(user.get())
                .expiresAt(LocalDateTime.now().minusHours(1))
                .build();

        List<Session> sessions = null;

        sessionService.saveSession(session);
        sessions = sessionDao.findAll();
        Assertions.assertFalse(sessions.isEmpty());

        sessionService.validateAllSessions();

        sessions = sessionDao.findAll();
        Assertions.assertTrue(sessions.isEmpty());
    }
}
