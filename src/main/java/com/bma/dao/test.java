package com.bma.dao;

import com.bma.model.Location;
import com.bma.model.User;
import com.bma.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.math.BigDecimal;
import java.util.function.BinaryOperator;


public class test {

    public static void main(String[] args) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){

            session.beginTransaction();
            User user = User.builder()
                    .login("TestUser")
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

        } catch (HibernateException e) {
            System.out.println("AAABBBCCC!!!");
        }

    }
}
