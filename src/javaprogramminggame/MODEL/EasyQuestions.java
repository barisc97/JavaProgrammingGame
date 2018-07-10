/**
 *
 * @author Asena Şahin, Halil Onur Fedai, Burak Alaydın, Barış Can
 */

package javaprogramminggame.MODEL;

public class EasyQuestions {
    
    // VARIABLES
    private static NormalQuestion[] questions;
    private static int selectedID;
    
    // CONSTRUCTOR
    public EasyQuestions(String[][] questionsFromDB){
        questions = new NormalQuestion[questionsFromDB.length];
        for(int i = 0; i<questionsFromDB.length; i++){
            // int ID, String question, String answer1, String answer2, String answer3, String answer4, int trueAns
            questions[i] = new NormalQuestion(Integer.parseInt(questionsFromDB[i][0]),
                                            questionsFromDB[i][1],
                                            questionsFromDB[i][2],
                                            questionsFromDB[i][3],
                                            questionsFromDB[i][4],
                                            questionsFromDB[i][5],
                                            Integer.parseInt(questionsFromDB[i][6]));
        }
    }
    
    // METHODS
    public int getMaxSize(){
        return questions.length;
    }
    public NormalQuestion getSelectedQuestion(int i){
        return questions[i];
        
    }
}
