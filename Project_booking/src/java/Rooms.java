

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.*;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.*;

@Named(value = "rooms")
@SessionScoped
public class Rooms implements Serializable{
    private String roomid;
    private String status;
    
    public Rooms(){
        
    }
    public Rooms(String roomid,String status){
        this.roomid=roomid;
        this.status=status;
    }

    public String getRoomid() {
        return roomid;
    }

    public String getStatus() {
        return status;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
   
}

    
    

