/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finework.core.utils;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author Aekasit
 */
public class JdbcUtil {

    public Connection getMysqlConnection() {
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("jdbc/backoffice");
            return ds.getConnection();
        } catch (NamingException | SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
