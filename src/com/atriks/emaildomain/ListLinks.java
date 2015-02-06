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
<<<<<<< HEAD

            boolean isArray = cd.isArray(); //check if there is more than one domain in the object

            //Check the object contains only 1 domain
            if (!isArray) {
                print("Single Domain");

                //initialize variables
                company = cd.getCompany();
                domain = cd.getDomain();

                //create url to scrape from
                url0 = "http://en.wikipedia.org/w/index.php?search=";
                url1 = company.replaceAll("[^\\x00-\\x7F]", "");
                url = url0 + url1;

                print("\nCompany: " + company + " Domain: " + domain);

                String userAgent = GetUserAgent.getAgent();

                //connect to URL, retrieve HTML source
                Document doc = Jsoup.connect(url).userAgent(userAgent).timeout(0).get();

                //Create an object to store every link object on the page
                //selects them by looking for <a href=""></a>
                Elements links = doc.select("a[href]");

                //Create iterator to iterate through list of links
                Iterator i$;
                i$ = links.iterator();

                //Create object to store single link
                Element link;
=======
>>>>>>> parser



            //initialize variables
            company = cd.getCompany();
            domain = cd.getDomain();

<<<<<<< HEAD
                        //creates a string to store the URL of the link
                        String wSite = link.attr("abs:href");

                        //checks if the stored URL matches the domain we're checking for
                        if (wSite.contains(domain)) {
                            print("MATCH FOUND FOR " + company + ": " + domain);

                            //create queries
                            queryList.add("update li_parse..qa_li_company_email_domains set [status] = 1, last_updated = getdate() where company like '" + company + "' and ehost like '" + domain.trim() + "'");
                            queryList.add("update li_parse..qa_li_company_email_domains set [status] = 0, last_updated = getdate() where company like '" + company + "' and ehost not like '" + domain.trim() + "'");
                        } else
                            print("NO MATCH...\n ");
                    }
                }

                //sleep to not get rate limited
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignored) {

                }

            } else {
                print("Multi Domain");

                //initialize variables
                company = cd.getCompany();
                domains = cd.getDomains();

                //create url to scrape from
                url0 = "http://en.wikipedia.org/w/index.php?search=";
                url1 = company.replaceAll("[^\\x00-\\x7F]", "");
                url = url0 + url1;
=======

            if(isEqual(domain, domainCheck)) {
                MarkComplete.markComplete(company, domain);
                continue;
            }
            else
                domainCheck = cd.getDomain();
>>>>>>> parser


                String userAgent = GetUserAgent.getAgent();

                url = "https://www.email-format.com/d/" + domain;

<<<<<<< HEAD
                //Iterate through list of domains
                for (int j = 0; j < cd.getNumDomains(); j++) {
                    print("\nCompany: " + company + " Domain: " + domains[j]);

                    //Create iterator to iterate through list of links
                    Iterator i$;
                    i$ = links.iterator();

                    //Create object to store single link
                    Element link;

                    //Iterate through list of links
                    while (i$.hasNext()) {
                        //Store current link in created object
                        link = (Element) i$.next();

                        //checks if the link text contains the word "website"
                        if (link.text().contains("website")) {
=======
                try {
                    print("\nCompany: " + company + " Domain: " + domain);

                    System.setProperty("javax.net.ssl.trustStore", "email-format.jks");
>>>>>>> parser

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
<<<<<<< HEAD
                    Thread.sleep(500);
=======
                    Thread.sleep(20000);
>>>>>>> parser
                } catch (InterruptedException ignored) {

                }

        }
        print("number of queries : " + count);
    }

    private static void print(String msg, Object... args) {
        try {
            System.out.println(String.format(msg, args));
        } catch (MissingFormatArgumentException ignored) {

<<<<<<< HEAD
        //print list of queries to be thrown into SQL
        print("\n\nListing matches for last run:\n");
        for (String aQueryList : queryList) {
            System.out.println("\n" + aQueryList + "\n");
=======
>>>>>>> parser
        }
    }

    public static boolean isEqual(Object o1, Object o2) {
        return o1 == o2 || (o1 != null && o1.equals(o2));
    }

}