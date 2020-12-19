
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

@Named
@RequestScoped
public class BackCheckRoom {
    private Statement stmt;
    private Connection con;
    private String dateIn;
    private String dateOut;
    
    initizeDB db = new initizeDB();
    
    private LocalDate checkIn;
    private LocalDate checkOut;
    
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    public BackCheckRoom() {
    }
    public void setDateIn(String dateIn) {
        this.dateIn = dateIn;
    }

    public void setDateOut(String dateOut) throws SQLException {
        this.dateOut = dateOut;

    }
    
    public String getDateIn() {
        return "";
    }

    public String getDateOut() {
        return "";
    }
    
    public String book(){
        return "frontBooking";
    }

    public String getCheckroom() throws SQLException {
        if(dateIn ==  null){
            return "";
        }
        else{
            stmt = db.getStmt();
            con = db.getCon();
            String mark = "<h2 style = \"text-align: center; color: green\">";
            System.out.println(mark);

            ResultSet rest = stmt.executeQuery("select * from room;");
            ResultSetMetaData reMeta = rest.getMetaData();

            for (int i = 1; i<= reMeta.getColumnCount(); i++){
                mark += reMeta.getColumnName(i);
                mark += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
            }
            mark += "<br/>";
            System.out.println(mark);
            
            rest.close();

            String command_sql = "select * from room,booking where (('"+dateIn+"' between checkin and checkout) or ('"+dateOut+"' between checkin and checkout )) and room.roomid = booking.roomid group by room.roomid;";
            ResultSet open = stmt.executeQuery(command_sql);
            ArrayList tempNotRoom = new ArrayList();
                while (open.next()) {
                    String tempS = String.valueOf(open.getObject(1));
                    int temp = Integer.parseInt(tempS);
                    tempNotRoom.add(temp);
                    System.out.println("open = "+tempS);
                }
            System.out.println("\n");
            open.close(); 

            for (int i = 0; i < tempNotRoom.size();i++)
            {
                System.out.println("Booking room"+tempNotRoom.get(i));
            }

            // select all roomid
            String command_sql2 = "select roomid from room;";
            ResultSet open2 = stmt.executeQuery(command_sql2);

            ArrayList tempNotRoom2 = new ArrayList();
            while (open2.next()) {
                String tempS = String.valueOf(open2.getObject(1));
                int temp = Integer.parseInt(tempS);
                tempNotRoom2.add(temp);
                System.out.println("open2 = "+tempS);
            }

            for (int i = 0; i < tempNotRoom2.size();i++)
            {
                System.out.println("All room "+tempNotRoom2.get(i));
            }

            for (int i = 0; i < tempNotRoom2.size(); i++){
                String tempFree2 = String.valueOf(tempNotRoom2.get(i));
                boolean check = true;
                for (int j = 0; j < tempNotRoom.size(); j++){
                    String tempFree = String.valueOf(tempNotRoom.get(j));
                    if (tempFree.equals(tempFree2)){
                        check = false;
                        
                    }
                }
                if (check){
                    String temp = String.valueOf(tempNotRoom2.get(i));

                    ResultSet print = stmt.executeQuery("select * from room where roomid = "+temp+";");
                    print.next();
                    mark += "<br />";
                    System.out.println(mark);
                    for (int j = 1; j<= reMeta.getColumnCount(); j++){
                        String temp2 = String.valueOf(print.getObject(j));
                        
                        if(temp2.equals("true")){
                            mark += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ready";
                            System.out.println("Ready");
                        }
                        else if(temp2.equals("false")){
                            mark += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Not ready";
                            System.out.println("Not ready");
                        }
                        else{
                            System.out.print(temp2+"\t");
                            mark += temp2;
                        }
                        mark += " ";
                        System.out.println(mark);
                    }    
                }
                check = true;
            }


            mark += "</h2>";

            System.out.println(mark);

            return mark;
        }
    }
   
}
