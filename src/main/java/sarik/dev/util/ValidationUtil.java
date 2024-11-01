package sarik.dev.util;

public class ValidationUtil {

    public static boolean validatePhoneNumber(String phone) {
        if (phone.startsWith("+998") && phone.length() == 13) {
            String code = phone.substring(4, 6);

            String[] validCodes = {"97", "88", "90", "91", "93", "94", "95", "99", "33", "71", "98"};

            boolean isCodeValid = false;
            for (String validCode : validCodes) {
                if (validCode.equals(code)) {
                    isCodeValid = true;
                    break;
                }
            }

            if (!isCodeValid) {
                return false;
            }

            for (int i = 1; i < phone.length(); i++) {
                if (!Character.isDigit(phone.charAt(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static boolean validateName(String name) {
        return name != null && !name.trim().isEmpty();
    }
}
