CREATE DATABASE ATM;
USE ATM;
CREATE TABLE account(
    accountId INT AUTO_INCREMENT PRIMARY KEY,
    login VARCHAR(255) NOT NULL,
    pinCode INT NOT NULL,
    holderName VARCHAR(255) NOT NULL,
    balance DECIMAL(10, 2) NOT NULL,
    status ENUM('active', 'disabled') NOT NULL
    );
SELECT * FROM account;