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
    public int numDomains = 0;
    public boolean dArray;


    public CompDom(String x, String y){
        company = x;
        domain = y;
        dArray = false;
    }

    public CompDom(String x, String[] y, int i){
        company = x;
        domains = y;
        dArray = true;
        numDomains = i;
    }

    @Override
    public String toString(){
        return company + " " + domain;
    }

    public String getCompany(){
        return company;
    }

    public String getDomain(){
        return domain;
    }

    public String[] getDomains(){
        return domains;
    }

    public boolean isArray(){
        if (dArray) return true;
        else return false;
    }

    public int getNumDomains(){
        return numDomains;
    }
}
