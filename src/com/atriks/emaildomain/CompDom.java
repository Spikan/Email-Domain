package com.atriks.emaildomain;

/**
 * Created by Dan Chick
 * for Atriks, LLC
 * 2014
 */
public class CompDom {

    public String company;
    public String domain;
    public String domains[];


    public CompDom(String x, String y) {
        company = x;
        domain = y;
    }

    @Override
    public String toString() {
        return company + " " + domain;
    }

    public String getCompany() {
        return company;
    }

    public String getDomain() {
        return domain;
    }

}
