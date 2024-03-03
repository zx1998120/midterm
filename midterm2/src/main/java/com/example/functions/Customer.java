package com.example.functions;

import com.example.data.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class Customer {
    private static Connection connection;

    public Customer(Connection connection) {
        this.connection = connection;
    }

    public void customerLogin(Scanner scanner) {
        System.out.print("Enter login: ");
        String login = scanner.nextLine();
        System.out.print("Enter Pin code: ");
        int pinCode = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        try {
            String query = "SELECT * FROM account WHERE login = ? AND pinCode = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, login);
            statement.setInt(2, pinCode);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Account account = new Account(
                        resultSet.getInt("accountId"),
                        resultSet.getString("login"),
                        resultSet.getInt("pinCode"),
                        resultSet.getString("holderName"),
                        resultSet.getDouble("balance"),
                        resultSet.getString("status")
                );
                System.out.println("Customer login successful.");
                displayCustomerMenu(scanner, account);
            } else {
                System.out.println("Invalid login or pin code. Please try again.");
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayCustomerMenu(Scanner scanner, Account account) {
        while (true) {
            System.out.println("1 - Withdraw Cash");
            System.out.println("2 - Deposit Cash");
            System.out.println("3 - Display Balance");
            System.out.println("4 - Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    withdrawCash(scanner, account);
                    break;
                case 2:
                    depositCash(scanner, account);
                    break;
                case 3:
                    displayBalance(account);
                    break;
                case 4:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    public void withdrawCash(Scanner scanner, Account account) {
        System.out.print("Enter the withdrawal amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        if (amount > account.getBalance()) {
            System.out.println("Insufficient funds.");
        } else {
            // Update account balance
            double newBalance = account.getBalance() - amount;
            account.setBalance(newBalance);

            // Update account balance in the database
            updateAccountBalance(account.getAccountId(), newBalance);

            System.out.println("Cash Successfully Withdrawn");
            System.out.println("Account #" + account.getAccountId());
            System.out.println("Date: " + LocalDate.now());
            System.out.println("Withdrawn: " + amount);
            System.out.println("Balance: " + newBalance);
        }
    }

    public void depositCash(Scanner scanner, Account account) {
        System.out.print("Enter the cash amount to deposit: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        // Update account balance
        double newBalance = account.getBalance() + amount;
        account.setBalance(newBalance);

        // Update account balance in the database
        updateAccountBalance(account.getAccountId(), newBalance);

        System.out.println("Cash Deposited Successfully.");
        System.out.println("Account #" + account.getAccountId());
        System.out.println("Date: " + LocalDate.now());
        System.out.println("Deposited: " + amount);
        System.out.println("Balance: " + newBalance);
    }

    public void displayBalance(Account account) {
        System.out.println("Account #" + account.getAccountId());
        System.out.println("Date: " + LocalDate.now());
        System.out.println("Balance: " + account.getBalance());
    }

    private void updateAccountBalance(int accountId, double newBalance) {
        try {
            String query = "UPDATE account SET balance = ? WHERE accountId = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setDouble(1, newBalance);
            statement.setInt(2, accountId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
