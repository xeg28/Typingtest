package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

public class Users implements Serializable{
	private String name;
	private double avgWPM = 0;
	private ArrayList<Double> lastTenTests = new ArrayList<>();
	private ArrayList<Users> users = new ArrayList<>();	
	private static final long serialVersionUID = 6529685098267757690L;
	
	public Users(String name) {
		this.name = name;
	}
	
	public void addTest(double test) {
		if(this.lastTenTests.size() == 10) {
			this.lastTenTests.remove(0);
			this.lastTenTests.add(test);
			System.out.println(lastTenTests);
		}
		else {
			this.lastTenTests.add(test);
			System.out.println(lastTenTests);
		}
	}
	
	private void setAvgWPM() {
		DecimalFormat format1 = new DecimalFormat("0.#");
		double temp = 0;
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
	
	public void addUser(Users user) {
		this.users.add(user);
	}
	
	public void loadUsers() {
		try {
			ObjectInputStream ois = new ObjectInputStream(
					new FileInputStream("src/users/users.dat"));
			this.users = (ArrayList<Users>)ois.readObject();
			ois.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
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
	
	public void setUsers(ArrayList<Users> users) {
		this.users = users;
	}
	
	private void updateUsers(Users user) {
		for(int i = 0; i < users.size(); i++) {
			if(user.getName() == users.get(i).getName()) {
				users.set(i, user);
			}
		}
	}
	
	public void updateAndSave(Users user) {
		File newFile = new File("src/users/users.dat");
		FileOutputStream fos;
		updateUsers(user);
		try {
			fos = new FileOutputStream(newFile);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			System.out.println(users);
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
	
	public String toString() {
		return this.name;
	}
	
}
