
import java.sql.*;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

@Named
@RequestScoped
public class Unbook {
    Connection con = null;
    private Statement stmt;
    private PreparedStatement pstmt;
    
    private String name;
    private String ID;
    private String phone;
    private String checkIn ="";
    private String checkOut ="";
    private boolean have;
    
    public Unbook(){
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public String getName() {
        return name;
    }

    public String getID() {
        return ID;
    }

    public String getPhone() {
        return phone;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public boolean isHave() {
        return have;
    }

    public void setHave(boolean have) {
        this.have = have;
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
    
    public String back(){
        return "index";
    }
    
    public String getWaring() throws ClassNotFoundException, SQLException{
        if(name==null)
             return "";
        else{
            connectDB();
            stmt = con.createStatement();
         String queryString = "select * from booking";
         ResultSet rset = stmt.executeQuery(queryString);
         
         setHave(true);
            while(rset.next())
            { 
                System.out.println("Enter");
                   String DateIn = String.valueOf(rset.getObject(4));
                   String DateOut = String.valueOf(rset.getObject(5));
                   //check data, Is it same in database or not?
                   if(ID.equals(rset.getString(6))&&name.equals(rset.getString(2))&&phone.equals(rset.getString(3))&&checkIn.equals(DateIn)&&checkOut.equals(DateOut))
                   {
                        //delete data in database
                        pstmt = con.prepareStatement("delete from booking where roomid=? and checkin=?;");
                        pstmt.setString(1, ID);
                        pstmt.setString(2, DateIn);
                        pstmt.executeUpdate();
                        setHave(false);
                        break;
                   }
            }
            
            if(!isHave()){
              return "<p>Have unbooked room!</p>";
            }
            else{
              return "<p>Hadn't had that data!!!</p>";
            }
        }
    } 
}
