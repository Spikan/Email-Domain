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
        boolean dashDelim = false;
        boolean delim = false;
        boolean fInitial = false;
        boolean lInitial = false;

        String[] splitName;

        if(emailAddress.length()<5)
            return "Not a valid Email Address";

        String[] splitEA = emailAddress.split("@");


        if(splitEA == null)
            return "Not a valid Email Address";
        else if(splitEA[0].length()<=1)
            return "Not a valid Email Address";

        int pCount = splitEA[0].length() - splitEA[0].replace(".", "").length();
        int uCount = splitEA[0].length() - splitEA[0].replace("_", "").length();
        int dCount = splitEA[0].length() - splitEA[0].replace("-", "").length();

        if(pCount > 0)
            periodDelim = true;
        else if (uCount > 0)
            underscoreDelim = true;
        else if (dCount > 0)
            dashDelim = true;
        else delim = false;


        if (pCount > 1 || uCount > 1 || dCount > 1)
            middleInitial = true;


        if(periodDelim)
            splitName = splitEA[0].split("\\.");
        else if (underscoreDelim)
            splitName = splitEA[0].split("_");
        else if (dashDelim)
            splitName = splitEA[0].split("-");
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

        int len;

        if(periodDelim||underscoreDelim||dashDelim)
            delim = true;


        for(int i = 0;i<fName.size();i++) {

            len = fName.get(i).getfName().length();

            if(splitName[0].length()>=len+1 && !delim)
                firstNameCheck = splitName[0].substring(0,len);
            else
                firstNameCheck = splitName[0];

            if (firstNameCheck.equals(fName.get(i))) {
                firstNameFirst = true;


                if(splitName[0].length() == fName.get(i).getfName().length() + 1)
                {
                    lInitial = true;
                }
            }
        }
        for(int i = 0;i<lName.size();i++) {

            len = lName.get(i).getlName().length();

            if(splitName[0].length()>=len+1 && !delim)
                lastNameCheck = splitName[0].substring(splitName[0].length()-(len + 1), splitName[0].length());
            else
                lastNameCheck = splitName[0];

            if (lastNameCheck.equals(lName.get(i))) {
                firstNameFirst = false;
                if(splitName[1].length() == lName.get(i).getlName().length() + 1)
                {
                    fInitial = true;
                }
            }
        }

        if(firstNameFirst)
        {
            if(middleInitial)
            {
                if(periodDelim)
                    format = "{first_name}{.}{left(middle_name,1)}{.}{last_name}";
                else if (underscoreDelim)
                    format = "{first_name}{_}{left(middle_name,1)}{_}{last_name}";
                else if (dashDelim)
                    format = "{first_name}{-}{left(middle_name,1)}{-}{last_name}";
            }
            else if (periodDelim) {
                if(splitName[0].length()==1)
                    format = "{left(first_name,1)}{.}{last_name}";
                else
                    format = "{first_name}{.}{last_name}";
            }
            else if (underscoreDelim) {
                if(splitName[0].length()==1)
                    format = "{left(first_name,1)}{_}{last_name}";
                else
                    format = "{first_name}{_}{last_name}";
            }
            else if (dashDelim) {
                if(splitName[0].length()==1)
                    format = "{left(first_name,1)}{-}{last_name}";
                else
                    format = "{first_name}{-}{last_name}";
            }
            else if (lInitial)
                format = "{first_name}{left(last_name,1)}";
            else
                format = "{first_name}{last_name}";
        }
        else
        {
            if(middleInitial)
            {
                if(periodDelim)
                    format = "{last_name}{.}{left(middle_name,1)}{.}{first_name}";
                else if (underscoreDelim)
                    format = "{last_name}{_}{left(middle_name,1)}{_}{first_name}";
                else if (dashDelim)
                    format = "{last_name}{-}{left(middle_name,1)}{-}{first_name}";
            }
            else if (periodDelim) {
                if(splitName[1].length()==1)
                    format = "{first_name}{.}{left(last_name,1)}";
                else
                    format = "{last_name}{.}{first_name}";
            }
            else if (underscoreDelim) {
                if(splitName[1].length()==1)
                    format = "{first_name}{_}{left(last_name,1)}";
                else
                    format = "{last_name}{_}{first_name}";
            }
            else if (dashDelim) {
                if(splitName[1].length()==1)
                    format = "{first_name}{-}{left(last_name,1)}";
                else
                    format = "{last_name}{-}{first_name}";
            }
            else if (fInitial)
                format = "{left(first_name,1)}{last_name}";
            else
                format = "{last_name}{last_name}";
        }

       return format;
    }
}
