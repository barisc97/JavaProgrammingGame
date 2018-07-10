/**
 *
 * @author Asena Şahin, Halil Onur Fedai, Burak Alaydın, Barış Can
 */

package javaprogramminggame.MODEL;

import javax.swing.ImageIcon;

public class Gifs {
    private ImageIcon[] trueGifs;
    private ImageIcon[] falseGifs;
    private ImageIcon endGif;
    
    public Gifs(){
        trueGifs = new ImageIcon[3];
        trueGifs[0]= new ImageIcon("./resources/Gif.gif");
        trueGifs[1]= new ImageIcon("./resources/Gif2.gif");
        trueGifs[2]= new ImageIcon("./resources/Gif3.gif");
        falseGifs = new ImageIcon[3];
        falseGifs[0]= new ImageIcon("./resources/FalseGif.gif");
        falseGifs[1]= new ImageIcon("./resources/FalseGif2.gif");
        falseGifs[2]= new ImageIcon("./resources/FalseGif3.gif");
        endGif = new ImageIcon("./resources/EndGif.gif");
    }
    
    public ImageIcon getGif(int i){ // 1 true 2 false 3 end
    
        if(i == 1){
            return trueGifs[(int)(Math.random()*trueGifs.length)];
        } else if(i == 2){
            return falseGifs[(int)(Math.random()*falseGifs.length)];
        }else {
            return endGif;
        }
        
    }
    
}
