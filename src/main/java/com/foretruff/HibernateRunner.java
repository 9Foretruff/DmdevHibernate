package com.foretruff;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.SQLException;

public class HibernateRunner {
    public static void main(String[] args) throws SQLException {
//        BlockingQueue<Connection> pool = null;

//        SessionFactory

//        var connection = DriverManager
//                .getConnection("db_url", "db_username", "db_password");
//        Session

        var configuration = new Configuration();
        configuration.configure();

        try (var sessionFactory = configuration.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            System.out.println("ok");
        }
    }
}
