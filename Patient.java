package HospitalManagementSystem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.mysql.cj.protocol.Resultset;
public class Patient {
 
	private Connection connection;
	private Scanner scanner;
	
	public Patient(Connection connection,Scanner scanner) {
		this.connection=connection;
		this.scanner=scanner;
	}
	
	public void addPatient()
	{
		System.out.println("Enter  Patient Name: ");
		String name=scanner.next();
		System.out.println("Enter patient age");
		int age=scanner.nextInt();
		System.out.println("Enter patient Gender");
		String gender=scanner.next();
		
		try {
			
			String queryString="INSERT INTO patients(name,age,gender)VALUES(?,?,?)";
			PreparedStatement preparedStatement=connection.prepareStatement(queryString);
			preparedStatement.setString(1, name);
			preparedStatement.setInt(2, age);
			preparedStatement.setString(3, gender);
			int affectedRows=preparedStatement.executeUpdate();
			if(affectedRows>0)
			{
				System.out.println("Patient Added Successfully!!");
			}else {
				System.out.println("Failed to add Patient");
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	
	public void viewPatients()
	{
		//bring all data from database and print it!!
		String query="select * from patients";
		
		try {
			
			PreparedStatement preparedStatement=connection.prepareStatement(query);
			ResultSet resultSet=preparedStatement.executeQuery();
			System.out.println("Patients are : ");
			System.out.println("+------------+--------------------+-----------+------------+");
			System.out.println("| Patient Id | Name               | Age       | Gender     |");
			System.out.println("+------------+--------------------+-----------+------------+");
			                                                                                 
			while(resultSet.next())
			{
				int id=resultSet.getInt("id");
				String nameString=resultSet.getString("name");
				int age=resultSet.getInt("age");
				String genderString=resultSet.getString("gender");
				System.out.printf("|%-13s|%-21s|%-12s|%-13s|\n",id,nameString,age,genderString);
				System.out.println("+------------+--------------------+-----------+------------+");
				
			}
			
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public boolean getPatientById(int id)
	{
		String queryString="SELECT * FROM PATIENTS WHERE ID= ?";
		try {
			PreparedStatement preparedStatement=connection.prepareStatement(queryString);
			preparedStatement.setInt(1, id);
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()) {
				return true;
			}else {
				return false;
			}
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
			
	
	
}
