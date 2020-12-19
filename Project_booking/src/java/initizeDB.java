
import java.sql.*;

public class initizeDB {
    private Statement stmt;
    private Connection con;
    
    public initizeDB (){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/bookroom", "root", "123");
            stmt = con.createStatement();
  
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public Connection getCon(){
        return con;
    }
    
    public Statement getStmt(){
        return stmt;
    }
    
}
