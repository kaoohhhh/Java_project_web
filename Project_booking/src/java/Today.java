
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import java.sql.*;
import java.time.LocalDate;

@Named
@RequestScoped
public class Today {
    private String ID;
    private boolean checkRoom;
    private boolean checkStatus;
    Connection con = null;
    private Statement stmt;
    private PreparedStatement pstmt;
    
    public Today() {
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public boolean isCheckRoom() {
        return checkRoom;
    }

    public void setCheckRoom(boolean checkRoom) {
        this.checkRoom = checkRoom;
    }

    public boolean isCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(boolean checkStatus) {
        this.checkStatus = checkStatus;
    }
    
    public void connectDB() throws ClassNotFoundException,SQLException{

         String url = "jdbc:mysql://localhost:3306/bookroom";
         String username = "root";
         String password = "123";
         try{
             Class.forName("com.mysql.cj.jdbc.Driver");
             con = DriverManager.getConnection(url, username, password);
         }
         catch(SQLException ex){
             System.out.println("Error at connect");
         }
    }  
    
    public String check() throws ClassNotFoundException, SQLException{
        String line ="";
        if(ID==null)
             return "";
        else{
            connectDB();
            stmt = con.createStatement();
            String queryString = "select roomid from room";
            ResultSet rset = stmt.executeQuery(queryString);
         
            setCheckRoom(false);
                
            LocalDate date = LocalDate.now();
            while (rset.next()) {
                String get = String.valueOf(rset.getObject(1));
                if(getID().equals(get))
                {
                   setCheckRoom(true);
                }
            }
        
            if(isCheckRoom()){
                pstmt = con.prepareStatement("select roomid,checkin,checkout from booking where ? between checkin and checkout group by roomid;");
                System.out.println("date = "+date.toString());
                pstmt.setString(1,date.toString());
                ResultSet rset2 = pstmt.executeQuery();
                    
                setCheckStatus(true);
                    
                    // Check status room
                    while (rset2.next()) {            
                        String get = String.valueOf(rset2.getObject(1));
                        if (ID.equals(get)){
                            setCheckStatus(false);
                        }
                    }

                    if(isCheckStatus()){
                        return "This Room Free Can Book!";
                    }
                    else{
                        
                        line = "This Room NOT Free <br/><br/>";
                        
                        try{
                            ResultSet rest = stmt.executeQuery("select * from booking;");
                            ResultSetMetaData reMeta = rest.getMetaData();
                            ResultSet rset3 = stmt.executeQuery("select * from booking where roomid= "+ID+" and '"+date.toString()+"' between checkin and checkout group by roomid;");
                            
                            rset3.next();
                            for (int i = 1; i<= reMeta.getColumnCount(); i++){
                                line += ""+reMeta.getColumnName(i)+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
                                line += ""+rset3.getObject(i) + "<br/>";
                            }
                            line += "<br/>";
                        }
                        catch (Exception ex) {
                             ex.printStackTrace();
                        }
                    }
                    rset2.next();  
            }
            else {
                  return "There is no room for this number.";
            }
                rset.close();  
            }
        return line;
    }
    
    public String book(){
        return "frontBooking";
    }
    
    public String back(){
        return "index";
    }
}
