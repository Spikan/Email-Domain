package com.atriks.emaildomain;

import org.xbill.DNS.*;


/**
 * Created by Programmer on 1/16/2015.
 */
public class GetMX {

    public String GetMX(String company) {
        String r = "MX Record Retrieval Error";

        try {
            Record[] records = new Lookup(company, Type.MX).run();
            for (Record record : records) {
                MXRecord mx = (MXRecord) record;
                print("Host " + mx.getTarget() + " has preference ", mx.getPriority());
                r = mx.getTarget().toString();
            }
        } catch (TextParseException e) {
            print(e.getMessage());
            r = "No MX Record Found";
        }
        return r;
    }

    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }
}

