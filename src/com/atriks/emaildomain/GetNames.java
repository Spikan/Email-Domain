package com.atriks.emaildomain;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Programmer on 1/20/2015.
 */
public class GetNames {

    public static Name retrieveNameList() throws SQLException{
        Name nameList = null;
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setUser("sa");
        ds.setPassword("liamcow");
        ds.setServerName("li_parse");
        ds.setInstanceName("data");
        ds.setDatabaseName("li_parse");
        Connection con = ds.getConnection();

        Statement s = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

        ResultSet rs = s.executeQuery("select first_name from parse_first_name");

        ArrayList<FirstName> fnList = new ArrayList<FirstName>();
        ArrayList<LastName> lnList = new ArrayList<LastName>();

        FirstName fn;
        LastName ln;


        while(rs.next())
        {
            fn = new FirstName(rs.getString("first_name"));
            fnList.add(fn);
        }

        ResultSet rs2 = s.executeQuery("select last_name from parse_last_name");
        while(rs2.next())
        {
            ln = new LastName(rs2.getString("last_name"));
            lnList.add(ln);
        }

        nameList.fName = fnList;
        nameList.lName = lnList;


        rs.close();
        rs2.close();
        s.close();
        con.close();

        return nameList;
    }

}
