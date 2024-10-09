package com.pk.main;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.pk.main.dao.UserDao;
import com.pk.main.entity.User;

@SpringBootApplication
public class SpringBootJdbcCrudOperationsApplication implements CommandLineRunner {

	@Autowired
	private UserDao userDao;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootJdbcCrudOperationsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// -------------------Insertion---------------------------------------

		User user1 = new User("pratham", "pratham@gmail.com", "male", "varanasi");
		User user2 = new User("nandi", "nandi@gmail.com", "female", "mumbai");
		boolean status = userDao.insertUser(user2);

		if (status) {
			System.out.println("User inserted successfully");
		} else {
			System.out.println("User not inserted due to some error");
		}

		// ------------------------Updation---------------------//

		User user = new User("pratham", "pratham@gmail.com", "male", "vijaywada");
		boolean statusUpdate = userDao.updateUser(user);
		if (statusUpdate) {
			System.out.println("User updated successfully");
		} else {
			System.out.println("User not updated due to some error");
		}

		// --------------Update method 2-----------------//

		// in this method we dont need to add user's all data
		// just by using email we can udate the attributes

		User userUpdate2 = userDao.readByEmail("pratham@gmail.com");
		// varanasi to delhi
		userUpdate2.setCity("delhi");

		boolean statusUpdate2 = userDao.updateUser(userUpdate2);

		if (statusUpdate2) {
			System.out.println("User updated successfully");
		} else {
			System.out.println("User not updated due to some error");
		}

		// -----------------Deletion----------------------//

		String email = "pratham@gmail.com";
		boolean statusDeletion = userDao.deleteUserByEmail(email);

		if (statusDeletion) {
			System.out.println("User deleted successfully");
		} else {
			System.out.println("Use not deleted due to some error");
		}

		// -----------------Select one user------------------//

		User userSelectOne = userDao.readByEmail("nandi@gmail.com");
		System.out.println("Name : " + userSelectOne.getName());
		System.out.println("Email: " + userSelectOne.getEmail());
		System.out.println("Gender: " + userSelectOne.getGender());
		System.out.println("City: " + userSelectOne.getCity());

		// ----------select all user---------------//

		List<User> list = userDao.getAllUsers();

		for (User userObject : list) {

			System.out.println("Name : " + userObject.getName());
			System.out.println("Email: " + userObject.getEmail());
			System.out.println("Gender: " + userObject.getGender());
			System.out.println("City: " + userObject.getCity());

			System.out.println("----------------------------------");
		}
	}

}
