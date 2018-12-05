package com.finework.core.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Administrator
 */
public class StringUtil {
//    String pattern2 = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\\\S+$).{8,}";

    /**
     * Validate the username string with the following business logic: 1) User
     * name must be between 6 to 15 characters. 2) User name can have lowercase
     * and uppercase characters. 3) User name can be alpha-numeric. 4) No
     * special character allowed.
     */
    private static final String USERNAME = "[a-zA-Z0-9]{3,14}";
    private static final String ACCOUNT_NBR = "[0-9]{5,20}";
    //private static final String PASSWORD = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}";
    private static final String PASSWORD = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$";
    private static final String EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    /*
     The password supplied must be meet the minimum complexity requirements.  
     Please enter password that meets all of the following criteria: 

     1.is at least 8 characters
     2. has not been used in the previous 5 passwords (DB)
     3. does not contain your account or full name (DB)
     4. does not contain space
     5. must contains three character groups
     6. English uppercase charecters (A through Z)
     7. English lowercase charecters (a through z)
     8. Numerals (0 through 9)
     */
    public static boolean validateEmail(String email) {
        return email.matches(EMAIL);
    }

    public static boolean validateBankAccountNbr(String acc) {
        acc = StringUtils.trimToEmpty(acc).replaceAll("-", "");
        return acc.matches(ACCOUNT_NBR);
    }

    public static boolean validatePasswd(String passwd) {
        return passwd.matches(PASSWORD);
    }

//    public static Boolean validatePasswd(User member, String passwd) throws Exception {
//
//        String passwdEncypt = MD5Generator.md5(passwd);
//        String txt = member.getUsername();
//        String username = MD5Generator.md5(txt);
//        String email = MD5Generator.md5(member.getEmail());
//
//        if (passwdEncypt.equals(username)) {
//            return false;
//        } else if (passwdEncypt.equals(email)) {
//            return false;
//        }
//
//        return true;
//    }
    public static boolean validateUserName(String username) {
        return username.matches(USERNAME);
    }

    private StringUtil() {
    }

    public static String replaceStringNull(String field) {
        if (field == null) {
            return null;
        } else if (field.equals("(null)")) {
            return null;
        }
        return field;
    }

    public static String numberFormat(int id, String patt) {
        NumberFormat formatter = new DecimalFormat(patt);
        return formatter.format(id);
    }
    public static String numberFormat(BigDecimal value, String patt) {
        DecimalFormat formatter = new DecimalFormat(patt);
        return formatter.format(value);
    }
    public static String numberFormat(Double value, String patt) {
        DecimalFormat formatter = new DecimalFormat(patt);
        return formatter.format(value);
    }

    public static boolean haveData(String param) {
        if (null == param || param.trim().length() < 1) {
            return false;
        } else {
            return true;
        }
    }
    
    public static String customFormat(String pattern, int value ) {
      DecimalFormat myFormatter = new DecimalFormat(pattern);
      return  myFormatter.format(value);
   }

    public static void main(String[] args) {
//        Integer a = Integer.parseInt("6");
//        Integer b = Integer.parseInt("5");

        System.out.println(validatePasswd("ao7mj1"));
    }

}
