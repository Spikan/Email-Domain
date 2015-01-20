package com.atriks.emaildomain;

import java.util.ArrayList;

/**
 * Created by Programmer on 1/20/2015.
 */
public class Name {

    String[] fName;
    String[] lName;

    public Name(String[] fn, String[] ln){
        fName = fn;
        lName = ln;
    }

    public String[] getfName()
    {
        return fName;
    }

    public String[] getlName()
    {
        return lName;
    }
}
