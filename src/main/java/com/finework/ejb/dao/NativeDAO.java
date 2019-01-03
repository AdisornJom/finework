/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finework.ejb.dao;


import com.finework.core.util.JdbcUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;

/**
 *
 * @author Aekasit
 */
public class NativeDAO {

    private static final Logger LOG = Logger.getLogger(NativeDAO.class);
    
    public void resetSequenceNo() {
        Connection conn = null;
        Statement stm = null;
        try {
            JdbcUtil jdbcUtil = new JdbcUtil();
            conn = jdbcUtil.getMysqlConnection();
            stm = conn.createStatement();

            String sql = "update sys_sequence set runningno = 0  where id=27 and startnewyear = 'Y'";
            stm.executeUpdate(sql);

        } catch (SQLException ex) {
            LOG.error(ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                }
            }
        }
    }
}
