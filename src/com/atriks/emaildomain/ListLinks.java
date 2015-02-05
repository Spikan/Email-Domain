package com.atriks.emaildomain;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
            print("Session ID required. Exiting.");
            return;
        } else {
            cdList = ConnectDBDispatch.retrieveCDDispatch(args[0]);
        }

        //Create variables
        String company;
        String domain;
        String url;
        ArrayList<CompanyFormat> formatList = new ArrayList<CompanyFormat>();
        int count = 0;

        //Iterate through list of companies and domains
        for (CompDom cd : cdList) {

            //initialize variables
            company = cd.getCompany();
            domain = cd.getDomain();

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
                            CompanyFormat cf = new CompanyFormat(company, form);
                            formatList.add(cf);
                            UpdateFormats.updateFormat(company, form);
                            MarkComplete.markComplete(company, domain);
                            print(form);
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
                } catch (InterruptedException ignored) {

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
        } catch (MissingFormatArgumentException ignored) {

        }
    }

}