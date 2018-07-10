/**
 *
 * @author Asena Şahin, Halil Onur Fedai, Burak Alaydın, Barış Can
 */

package javaprogramminggame.CONTROLLER;

import javaprogramminggame.VIEW.GamePanel2;
import javaprogramminggame.VIEW.MainPagePanel;
import javaprogramminggame.VIEW.GamePanel1;
import javaprogramminggame.VIEW.LeaderBoardPanel;
import javaprogramminggame.VIEW.MainFrame;
import javaprogramminggame.MODEL.DatabaseConnection;
import java.util.ArrayList;
import javaprogramminggame.MODEL.AboutUsModel;
import javaprogramminggame.MODEL.EasyQuestions;
import javaprogramminggame.MODEL.GameModel;
import javaprogramminggame.MODEL.Gifs;
import javaprogramminggame.MODEL.HardQuestions;
import javaprogramminggame.MODEL.TrainQuestions;
import javaprogramminggame.VIEW.AboutUsView;
import javaprogramminggame.VIEW.TrainChooseTopicPanel;
import javaprogramminggame.VIEW.TrainGamePanel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainGameController {

    private MainFrame mainFrame;
    private MainPagePanel mainPagePanel;
    private LeaderBoardPanel leaderBoardPanel;
    private DatabaseConnection db;
    private GamePanel1 gamePanel1;
    private GamePanel2 gamePanel2;
    private TrainChooseTopicPanel trainChooseTopicPanel;
    private TrainGamePanel trainGamePanel;
    private TrainQuestions trainQuestions;
    private GameModel gameModel;
    private EasyQuestions easyQuestions;
    private HardQuestions hardQuestions;
    private Gifs gifs;
    private AboutUsView aboutUsView;
    private AboutUsModel aboutUsModel;
   
    
    public MainGameController(){
       db = new DatabaseConnection();
       mainFrame = new MainFrame();
       mainPagePanel = new MainPagePanel();
       mainFrame.add(mainPagePanel);
       mainFrame.revalidate();
       mainFrame.repaint();
       mainMenuActions();
    }
    
    // LOADGAME
    private void loadGame(){
        db.loadGame();
            if(Integer.parseInt(db.getLoadGame()[3]) == 1){
                gameModel = new GameModel(db.getLoadGame()[0], db.getLoadGame()[2], Integer.parseInt(db.getLoadGame()[1]));
                if(db.getLoadGame()[2].equals("EASY")){
                    easyQuestions = new EasyQuestions(db.getEasyQuestion());
                    getGamePanel1();
                    setEasyQuestion();
                    db.saveGame(gameModel.getName(), gameModel.getScore(), gameModel.getDifficulty());
                }else if(db.getLoadGame()[2].equals("HARD")){
                    hardQuestions = new HardQuestions(db.getHardQuestion());
                    getGamePanel2();
                    setHardQuestion();
                    db.saveGame(gameModel.getName(), gameModel.getScore(), gameModel.getDifficulty());
                }
            } else{
                 Object[] options = {"OK"};
                int n = JOptionPane.showOptionDialog(mainFrame,
                   "There is not a saved game.","Sorry",
                   JOptionPane.PLAIN_MESSAGE,
                   JOptionPane.WARNING_MESSAGE,
                   null,
                   options,
                   options[0]);
            }
           
    }
    // EASY HARD SELECTION
    private void showHardnessSelection(){
        Object[] options = {"BACK","HARD","EASY"};
        JTextField playerName = new JTextField("");
        Object[] enterName = {"Enter Name", playerName, "\n","Easy Game or Hard Game?"};
        int n = JOptionPane.showOptionDialog(mainFrame,enterName, "Select Difficulty",JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE,null, options,options[2]);
        if(n == 2 && !playerName.getText().equals("")){
            gameModel = new GameModel(playerName.getText(), "EASY");
            easyQuestions = new EasyQuestions(db.getEasyQuestion());
            getGamePanel1();
            setEasyQuestion();
            db.saveGame(gameModel.getName(), gameModel.getScore(), gameModel.getDifficulty());
            
        }else if (n == 1  && !playerName.getText().equals("")){
            gameModel = new GameModel(playerName.getText(), "HARD");
            hardQuestions = new HardQuestions(db.getHardQuestion());
            getGamePanel2();
            setHardQuestion();
            db.saveGame(gameModel.getName(), gameModel.getScore(), gameModel.getDifficulty());
        }else if (playerName.getText().equals("") && n != 0){ // BACK ISE DIYE n!=0
            JOptionPane.showConfirmDialog(mainFrame, "Please Enter Name", "Be Careful!", JOptionPane.WARNING_MESSAGE, JOptionPane.OK_OPTION);
            showHardnessSelection();
        }
    }
   
    // GAME PANEL 2
    private void getGamePanel2(){
        if(gamePanel2 == null){
            gamePanel2 = new GamePanel2();
            gamePanel2Actions();
        }
        mainFrame.remove(mainPagePanel);
        mainFrame.add(gamePanel2);
        mainFrame.revalidate();
        mainFrame.repaint();
        
    }
    
    private void gamePanel2Actions(){
        gamePanel2.getBackButton().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mainFrame.remove(gamePanel2);
                mainMenu();
            }
        });
        
        gamePanel2.getSubmitButton().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if(hardQuestions.isAnswerTrue(gamePanel2.getAnswerArea().getText(), gameModel.getScore())){
                   gameModel.setScore(gameModel.getScore()+1);
                   db.saveGame(gameModel.getName(), gameModel.getScore(), gameModel.getDifficulty());
                   showGif(1,true);
                   setHardQuestion();
               }else{
                    db.setLeaderList(gameModel.getName(), gameModel.getScore(), gameModel.getDifficulty());
                    db.deleteSaveGame();
                    showGif(2,true);
                    mainFrame.remove(gamePanel2);
                    mainMenu();
               }
            }
        });
        
    }
    
    // GAME PANEL 1
    private void getGamePanel1(){
        if(gamePanel1 == null){
            gamePanel1 = new GamePanel1();
            gamePanel1Actions();
        }
        mainFrame.remove(mainPagePanel);
        mainFrame.add(gamePanel1);
        mainFrame.revalidate();
        mainFrame.repaint();
        
    }
    
    private void gamePanel1Actions(){
        gamePanel1.getBackButton().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mainFrame.remove(gamePanel1);
                mainMenu();
            }
        });
        gamePanel1.getButton1().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
               if(easyQuestions.getSelectedQuestion(gameModel.getScore()).isAnsweTrue(1)){
                   gameModel.setScore(gameModel.getScore()+1);
                   db.saveGame(gameModel.getName(), gameModel.getScore(), gameModel.getDifficulty());
                   showGif(1,true);
                   setEasyQuestion();
               }else{
                    db.setLeaderList(gameModel.getName(), gameModel.getScore(), gameModel.getDifficulty());
                    db.deleteSaveGame();
                    showGif(2,true);
                    mainFrame.remove(gamePanel1);
                    mainMenu();
               }
            }
        });
        gamePanel1.getButton2().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
               if(easyQuestions.getSelectedQuestion(gameModel.getScore()).isAnsweTrue(2)){
                   gameModel.setScore(gameModel.getScore()+1);
                   db.saveGame(gameModel.getName(), gameModel.getScore(), gameModel.getDifficulty());
                   showGif(1,true);
                   setEasyQuestion();
               }else{
                    db.setLeaderList(gameModel.getName(), gameModel.getScore(), gameModel.getDifficulty());
                    db.deleteSaveGame();
                    showGif(2,true);
                    mainFrame.remove(gamePanel1);
                    mainMenu();
               }
            }
        });
        gamePanel1.getButton3().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
               if(easyQuestions.getSelectedQuestion(gameModel.getScore()).isAnsweTrue(3)){
                   gameModel.setScore(gameModel.getScore()+1);
                   db.saveGame(gameModel.getName(), gameModel.getScore(), gameModel.getDifficulty());
                   showGif(1,true);
                   setEasyQuestion();
               }else{
                    db.setLeaderList(gameModel.getName(), gameModel.getScore(), gameModel.getDifficulty());
                    db.deleteSaveGame();
                    showGif(2,true);
                    mainFrame.remove(gamePanel1);
                    mainMenu();
               }
            }
        });
        gamePanel1.getButton4().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
               if(easyQuestions.getSelectedQuestion(gameModel.getScore()).isAnsweTrue(4)){
                   gameModel.setScore(gameModel.getScore()+1);
                   db.saveGame(gameModel.getName(), gameModel.getScore(), gameModel.getDifficulty());
                   showGif(1,true);
                   setEasyQuestion();
               }else{
                    db.setLeaderList(gameModel.getName(), gameModel.getScore(), gameModel.getDifficulty());
                    db.deleteSaveGame();
                    showGif(2,true);
                    mainFrame.remove(gamePanel1);
                    mainMenu();
               }
            }
        });
        
        
    }
    private void setEasyQuestion(){
        if(gameModel.getScore() < easyQuestions.getMaxSize()){
            gamePanel1.getQuestionArea().setText(easyQuestions.getSelectedQuestion(gameModel.getScore()).getQuestion());
            gamePanel1.getButton1().setText(easyQuestions.getSelectedQuestion(gameModel.getScore()).getAnswer1());
            gamePanel1.getButton2().setText(easyQuestions.getSelectedQuestion(gameModel.getScore()).getAnswer2());
            gamePanel1.getButton3().setText(easyQuestions.getSelectedQuestion(gameModel.getScore()).getAnswer3());
            gamePanel1.getButton4().setText(easyQuestions.getSelectedQuestion(gameModel.getScore()).getAnswer4()); 
            gamePanel1.revalidate();
            gamePanel1.repaint();
        }else{
            db.setLeaderList(gameModel.getName(), gameModel.getScore(), gameModel.getDifficulty());
            db.deleteSaveGame();
            showGif(3,true);
            mainFrame.remove(gamePanel1);
            mainMenu();
        }
   }
   
    /// DO 000
    private void setHardQuestion(){
    if(gameModel.getScore() < hardQuestions.getMaxSize()){
            gamePanel2.getQuestionArea().setText(hardQuestions.getSelectedQuestion(gameModel.getScore()));
            gamePanel2.getAnswerArea().setText("");
            gamePanel2.revalidate();
            gamePanel2.repaint();
        }else{
            db.setLeaderList(gameModel.getName(), gameModel.getScore(),gameModel.getDifficulty());
            db.deleteSaveGame();
            showGif(3,true);
            mainFrame.remove(gamePanel2);
            mainMenu();
            // ALL EASY QUESTIONS COMPLETED
        }
    }
       
    
    // LEADER BOARD
    
     private void leaderBoardPanel(){
        if(leaderBoardPanel == null){
            leaderBoardPanel = new LeaderBoardPanel();
            leaderBoardActions();
        }
        leaderBoardPanel.getLB1().setText(db.getLeaderList(0,1));
        leaderBoardPanel.getLB2().setText(db.getLeaderList(1,1));
        leaderBoardPanel.getLB3().setText(db.getLeaderList(2,1));
        leaderBoardPanel.getLB4().setText(db.getLeaderList(3,1));
        leaderBoardPanel.getLB5().setText(db.getLeaderList(4,1));
        leaderBoardPanel.getLB11().setText(db.getLeaderList(0,2));
        leaderBoardPanel.getLB22().setText(db.getLeaderList(1,2));
        leaderBoardPanel.getLB33().setText(db.getLeaderList(2,2));
        leaderBoardPanel.getLB44().setText(db.getLeaderList(3,2));
        leaderBoardPanel.getLB55().setText(db.getLeaderList(4,2));
        mainFrame.remove(mainPagePanel);
        mainFrame.add(leaderBoardPanel);
        mainFrame.revalidate();
        mainFrame.repaint();
    }
     
    private void leaderBoardActions(){
        leaderBoardPanel.getBackButton().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mainFrame.remove(leaderBoardPanel);
                mainMenu();
            }
        });
        
    }
    
    // MAIN MENU
    private void mainMenu(){
       mainFrame.add(mainPagePanel);
       mainFrame.revalidate();
       mainFrame.repaint();
       
    }
        
    private void mainMenuActions(){
        
        mainPagePanel.getTrainGameButton().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
               getTrainMenu();
            }
        });
        mainPagePanel.getNewGameButton().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showHardnessSelection();
            }
        });
        
        
        mainPagePanel.getLoadGameButton().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadGame();
            }
        });
        
        
        mainPagePanel.getLeaderBoardButton().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                leaderBoardPanel();
            }
        });
        
        
        mainPagePanel.getAboutUsButton().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutUsPanel();
            }
        });
    }
    private void getTrainMenu(){
        if(trainChooseTopicPanel == null){
           trainChooseTopicPanel = new TrainChooseTopicPanel();
            trainMenuActions();
        }
        if(trainGamePanel == null){
           trainGamePanel = new TrainGamePanel();
            trainGameActions();
        }
        mainFrame.remove(mainPagePanel);
        mainFrame.add(trainChooseTopicPanel);
        mainFrame.revalidate();
        mainFrame.repaint();
        
    }
    
    private void trainMenuActions(){
        trainChooseTopicPanel.getBackButton().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mainFrame.remove(trainChooseTopicPanel);
                mainMenu();
            }
        });
        
        trainChooseTopicPanel.getT1b().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                trainQuestions = new TrainQuestions(db.getTrainQuestion());    
                if(setTrainQuestion("LOGIC")){
                    getTrainGame();
                }
            }
        });
        trainChooseTopicPanel.getT2b().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                trainQuestions = new TrainQuestions(db.getTrainQuestion());    
                if(setTrainQuestion("LOOPS")){
                    getTrainGame();
                }
            }
        });
        trainChooseTopicPanel.getT3b().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
               trainQuestions = new TrainQuestions(db.getTrainQuestion());    
                if(setTrainQuestion("FUNDAMENTALS")){
                    getTrainGame();
                }
            }
        });
        trainChooseTopicPanel.getT4b().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                trainQuestions = new TrainQuestions(db.getTrainQuestion());    
                if(setTrainQuestion("STRING")){
                    getTrainGame();
                }
            }
        });
        trainChooseTopicPanel.getT5b().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
               trainQuestions = new TrainQuestions(db.getTrainQuestion());    
                if(setTrainQuestion("MATH")){
                    getTrainGame();
                }
            }
        });
        trainChooseTopicPanel.getT6b().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                trainQuestions = new TrainQuestions(db.getTrainQuestion());    
                if(setTrainQuestion("ARRAY")){
                    getTrainGame();
                }
            }
        });
        trainChooseTopicPanel.getT7b().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                trainQuestions = new TrainQuestions(db.getTrainQuestion());    
                if(setTrainQuestion("DATA")){
                    getTrainGame();
                }
            }
        });
        
    }
    
    private void getTrainGame(){
        if(trainGamePanel == null){
           trainGamePanel = new TrainGamePanel();
            trainGameActions();
        }
        mainFrame.remove(trainChooseTopicPanel);
        mainFrame.add(trainGamePanel);
        mainFrame.revalidate();
        mainFrame.repaint();
        
    }
    
    private void trainGameActions(){
        trainGamePanel.getBackButton().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mainFrame.remove(trainGamePanel);
                getTrainMenu();
            }
        });
        
        trainGamePanel.getButton1().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
               if(trainQuestions.getSelectedQuestion(trainQuestions.getSelectedID()).isAnsweTrue(1)){
                   showGif(1,false);
                   setTrainQuestion(trainQuestions.getSelectedType());
               }else{
                  showGif(2,false);
               }
            }
        });    
        trainGamePanel.getButton2().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
               if(trainQuestions.getSelectedQuestion(trainQuestions.getSelectedID()).isAnsweTrue(2)){
                   showGif(1,false);
                   setTrainQuestion(trainQuestions.getSelectedType());
               }else{
                  showGif(2,false);
               }
            }
        });    
        trainGamePanel.getButton3().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
               if(trainQuestions.getSelectedQuestion(trainQuestions.getSelectedID()).isAnsweTrue(3)){
                   showGif(1,false);
                   setTrainQuestion(trainQuestions.getSelectedType());
               }else{
                  showGif(2,false);
               }
            }
        });    
        trainGamePanel.getButton4().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
               if(trainQuestions.getSelectedQuestion(trainQuestions.getSelectedID()).isAnsweTrue(4)){
                  showGif(1,false);
                   setTrainQuestion(trainQuestions.getSelectedType());
               }else{
                  showGif(2,false);
               }
            }
        });    
        
    }
    private int findTrainQuestion(String type){
        int finder = -1;
        for(int i = 0; i<trainQuestions.getMaxSize(); i++){
            System.out.println(trainQuestions.getSelectedQuestion(i).getType());
            if(trainQuestions.getSelectedQuestion(i).getType().equals(type) && !trainQuestions.getSelectedQuestion(i).isAnswered()){
                finder = i;
                trainQuestions.setSelectedID(i);
                trainQuestions.getSelectedQuestion(trainQuestions.getSelectedID()).setAnswered();
                trainQuestions.setSelectedType(type);
                return finder;
            }
        }
        System.out.println(finder);
        //trainQuestions.setSelectedType("UNKNOWN");
        return finder;
    }
    private boolean setTrainQuestion(String type){
        if(findTrainQuestion(type) == -1){
            System.out.println("Setting Train Question");
            showGif(3,false);
            try{ //To 
                
                mainFrame.remove(trainGamePanel);
                mainFrame.add(trainChooseTopicPanel);
                mainFrame.revalidate();
                mainFrame.repaint();
            }catch(Exception e){
                System.err.println("QUESTIONS ARE USED OR NO QUESTIONIN.");
            }
            return false;
        }else {
            System.out.println(trainQuestions.getSelectedID() + "*****" + trainQuestions.getSelectedQuestion(trainQuestions.getSelectedID()).getQuestion());
            
            System.out.println(trainGamePanel.getQuestionArea().getText());
            
            trainGamePanel.getQuestionArea().setText(trainQuestions.getSelectedQuestion(trainQuestions.getSelectedID()).getQuestion());
            trainGamePanel.getButton1().setText(trainQuestions.getSelectedQuestion(trainQuestions.getSelectedID()).getAnswer1());
            trainGamePanel.getButton2().setText(trainQuestions.getSelectedQuestion(trainQuestions.getSelectedID()).getAnswer2());
            trainGamePanel.getButton3().setText(trainQuestions.getSelectedQuestion(trainQuestions.getSelectedID()).getAnswer3());
            trainGamePanel.getButton4().setText(trainQuestions.getSelectedQuestion(trainQuestions.getSelectedID()).getAnswer4());
            return true;
        }
       
   }
    
    private void showGif(int i, boolean k){ // true false, train normal
        if(gifs == null){
            gifs = new Gifs();
        }
        if(i == 1){
            Object[] options = {"CONGRATS"};
            int n = JOptionPane.showOptionDialog(mainFrame,
                   gifs.getGif(1),"TRUE",
                   JOptionPane.PLAIN_MESSAGE,
                   -1,
                   null,
                   options,
                   options[0]);
        } else if(i == 2){
            if(!k){
                Object[] options = {"TRY AGAIN!"};
                int n = JOptionPane.showOptionDialog(mainFrame,
                       gifs.getGif(2),"FALSE",
                       JOptionPane.PLAIN_MESSAGE,
                       -1,
                       null,
                       options,
                       options[0]);
            
            }else{
                Object[] options = {"WRONG ANSWER, SCORE: " + gameModel.getScore()};
                int n = JOptionPane.showOptionDialog(mainFrame,
                       gifs.getGif(2),"FALSE",
                       JOptionPane.PLAIN_MESSAGE,
                       -1,
                       null,
                       options,
                       options[0]);
            }

        }
        if(i == 3){
            if(!k){
                Object[] options = {"OK"};
                int n = JOptionPane.showOptionDialog(mainFrame,
                       gifs.getGif(3),"ALL QUESTIONS ARE COMPLETED",
                       JOptionPane.PLAIN_MESSAGE,
                       -1,
                       null,
                       options,
                       options[0]);
            }else {
            Object[] options = {"OK (Score: " + gameModel.getScore()+" )"};
                int n = JOptionPane.showOptionDialog(mainFrame,
                       gifs.getGif(3),"ALL QUESTIONS ARE COMPLETED",
                       JOptionPane.PLAIN_MESSAGE,
                       -1,
                       null,
                       options,
                       options[0]);
            }
            
        }
    }

    private void aboutUsPanel(){
        if(aboutUsModel == null){
            aboutUsModel = new AboutUsModel();
        }
        if(aboutUsView == null){
            aboutUsView = new AboutUsView();
            aboutUsView.getLb0().setText(aboutUsModel.getTitle());
            aboutUsView.getL1().setText(aboutUsModel.getCreatedText());
            aboutUsView.getL2().setText(aboutUsModel.getAuthors());
            aboutUsView.getL3().setText(aboutUsModel.getMessage());
            aboutUsView.getL4().setText(aboutUsModel.getMessage2());
            aboutUsView.getL5().setText(aboutUsModel.getEmail());
            aboutUsView.getBackButton().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mainFrame.remove(aboutUsView);
                mainMenu();
            }
        });
        }
        
        mainFrame.remove(mainPagePanel);
        mainFrame.add(aboutUsView);
        mainFrame.revalidate();
        mainFrame.repaint();
    }
}

