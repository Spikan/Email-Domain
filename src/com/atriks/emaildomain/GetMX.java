package com.atriks.emaildomain;

import org.xbill.DNS.*;

/**
 * Created by Programmer on 1/16/2015.
 */
public class GetMX {

    public GetMX() {
        try {
            Record[] records = new Lookup("gmail.com", Type.MX).run();
            for (int i = 0; i < records.length; i++){
                MXRecord mx = (MXRecord) records[i];
                print("Host " + mx.getTarget() + " has preference ", mx.getPriority());
            }
        } catch (TextParseException e) {
            print(e.getMessage());
        }
    }
    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }
}

