package com.atriks.emaildomain;

/**
 * Created by Programmer on 1/22/2015.
 */
public class CompanyFormat {

    public String company;
    public String format;
    public String[] formats;

    public CompanyFormat(String c, String f)
    {
        company = c;
        format = f;
    }

    public CompanyFormat(String c, String[] f)
    {
        company = c;
        formats = f;
    }

    public String getCompany(){
        return company;
    }

    public String getFormat(){
        return format;
    }

    public String[] getFormats(){
        return formats;
    }

}
