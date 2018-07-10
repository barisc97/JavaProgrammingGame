/**
 *
 * @author Asena Şahin, Halil Onur Fedai, Burak Alaydın, Barış Can
 */

package javaprogramminggame.MODEL;

public class TrainQuestion extends NormalQuestion{

    
    private String type;
    private boolean answered;
    public TrainQuestion(int ID, String question, String answer1, String answer2, String answer3, String answer4, int trueAnswer, String type) {
        super(ID, question, answer1, answer2, answer3, answer4, trueAnswer);
        this.type = type;
        this.answered = false;
    }
    
    public String getType(){
        return type;
    }
    public boolean isAnswered(){
        return answered;
    }
    public void setAnswered(){
        answered = true;
    }
    
}
