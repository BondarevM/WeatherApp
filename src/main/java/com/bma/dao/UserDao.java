package com.bma.dao;

import com.bma.exception.DatabaseException;
import com.bma.exception.InvalidUserDataException;
import com.bma.model.Location;
import com.bma.model.User;
import com.bma.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.math.BigDecimal;


public class UserDao {

    public void save(User user) throws DatabaseException {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (HibernateException e){
            throw new DatabaseException(e.getMessage());
        }
    }

    public User getUserByLoginAndPassword(String login, String hashedPassword) throws DatabaseException {
        String hql = "FROM User WHERE login = :login and password = :hashedPassword";

        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            Query query = session.createQuery(hql, User.class).setParameter("login", login).setParameter("hashedPassword", hashedPassword);
            User user =  (User) query.uniqueResult();
            session.getTransaction().commit();

            return user;

        } catch (HibernateException e){
            throw new DatabaseException("Something wrong with database");
        }
    }

    public static void main(String[] args) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            String login = "Bondarev";
            String hashedPassword = "71 10 ED A4 D0 9E 06 2A A5 E4 A3 90 B0 A5 72 AC 0D 2C 02 20 ";
            String hql = "SELECT User.login, User.password FROM User WHERE login =:login and password =:password";

            session.beginTransaction();

            Query query = session.createQuery(hql).setParameter("login", login).setParameter("password", hashedPassword);
            User user =  (User) query.uniqueResult();
            session.getTransaction().commit();

            System.out.println();


        }

    }

    private static final UserDao INSTANCE = new UserDao();
    private UserDao(){}
    public static UserDao getInstance(){
        return INSTANCE;
    }
}
