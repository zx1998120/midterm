package com.example.functions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Admin {
    private static final int ADMIN_PIN_CODE = 12345;
    private static Connection connection;

    public Admin(Connection connection) {
        this.connection = connection;
    }

    public void administratorLogin(Scanner scanner) {
        System.out.print("Enter admin code: ");
        int adminCode = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (adminCode == ADMIN_PIN_CODE) {
            System.out.println("Admin login successful.");
            // Proceed to administrator menu
            displayAdminMenu(scanner);
        } else {
            System.out.println("Invalid admin code. Please try again.");
        }
    }

    public void displayAdminMenu(Scanner scanner) {
        while (true) {
            System.out.println("1 - Create New Account");
            System.out.println("2 - Delete Existing Account");
            System.out.println("3 - Update Account Information");
            System.out.println("4 - Search for Account");
            System.out.println("5 - Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    createNewAccount(scanner);
                    break;
                case 2:
                    deleteAccount(scanner);
                    break;
                case 3:
                    updateAccount(scanner);
                    break;
                case 4:
                    searchForAccount(scanner);
                    break;
                case 5:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    public void createNewAccount(Scanner scanner) {
        try {
            System.out.print("Enter login: ");
            String login = scanner.nextLine();
            System.out.print("Enter Pin code: ");
            int pinCode = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            System.out.print("Enter holder name: ");
            String holderName = scanner.nextLine();
            System.out.print("Enter starting balance: ");
            double startingBalance = scanner.nextDouble();
            scanner.nextLine(); // Consume newline
            System.out.print("Enter status (Active/Disabled): ");
            String status = scanner.nextLine();

            // Insert account data into the database
            String query = "INSERT INTO account (login, pinCode, holderName, balance, status) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, login);
            statement.setInt(2, pinCode);
            statement.setString(3, holderName);
            statement.setDouble(4, startingBalance);
            statement.setString(5, status);
            statement.executeUpdate();

            System.out.println("Account created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to create account.");
        }
    }

    public void deleteAccount(Scanner scanner) {
        try {
            System.out.print("Enter account number to delete: ");
            int accountNumber = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            // Delete account from the database
            String query = "DELETE FROM account WHERE accountId = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, accountNumber);
            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Account deleted successfully.");
            } else {
                System.out.println("Account not found or failed to delete.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to delete account.");
        }
    }

    public void updateAccount(Scanner scanner) {
        try {
            System.out.print("Enter account number to update: ");
            int accountNumber = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            // Fetch current account details from the database
            String query = "SELECT * FROM account WHERE accountId = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, accountNumber);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Account found, update its details
                System.out.print("Enter new holder name: ");
                String newHolderName = scanner.nextLine();
                System.out.print("Enter new balance: ");
                double newBalance = scanner.nextDouble();
                scanner.nextLine(); // Consume newline
                System.out.print("Enter new status (Active/Disabled): ");
                String newStatus = scanner.nextLine();

                // Update account information in the database
                String updateQuery = "UPDATE account SET holderName = ?, balance = ?, status = ? WHERE accountId = ?";
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                updateStatement.setString(1, newHolderName);
                updateStatement.setDouble(2, newBalance);
                updateStatement.setString(3, newStatus);
                updateStatement.setInt(4, accountNumber);
                int rowsUpdated = updateStatement.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println("Account information updated successfully.");
                } else {
                    System.out.println("Failed to update account information.");
                }
            } else {
                System.out.println("Account not found.");
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to update account information.");
        }
    }

    public void searchForAccount(Scanner scanner) {
        try {
            System.out.print("Enter account number to search: ");
            int accountNumber = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            // Search for account in the database
            String query = "SELECT * FROM account WHERE accountId = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, accountNumber);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Account found, display its details
                System.out.println("Account found:");
                System.out.println("Account ID: " + resultSet.getInt("accountId"));
                System.out.println("Login: " + resultSet.getString("login"));
                System.out.println("Holder Name: " + resultSet.getString("holderName"));
                System.out.println("Balance: " + resultSet.getDouble("balance"));
                System.out.println("Status: " + resultSet.getString("status"));
            } else {
                System.out.println("Account not found.");
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to search for account.");
        }
    }

}

