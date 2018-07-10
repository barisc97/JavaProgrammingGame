/**
 *
 * @author Asena Şahin, Halil Onur Fedai, Burak Alaydın, Barış Can
 */

package javaprogramminggame.MODEL;

public class GameModel {
    
    // VARIABLES
    private String name;
    private String difficulty;
    private int score;
    
    // NEW GAME CONSTRUCTOR
    public GameModel(String name, String difficulty){
        this.name = name;
        this.difficulty = difficulty;
        score = 0;
        System.out.println(score);
    }
    // LOAD GAME CONSTRUCTOR
    public GameModel(String name, String difficulty, int score){
        this.name = name;
        this.difficulty = difficulty;
        this.score = score;
    }

    // GETTER METHODS
    public String getName() {
        return name;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public int getScore() {
        return score;
    }

    // SETTER METHODS
    public void setScore(int score) {
        this.score = score;
        System.out.println(score);
    }    
}
