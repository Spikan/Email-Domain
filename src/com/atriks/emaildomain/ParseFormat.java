package com.atriks.emaildomain;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Programmer on 1/20/2015.
 */
public class ParseFormat {

    public static String getFormat(String emailAddress) throws SQLException
    {
        if(!emailAddress.contains("@"))
            return "Not a valid Email Address";

        String[] splitEA = emailAddress.split("@");

        String[] splitName1 = splitEA[0].split("_");

        String[] splitName2 = splitName1[0].split("\\.");

        Name nameList = GetNames.retrieveNameList();

        Boolean firstNameFirst = true;

        String format = "Unknown";

        String[] fName = nameList.getfName();
        String[] lName = nameList.getlName();

        String firstNameCheck;
        String lastNameCheck;

        for(int i = 0;i<fName.length;i++)
            if (splitName2[0].equals(fName[i])) {
                firstNameCheck = splitName2[0];
                firstNameFirst = true;
            }
        for(int i = 0;i<lName.length;i++)
            if (splitName2[0].equals(lName[i])) {
                lastNameCheck = splitName2[0];
                firstNameFirst = false;
            }

        if(firstNameFirst)
            format = "first.last";
        else
            format = "last.first";

       return format;
    }
}
