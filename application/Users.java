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
	private double bestWPM = 0;
	private ArrayList<Double> lastTenTests = new ArrayList<>();
	private static ArrayList<Users> users = new ArrayList<>();	
	private static final String DIR = System.getProperty("user.dir");
	private ArrayList<Quote> uniqueQuotesTyped = new ArrayList<>();
	private ArrayList< ArrayList<Double> > topFiveQuoteTests = new ArrayList<>();
	private static final long serialVersionUID = 6529685098267757690L;
	
	public Users(String name) {
		this.name = name;
	}
	
	private Users(Users user) {
		this.name = user.name;
		this.avgWPM = user.avgWPM;
		this.bestWPM = user.bestWPM;
		this.lastTenTests = user.lastTenTests;
		this.uniqueQuotesTyped = new ArrayList<>();
		this.topFiveQuoteTests = new ArrayList<>();
	}
	
	// updated the users list with the a new reference when this class has 
	// added or edited data types.
	public static void update(ArrayList<Users> list) {
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).uniqueQuotesTyped == null || list.get(i).topFiveQuoteTests == null) {
				Users newUser = new Users(list.get(i));
				users.set(users.indexOf(list.get(i)), newUser);
			}
		}
	}
	
	public static void deleteUniqueQuote(Quote quote) {
		int index;
		for(Users user : users) {
			index = user.getQuoteIndex(quote);
			if(index == -1) {
				continue;
			}
			user.uniqueQuotesTyped.remove(index);
			user.topFiveQuoteTests.remove(index);
		}
		saveUsers();
	}
	
	private Boolean quoteInList(Quote quote) {
		for(Quote curr : this.uniqueQuotesTyped) {
			if(curr.toString().equals(quote.toString())) {
				return true;
			}	
		}
		return false;
	}
	
	public void addTest(double test, Quote quote) {
		if(!quoteInList(quote)) {
			this.uniqueQuotesTyped.add(quote);
			this.topFiveQuoteTests.add(new ArrayList<Double>());
			this.topFiveQuoteTests.get(this.topFiveQuoteTests.size()-1).add(test);
		}
		else {
			ArrayList<Double> topFive = this.topFiveQuoteTests.get(getQuoteIndex(quote));
			if(topFive.size() == 5) {
				if(test > topFive.get(0)) {
					topFive.remove(0);
					topFive.add(test);
					Collections.sort(topFive);
				}
			}
			else {
				topFive.add(test);
				Collections.sort(topFive);
			}
		}
		
		if(test > this.bestWPM) {
			this.bestWPM = test;
		}
		
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
	
	// returns the index of the quote 
	public int getQuoteIndex(Quote quote) {
		for(int i = 0; i < this.uniqueQuotesTyped.size(); i++) {
			if(uniqueQuotesTyped.get(i).toString().equals(quote.toString())) {
				return i;
			}
		}
		return -1;
	}
	
	public ArrayList<Double> getTopTenQuoteTests(int index) {
		return this.topFiveQuoteTests.get(index);
	}
	
	public double getBestWPM() {
		return bestWPM;		
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
	
	public static void addUser(Users user) {
		users.add(user);
	}
	
	public static void deleteUser(Users user) {
		users.remove(user);
	}
	
	@SuppressWarnings("unchecked")
	public static void loadUsers() {
		try {
			ObjectInputStream ois = new ObjectInputStream(
					new FileInputStream(DIR + "//users.dat"));
			users = (ArrayList<Users>)ois.readObject();
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
	
	public static void saveUsers(Users user) {
		File newFile = new File(DIR + "//users.dat");
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
	
	public static void saveUsers() {
		File newFile = new File(DIR + "//users.dat");
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
	
	public static void setUsers(ArrayList<Users> newUsers) {
		users = newUsers;
	}
	
	private void updateUser(Users user) {
		for(int i = 0; i < users.size(); i++) {
			if(user.getName() == users.get(i).getName()) {
				users.set(i, user);
			}
		}
	}
	
	public void updateAndSave(Users user) {
		File newFile = new File(DIR + "//users.dat");
		FileOutputStream fos;
		updateUser(user);
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
	
	public static ArrayList<Users> getUsers() {
		return users;
	}
	
	public String toString() {
		return this.name;
	}
	
}
