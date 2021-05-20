package StudentMgtSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class StudentManagement {
	private static Connection conn = null;
	private ResultSet rs;
	
	public StudentManagement() throws SQLException {
		String url = "jdbc:mysql://localhost/testDB";
        String uname = "root";
        String pwd = "root";
        conn = DriverManager.getConnection(url,uname,pwd);  
	
	}
	public void showRecords() throws SQLException
    {
          PreparedStatement stmt = conn.prepareStatement("Select * from Students");

            rs = stmt.executeQuery("select * from Student");
    		while (rs.next())
    			System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));

            stmt.executeUpdate();

            stmt.close();

    }
	public void searchRecord(int rollno) throws SQLException
	{
		    PreparedStatement stmt = conn.prepareStatement("Select *from Student where Rollno=" + rollno +" ");

             rs = stmt.executeQuery();
            while (rs.next())
            {
                System.out.println(rs.getInt("rollno") + " \t" + rs.getString("name") + "\t " +rs.getString("city"));

            }
	
	}

	 public int saveStudent(Student std) throws SQLException
	    {
	    	        int affectedRow = 0;

	            PreparedStatement stmt = conn.prepareStatement("insert into Student (rollno,name,city) values(?,?,?)");

	            stmt.setInt(1,std.getRollno());
	            stmt.setString(2, std.getName());
	            stmt.setString(3, std.getCity());

	            affectedRow = stmt.executeUpdate();

	            stmt.close();
	        
	        return affectedRow;

	    }
	 public int updateStudent(Student std) throws SQLException
	    {
		        int affectedRow = 0;
	             PreparedStatement stmt=conn.prepareStatement("update Student set(rollno,name,city) values(?,?,?) where rollno ="+ std.getRollno());
	             stmt.executeUpdate();
	            
	         
	            stmt.setInt(1,std.getRollno());
	            stmt.setString(2, std.getName());
	            stmt.setString(3, std.getCity());

	            affectedRow = stmt.executeUpdate();

	            stmt.close();
	        
	        return affectedRow;
	    }
	    
	 public int deleteStudent(int rollno) throws SQLException
	    { 
		 int affectedRow = 0;
	    
		 PreparedStatement stmt=conn.prepareStatement("delete from Student where rollno="+rollno);
		 affectedRow = stmt.executeUpdate();

		 stmt.close();
         return affectedRow;
	    }
	 
	 public static void main(String[] args) throws SQLException {

		 try{
			 StudentManagement obj = new StudentManagement();
			    Scanner sc=new Scanner(System.in);
				System.out.println("1. Display Records");
				System.out.println("2. Insert Record");
				System.out.println("3. Update Record");
				System.out.println("4. Search Record");
				System.out.println("5. Delete Record");
				System.out.println("6. Exit");
				System.out.println("Enter Your Choice:"); 
			
				int num=sc.nextInt();
			
				switch(num){    
				    case 1: obj.showRecords();  
				        break;  
				    
				    case 2: System.out.println("Enter Rollno:");
							int rollno =sc.nextInt();
							System.out.println("Enter Name:");
							String name = sc.next();
							System.out.println("Enter city:");	
							String city = sc.next();
							Student std=new Student(rollno, name, city);
							int res= obj.saveStudent(std);
							if (res != 0)
				                System.out.println("record Deleted");
				            else
				                System.out.println("record not Deleted");
				    	    
							break;  
				    case 3:
					    	System.out.println("Enter Rollno:");
							int rollno1 =sc.nextInt();
							System.out.println("Enter Name:");
							String name1 = sc.next();
							System.out.println("Enter city:");	
							String city1 = sc.next();
							Student std1=new Student(rollno1, name1, city1);
							int res1= obj.updateStudent(std1);
							if (res1 != 0)
				                System.out.println("record Updated");
				            else
				                System.out.println("record not Updated");
				    	
				    	break;  
				    case 4:  
				    	System.out.println("Enter Rollno:"); 
				    		int rollno2=sc.nextInt();
				    	    obj.searchRecord(rollno2);
				        break;  
				    case 5:
					    	System.out.println("Enter Rollno:"); 
				    		int rollno3=sc.nextInt();
				    		int res2=obj.deleteStudent(rollno3);
				    	  
				            if (res2 != 0)
				                System.out.println("record Deleted");
				            else
				                System.out.println("record not Deleted");
				    	    
				    	break;
				    case 6:  System.exit(0);
				    	break;  
				    	
				    default:System.out.println("Not in 1, 2, 3, 4 or 5");  
			    }  
				sc.close();
			}
		 catch(Exception e){
		 }
		 
		finally {
				conn.close();
				System.out.println("Database Closed....");
		 	 }
  }
}

