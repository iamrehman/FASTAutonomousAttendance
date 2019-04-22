/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package faa;

import com.sun.org.apache.xalan.internal.lib.ExsltDatetime;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author iamrehman
 */
public class QuerryController {
    
     

    public static  ArrayList<Admin> getAdmin(String id) throws SQLException{
        ResultSet rs= FAA.getResultSet("select * from admin where id= '" + id+ "' ;");
        
        ArrayList<Admin> admins = new ArrayList<>();
        while(rs.next()){
            
            //Admin(String id, String name,  String dob, String address, String phone, String pass)
            Admin admin = new Admin(
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getString("dob"),
                    rs.getString("address"),
                    rs.getString("phone"),
                    rs.getString("password")
            );
            
            admins.add(admin);
        }
        
        return admins;
    }
    
    
    public boolean addAdmin(Admin admin) throws IOException{
        // Admin(String id, String name,  String dob, String address, String phone, String pass
         String INSERT_RECORD = "insert into admin(id, name, dob, address, phone, password ) values(?, ?, ?, ? ,? ,?)";
        
       PreparedStatement pstmt=null;
        try {
             pstmt = FAA.con.prepareStatement(INSERT_RECORD);
             pstmt.setString(1, admin.id.getValue());
             pstmt.setString(2, admin.name.getValue());
             pstmt.setString(3, admin.dob.getValue());
             pstmt.setString(4, admin.address.getValue());
             pstmt.setString(5, admin.phone.getValue());
             pstmt.setString(6, admin.pass.getValue());
             pstmt.executeUpdate();
        } catch (SQLException e) {
                    while (e != null) {
                String errorMessage = e.getMessage();

                show_warn(errorMessage);

                e = e.getNextException();
                } 
           return false;
       }   
        
        show_warn("Account Successfully created!");
        return true;
    }
    
     public void  show_warn(String message) throws IOException{
         
            FAA.warning_message=message;
            
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("warning.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            /* first close this window */

            Stage stage = new Stage();
            FAA.warn_scene=stage;
            
            stage.setScene(new Scene(root1)); 
            stage.showAndWait();
  
    }
    
    
     public ArrayList<Attendance> student_rec(String id) throws IOException{
         
         ArrayList<Attendance> attend= new ArrayList<>();
         
        ResultSet rs=null;
         
        try {
            rs = FAA.getResultSet("select * from attendance where StudentId= '" + id+ "' ;");
                   while(rs.next()){ 
                       
                   attend.add(new Attendance(rs.getString("record_no"), rs.getString("StudentID"), rs.getString("Date")));                       
                }
        
        } 
        catch (SQLException e) {
                     while (e != null) {
                 String errorMessage = e.getMessage();

                 show_warn(errorMessage);

                 e = e.getNextException();
          }          
         
        }
   return attend;  
     
     }
    
    
    public ArrayList<Student> students_on_day(String date) throws IOException {
         ArrayList<Student> students= new ArrayList<>();
         
         ArrayList<String> ids= new ArrayList<>();
         ResultSet rs=null;
         
        try {
            rs = FAA.getResultSet("select * from attendance where date= '" + date+ "' ;");
                   while(rs.next()){                            
                       ids.add(rs.getString("StudentId"));        
                       
              
             }
        
        } 
        catch (SQLException e) {
                     while (e != null) {
                 String errorMessage = e.getMessage();

                 show_warn(errorMessage);

                 e = e.getNextException();
        }   
          
       
    } 
        
        try {
            students=getStudents(ids);
        } catch (SQLException ex) {
            Logger.getLogger(QuerryController.class.getName()).log(Level.SEVERE, null, ex);
        }
         return students;
 }
    public ArrayList<Student> getStudents (ArrayList<String> ids) throws SQLException{
        
        ArrayList<Student> students= new ArrayList<>();
        
        for (String id: ids){
            if (id.equals("Unknown Face") )
            {
                students.add(new Student("00000000", "unknown-face", "---------"));
                continue;
            }
           ResultSet rs= FAA.getResultSet("select * from student where StudentId= '" + id+ "' ;");
           while(rs.next()) {
               Student student = new Student(
                       rs.getString("StudentId"),
                       rs.getString("name"),
                       rs.getString("section")
                       
               );
               students.add(student);
           }
        }
        
        return students;
    }
    
    public boolean AddAttendance(ArrayList<Student> students) throws IOException{
        // Admin(String id, String name,  String dob, String address, String phone, String pass
       
      for (Student x :students){ 
        if(x.id.equals("00000000")){
            continue;
        }
        String INSERT_RECORD = "insert into attendance(studentId,Date ) values(?, ?)";

        PreparedStatement pstmt=null;
         try {
              pstmt = FAA.con.prepareStatement(INSERT_RECORD);
            //  pstmt.setString(1, x.record_no.getValue());
              pstmt.setString(1, x.id.getValue());
              java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
              pstmt.setString(2, date.toString());
          
              pstmt.executeUpdate();
         } catch (SQLException e) {
                     while (e != null) {
                 String errorMessage = e.getMessage();

                 show_warn(errorMessage);

                 e = e.getNextException();
                 } 
            return false;
        }   
     }
      
      return true;
   }
    
}
