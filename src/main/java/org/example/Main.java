package org.example;

import service.FinanceService;
import storage.UserStorage;
import usersInterface.UsersInterface;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UserStorage userStorage = new UserStorage();
        FinanceService financeService = new FinanceService(userStorage);
        UsersInterface usersInterface = new UsersInterface(financeService);
        Scanner scanner = new Scanner(System.in);
        usersInterface.start(scanner);
    }
}