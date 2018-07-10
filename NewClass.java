/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

package javaprogramminggame.VIEW;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author pc
 */
public class NewClass {
    private static Connection c;
    private static Statement stmt;
    private static ResultSet rs;
    
    
    NewClass();
            rs = stmt.executeQuery( "SELECT * FROM HARDQUESTIONS;" );
}
