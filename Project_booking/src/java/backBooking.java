
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javafx.scene.control.Alert;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

@Named
@RequestScoped
public class backBooking {
    private Statement stmt;
    private Connection con;
    private String dateIn;
    private String dateOut;
    private String name;
    private String roomId;
    private String phone;
    
    private PreparedStatement ppstmt;
    
    initizeDB db = new initizeDB();

    private LocalDate checkIn;
    private LocalDate checkOut;
    
    
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    public backBooking (){}

    public void setDateIn(String dateIn) {
        this.dateIn = dateIn;
    }

    public void setDateOut(String dateOut) {
        this.dateOut = dateOut;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getDateIn() {
        return "";
    }

    public String getDateOut() {
        return "";
    }

    public String getName() {
        return "";
    }

    public String getPhone() {
        return "";
    }

    public String getRoomId() {
        return "";
    }
    
    public String getStatus(){
        if(roomId == null){
            return "";
        }
        else{
            boolean have = true;
            stmt = db.getStmt();
            con = db.getCon();
            try{
                checkIn = LocalDate.parse(this.dateIn);
                checkOut = LocalDate.parse(this.dateOut);
                int dif = checkOut.compareTo(checkIn);
                int difn = checkIn.compareTo(LocalDate.now());
                if(dif>= 0 && difn>=0)
                {
                    //get bookcode from booking table
                    ResultSet rset2 = stmt.executeQuery("select bookcode from booking group by bookcode;");
                    int num = 1;
                    String temp;
                    //start line and go next line
                    while(rset2.next())
                    {
                        temp = String.valueOf(rset2.getObject(1));
                        int temp2 = Integer.parseInt(temp);
                        System.out.println(temp2);
                        if(num != temp2){
                            break;
                        }
                        num += 1;
                    }
                    System.out.print(num);
                    
                    ppstmt = con.prepareStatement("insert into booking value (?,?,?,?,?,?)");
                    
                    ppstmt.setInt(1, num);
                    ppstmt.setString(2, name);
                    ppstmt.setString(3, phone);
                    ppstmt.setString(4, dateIn);
                    ppstmt.setString(5, dateOut);
                    ppstmt.setString(6, roomId);
                    ppstmt.executeUpdate();
                    
                    have = false;
                }
                else
                {
                    return "<h2 style = \"text-align: center; color: red\">Error, wrong date Please check again.</h2>";
                }
                
            }
            catch(SQLException ex){
                ex.printStackTrace();
            }
            if(have)
            {
                return "<h2 style = \"text-align: center; color: red\">There is no room number specified.</h2>";
            }
            else{
                return "<h2 style = \"text-align: center; color: green\">Booking completed.</h2>";
            }
        }
         
    }
    
    
    
}
