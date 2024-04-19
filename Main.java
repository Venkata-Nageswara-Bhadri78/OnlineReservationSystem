package SamplePackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
	private static final String url = "jdbc:mysql://localhost:3306/OnlineReservationSystem";
	private static final String username = "root";
	private static final String password = "root";

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		try {

			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, username, password);
			while (true) {
				System.out.println("Enter your choice : ");
				System.out.println("0. Exit\t 1. Register\t 2. LogIn\t 3.cancel Tickets");
				int choice = scan.nextInt();
				boolean flag = false;
				switch (choice) {
					case 0:
						flag = true;
						break;
					case 1:
						Register(scan, conn);
						break;
					case 2:
						logIn(scan, conn);
						break;
					case 3:
						scan.nextLine();
						cancelTicket(scan, conn);
						break;
					default:
						System.out.println("Invalid Entry - try again");
				}
				if (flag == true) {
					System.out.println("EXITED SUCESSFULLY");
					break;
				}
				
			}
		} catch (Exception e) {
			System.out.println("ERROR IN CONNECTING TO DATABASE " + e);
		}
		
	}

	public static void logIn(Scanner scan, Connection conn) {
		String logIn = "fail";
		String userName = "";
		try {
			scan.nextLine();
			System.out.print("Enter UserName or MobileNumber : ");
			userName = scan.nextLine();
			System.out.print("Enter Password : ");
			String password = scan.nextLine();
			
			PreparedStatement state = conn
					.prepareStatement("select * from LogInDetails where userName=? or mobileNumber=?");
			state.setString(1, userName);
			state.setString(2, userName);
			ResultSet s = state.executeQuery();
			if (s.next()) {
				if ((userName.equals(s.getString(1)) || userName.equals(s.getString(2)))
						&& (password.equals(s.getString(3)))) {
					System.out.println("Login Sucessful");
					logIn = "sucess";
				} else {
					System.out.println("Invalid Credentials - try again");
				}
			} else {
				System.out.println("Invalid Credentials - try again");
			}

		} catch (Exception e) {
			System.out.println("Error in connecting to dataBase" + e);
		}
		if(logIn.equals("sucess")) {
			System.out.println("ENTER REQUIRES TO BOOK TRAIN TICKET");
			System.out.println("Enter train Number : ");
			int trainNumber = scan.nextInt();
			try {
				PreparedStatement s = conn.prepareStatement("select * from trainDetails where trainNumber = ? ;");
				s.setInt(1, trainNumber);
				ResultSet settu = s.executeQuery();
				if(settu.next()) {
					System.out.println("Your Train Details are : ");
					System.out.println("Your Train Name is :"+settu.getString(2));
					System.out.println("Journey Starts from "+settu.getString(3)+" to "+settu.getString(4)+" on "+settu.getString(5)+" in "+settu.getString(6)+" class");
					System.out.println("Is it ok to you ? (yes/no)");
					scan.nextLine();
					String opinion = scan.nextLine();
					if(opinion.equalsIgnoreCase("Yes")) {
						PreparedStatement prepare2 = conn.prepareStatement("UPDATE logInDetails SET booking = ? WHERE mobileNumber = ?;");
						prepare2.setInt(1, trainNumber);
						prepare2.setString(2, userName);
						prepare2.executeUpdate();
						System.out.println("YES - Your Ticket is confirmed");
					}
					else {
						System.out.println("Your ticket booking discontinued");
					}
				}
				else {
					System.out.println("Invalid Train Number - try Again");
				}
			}
			catch(Exception e) {
				System.out.println("Error Aquaried "+e);
			}
		}
			
	}

	public static void Register(Scanner scan, Connection conn) {
		try {
			scan.nextLine();
			System.out.println("Enter your Full Name : ");
			String name = scan.nextLine();
			String userName;
			while (true) {
				System.out.println("Enter UserName : ");
				userName = scan.nextLine();
				PreparedStatement state = conn
						.prepareStatement("select count(*) as count from logInDetails where UserName = ?");
				
				state.setString(1, userName);
				ResultSet s = state.executeQuery();
				s.next();
				int count = s.getInt("count");
				if (count > 0) {
					System.out.println("UserName already exist - try another one");
				} else {

					break;
				}
			}

			String mobNumber;
			while (true) {
				System.out.println("Enter  Mobile Number : ");
				mobNumber = scan.nextLine();
				if (mobNumber.length() != 10) {
					System.out.println("MobileNumber length should be 10 - try again");
				} else {
					PreparedStatement state = conn
							.prepareStatement("select count(*) as count from logInDetails where mobileNumber = ?");
					state.setString(1, mobNumber);
					ResultSet s = state.executeQuery();
					if (s.next() && s.getInt("count") > 0) {
						System.out.println("MobileNumber Already Exist - try with another number");
					} else {
						break;
					}
				}

			}
			String password;
			System.out.print("Enter password : ");
			password = scan.nextLine();

			while (password.length() < 8 || password.contains(" ")) {
				System.out.println(
						"password must be minimum of 8 characters and should not consistes of white spaces - try again");
				System.out.print("Enter password : ");
				password = scan.nextLine();
			}
			String pnrNumber = createPnrNumber();
			PreparedStatement state = conn.prepareStatement("INSERT INTO logInDetails (UserName, mobileNumber, password, PNRNumber, FullName) VALUES (?, ?, ?, ?, ?);");

			state.setString(1, userName);
			state.setString(2, mobNumber);
			state.setString(3, password);
			state.setString(4, pnrNumber);
			state.setString(5, name);
			state.executeUpdate();
			System.out.println("SUCESSFULLY REGISTERED");
			System.out.println("Your PNR NUMBER is : " + pnrNumber);
			System.out.println("REMEMBER THIS PNR NUMBER FOR FURTHER USE");
		} catch (Exception e) {
			System.out.println("Exception is : " + e);
		}
	}

	public static String createPnrNumber() {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String pnrNumber = "";
		while (pnrNumber.length() != 12) {
			int randomCharacter = (int) (Math.random() * characters.length());
			pnrNumber += characters.charAt(randomCharacter);
		}
		return pnrNumber;
	}

	public static void cancelTicket(Scanner scan, Connection conn) {
		System.out.print("Enter PNR Number : ");
		String pnrNumber = scan.nextLine();
		try {
			PreparedStatement state = conn
					.prepareStatement("select count(*) as count from logInDetails where PNRNumber = ?;");
			state.setString(1, pnrNumber);
			ResultSet set = state.executeQuery();
			set.next();
			if (set.getInt("count") > 0) {
				PreparedStatement s = conn.prepareStatement("select * from logInDetails where PNRNumber = ?");
				s.setString(1, pnrNumber);
				ResultSet st = s.executeQuery();
				st.next();
				System.out.println("Your Details are : ");
				System.out.println("Name : " + st.getString(5));
				System.out.println("UserName : " + st.getString(1));
				System.out.println("Mobile Number : " + st.getString(2));
				System.out.println("You want to cancel your Ticket ? (Yes/No)");
				String choice = scan.nextLine();
				if (choice.equalsIgnoreCase("Yes")) {
					PreparedStatement statement = conn.prepareStatement("Delete from logInDetails where PNRNumber = ?");
					statement.setString(1, pnrNumber);
					statement.executeUpdate();
					System.out.println("SUCESSFULLY CANCELLED TICKET AND DELETED YOUR RECORDS");
				}
			} else {
				System.out.println("Invalid PNR NUMBER - try Again");
			}
		} catch (Exception e) {
			System.out.println("Error in entering into DataBase - try Again " + e);
		}
	}
}

