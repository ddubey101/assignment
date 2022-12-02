package com.cs.dao;

import com.cs.db.config.DbConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EventDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventDao.class);

    public static void saveToFile(Event event) {
        String query="INSERT INTO EVENTS (id, duration, type, host, alert) VALUES (?,?,?,?,?)";
        Connection con = DbConnection.getFileHSqlDbConnection();
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, event.getId());
            pst.setLong(2, event.getDuration());
            pst.setString(3, event.getType());
            pst.setString(4, event.getHost());
            pst.setBoolean(5, event.isAlert());
            pst.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Exception saving event with EventId : " + event.getId() + " in DB : " + e.getMessage() );
        }
    }

    public static void saveToDb(Event event) {
        String query="INSERT INTO EVENTS (id, duration, type, host, alert) VALUES (?,?,?,?,?)";
        Connection con = DbConnection.getHSqlDbConnection();
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, event.getId());
            pst.setLong(2, event.getDuration());
            pst.setString(3, event.getType());
            pst.setString(4, event.getHost());
            pst.setBoolean(5, event.isAlert());
            pst.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Exception saving event with EventId : " + event.getId() + " in DB : " + e.getMessage() );
        }
    }
}
