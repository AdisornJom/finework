/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finework.core.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.log4j.Logger;

/**
 *
 * @author Aekasit
 */
public class JDBC {

    private static final Logger LOG = Logger.getLogger(JDBC.class);

    public Connection getConnection(String url, String db, String user, String pass) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://" + url + "/" + db + "?user=" + user + "&password=" + pass);
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            LOG.error(ex);
        }

        return null;
    }
}
