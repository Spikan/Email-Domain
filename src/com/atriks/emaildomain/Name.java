package com.atriks.emaildomain;

import java.util.ArrayList;

/**
 * Created by Programmer on 1/20/2015.
 */
public class Name {

    ArrayList<FirstName> fName;
    ArrayList<LastName> lName;

    public Name(ArrayList<FirstName> fn, ArrayList<LastName> ln) {
        fName = fn;
        lName = ln;
    }

    public ArrayList<FirstName> getfName() {
        return fName;
    }

    public ArrayList<LastName> getlName() {
        return lName;
    }
}
