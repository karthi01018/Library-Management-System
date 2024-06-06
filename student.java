package students;
import java.sql.*;
import java.util.*;
import java.lang.*;
public class student {
    String regno;
    Scanner sc=new Scanner(System.in);
    public void smain(String username,String regno)throws Exception
    {
        this.regno=regno;
        //Scanner sc=new Scanner(System.in);
        System.out.printf("\n  ------------------------------------------");
        System.out.printf("\n \t| Hello "+ username+",Welcome to LMS \t|");
        System.out.printf("\n  ------------------------------------------");
        int choice=0;
        String sstring;
        System.out.printf("\n1.View My profile\n");
        System.out.println("2.Search books");
        System.out.println("3.Track Checkout books");
        System.out.println("4.Return Book");
        System.out.println("5.Change username");
        System.out.println("6.Change password");
        System.out.println("7.Logout");
        do{
            System.out.println("Enter the choice:");
            choice=sc.nextInt();
            switch(choice)
            {
                case 1:
                    this.student_details(username);
                    break;
                case 2:
                    System.out.println("Enter the book name:");
                    sc.nextLine();
                    sstring=sc.nextLine();
                    search_books(sstring);
                    break;
                case 3:
                    this.track_checkout_cart(regno);
                    break;
                case 4:
                    this.return_book();
                    break;
                case 5:
                    this.change_username();
                    return;
                case 6:
                    this.change_password();
                    return;
                case 7:
                    break;

                default:
                    System.out.println("Invalid Option");
            }
        }while(choice!=7);


    }

    public void student_details(String username)throws Exception
    {
        Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/library_schema",
                "root", "Karthick@01");

        Statement stm = con.createStatement();
        PreparedStatement pstm=con.prepareStatement("select * from student where username=?");
        pstm.setString(1,username);
        ResultSet rs=pstm.executeQuery();
        while(rs.next()) {
            System.out.println("regno:"+rs.getString("regno") + " ");
            System.out.println("Name:"+rs.getString("name")+" ");
            System.out.println("Age:"+rs.getInt("age")+" ");
            System.out.println("Email_id:"+rs.getString("email_id")+" ");
        }


    }
    public void search_books(String bname)throws Exception
    {
        int flag=0;
        int bid=0;
        Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/library_schema",
                "root", "Karthick@01");
        Statement stm = con.createStatement();
        PreparedStatement pstm=con.prepareStatement("select * from books where book_name=?");
        pstm.setString(1,bname);
        ResultSet rs=pstm.executeQuery();
        while(rs.next())
        {
            System.out.println("book name: "+rs.getString("book_name"));
            System.out.println("book id: "+rs.getInt("book_id"));
            flag=1;
            bid=rs.getInt("book_id");
        }
        String choice = "";
        if(flag==1) {

            System.out.println("Wanna checkout this book (Yes/No):");
            choice = sc.next();
        }
        else
        {
            System.out.println("Book not found in the database");
        }
        if(choice.equalsIgnoreCase("Yes"))
        {
            pstm=con.prepareStatement("insert into checkout_cart values(?,?)");
            pstm.setString(1,regno);
            pstm.setInt(2,bid);
            pstm.executeUpdate();
            System.out.println("Book details updated in checked out cart");

        }




    }
    public void track_checkout_cart(String regno)throws Exception
    {
        int flag=0;
        Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/library_schema",
                "root", "Karthick@01");
        Statement stm = con.createStatement();
        PreparedStatement pstm=con.prepareStatement("select * from checkout_cart where regno=?");
        pstm.setString(1,regno);
        ResultSet rs=pstm.executeQuery();
        System.out.printf("\n----------------------------------------");
        System.out.printf("\n| Book-id | \t\t| Student regno | ");
        System.out.printf("\n----------------------------------------");
        while(rs.next())
        {
            flag=1;

            System.out.printf("\n %d \t \t \t %s",rs.getInt("book_id"),regno);


        }
        if(flag==0)
        {
            System.out.println("Your Checkout Cart is Empty");
        }
        flag=0;
        System.out.println();
    }
    public void return_book()throws Exception
    {
        String bname;int bid=0,flag=0;
        Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/library_schema",
                "root", "Karthick@01");
        Statement stm = con.createStatement();
        PreparedStatement pstm=con.prepareStatement("select * from books where book_name=?");
        System.out.println("Enter the boook name to return :");
        sc.nextLine();
        bname=sc.nextLine();
        //System.out.println("entered bname:"+bname);
        pstm.setString(1,bname);
        ResultSet rs=pstm.executeQuery();
        while(rs.next())
        {
            //System.out.println("msg from rs.next while loop");
            bid=rs.getInt("book_id");
            flag=1;
            // System.out.println("Book id fetched:"+bid);
        }
        if(flag==1)
        {
            PreparedStatement pstm1=con.prepareStatement("delete from checkout_cart where book_id=?");
            pstm1.setInt(1,bid);
            pstm1.executeUpdate();
            // stm.executeUpdate("delete from checkout_cart where book_id=1001");
            System.out.println("Book Returned Successfully");

        }
        else {
            System.out.println("There is an error in returning a book ");
        }

    }
    public void change_username()throws Exception
    { Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/library_schema",
            "root", "Karthick@01");
        Statement stm = con.createStatement();
        String uname="";
        System.out.println("Enter the new username:");
        sc.nextLine();
        uname=sc.nextLine();
        PreparedStatement pstm=con.prepareStatement("update student set username=? where regno=?");
        pstm.setString(1,uname);
        pstm.setString(2,regno);
        pstm.executeUpdate();
        System.out.println("Username updated successfully");
        System.out.println("New username is "+uname);
    }
    public void change_password()throws Exception
    {Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/library_schema",
            "root", "Karthick@01");
        Statement stm = con.createStatement();
        String pass;
        System.out.println("Enter new password:");
        pass=sc.next();
        PreparedStatement pstm=con.prepareStatement("update student set password=? where regno=?");
        pstm.setString(1,pass);
        pstm.setString(2,regno);
        pstm.executeUpdate();
        System.out.println("Password updated successfully");
        System.out.println("Your new password is :"+pass);


    }






}
