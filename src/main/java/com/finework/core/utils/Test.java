package com.finework.core.utils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Lenovo
 */
public class Test {

    public static void main(String[] args) throws SQLException {
        Statement s = null;
        Connection conn = null;
        try {

            Class.forName("com.mysql.jdbc.Driver");//jdbc:mysql://localhost:3306/fineworkprd20170804
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bmcnx?user=root&password=dbp@$$w0rd");

            s = conn.createStatement();

            String sql = "SELECT * FROM  sys_payment_manufactory_detail";

            ResultSet rec = s.executeQuery(sql);
            int row = 0;
            while ((rec != null) && (rec.next())) {
               // System.out.println(rec.getString("factory_real_id"));
                int factory_real_id = rec.getInt("factory_real_id");

                String sql2 = "SELECT status FROM  sys_manufactory_real WHERE factory_real_id=?";
                PreparedStatement statement2 = conn.prepareStatement(sql2);
                statement2.setInt(1, factory_real_id);
                ResultSet rs2 = statement2.executeQuery();
                while (rs2.next()) {
                    int status = rs2.getInt("status");
                    if (status == 1) {
                        System.out.println("status>>>>>>>>" + factory_real_id + "<>" + status);
                        String sql1 = "update  sys_manufactory_real set status=2  WHERE factory_real_id=?";

                        PreparedStatement statement = conn.prepareStatement(sql1);
                        statement.setInt(1, factory_real_id);

                        int rowsUpdated = statement.executeUpdate();
                        if (rowsUpdated > 0) {
                            System.out.println("An existing user was updated successfully!");
                        }
                    }
                }
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        try {
            if (s != null) {
                s.close();
                conn.close();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
