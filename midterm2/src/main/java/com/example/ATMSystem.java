package com.example;

import com.example.data.Account;
import com.example.functions.Admin;
import com.example.functions.Customer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class ATMSystem {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/ATM";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "zx19980611";

    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Customer customer = new Customer(connection);
            Admin admin = new Admin(connection);
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("1 - Customer Login");
                System.out.println("2 - Administrator Login");
                System.out.println("3 - Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        customer.customerLogin(scanner);
                        break;
                    case 2:
                        admin.administratorLogin(scanner);
                        break;
                    case 3:
                        System.out.println("Exiting...");
                        connection.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
