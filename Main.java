import java.util.*;
import java.sql.*;
import java.lang.*;
import admins.admin;
import students.student;
public class Main
{
    public static void main(String [] args)throws Exception
    {
        Scanner sc =new Scanner(System.in);
        student s=new student();
        admin a=new admin();
        String uname="",spass="";
        String auname,apass;
        int flag=0;
        Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/library_schema",
                "root", "Karthick@01");
        Statement stm = con.createStatement();
        PreparedStatement pstm;
        ResultSet rs;
        int choice;

        do {
            System.out.println("1.Student login");
            System.out.println("2.Admin login");
            System.out.println("3.Exit");
            System.out.println("Enter the choice:");
            choice=sc.nextInt();
            switch(choice)
            {
                case 1:
                    System.out.println("Enter the username:");
                    uname=sc.next();
                    System.out.println("Enter the password:");
                    spass=sc.next();
                    pstm=con.prepareStatement("select regno,password from student where username=?");
                    pstm.setString(1,uname);
                    rs=pstm.executeQuery();
                    while(rs.next())
                    {   flag=1;
                        if( (spass.equals(rs.getString("password"))) )
                        {
                            s.smain(uname,rs.getString("regno"));
                            flag++;
                        }
                        else {
                            System.out.println("incorrect password");

                        }

                    }

                    if(flag==0)
                    {
                        System.out.println("Invalid login credentials");
                        break;
                    }
                    if(flag==2) {
                        System.out.println("Logged Out Successfully");
                        break;
                    }
                case 2:
                    System.out.println("Enter the  username:");
                    auname=sc.next();
                    System.out.println("Enter the password:");
                    apass=sc.next();
                    pstm=con.prepareStatement("select admin_username,admin_password from admin where admin_username=?");
                    pstm.setString(1,auname);
                    rs=pstm.executeQuery();
                    while(rs.next())
                    {
                        if( (apass.equals(rs.getString("admin_password"))) )
                        {
                            a.amain(auname);

                        }
                        else
                        {
                            System.out.println("Invalid Login credentials");
                        }
                    }
                    System.out.println("Logged Out Successfully");
                    break;
                case 3:
                    System.out.println("Exiting the application");
                    break;
                default:
                    System.out.println("Invalid choice");



            }

        }while(choice!=3);

    }

}



