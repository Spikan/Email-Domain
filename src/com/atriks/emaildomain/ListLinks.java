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

/**
 * A program that will take a list of companies and domains
 * and check to see if the official website of that company
 * is the listed domain by scraping Wikipedia
 * <p/>
 * Input: List of companies and domains in a custom CompDom object
 * Output: List of queries to use in SQL Server
 */
public class ListLinks {


    public ListLinks() {
    }

    public static void main(String[] args) throws IOException, SQLException {

        //Get list of companies and domains to parse
        ArrayList<CompDom> cdList = ConnectDB.retrieveCD();


        //Create variables
        String company;
        String domain;
        String[] domains;
        //String url0;
        //String url1;
        String url;
        ArrayList<CompanyFormat> formatList = new ArrayList<CompanyFormat>();
        int count = 0;

        //Iterate through list of companies and domains
        for (CompDom cd : cdList) {

            boolean isArray = cd.isArray(); //check if there is more than one domain in the object

            //Check the object contains only 1 domain
            if (!isArray) {
                print("Single Domain");

                //initialize variables
                company = cd.getCompany();
                domain = cd.getDomain();

                url = "https://www.email-format.com/d/" + domain;

                String userAgent = GetUserAgent.getAgent();

                try {
                    print("\nCompany: " + company + " Domain: " + domain);

                    System.setProperty("javax.net.ssl.trustStore", "email-format.jks");

                    //connect to URL, retrieve HTML source
                    Document doc = Jsoup.connect(url).userAgent(userAgent).referrer("https://www.email-format.com/i/search_result/?q=" + domain).timeout(0).get();

                    //Create an object to store every link object on the page
                    //selects them by looking for <a href=""></a>
                    Elements formats = doc.select("[class=\"format fl\"]");

                    if (formats.size() > 0) {
                        for (Element link : formats) {
                            String form = link.text();
                            CompanyFormat cf = new CompanyFormat(company, form);
                            formatList.add(cf);
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

            } else {
                print("Multi Domain");

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

                        //Create an object to store every link object on the page
                        //selects them by looking for <a href=""></a>
                        Elements formats = doc.select("[class=\"format fl\"]");

                        if (formats.size() > 0) {
                            for (Element link : formats) {
                                String form = link.text();
                                CompanyFormat cf = new CompanyFormat(company, form);
                                formatList.add(cf);
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