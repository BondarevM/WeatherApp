package com.bma.service;

import com.bma.dao.SessionDao;
import com.bma.exception.DatabaseException;
import com.bma.exception.InvalidSessionException;
import com.bma.model.Session;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class SessionService {
    private static final SessionDao sessionDao = SessionDao.getInstance();

    public void validateAllSessions() throws DatabaseException {
        List<Session> allSessions = sessionDao.findAll();

        for (Session session : allSessions){
            if (LocalDateTime.now().isAfter(session.getExpiresAt())){
                sessionDao.delete(session);
            }
        }

    }

    public boolean isSessionValid(String sessionId) throws InvalidSessionException {
        Session session = getSessionById(sessionId);

        return !LocalDateTime.now().isAfter(session.getExpiresAt());
    }

    public Session getSessionById(String sessionId) throws InvalidSessionException {
        Optional<Session> session = sessionDao.getSessionById(sessionId);
        if (session.isEmpty()) {
            throw new InvalidSessionException("Current session has expired");
        }

        return session.get();
    }

    public void saveSession(Session session) {
        sessionDao.save(session);
    }


    public static SessionService getInstance() {
        return INSTANCE;
    }

    private SessionService() {
    }

    private static final SessionService INSTANCE = new SessionService();
}
