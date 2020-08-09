package com.uriallab.haat.haat.Utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by MAHMOUD on 12/12/2018.
 */

public class Validations {


    /**
     * This method is used to validate mobile.
     * called when handle validation of user mobile.
     *
     * @param name of mobile user
     */
    public static boolean isValidName(String name) {
        return name.length() > 2;
    }


    /**
     * This method is used to validate mobile.
     * called when handle validation of user mobile.
     *
     * @param mobile of mobile user
     */

    public static boolean isValidMobile(String mobile) {
        boolean isValid = false;

        String expression = "[0-9\\+]+";
        CharSequence inputStr = mobile;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }

        return mobile.length() == 9 && isValid;
    }


    public static boolean isValidCode(String code) {
        return code.length() == 0;
    }

    /**
     * This method is used to validate password.
     * called when handle validation of user password.
     *
     * @param password of password user
     */
    public static boolean isValidPassword(String password) {
        return password.length() >= 6;
    }

    public static boolean isMatchPassword(String password, String confPassword) {
        return confPassword.equals(password);
    }


    public static boolean isValidSpinnerItem(int pos) {
        return pos != 0;
    }


    public static boolean isValidGender(String gender) {
        return !gender.toLowerCase().equals("Gender".toLowerCase());
    }

    public static boolean isValidDate(String date) {
        return date.length() > 0;
    }

    public static boolean isValidStr(String str) {
        return str.length() > 0;
    }


    /**
     * This method is used to validate email.
     * called when handle validation of user email.
     *
     * @param email of email user
     */
    public static boolean isValidEmail(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }
}