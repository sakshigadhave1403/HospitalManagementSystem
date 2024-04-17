package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.mysql.cj.PreparedQuery;
import com.mysql.cj.x.protobuf.MysqlxCrud.Insert;

public class HospitalManagementSystem {
    private static final String url="jdbc:mysql://localhost:3306/hospital";
    private static final String username="root";
    private static final String password="Yojana@001";
    
    
    public static void main(String[] args) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		Scanner scanner=new Scanner(System.in);
		try {
			Connection connection=DriverManager.getConnection(url,username,password);
			Patient patient=new Patient(connection, scanner);
			Doctor doctor=new Doctor(connection);
			while(true)
			{
				System.out.println("Hospital Management System");
				System.out.println("1. Add Patient");
				System.out.println("2. View Patients");
				System.out.println("3. View Doctors");
				System.out.println("4. Book Appointment");
				System.out.println("5. Exit");
				System.out.println("Enter your choice: ");
				
				int choice=scanner.nextInt();
				
				switch(choice)
				{
				case 1:
					//Add patient
					patient.addPatient();
					System.out.println();
					break;
					
				case 2:
					//View Patient
					patient.viewPatients();
					System.out.println();
					break;
				case 3:
					//View Doctors
					doctor.viewDoctors();
					System.out.println();
					break;
				case 4:
					//Book Appointment
					bookAppointment(patient, doctor, connection, scanner);
					System.out.println();
					break;
					
				case 5:
					//Exit
					System.err.println("Thank you for using our system");
					return;
				default:
					System.out.println("Enter valid choice");
				}
				
				
			}
		}
		catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();

		}
	}
    
    public static void bookAppointment(Patient patient,Doctor doctor,Connection connection,Scanner scanner)
    {
    	 System.out.println("Enter Patient Id: ");
    	 int patientId=scanner.nextInt();
    	 System.out.println("Enter Doctor Id: ");
    	 int doctorId=scanner.nextInt();
    	 System.out.println("Enter appointment date(YYYY-MM-DD): ");
    	 String appointmentdate =scanner.next();
    	 if(patient.getPatientById(patientId) && doctor.getDoctorById(doctorId))
    	 {
    		 if(checkDoctorAvailability(doctorId,appointmentdate,connection))
    		 {
    			 String appointmentquery="INSERT INTO appointments(patient_id,docotr_id,appointment_date)";
    			 try {
					PreparedStatement preparedStatement=connection.prepareStatement(appointmentquery);
					preparedStatement.setInt(1, patientId);
					preparedStatement.setInt(2, doctorId);
					preparedStatement.setString(3, appointmentdate);
					int rowsAffected=preparedStatement.executeUpdate();
					if(rowsAffected>0)
					{
						System.out.println("Appointment Booked!!");
					}else {
						System.out.println("Failed to book Appointment");
					}
					
				} catch (SQLException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
    		 }
    	 }
    	 else {
    		 System.out.println("Either doctor or patient doesnt exist");
    	 }
    }
    
    
    public static boolean checkDoctorAvailability(int doctorId,String appointmentdate,Connection connection) {
    	String queryString="SELECT COUNT(*) FROM appointments WHERE doctor_id=? AND appointment_date=?";
    	try {
    		PreparedStatement preparedStatement=connection.prepareStatement(queryString);
			preparedStatement.setInt(1, doctorId);
			preparedStatement.setString(2, appointmentdate);
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next())
			{
				int count=resultSet.getInt(1);
				if(count==0)
				{
					return true;
				}
				else {
					return false;
				}
			}
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	return false;
    }
}
