package com.atriks.emaildomain;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
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

        Name nameList = GetNames.retrieveNameList();


        //Create variables
        String company;
        String domain;
        String[] domains;
        //String url0;
        //String url1;
        String url;
        ArrayList<CompanyFormat> formatList = new ArrayList<CompanyFormat>();

        //Iterate through list of companies and domains
        for (CompDom cd : cdList) {

            boolean isArray = cd.isArray(); //check if there is more than one domain in the object

            //Check the object contains only 1 domain
            if (!isArray) {
                print("Single Domain");

                //initialize variables
                company = cd.getCompany();
                domain = cd.getDomain();

                //create url to scrape from
                //url0 = "http://en.wikipedia.org/w/index.php?search=";
                //url1 = company.replaceAll("[^\\x00-\\x7F]","");
                //url = url0 + url1;

                //url = "http://" + domain;
                url = "https://www.google.com/search?site=&source=hp&q=" + domain + "+email";

                String userAgent = GetUserAgent.getAgent();

                try {
                    print("\nCompany: " + company + " Domain: " + domain);

                    //connect to URL, retrieve HTML source
                    Document doc = Jsoup.connect(url).userAgent(userAgent).referrer("google.com").timeout(0).get();

                    //Create an object to store every link object on the page
                    //selects them by looking for <a href=""></a>
                    Elements links = doc.select("[class=\"st\"]");

                    if(links.size() > 0){
                        for(Element link:links)
                        {
                            String s = link.text();
                            if(s.contains("@")) {
                                String sParts[] = s.split("\\s+|,\\s*|\\.\\s+");
                                for(int i = 0;i<sParts.length;i++)
                                {
                                    if (sParts[i].contains("@")) {
                                        sParts[i] = sParts[i].replaceAll("(@.+\\.[a-z]{3}).+", "$1");
                                        print(sParts[i]);
                                        String format = ParseFormat.getFormat(sParts[i], nameList);
                                        CompanyFormat cf = new CompanyFormat(company, format);
                                        formatList.add(cf);
                                        print(format);
                                    }
                                }
                            }
                        }

                    }
                }
                catch(HttpStatusException e)
                {
                    print(e.getMessage() + " " + e.getUrl()  + " | " + e.getStatusCode());
                }
                catch(UnknownHostException e)
                {
                    print("Unknown Host: " + e.getMessage());
                }
                catch(ConnectException e)
                {
                    print(e.getMessage());
                }
                catch(SSLHandshakeException e)
                {
                    print(e.getMessage());
                }
                catch(SocketException e)
                {
                    print(e.getMessage());
                }
                catch(SSLException e)
                {
                    print(e.getMessage());
                }
                catch(UnsupportedMimeTypeException e)
                {
                    print("Cannot open page with mime type " + e.getMimeType());
                }
                catch(IllegalArgumentException e)
                {
                    print(e.getMessage());
                }
                catch(SocketTimeoutException e)
                {
                    print(e.getMessage());
                }
                catch(IOException e)
                {
                    print(e.getMessage());
                }
                try
                {
                    Thread.sleep(5000);
                }catch (InterruptedException e)
                {

                }

            } else {
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
                for (int j = 0; j < cd.getNumDomains(); j++) {
                    //url = "http://" + domains[j];
                    url = "https://www.google.com/search?site=&source=hp&q=" + domains[j] + "+email";

                    try {
                        print("\nCompany: " + company + " Domain: " + domains[j]);


                        //connect to URL, retrieve HTML source
                        Document doc = Jsoup.connect(url).userAgent(userAgent).referrer("google.com").timeout(0).get();

                        //Create an object to store every link object on the page
                        //selects them by looking for <a href=""></a>
                        Elements links = doc.select("[class=\"st\"]");

                        if(links.size() > 0){
                            for(Element link:links)
                            {
                                String s = link.text();
                                if(s.contains("@")) {
                                    String sParts[] = s.split("\\s+|,\\s*|\\.\\s+");
                                    for(int i = 0;i<sParts.length;i++)
                                    {
                                        if (sParts[i].contains("@")) {
                                            sParts[i] = sParts[i].replaceAll("(@.+\\.[a-z]{3}).+", "$1");
                                            print(sParts[i]);
                                            String format = ParseFormat.getFormat(sParts[i], nameList);
                                            CompanyFormat cf = new CompanyFormat(company, format);
                                            formatList.add(cf);
                                            print(format);
                                        }
                                    }
                                }
                            }

                            }

                    }
                    catch(HttpStatusException e)
                    {
                        print(e.getMessage() + " " + e.getUrl()  + " | " + e.getStatusCode());
                    }
                    catch(UnknownHostException e)
                    {
                        print("Unknown Host: " + e.getMessage());
                    }
                    catch(ConnectException e)
                    {
                        print(e.getMessage());
                    }
                    catch(SSLHandshakeException e)
                    {
                        print(e.getMessage());
                    }
                    catch(SocketException e)
                    {
                        print(e.getMessage());
                    }
                    catch(SSLException e)
                    {
                        print(e.getMessage());
                    }
                    catch(UnsupportedMimeTypeException e)
                    {
                        print("Cannot open page with mime type " + e.getMimeType());
                    }
                    catch(IllegalArgumentException e)
                    {
                        print(e.getMessage());
                    }
                    catch(SocketTimeoutException e)
                    {
                        print(e.getMessage());
                    }
                    catch(IOException e)
                    {
                        print(e.getMessage());
                    }

                    try
                    {
                        Thread.sleep(5000);
                    }catch (InterruptedException e)
                    {

                    }
                }
            }

        }
        for(int i = 0;i<formatList.size();i++)
        {
            String cCheck = formatList.get(i).getCompany();
            if(i>0) {
                if (cCheck.equals(formatList.get(i - 1).getCompany())) {
                    print(formatList.get(i).getFormat());
                }
                else
                    print("Listing retrieved formats for " + formatList.get(i).getCompany() + ":\n");
            }
            else
            print("Listing retrieved formats for " + formatList.get(i).getCompany() + ":\n");
        }
    }

    private static void print(String msg, Object... args) {
        try {
            System.out.println(String.format(msg, args));
        }catch (MissingFormatArgumentException e)
        {

        }
    }

}