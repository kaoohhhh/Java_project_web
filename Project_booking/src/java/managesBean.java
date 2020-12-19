

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;


@Named(value = "managebean")
@ManagedBean
@SessionScoped
public class managesBean implements Serializable{
    private String roomid;
    private String status;
    Connection con;
    String url = "jdbc:mysql://localhost:3306/bookroom";
        String username = "root";
        String password = "123";
        PreparedStatement pre;
    public List<Rooms> getRooms() throws ClassNotFoundException, SQLException {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url,username,password);
        
        }catch (SQLException ex){
            System.out.println("in exec");
            System.out.println(ex.getMessage());
        }
        
        List<Rooms> rooms = new ArrayList<Rooms>();
        
        pre = con.prepareStatement("select roomid ,status from room");
        ResultSet rs = pre.executeQuery();
        
        while(rs.next()){
            Rooms room = new Rooms();
            room.setRoomid(rs.getString("roomid"));
            room.setStatus(rs.getString("status"));
            rooms.add(room);
        }
        return rooms;
    }
    public void connectDB() throws ClassNotFoundException, SQLException{
        String url = "jdbc:mysql://localhost:3306/bookroom";
        String username = "root";
        String password = "123";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url,username,password);
        
        }catch (SQLException ex){
            System.out.println("in exec");
            System.out.println(ex.getMessage());
        }
    }
     public void Ready(String roomid,String status) throws ClassNotFoundException, SQLException {
        connectDB();
        String sql=("update room set status = 1 WHERE roomid= '"+roomid+"' ;");
         pre.executeUpdate(sql);
    }
     public void NotReady(String roomid,String status) throws ClassNotFoundException, SQLException {
        connectDB();
        String sql=("update room set status = 0 WHERE roomid= '"+roomid+"' ;");
         pre.executeUpdate(sql);
    }
     
     public managesBean() {
    }
}
