package HospitalManagementSystem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.mysql.cj.protocol.Resultset;
public class Doctor {
 
	private Connection connection;
	
	public Doctor(Connection connection) {
		this.connection=connection;
	}
	
	
	
	public void viewDoctors()
	{
		//bring all data from database and print it!!
		String query="select * from doctors";
		
		try {
			
			PreparedStatement preparedStatement=connection.prepareStatement(query);
			ResultSet resultSet=preparedStatement.executeQuery();
			System.out.println("Doctors are : ");
			System.out.println("+------------+--------------------+-----------+---------+");
			System.out.println("| Doctor Id  | Name               |Specialization       |");
			System.out.println("+------------+--------------------+-----------+---------+");
			                                                                                 
			while(resultSet.next())
			{
				int id=resultSet.getInt("id");
				String nameString=resultSet.getString("name");
		
				String specialization=resultSet.getString("specialization");
				System.out.printf("|%-13s|%-21s|%-20s|\n",id,nameString,specialization);
				System.out.println("+------------+--------------------+-----------+---------+");
				
			}
			
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public boolean getDoctorById(int id)
	{
		String queryString="SELECT * FROM DOCTORS WHERE ID= ?";
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
