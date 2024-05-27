package com.bma.dao;

import com.bma.model.User;
import com.bma.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


public class test {

    public static void main(String[] args) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){

            session.beginTransaction();
            User user = User.builder()
                    .login("mishanya228")
                    .password("44")
                    .build();

            Object save = session.save(user);
            session.getTransaction().commit();

        }

    }
}
