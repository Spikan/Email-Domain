package com.atriks.emaildomain;

import java.util.Random;

/**
 * Created by Programmer on 1/12/2015.
 */
public class GetUserAgent {

    public static String getAgent() {
        Random rand = new Random();
        int randomNum = rand.nextInt(5) + 1;

        switch (randomNum) {
            case 1:
                return "Mozilla/5.0 (Windows NT 6.3; rv:36.0) Gecko/20100101 Firefox/36.0";
            case 2:
                return "Mozilla/5.0 (compatible, MSIE 11, Windows NT 6.3; Trident/7.0; rv:11.0) like Gecko";
            case 3:
                return "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36";
            case 4:
                return "Opera/9.80 (X11; Linux i686; Ubuntu/14.10) Presto/2.12.388 Version/12.16";
            case 5:
                return "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) AppleWebKit/537.75.14 (KHTML, like Gecko) Version/7.0.3 Safari/7046A194A";
            default:
                return "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36";

        }

    }

}
