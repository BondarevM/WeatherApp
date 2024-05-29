package com.bma.dao;

import com.bma.exception.DatabaseException;
import com.bma.model.Location;
import com.bma.model.User;
import com.bma.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;

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

    public static void main(String[] args) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){

            session.beginTransaction();
            User user = User.builder()
                    .login("TestUser2")
                    .password("qwerty")
                    .build();


            Location location = Location.builder()
                    .name("Kransoyarsk")
                    .user(user)
                    .longitude(new BigDecimal(123))
                    .latitude(new BigDecimal(321))
                    .build();
            session.save(user);
            session.save(location);


            session.getTransaction().commit();

        }

    }

    private static final UserDao INSTANCE = new UserDao();
    private UserDao(){}
    public static UserDao getInstance(){
        return INSTANCE;
    }
}
