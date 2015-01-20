package com.atriks.emaildomain;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Programmer on 1/20/2015.
 */
public class GetNames {

    public static ArrayList<Name> retrieveNameList() throws SQLException{
        ArrayList<Name> nameList = new ArrayList<Name>();
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setUser("sa");
        ds.setPassword("liamcow");
        ds.setServerName("li_parse");
        ds.setInstanceName("data");
        ds.setDatabaseName("li_parse");
        Connection con = ds.getConnection();

        Statement s = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

        ResultSet rs = s.executeQuery("select first_name from parse_first_name");

        String[] fn = new String[3000];
        String[] ln = new String[3000];
        int x = 0;

        while(rs.next())
        {
            fn[x] = rs.getString("first_name");
            x++;
        }

        ResultSet rs2 = s.executeQuery("select last_name from parse_last_name");
        x = 0;
        while(rs2.next())
        {
            ln[x] = rs.getString("last_name");
            x++;
        }

        nameList.add(new Name(fn,ln));

        return nameList;
    }

}
