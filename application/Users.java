package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

public class Users {
	private String name;
	private double avgWPM = 0;
	private ArrayList<Double> lastTenTests = new ArrayList<>();
	private ArrayList<Users> users = new ArrayList<>();	
	
	public Users(String name) {
		this.name = name;
	}
	
	public void addTest(double test) {
		if(this.lastTenTests.size() == 10) {
			this.lastTenTests.remove(9);
			this.lastTenTests.add(test);
		}
		else {
			this.lastTenTests.add(test);
			System.out.println(lastTenTests);
		}
	}
	
	private void setAvgWPM() {
		DecimalFormat format1 = new DecimalFormat("0.#");
		double temp=0;
		if(this.lastTenTests.isEmpty()) {
			this.avgWPM = 0;
			return;
		}
		
		for(int i = 0; i < this.lastTenTests.size(); i++) {
			temp += this.lastTenTests.get(i);
			//System.out.println(this.lastTenTests.get(i));
		}
		//System.out.println(lastTenTests.size());
		this.avgWPM = Double.parseDouble(format1.format(temp / lastTenTests.size()));
	}
	
	public double getAvgWPM() {
		this.setAvgWPM();
		return this.avgWPM;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void loadUsers() {
		try {
			ObjectInputStream ois = new ObjectInputStream(
					new FileInputStream("src/users/user.dat"));
			this.users = (ArrayList<Users>)ois.readObject();
			ois.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void saveUsers(Users user) {
		File newFile = new File("src/users/users.dat");
		users.add(user);
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(newFile);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(users);
			fos.close();
			oos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<Users> getUsers() {
		return this.users;
	}
}
