package com.atriks.emaildomain;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by Dan Chick
 * for Atriks, LLC
 * 2014
 */

/**
 * A program that will take a list of companies and domains
 * and check to see if the official website of that company
 * is the listed domain by scraping Wikipedia
 *
 * Input: List of companies and domains in a custom CompDom object
 * Output: List of queries to use in SQL Server
 */
public class ListLinks {


    public ListLinks() {
    }

    public static void main(String[] args) throws IOException, SQLException {

        //Get list of companies and domains to parse
        ArrayList<CompDom> cdList = ConnectDB.retrieveCD();

        //Create list to store newly created queries
        ArrayList<String> queryList = new ArrayList<String>();

        //Create variables
        String company;
        String domain;
        String[] domains;
        String url0;
        String url1;
        String url;

        //Iterate through list of companies and domains
        for(int i = 0;i<cdList.size();i++) {

            CompDom cd = cdList.get(i);     //get a company and domain
            boolean isArray = cd.isArray(); //check if there is more than one domain in the object

            //Check the object contains only 1 domain
            if (isArray == false) {
                print("Single Domain");

                //initialize variables
                company = cd.getCompany();
                domain = cd.getDomain();

                //create url to scrape from
                //url0 = "http://en.wikipedia.org/w/index.php?search=";
                //url1 = company.replaceAll("[^\\x00-\\x7F]","");
                //url = url0 + url1;

                url = "http://" + domain;

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

                //Iterate through list of links
                while (i$.hasNext()) {

                    //Store current link in created object
                    link = (Element) i$.next();

                    //checks if the link text contains the word "website"
                    if (link.text().contains("website")) {

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
                } catch (InterruptedException e) {

                }

            }
            else {
                print("Multi Domain");

                //initialize variables
                company = cd.getCompany();
                domains = cd.getDomains();

                //create url to scrape from
                //url0 = "http://en.wikipedia.org/w/index.php?search=";
                //url1 = company.replaceAll("[^\\x00-\\x7F]","");
                //url = url0 + url1;


                String userAgent = GetUserAgent.getAgent();


                //Iterate through list of domains
                for(int j = 0;j<cd.getNumDomains();j++) {
                    url = "http://" + domains[j];

                    //connect to URL, retrieve HTML source
                    Document doc = Jsoup.connect(url).userAgent(userAgent).timeout(0).get();

                    //Create an object to store every link object on the page
                    //selects them by looking for <a href=""></a>
                    Elements links = doc.select("a[href]");


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

                            //creates a string to store the URL of the link
                            String wSite = link.attr("abs:href");

                            if (wSite.contains(domains[j])) {
                                print("MATCH FOUND FOR " + company + ": " + domains[j]);

                                //create queries
                                queryList.add("update li_parse..qa_li_company_email_domains set [status] = 1, last_updated = getdate() where company like '" + company + "' and ehost like '" + domains[j].trim() + "'");
                                queryList.add("update li_parse..qa_li_company_email_domains set [status] = 0, last_updated = getdate() where company like '" + company + "' and ehost not like '" + domains[j].trim() + "'");
                            } else
                                print("NO MATCH...\n ");
                        }
                    }

                }

                //sleep to not get rate limited
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {

                }
            }

        }


        //print list of queries to be thrown into SQL
        print("\n\nListing matches for last run:\n");
        for(int q = 0;q<queryList.size();q++)
        {
            System.out.println("\n" + queryList.get(q) + "\n");
        }
    }

    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

    private static String trim(String s, int width) {
        return s.length() > width?s.substring(0, width - 1) + ".":s;
    }
}