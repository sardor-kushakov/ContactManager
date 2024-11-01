package sarik.dev.core;

import sarik.dev.util.DatabaseUtil;

import static sarik.dev.controller.ContactController.*;
import static sarik.dev.util.ScannerUtils.getAction;

public class AppLauncher {
    public static void start() {
        DatabaseUtil.createTable();

        while (true) {
            System.out.println("1. Add  2. Show  3. Edit  4. Delete  5. Search  0. Exit");
            String action = getAction();
            switch (action) {
                case "1" -> addContact();
                case "2" -> showContacts();
                case "3" -> editContact();
                case "4" -> deleteContact();
                case "5" -> searchContacts();
                case "0" -> {
                    if (confirmOrBack()) {
                        System.out.print("Exiting the application");
                        try {
                            for (int i = 1; i <= 3; i++) {
                                System.out.print(".");
                                Thread.sleep(1000);
                            }
                            System.out.print("\r \r");
                            System.out.println("Thank you for being with us!😊");
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        return;
                    }
                    continue;
                }
                default -> System.out.println("Invalid Action. Please try again.");
            }
        }
    }

    private static boolean confirmOrBack() {
        while (true) {
            System.out.print("Are you sure you want to exit? (y/n): ");
            String response = getAction().trim().toLowerCase();
            if (response.equals("y") || response.equals("yes")) {
                return true;
            } else if (response.equals("n") || response.equals("no")) {
                return false;
            } else {
                System.out.println("Invalid input. Please enter 'y' for yes or 'n' for no.");
            }
        }
    }
}