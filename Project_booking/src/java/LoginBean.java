import java.io.*;
import java.util.*;
import java.sql.*;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

@Named(value = "loginbean")
@SessionScoped
public class LoginBean implements Serializable{
       private String user;
       private String pass;
       private Connection con;
       private ResultSet result;
       private Statement stmt;
   
    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
       
 

    public void connectDB() throws ClassNotFoundException, SQLException{
        String url = "jdbc:mysql://localhost:3306/bookroom";
        String username = "root";
        String password = "123";
        System.out.println("In connect "+user+" "+pass);
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url,username,password);
        
        }catch (SQLException ex){
           
            System.out.println("Error at connect");
        }
    } 
    public String logout(){
        this.user = null;
        return "index";
    }
    public String login() throws ClassNotFoundException, SQLException {
       connectDB();
        String sql = "select * from login where username='"+user+"' and password='"+pass+"';";
        
        try{
            stmt = con.createStatement();
            result = stmt.executeQuery(sql);
            if(result.next()){
                return "index";
            }else{
                   return "Don't have username and password";
                }
        }catch(SQLException ex)
            {
                ex.printStackTrace();
            }
        return "Error";
    }   
       
}