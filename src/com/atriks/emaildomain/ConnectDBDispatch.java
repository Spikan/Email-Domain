package com.atriks.emaildomain;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Dan Chick on 2/3/2015.
 * For Atriks, LLC
 */
public class ConnectDBDispatch {

    public static ArrayList<CompDom> retrieveCDDispatch(String s) throws SQLException {
        ArrayList<CompDom> cdList = new ArrayList<CompDom>();

        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setUser("sa");
        ds.setPassword("liamcow");
        ds.setServerName("li_parse");
        ds.setInstanceName("data");
        ds.setDatabaseName("li_parse");
        Connection con = ds.getConnection();

        PreparedStatement ps = con.prepareStatement("exec s_ef_get_work ?");
        ps.setEscapeProcessing(true);
        ps.setString(1, s);
        ResultSet rs = ps.executeQuery();

        int i = 0;
        String[] companies = new String[3000];
        String[] domains = new String[3000];

        while (rs.next()) {
            companies[i] = rs.getString("company");
            domains[i] = rs.getString("ehost");
            cdList.add(new CompDom(companies[i].trim(), domains[i].trim()));
            i++;
        }

        con.close();

        return cdList;
    }

}
