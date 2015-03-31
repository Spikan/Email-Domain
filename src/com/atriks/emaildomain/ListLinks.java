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
import java.util.UUID;

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
        //ArrayList<CompDom> cdList = ConnectDB.retrieveCD();

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
        String url;

        //Iterate through list of companies and domains
        for (CompDom cd : cdList) {

            //Check the object contains only 1 domain

                //initialize variables
                company = cd.getCompany();
                domain = cd.getDomain();

                //create url to scrape from
                //url0 = "http://en.wikipedia.org/w/index.php?search=";
                //url1 = company.replaceAll("[^\\x00-\\x7F]","");
                //url = url0 + url1;

                url = "https://www.google.com/search?&q=" + company;

                String userAgent = GetUserAgent.getAgent();

                try {
                    print("\nCompany: " + company + " Domain: " + domain);

                    //connect to URL, retrieve HTML source
                    Document doc = Jsoup.connect(url).userAgent(userAgent).referrer("https://google.com").timeout(0).get();

                    //Create an object to store every link object on the page
                    //selects them by looking for <a href=""></a>
                    Elements links = doc.select("cite._Rm");

                    boolean containsDomain = false;

                    for (Element link : links) {
                        String lString = link.toString();
                        lString = lString.replaceAll("</?[^>]+>", "");
                        if(lString.contains(domain)) {
                            print(lString);
                            containsDomain = true;
                        }
                    }

                   if(containsDomain) {
                       print("MATCH FOUND FOR " + company + ": " + domain);
                       UpdateCompDom.updateCD(domain,company);
                       MarkComplete.markComplete(company, domain);
                   } else MarkWrong.markWrong(company,domain);

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
                    Thread.sleep(15000);
                } catch (InterruptedException ignored) {

                }
            }

        }

    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

}