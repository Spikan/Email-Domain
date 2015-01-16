package com.atriks.emaildomain;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;


import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;
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

        //Create list to store newly created queries
        ArrayList<String> queryList = new ArrayList<String>();

        //Create variables
        String company;
        String domain;
        String[] domains;
        //String url0;
        //String url1;
        String url;

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

                url = "http://" + domain;

                String userAgent = GetUserAgent.getAgent();

                try {
                    print("\nCompany: " + company + " Domain: " + domain);

                    //connect to URL, retrieve HTML source
                    Document doc = Jsoup.connect(url).userAgent(userAgent).timeout(0).get();

                    //Create an object to store every link object on the page
                    //selects them by looking for <a href=""></a>
                    Elements links = doc.select(":contains("+ company +")");

                    if(links.size() > 0){

                        boolean check = false;

                        for (String aQueryList : queryList) {
                            if (aQueryList.equals("update li_parse..qa_li_company_email_domains set [status] = 1, last_updated = getdate() where company like '" + company + "' and ehost like '" + domain.trim() + "'")) {
                                check = true;
                                break;
                            }

                            }

                            if (!check) {

                                print("MATCH FOUND FOR " + company + ": " + domain);
                                queryList.add("update li_parse..qa_li_company_email_domains set [status] = 1, last_updated = getdate() where company like '" + company + "' and ehost like '" + domain.trim() + "'");
                                queryList.add("update li_parse..qa_li_company_email_domains set [status] = 0, last_updated = getdate() where company like '" + company + "' and ehost not like '" + domain.trim() + "'");
                            } else
                                print("NO MATCH...\n ");
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
                    url = "http://" + domains[j];

                    try {
                        print("\nCompany: " + company + " Domain: " + domains[j]);


                        //connect to URL, retrieve HTML source
                        Document doc = Jsoup.connect(url).userAgent(userAgent).timeout(0).get();

                        //Create an object to store every link object on the page
                        //selects them by looking for <a href=""></a>
                        Elements links = doc.select(":contains(" + company + ")");


                        if(links.size() > 0){
                            boolean check = false;

                                for (String aQueryList : queryList) {
                                    if (aQueryList.equals("update li_parse..qa_li_company_email_domains set [status] = 1, last_updated = getdate() where company like '" + company + "' and ehost like '" + domains[j].trim() + "'")) {
                                        check = true;
                                        break;
                                    }

                                }

                                if (!check) {
                                    print("MATCH FOUND FOR " + company + ": " + domains[j]);
                                    queryList.add("update li_parse..qa_li_company_email_domains set [status] = 1, last_updated = getdate() where company like '" + company + "' and ehost like '" + domains[j].trim() + "'");
                                    queryList.add("update li_parse..qa_li_company_email_domains set [status] = 0, last_updated = getdate() where company like '" + company + "' and ehost not like '" + domains[j].trim() + "'");
                                } else
                                    print("NO MATCH...\n ");
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
                }
            }

        }


        //print list of queries to be thrown into SQL
        print("\n\nListing matches for last run:\n");
        for (String aQueryList : queryList) {
            System.out.println("\n" + aQueryList + "\n");
        }

    }

    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

}