package io.takima.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CLI implements CommandLineRunner {

    private Scanner sc = new Scanner(System.in);
    private final UserDAO userDAO;

    @Autowired
    public CLI(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    private void listUsers() {
        System.out.println(userDAO.getAll());
    }

    private void leaveApp() {
        System.out.println("Goodbye Boss");
        System.exit(0);
    }

    private void showUserCreation() {
        System.out.println("User creation");
        System.out.println("Enter first name");
        String firstName = readProtectedString(2);
        System.out.println("Enter last name");
        String lastName = readProtectedString(3);
        System.out.println("Enter age");
        Integer age = readProtectedInt(18);
        userDAO.create(new User(0L, firstName, lastName, age));
        System.out.println("User added successfully. Champain!");
    }

    private Integer readProtectedInt(int minValue) {
        Integer val = null;
        do {
            if (val != null) {
                System.out.println("Careful idiot. This field must be greater than or equal to " + minValue + "");
            }
            try {
                val = Integer.parseInt(sc.next());
            } catch (NumberFormatException e) {
                System.err.println("Please input a real integer value, you dumbass");
                val = 0;
                continue;
            }
        } while (val < minValue);
        return val;
    }


    private String readProtectedString(Integer length) {
        String str = null;
        do {
            if (str != null) {
                System.out.println("Careful idiot. This field must contain at least " + length + " characters");
            }
            str = sc.next();
        } while (str.length() < length);
        return str;
    }

    private void showHelp() {
        System.out.println("Available commands:");
        System.out.println("l - list");
        System.out.println("a - add new user");
        System.out.println("q - quit");
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Welcome to the awesome user database");
        showHelp();
        String line = null;
        while (true) {
            System.out.println("Enter command:");
            line = sc.next();
            switch (line) {
                case "l":
                    listUsers();
                    break;
                case "a":
                    showUserCreation();
                    break;

                case "q":
                    leaveApp();
                default:
                    showHelp();
                    break;
            }
        }
    }
}
