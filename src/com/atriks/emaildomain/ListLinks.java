package com.atriks.emaildomain;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.MissingFormatArgumentException;
import java.util.UUID;

/**
 * Created by Dan Chick
 * for Atriks, LLC
 */

public class ListLinks {


    public ListLinks() {
    }

    public static void main(String[] args) throws IOException, SQLException {

        ArrayList<CompDom> cdList;

        if (args.length == 0) {
            java.net.InetAddress localMachine = java.net.InetAddress.getLocalHost();
            cdList = ConnectDBDispatch.retrieveCDDispatch(localMachine.getHostName() + UUID.randomUUID());
        } else {
            cdList = ConnectDBDispatch.retrieveCDDispatch(args[0]);
        }

        //Create variables
        String company;
        String domain;
        String domainCheck = null;
        String url;
        int count = 0;

        //Iterate through list of companies and domains
        for (CompDom cd : cdList) {



            //initialize variables
            company = cd.getCompany();
            domain = cd.getDomain();


            if(isEqual(domain, domainCheck)) {
                MarkComplete.markComplete(company, domain);
                continue;
            }
            else
                domainCheck = cd.getDomain();


                String userAgent = GetUserAgent.getAgent();

                url = "https://www.email-format.com/d/" + domain;

                try {
                    print("\nCompany: " + company + " Domain: " + domain);

                    System.setProperty("javax.net.ssl.trustStore", "email-format.jks");

                    //connect to URL, retrieve HTML source
                    Document doc = Jsoup.connect(url).userAgent(userAgent).referrer("https://www.email-format.com/i/search_result/?q=" + domain).timeout(0).get();

                    //Create an object to store objects on the page
                    Elements formats = doc.select("[class=\"format fl\"]");

                    if (formats.size() > 0) {
                            String form = formats.first().text();
                            form = ParseFormat.parseFormat(form);
                            UpdateFormats.updateFormat(domain, form);
                            MarkComplete.markComplete(company, domain);
                            print(form);
                        }
                    else MarkWrong.markWrong(company, domain);

                    count++;
                    print("number of queries : " + count);
                } catch (HttpStatusException e) {
                    print(e.getMessage() + " " + e.getUrl() + " | " + e.getStatusCode());
                } catch (UnknownHostException e) {
                    print("Unknown Host: " + e.getMessage());
                } catch (ConnectException e) {
                    print(e.getMessage());
                } catch (SSLHandshakeException e) {
                    print(e.getMessage());
                } catch (SocketException e) {
                    print(e.getMessage());
                } catch (SSLException e) {
                    print(e.getMessage());
                } catch (UnsupportedMimeTypeException e) {
                    print("Cannot open page with mime type " + e.getMimeType());
                } catch (IllegalArgumentException e) {
                    print(e.getMessage());
                } catch (SocketTimeoutException e) {
                    print(e.getMessage());
                } catch (IOException e) {
                    print(e.getMessage());
                }

                try {
                    Thread.sleep(20000);
                } catch (InterruptedException ignored) {

                }

        }
        print("number of queries : " + count);
    }

    private static void print(String msg, Object... args) {
        try {
            System.out.println(String.format(msg, args));
        } catch (MissingFormatArgumentException ignored) {

        }
    }

    public static boolean isEqual(Object o1, Object o2) {
        return o1 == o2 || (o1 != null && o1.equals(o2));
    }

}