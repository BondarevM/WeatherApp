package com.bma.util;

import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.testcontainers.containers.PostgreSQLContainer;

import java.io.IOException;
import java.util.Properties;

@UtilityClass
public class HibernateUtil {
    private static SessionFactory sessionFactory;

    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.3");
    Properties properties;

    static {
        properties = new Properties();
        try {
            properties.load(HibernateUtil.class.getClassLoader().getResourceAsStream("application.properties"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (properties.getProperty("RUN_MODE").equals("TEST")) {
            postgres.start();
        }
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration configuration = buildConfiguration();
            configuration.configure();
            sessionFactory = configuration.buildSessionFactory();
        }

        return sessionFactory;
    }

    public static Configuration buildConfiguration() {
        Configuration configuration = new Configuration();

        if (properties.getProperty("RUN_MODE").equals("DEV")) {
            String jdbcUrl = System.getenv("DB_HOST") != null
                    ? "jdbc:postgresql://" + System.getenv("DB_HOST") + ":" + System.getenv("DB_PORT") + "/" + System.getenv("DB_NAME")
                    : "jdbc:postgresql://localhost:5432/postgres";
            String username = System.getenv("DB_USER") != null ? System.getenv("DB_USER") : "postgres";
            String password = System.getenv("DB_PASSWORD") != null ? System.getenv("DB_PASSWORD") : "postgres";

            configuration.setProperty(Environment.JAKARTA_JDBC_URL, jdbcUrl);
            configuration.setProperty(Environment.JAKARTA_JDBC_USER, username);
            configuration.setProperty(Environment.JAKARTA_JDBC_PASSWORD, password);
        } else {
            configuration.setProperty("hibernate.connection.url", postgres.getJdbcUrl());
            configuration.setProperty("hibernate.connection.username", postgres.getUsername());
            configuration.setProperty("hibernate.connection.password", postgres.getPassword());
        }

        configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");

        return configuration;
    }
}
