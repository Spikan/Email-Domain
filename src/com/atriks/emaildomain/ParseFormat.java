package com.atriks.emaildomain;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Programmer on 1/20/2015.
 */
public class ParseFormat {

    public static String getFormat(String emailAddress, Name nameList) throws SQLException
    {
        if(!emailAddress.contains("@"))
            return "Not a valid Email Address";

        String[] splitEA = emailAddress.split("@");

        String[] splitName1 = splitEA[0].split("_");

        String[] splitName2 = splitName1[0].split("\\.");

        Boolean firstNameFirst = true;

        String format = "Unknown";

        ArrayList<FirstName> fName = nameList.getfName();
        ArrayList<LastName> lName = nameList.getlName();

        FirstName afName;
        LastName alName;

        String firstNameCheck;
        String lastNameCheck;

        for(int i = 0;i<fName.size();i++)
            if (splitName2[0].equals(fName.get(i))) {
                firstNameCheck = splitName2[0];
                firstNameFirst = true;
            }
        for(int i = 0;i<lName.size();i++)
            if (splitName2[0].equals(lName.get(i))) {
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
