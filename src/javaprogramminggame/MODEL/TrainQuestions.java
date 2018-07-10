/**
 *
 * @author Asena Şahin, Halil Onur Fedai, Burak Alaydın, Barış Can
 */

package javaprogramminggame.MODEL;

public class TrainQuestions {
// VARIABLES
    private static TrainQuestion[] questions;
    private static int selectedID;
    private static String selectedType;
    
    // CONSTRUCTOR
    public TrainQuestions(String[][] questionsFromDB){
        questions = new TrainQuestion[questionsFromDB.length];
        for(int i = 0; i<questionsFromDB.length; i++){
            questions[i] = new TrainQuestion(Integer.parseInt(questionsFromDB[i][0]),
                                            questionsFromDB[i][1],
                                            questionsFromDB[i][2],
                                            questionsFromDB[i][3],
                                            questionsFromDB[i][4],
                                            questionsFromDB[i][5],
                                            Integer.parseInt(questionsFromDB[i][6]),
                                            questionsFromDB[i][7]);
        }
    }
    
    // METHODS
    public int getMaxSize(){
        return questions.length;
    }
    public TrainQuestion getSelectedQuestion(int i){
        return questions[i];   
    }
    
    public int getSelectedID(){
        return selectedID;
    }
    
    public void setSelectedID(int i){
        this.selectedID = i;
    }
    
    public void setSelectedType(String selectedType){
        this.selectedType = selectedType;
    }
    public String getSelectedType(){
        return selectedType;
    }
}

