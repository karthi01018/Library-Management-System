package admins;
import java.util.*;
import java.sql.*;
import java.lang.*;
public class admin {
String username;
Scanner sc=new Scanner(System.in);
public void amain(String username)throws Exception
{
    this.username=username;
    System.out.printf("\n  ------------------------------------------");
    System.out.printf("\n \t| Hello "+ username+",Welcome to LMS \t|");
    System.out.printf("\n  ------------------------------------------");
    int choice=0;
    System.out.printf("\n1.Add Student\n");
    System.out.println("2.Add Books");
    System.out.println("3.View Checked out Books");
    System.out.println("4.Exit");
    do {
     System.out.println("Enter the choice:");
     choice=sc.nextInt();
     switch(choice)
     {
         case 1:
             this.add_student();
             break;
         case 2:
             this.add_books();
             break;
         case 3:
             this.checkedout_books();
             break;
         case 4:
             break;
         default:
             System.out.println("Invalid Choice !");

     }


    }while(choice!=4);
}
public void add_student()throws Exception
{
    Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/library_schema",
            "root", "Karthick@01");
    Statement stm = con.createStatement();
    String sname,uname,pass,email,regno;
    int age=0;
    PreparedStatement pstm=con.prepareStatement("insert into student values(?,?,?,?,?,?)");
    System.out.println("Enter the student regno:");
    regno=sc.next();
    System.out.println("Enter the student name:");
    sc.nextLine();
    sname=sc.nextLine();
    System.out.println("Enter the age:");
    age=sc.nextInt();
    System.out.println("Enter the student email-id: ");
    email=sc.next();
    System.out.println("Enter or Create new username:");
    uname=sc.next();
    System.out.println("Enter or create new Password:");
    pass=sc.next();
    pstm.setString(1,regno);
    pstm.setString(2,sname);
    pstm.setInt(3,age);
    pstm.setString(4,email);
    pstm.setString(5,uname);
    pstm.setString(6,pass);
    pstm.executeUpdate();
    System.out.println("Successfully created student login credentials");


}
public void add_books()throws Exception
{
    Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/library_schema",
            "root", "Karthick@01");
    Statement stm = con.createStatement();
    String bname,author;
    int bid=0;
    PreparedStatement pstm=con.prepareStatement("insert into books values(?,?,?)");
    System.out.println("Enter the Book-id: ");
    bid=sc.nextInt();
    System.out.println("Enter the Book Name: ");
    sc.nextLine();
    bname=sc.nextLine();
    System.out.println("Enter the Author name: ");
    author=sc.nextLine();
    pstm.setInt(1,bid);
    pstm.setString(2,bname);
    pstm.setString(3,author);
    pstm.executeUpdate();
    System.out.println("Book details had been added successfully in the db");

}
public void checkedout_books()throws Exception
{
    Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/library_schema",
            "root", "Karthick@01");
    Statement stm = con.createStatement();
    ResultSet rs=stm.executeQuery("select c.book_id,c.regno,s.name from checkout_cart c,student s where c.regno=s.regno");
    System.out.printf("\n------------------------------------------------------------");
    System.out.printf("\n| Book-id | \t\t| Student regno | \t | Student Name |");
    System.out.printf("\n------------------------------------------------------------");
    while(rs.next())
    {
    System.out.printf("\n\t %d\t\t\t %s \t\t\t\t %s",rs.getInt("book_id"),rs.getString("regno"),rs.getString("name"));
    }
    System.out.println();
}
}
