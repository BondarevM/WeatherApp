package com.bma.dao;

import com.bma.exception.DatabaseException;
import com.bma.model.Location;
import com.bma.model.User;
import com.bma.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Optional;

public class LocationDao {
    public void save(Location location) throws  DatabaseException {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(location);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            throw new DatabaseException("Something wrong with database");
        }
    }





    public static LocationDao getInstance(){
        return INSTANCE;
    }
    private static final LocationDao INSTANCE = new LocationDao();
    private LocationDao(){

    }
}
