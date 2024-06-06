package com.bma.dao;

import com.bma.exception.DatabaseException;
import com.bma.model.Location;
import com.bma.model.User;
import com.bma.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
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

    public List<Location> getLocationsByUser(User user){
        String hql = "FROM Location WHERE user =:user";

        try(org.hibernate.Session session = HibernateUtil.getSessionFactory().openSession()){
            session.beginTransaction();
            Query<Location> query = session.createQuery(hql, Location.class).setParameter("user", user);
            List<Location> locations = query.list();
            session.getTransaction().commit();
            return locations;
        }
    }



    public static LocationDao getInstance(){
        return INSTANCE;
    }
    private static final LocationDao INSTANCE = new LocationDao();
    private LocationDao(){

    }
}
