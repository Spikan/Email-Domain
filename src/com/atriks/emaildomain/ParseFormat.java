package com.atriks.emaildomain;

/**
 * Created by Programmer on 2/5/2015.
 */
public class ParseFormat {

    public static String parseFormat(String format){

        String parsedFormat = new String();

        if(format.equals("first_name"))
            parsedFormat = "{first_name}";
        else if(format.equals("last_name"))
            parsedFormat = "{last_name}";
        else if(format.equals("first_name last_name"))
            parsedFormat = "{first_name}{last_name}";
        else if(format.equals("first_name . last_name"))
            parsedFormat = "{first_name}{.}{last_name}";
        else if(format.equals("first_name - last_name"))
            parsedFormat = "{first_name}{-}{last_name}";
        else if(format.equals("first_name _ last_name"))
            parsedFormat = "{first_name}{_}{last_name}";
        else if(format.equals("first_initial . last_name"))
            parsedFormat = "{left(first_name,1)}{.}{last_name}";
        else if(format.equals("first_initial - last_name"))
            parsedFormat = "{left(first_name,1)}{-}{last_name}";
        else if(format.equals("first_initial _ last_name"))
            parsedFormat = "{left(first_name,1)}{_}{last_name}";
        else if(format.equals("first_name . last_initial"))
            parsedFormat = "{first_name}{.}{left(last_name,1)}";
        else if(format.equals("first_name - last_initial"))
            parsedFormat = "{first_name}{-}{left(last_name,1)}";
        else if(format.equals("first_name _ last_initial"))
            parsedFormat = "{first_name}{_}{left(last_name,1)}";
        else if(format.equals("first_name last_initial"))
            parsedFormat = "{first_name}{left(last_name,1)}";
        else if(format.equals("first_initial last_name"))
            parsedFormat = "{left(first_name,1)}{last_name}";
        else if(format.equals("last_name first_name"))
            parsedFormat = "{last_name}{first_name}";
        else if(format.equals("last_name first_initial"))
            parsedFormat = "{last_name}{left(first_name,1)}";
        else if(format.equals("last_initial first_name"))
            parsedFormat = "{left(last_name,1)}{first_name}";
        else if(format.equals("last_name . first_name"))
            parsedFormat = "{last_name}{.}{first_name}";
        else if(format.equals("last_name - first_name"))
            parsedFormat = "{last_name}{-}{first_name}";
        else if(format.equals("last_name _ first_name"))
            parsedFormat = "{last_name}{_}{first_name}";
        else if(format.equals("last_initial . first_name"))
            parsedFormat = "{left(last_name,1)}{.}{first_name}";
        else if(format.equals("last_initial - first_name"))
            parsedFormat = "{left(last_name,1)}{-}{first_name}";
        else if(format.equals("last_initial _ first_name"))
            parsedFormat = "{left(last_name,1)}{_}{first_name}";
        else if(format.equals("last_name . first_initial"))
            parsedFormat = "{last_name}{.}{left(first_name,1)}";
        else if(format.equals("last_name - first_initial"))
            parsedFormat = "{last_name}{-}{left(first_name,1)}";
        else if(format.equals("last_name _ first_initial"))
            parsedFormat = "{last_name}{_}{left(first_name,1)}";
        else
            parsedFormat = "Could not parse format";



        return parsedFormat;
    }
}
