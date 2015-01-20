package com.atriks.emaildomain;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Dan Chick
 * for Atriks, LLC
 * 2014
 */
public class ConnectDB {


    public static ArrayList<CompDom> retrieveCD() throws SQLException{
        ArrayList<CompDom> cdList = new ArrayList<CompDom>();
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setUser("sa");
        ds.setPassword("liamcow");
        ds.setServerName("li_parse");
        ds.setInstanceName("data");
        ds.setDatabaseName("li_parse");
        Connection con = ds.getConnection();



        Statement s = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

        ResultSet rs = s.executeQuery("select top 10000 dbo.RemoveNonASCII(company) as company, ehost, total from qa_li_company_email_domains where status = 1 and ehost not like '%.mil' order by total desc");
        while(rs.next()) {
            String c = rs.getString("company");
            String d = rs.getString("ehost");
            PreparedStatement ps = con.prepareStatement("select ehost from qa_li_company_email_domains where company = '" + c.replace("'", "''") + "' and status = 1");
            ResultSet rs2 = ps.executeQuery();
            int i = 0;
            String[] domains = new String[3000];
            while(rs2.next()){
                domains[i] = rs2.getString("ehost");
                i++;
            }
            //PreparedStatement ps2 = con.prepareStatement("update qa_li_company_email_domains set parsed = 1 where company = '" + c.replace("'","''") + "'");
            //ps2.execute();
           // ps2.executeUpdate();
            if(domains.length == 1)
                cdList.add(new CompDom(c.trim(), d.trim()));
            else {
                cdList.add(new CompDom(c.trim(), domains, i));
                while(rs.next()){
                    if(rs.getString("company").equals(c)){
                    }
                    else
                    {
                        rs.previous();
                        break;
                    }
                }

            }
        }
        rs.close();
        s.close();
        //ps.close();
        con.close();
        return cdList;
    }


    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }
}
