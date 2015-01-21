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

        boolean middleInitial = false;
        boolean underscoreDelim = false;
        boolean periodDelim = false;
        String[] splitName;

        String[] splitEA = emailAddress.split("@");

        int pCount = splitEA[0].length() - splitEA[0].replace(".", "").length();
        int uCount = splitEA[0].length() - splitEA[0].replace("_", "").length();

        if(pCount > 0)
            periodDelim = true;
        else if (uCount > 0)
            underscoreDelim = true;

        if (pCount > 1 | uCount > 1)
            middleInitial = true;


        if(periodDelim)
            splitName = splitEA[0].split("\\.");
        else if (underscoreDelim)
            splitName = splitEA[0].split("_");
        else
            splitName = splitEA;

        Boolean firstNameFirst = true;

        String format = "Unknown";

        ArrayList<FirstName> fName = nameList.getfName();
        ArrayList<LastName> lName = nameList.getlName();

        FirstName afName;
        LastName alName;

        String firstNameCheck;
        String lastNameCheck;

        for(int i = 0;i<fName.size();i++)
            if (splitName[0].equals(fName.get(i))) {
                firstNameCheck = splitName[0];
                firstNameFirst = true;
            }
        for(int i = 0;i<lName.size();i++)
            if (splitName[0].equals(lName.get(i))) {
                lastNameCheck = splitName[0];
                firstNameFirst = false;
            }

        if(firstNameFirst)
        {
            if(middleInitial)
            {
                if(periodDelim)
                    format = "{first_name}{.}{left(middle_name,1)}{.}{last_name}";
                else if (underscoreDelim)
                    format = "{first_name}{_}{left(middle_name,1)}{_}{last_name}";
            }
            else if (periodDelim)
                format = "{first_name}{.}{last_name}";
            else if (underscoreDelim)
                format = "{first_name}{_}{last_name}";
        }
        else
        {
            if(middleInitial)
            {
                if(periodDelim)
                    format = "{last_name}{.}{left(middle_name,1)}{.}{first_name}";
                else if (underscoreDelim)
                    format = "{last_name}{_}{left(middle_name,1)}{_}{first_name}";
            }
            else if (periodDelim)
                format = "{last_name}{.}{first_name}";
            else if (underscoreDelim)
                format = "{last_name}{_}{first_name}";
        }

       return format;
    }
}
