
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

@Named
@RequestScoped
public class MainIn {
    
    public MainIn() {
    }
    
    public String login(){
        return "login";
    }
    
    public String unbook(){
        return "Unbooking_Room";
    }
    
    public String check(){
       return "frontCheckroom";
    }
    
    public String today(){
        return "RoomToday";
    }
    
    public String manage(){
        return "manage";
    }
    
}
