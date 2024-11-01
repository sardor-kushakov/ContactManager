package sarik.dev.util;

import java.util.Scanner;

public class ScannerUtils {

    public static final Scanner scanNum = new Scanner(System.in);
    public static final Scanner scanStr = new Scanner(System.in);

    public static String getAction() {
        System.out.print("Enter action: ");
        return scanStr.nextLine();
    }
}
