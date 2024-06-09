package com.bma;

import com.bma.util.HibernateTestUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {

    @Test
    public void sum() {
        Assertions.assertTrue(true);
    }

    @Test
    public void abc() {
        try (SessionFactory sessionFactory = HibernateTestUtil.getSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Assertions.assertTrue(true);
            session.getTransaction().commit();

        }
    }
}
