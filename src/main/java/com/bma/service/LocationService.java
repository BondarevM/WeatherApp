package com.bma.service;

import com.bma.dao.LocationDao;
import com.bma.exception.DatabaseException;
import com.bma.exception.InvalidSessionException;
import com.bma.model.Location;
import com.bma.model.Session;
import com.bma.model.User;
import com.bma.util.HibernateUtil;
import org.hibernate.query.Query;

import java.util.List;

public class LocationService {
    private static final LocationDao locationDao = LocationDao.getInstance();
    private static final SessionService sessionService = SessionService.getInstance();


    public void saveLocation(String cityName, String latitude, String longitude, String sessionId) throws InvalidSessionException, DatabaseException {
        Session session = sessionService.getSessionById(sessionId);

        User user = session.getUser();
        Double lat = Double.valueOf(latitude);
        Double lon = Double.valueOf(longitude);

        Location location = Location.builder()
                .name(cityName)
                .user(user)
                .latitude(lat)
                .longitude(lon)
                .build();

        locationDao.save(location);
    }


    public static LocationService getInstance(){
        return INSTANCE;
    }
    private static final LocationService INSTANCE = new LocationService();
    private LocationService(){

    }
}
