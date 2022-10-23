package com.hilton.db;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBTest {

    @Test
    public void checkCon() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dropwizschema?autoReconnect=true"
//             con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dropwizschema?autoReconnect=true&useSSL=false"
                , "root"
                , "password")) {

            System.out.println(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
