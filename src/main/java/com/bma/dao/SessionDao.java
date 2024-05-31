package com.bma.dao;

import com.bma.exception.DatabaseException;
import com.bma.model.Session;
import com.bma.model.User;
import com.bma.util.HibernateUtil;
import org.hibernate.HibernateException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SessionDao {

    public void save(Session userSession){
        try(org.hibernate.Session session = HibernateUtil.getSessionFactory().openSession()){
            session.beginTransaction();
            session.save(userSession);
            session.getTransaction().commit();
        }
    }

    public void delete(Session userSession){
        try(org.hibernate.Session session = HibernateUtil.getSessionFactory().openSession()){
            session.beginTransaction();
            session.delete(userSession);
            session.getTransaction().commit();
        }
    }

    public Optional<Session> getSessionById(String id){
        String hql = "FROM Session where id =:id";

        try(org.hibernate.Session session = HibernateUtil.getSessionFactory().openSession()){
            session.beginTransaction();

            Session userSession = session.createQuery(hql, Session.class).setParameter("id", id).uniqueResult();

            session.getTransaction().commit();
            return Optional.ofNullable(userSession);
        }
    }

    public List<Session> findAll() throws DatabaseException {
        String hql = "FROM Session";
        List<Session> allSessions;
        try(org.hibernate.Session session = HibernateUtil.getSessionFactory().openSession()){
            session.beginTransaction();
            allSessions = session.createQuery(hql, Session.class).list();
            session.getTransaction().commit();
            return allSessions;
        } catch (HibernateException e){
            throw new DatabaseException("Something wrong with database");
        }

    }

    private SessionDao() {
    }
    private static final SessionDao INSTANCE = new SessionDao();
    public static SessionDao getInstance(){
        return INSTANCE;
    }
}
