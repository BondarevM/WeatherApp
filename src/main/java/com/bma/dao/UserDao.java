package com.bma.dao;

import com.bma.exception.DatabaseException;
import com.bma.exception.InvalidUserDataException;
import com.bma.model.Location;
import com.bma.model.User;
import com.bma.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Optional;


public class UserDao {

    public void save(User user) throws InvalidUserDataException {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            throw new InvalidUserDataException("This login already exist");
        }
    }

    public Optional<User> getUserByLoginAndPassword(String login, String hashedPassword) throws DatabaseException {
        String hql = "FROM User WHERE login = :login and password = :hashedPassword";

        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            Query query = session.createQuery(hql, User.class).setParameter("login", login).setParameter("hashedPassword", hashedPassword);
            User user =  (User) query.uniqueResult();
            session.getTransaction().commit();

            return Optional.ofNullable(user);

        } catch (HibernateException e){
            throw new DatabaseException("Something wrong with database");
        }
    }


    private static final UserDao INSTANCE = new UserDao();
    private UserDao(){}
    public static UserDao getInstance(){
        return INSTANCE;
    }
}
