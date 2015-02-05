package com.atriks.emaildomain;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.MissingFormatArgumentException;


import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;

/**
 * Created by Dan Chick
 * for Atriks, LLC
 * 2014
 */

public class ListLinks {


    public ListLinks() {
    }

    public static void main(String[] args) throws IOException, SQLException {

        ArrayList<CompDom> cdList;

        if (args.length==0) {
            print("Session ID required. Exiting.");
            return;
        }
        else {
            cdList = ConnectDBDispatch.retrieveCDDispatch(args[0]);
        }

        //Create variables
        String company;
        String domain;
        String[] domains;
        String url;
        ArrayList<CompanyFormat> formatList = new ArrayList<CompanyFormat>();
        int count = 0;

        //Iterate through list of companies and domains
        for (CompDom cd : cdList) {

            //initialize variables
            company = cd.getCompany();
            domains = cd.getDomains();

            String userAgent = GetUserAgent.getAgent();

            //Iterate through list of domains
            for (int j = 0; j < cd.getNumDomains(); j++) {
                url = "https://www.email-format.com/d/" + domains[j];

                try {
                    print("\nCompany: " + company + " Domain: " + domains[j]);

                    System.setProperty("javax.net.ssl.trustStore", "email-format.jks");

                    //connect to URL, retrieve HTML source
                    Document doc = Jsoup.connect(url).userAgent(userAgent).referrer("https://www.email-format.com/i/search_result/?q=" + domains[j]).timeout(0).get();

                    //Create an object to store objects on the page
                    Elements formats = doc.select("[class=\"format fl\"]");

                    if (formats.size() > 0) {
                        for (Element link : formats) {
                            String form = link.text();
                            form = ParseFormat.parseFormat(form);
                            CompanyFormat cf = new CompanyFormat(company, form);
                            formatList.add(cf);
                            UpdateFormats.updateFormat(company, form);
                            MarkComplete.MarkComplete(company, domains[j]);
                            print(form);
                        }
                    }
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
                } catch (InterruptedException e) {

                }
            }
        }
        for (int i = 0; i < formatList.size(); i++) {
            String cCheck = formatList.get(i).getCompany();
            if (i > 0) {
                if (cCheck.equals(formatList.get(i - 1).getCompany())) {
                    print(formatList.get(i).getFormat());
                } else
                    print("Listing retrieved formats for " + formatList.get(i).getCompany() + ":\n");
            } else
                print("Listing retrieved formats for " + formatList.get(i).getCompany() + ":\n");
        }
        print("number of queries : " + count);
    }

    private static void print(String msg, Object... args) {
        try {
            System.out.println(String.format(msg, args));
        } catch (MissingFormatArgumentException e) {

        }
    }

}