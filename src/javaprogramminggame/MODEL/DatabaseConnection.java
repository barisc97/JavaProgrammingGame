/**
 *
 * @author Asena Şahin, Halil Onur Fedai, Burak Alaydın, Barış Can
 */

package javaprogramminggame.MODEL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseConnection {
    private static ArrayList<String> leaderList;
    private static ArrayList<String> leaderList2;
    private static String[][] leaderArray;
    private static String[][] leaderArray2;
    private static String[][] easyQuestions;
    private static String[][] hardQuestions;
    private static String[][] trainQuestions;
    private static Connection c;
    private static Statement stmt;
    private static ResultSet rs;
    private static String[] loadGame;
    public DatabaseConnection(){
        if(leaderList == null){
            leaderList = new ArrayList<String>();
            leaderArray = new String[5][2];   
        }
        
        if(leaderList2 == null){
            leaderList2 = new ArrayList<String>();
            leaderArray2 = new String[5][2];
        } 
        updateLeaderList();
        
    }
    
    // HARD QUESTION
    private int getHardQuestionCount(){
        int count = 0;
        try{
            dbConnector();
            rs = stmt.executeQuery( "SELECT * FROM HARDQUESTIONS;" );
            while(rs.next()){
               count++;
            }    
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }

        return count;
    }
    
    public String[][] getHardQuestion(){
     try {
         if(hardQuestions == null){
            hardQuestions = new String[getHardQuestionCount()][2];
            
        }
            int number = 0;
            dbConnector();
            rs = stmt.executeQuery( "SELECT * FROM HARDQUESTIONS;" );

            while(rs.next()){
                hardQuestions[number][0] = rs.getString("QUESTION");
                hardQuestions[number][1] = rs.getString("ANSWER");
                number++;
            }      
           rs.close();
           stmt.close();
           c.close();
           
        } catch (Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }   
         return hardQuestions;
    }
    
    // EASY QUESTION
    private int getEasyQuestionCount(){
        int count = 0;
        try{
            dbConnector();
            rs = stmt.executeQuery( "SELECT * FROM EASYQUESTIONS;" );
            while(rs.next()){
               count++;
            }    
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }

        return count;
    }
    
    public String[][] getEasyQuestion(){
     try {
         if(easyQuestions == null){
            easyQuestions = new String[getEasyQuestionCount()][7];
            
        }
            int number = 0;
            dbConnector();
            rs = stmt.executeQuery( "SELECT * FROM EASYQUESTIONS;" );

            while(rs.next()){
                easyQuestions[number][0] = String.valueOf(rs.getInt("ID"));
                easyQuestions[number][1] = rs.getString("QUESTION");
                easyQuestions[number][2] = rs.getString("ANSWER1");
                easyQuestions[number][3] = rs.getString("ANSWER2");
                easyQuestions[number][4] = rs.getString("ANSWER3");
                easyQuestions[number][5] = rs.getString("ANSWER4");
                easyQuestions[number][6] = String.valueOf(rs.getInt("TRUEANSWER"));
                number++;
            }      
           rs.close();
           stmt.close();
           c.close();
           
        } catch (Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }   
         return easyQuestions;
    }
    
    // DB CONNECTION
    private void dbConnector() {
        
        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:JPGDB");
            c.setAutoCommit(false);
            stmt = c.createStatement();
        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
       
         
    }
    
    // LEADER LIST
    public String getLeaderList(int i, int k){
        if(k == 1)
            return leaderList.get(i);
        else 
            return leaderList2.get(i);
    }
    
    public void updateLeaderList(){
        try {
            int count = 0;
            leaderList.clear();
            dbConnector();
            rs = stmt.executeQuery( "SELECT * FROM LEADERBOARD;" );
            while ( rs.next() ) {
                leaderList.add(rs.getInt("ID") + ") Name: "+ rs.getString("NAME") + " , Score:  " +  rs.getInt("SCORE"));
                leaderArray[count][0] = rs.getString("NAME");
                leaderArray[count][1] = String.valueOf(rs.getInt("SCORE"));
                count++;
            }
          rs.close();
          stmt.close();
          c.close();
        } catch (Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        
        try {
            dbConnector();
            int count = 0;
            leaderList2.clear();
            dbConnector();
            rs = stmt.executeQuery( "SELECT * FROM LEADERBOARD2;" );
            while ( rs.next() ) {
                leaderList2.add(rs.getInt("ID") + ") Name: "+ rs.getString("NAME") + " , Score:  " +  rs.getInt("SCORE"));
                leaderArray2[count][0] = rs.getString("NAME");
                leaderArray2[count][1] = String.valueOf(rs.getInt("SCORE"));
                count++;
            }
           rs.close();
           stmt.close();
           c.close();
        } catch (Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
    
    public void setLeaderList(String name, int score, String difficulty){
        if(difficulty.equals("EASY")){
            if(Integer.parseInt(leaderArray[0][1]) < score){
                for(int i = 3; i>=0; i--){
                    leaderArray[i+1][1] = leaderArray[i][1];
                    leaderArray[i+1][0] = leaderArray[i][0];
                }
                leaderArray[0][1] = String.valueOf(score);
                leaderArray[0][0] = name;
                changeLeaderList(1);
            }
            else if(Integer.parseInt(leaderArray[1][1]) < score){
                for(int i = 3; i>=1; i--){
                    leaderArray[i+1][1] = leaderArray[i][1];
                    leaderArray[i+1][0] = leaderArray[i][0];
                }
                leaderArray[1][1] = String.valueOf(score);
                leaderArray[1][0] = name;
                changeLeaderList(1);
            }
            else if(Integer.parseInt(leaderArray[2][1]) < score){
                for(int i = 3; i>=2; i--){
                    leaderArray[i+1][1] = leaderArray[i][1];
                    leaderArray[i+1][0] = leaderArray[i][0];
                }
                leaderArray[2][1] = String.valueOf(score);
                leaderArray[2][0] = name;
                changeLeaderList(1);
            }
            else if(Integer.parseInt(leaderArray[3][1]) < score){
                for(int i = 3; i>=3; i--){
                    leaderArray[i+1][1] = leaderArray[i][1];
                    leaderArray[i+1][0] = leaderArray[i][0];
                }
                leaderArray[3][1] = String.valueOf(score);
                leaderArray[3][0] = name;
                changeLeaderList(1);
            }
            else if(Integer.parseInt(leaderArray[4][1]) < score){
                leaderArray[4][1] = String.valueOf(score);
                leaderArray[4][0] = name;
                changeLeaderList(1);
            }
        }else if(difficulty.equals("HARD")){
            if(Integer.parseInt(leaderArray2[0][1]) < score){
                for(int i = 3; i>=0; i--){
                    leaderArray2[i+1][1] = leaderArray2[i][1];
                    leaderArray2[i+1][0] = leaderArray2[i][0];
                }
                leaderArray2[0][1] = String.valueOf(score);
                leaderArray2[0][0] = name;
                changeLeaderList(2);
            }
            else if(Integer.parseInt(leaderArray2[1][1]) < score){
                for(int i = 3; i>=1; i--){
                    leaderArray2[i+1][1] = leaderArray2[i][1];
                    leaderArray2[i+1][0] = leaderArray2[i][0];
                }
                leaderArray2[1][1] = String.valueOf(score);
                leaderArray2[1][0] = name;
                changeLeaderList(2);
            }
            else if(Integer.parseInt(leaderArray2[2][1]) < score){
                for(int i = 3; i>=2; i--){
                    leaderArray2[i+1][1] = leaderArray2[i][1];
                    leaderArray2[i+1][0] = leaderArray2[i][0];
                }
                leaderArray2[2][1] = String.valueOf(score);
                leaderArray2[2][0] = name;
                changeLeaderList(2);
            }
            else if(Integer.parseInt(leaderArray2[3][1]) < score){
                for(int i = 3; i>=3; i--){
                    leaderArray2[i+1][1] = leaderArray2[i][1];
                    leaderArray2[i+1][0] = leaderArray2[i][0];
                }
                leaderArray2[3][1] = String.valueOf(score);
                leaderArray2[3][0] = name;
                changeLeaderList(2);
            }
            else if(Integer.parseInt(leaderArray2[4][1]) < score){
                leaderArray2[4][1] = String.valueOf(score);
                leaderArray2[4][0] = name;
                changeLeaderList(2);
            }
        }
        
    }
    private void changeLeaderList(int diff){
        if(diff == 1){
             try {
                dbConnector();
                String sql;
                for(int i = 0; i<5; i++){
                    sql= "UPDATE LEADERBOARD set NAME = '"+leaderArray[i][0]+"' where ID="+(i+1)+";";
                    stmt.executeUpdate(sql);
                    c.commit();

                    sql = "UPDATE LEADERBOARD set SCORE = "+Integer.parseInt(leaderArray[i][1])+" where ID="+(i+1)+";";
                    stmt.executeUpdate(sql);
                    c.commit();
                }
                stmt.close();
                c.close();
             }catch ( Exception e ) {
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
           }
            updateLeaderList();
        }else if(diff == 2){
             try {
                dbConnector();
                String sql;
                for(int i = 0; i<5; i++){
                    sql= "UPDATE LEADERBOARD2 set NAME = '"+leaderArray2[i][0]+"' where ID="+(i+1)+";";
                    stmt.executeUpdate(sql);
                    c.commit();

                    sql = "UPDATE LEADERBOARD2 set SCORE = "+Integer.parseInt(leaderArray2[i][1])+" where ID="+(i+1)+";";
                    stmt.executeUpdate(sql);
                    c.commit();
                }
                stmt.close();
                c.close();
             }catch ( Exception e ) {
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
           }
            updateLeaderList();
        }
       
    }
    // LOAD GAME
    
    public void deleteSaveGame(){
        try{
            dbConnector();
            String sql = "UPDATE LOADGAME set ACTIVE = 0 where ID=1;";
            stmt.executeUpdate(sql);
            c.commit();
            stmt.close();
            c.close();
        }catch( Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
    public void saveGame(String name, int score, String difficulty){
         try {
            dbConnector();
            String sql = "UPDATE LOADGAME set NAME = '"+name+"' where ID=1;";
            stmt.executeUpdate(sql);
            c.commit();
             
            sql = "UPDATE LOADGAME set ACTIVE = 1 where ID=1;";
            stmt.executeUpdate(sql);
            c.commit();
             
            sql = "UPDATE LOADGAME set DIFFICULTY = '"+difficulty+"' where ID=1;";
            stmt.executeUpdate(sql);
            c.commit();
            
            sql = "UPDATE LOADGAME set SCORE = "+score+" where ID=1;";
            stmt.executeUpdate(sql);
            c.commit();

            stmt.close();
            c.close();
         }catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
       }
    }
    public String[] getLoadGame(){
        return loadGame;
    }
    public void loadGame(){
        try {
            if(loadGame == null){
                loadGame = new String[4];
            }
            int number = 0;
            dbConnector();
            rs = stmt.executeQuery( "SELECT * FROM LOADGAME;" );

            while(rs.next()){
                loadGame[0] = rs.getString("NAME");
                loadGame[1] = String.valueOf(rs.getInt("SCORE"));
                loadGame[2] = rs.getString("DIFFICULTY");
                loadGame[3] = String.valueOf(rs.getInt("ACTIVE"));
            }      
           rs.close();
           stmt.close();
           c.close();
           
        } catch (Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }   
        
    }
    
    private int getTrainQuestionCount(){
        int count = 0;
        try{
            dbConnector();
            rs = stmt.executeQuery( "SELECT * FROM TRAINQUESTIONS;" );
            while(rs.next()){
               count++;
            }    
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }

        return count;
    } 
    
    public String[][] getTrainQuestion(){
     try {
         if(trainQuestions == null){
            trainQuestions = new String[getTrainQuestionCount()][8];
            
        }
            int number = 0;
            dbConnector();
            rs = stmt.executeQuery( "SELECT * FROM TRAINQUESTIONS;" );

            while(rs.next()){
                trainQuestions[number][0] = String.valueOf(rs.getInt("ID"));
                trainQuestions[number][1] = rs.getString("QUESTION");
                trainQuestions[number][2] = rs.getString("ANSWER1");
                trainQuestions[number][3] = rs.getString("ANSWER2");
                trainQuestions[number][4] = rs.getString("ANSWER3");
                trainQuestions[number][5] = rs.getString("ANSWER4");
                trainQuestions[number][6] = String.valueOf(rs.getInt("TRUEANSWER"));
                trainQuestions[number][7] = rs.getString("TYPEOFQUESTION");
                number++;
            }      
           rs.close();
           stmt.close();
           c.close();
           
        } catch (Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }   
         return trainQuestions;
    }
    
    
    
    
}
