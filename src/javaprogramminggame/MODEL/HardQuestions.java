/**
 *
 * @author Asena Şahin, Halil Onur Fedai, Burak Alaydın, Barış Can
 */

package javaprogramminggame.MODEL;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class HardQuestions {
    
    // VARIABLES
    private static String[][] questions;
    private static int selectedID;
    
    // CONSTRUCTOR
    public HardQuestions(String[][] questionsFromDB){
        questions = new String[questionsFromDB.length][2];
        for(int i = 0; i<questionsFromDB.length; i++){
            questions[i][0] = questionsFromDB[i][0];
            questions[i][1] = questionsFromDB[i][1];    
        }
    }
    
    //METHODS
    public int getMaxSize(){
        return questions.length;
    }
    public String getSelectedQuestion(int i){
        return questions[i][0];
    }
    
    private void compileTheCode(String code) {
        try{
            BufferedWriter out = new BufferedWriter(new FileWriter("TheCode.java"));

            out.write("import java.io.FileOutputStream;"+
                    "\nimport java.io.PrintStream;"+
                    "\npublic class TheCode {"
                    + "\npublic static void main(String[] args) {"
                    + "\ntry{\n"
                    + "\nPrintStream out = new PrintStream(new FileOutputStream(\"outputOfCode.txt\"));"
                    + "\nSystem.setOut(out);"
                    + "\n"+code+"\n"
                    + "}catch(Exception e){System.out.println();}"
                            + "\n}}");
            out.close();
            Process p1 = Runtime.getRuntime().exec("javac TheCode.java");
            p1.waitFor();
            Process p2 = Runtime.getRuntime().exec("java TheCode");
            p2.waitFor();
        }catch(Exception e){
            System.err.println("IO EXCEPTION IN HARD QUESTION COMPILER");
        }
    }
    public boolean isAnswerTrue(String code, int selectedQuestionID){
        compileTheCode(code);
        String output = "";
           try{
                File input = new File("outputOfCode.txt");
                Scanner freader = new Scanner(input);
                while (freader.hasNextLine()) {
                    output += freader.nextLine();
                    if(freader.hasNextLine()){
                        output+="\n";
                    }
                }
                freader.close();
           }catch(FileNotFoundException e){
               System.err.println("Hard Questions, Is Answer True FileNotFound");
               e.printStackTrace();
           }
           if(output.equals(questions[selectedQuestionID][1])){
               return true;
           }
           
        return false;
    }

}
