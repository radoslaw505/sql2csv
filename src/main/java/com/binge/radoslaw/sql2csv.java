package com.binge.radoslaw;

import com.opencsv.CSVWriter;
import org.apache.commons.lang3.StringUtils;

import java.io.FileWriter;
import java.sql.*;
import java.util.Properties;
import java.util.TimeZone;

public class sql2csv {

    public static void main(String[] args) {

        Properties info = new Properties();
        String url = "jdbc:mysql://localhost:3306/employees?serverTimezone=" + TimeZone.getDefault().getID();
        info.put("user", "radoslaw");
        info.put("password", "Radsoon#321");

        System.out.println("Connecting database...");

        try {
//            1. Create connection
//            load and register JDBC driver for MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, info);
            if (connection != null) {
                System.out.println("Successfully connected to MySQL database employees");
            }
//            2. Create statement
            Statement statement = connection.createStatement();
//            3. Execute SQL query
            String sql = "select e.* from employees e where e.last_name like 'Bon%'";
            String presql = StringUtils.substringBefore(sql, "where");
            System.out.println(presql);

//            String presql = "select e.* from employees e where first_name = 'John'";
            ResultSet resultSet = statement.executeQuery(presql);
            System.out.println("Fetching records with condition...");
            resultSet = statement.executeQuery(sql);
//            4. Process the result set
            CSVWriter writer = new CSVWriter(new FileWriter("yourfile.csv", false), ';');
            Boolean includeHeaders = true;
            writer.writeAll(resultSet, includeHeaders);
            writer.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
