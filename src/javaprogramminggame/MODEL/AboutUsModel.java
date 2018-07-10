/**
 *
 * @author Asena Şahin, Halil Onur Fedai, Burak Alaydın, Barış Can
 */

package javaprogramminggame.MODEL;

public class AboutUsModel {
   private String title, createdText, authors, message, message2, email;
    
    public AboutUsModel(){
        title = "ABOUT US";
        createdText = "CREATED BY";
        authors = "Onur FEDAİ, Barış Can, Burak ALAYDIN, Asena ŞAHİN";
        message = "SEND US YOUR FEEDBACK AND COMMENTS";
        message2 = "HELP US TO IMPROVE OUR GAME";
        email = "javaprogramminggame@gmail.com";
    }

    public String getTitle() {
        return title;
    }

    public String getCreatedText() {
        return createdText;
    }

    public String getAuthors() {
        return authors;
    }

    public String getMessage() {
        return message;
    }

    public String getMessage2() {
        return message2;
    }

    public String getEmail() {
        return email;
    } 
    
}
