package com.atriks.emaildomain;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Programmer on 2/3/2015.
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

        Statement state = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

        state.execute("exec s_prep_ef_dispatch");

        PreparedStatement ps = con.prepareStatement("exec s_ef_get_work \"" + s + "\"");
        ResultSet rs = ps.executeQuery();

        int i = 0;
        String[] companies = new String[3000];
        String[] domains = new String[3000];

        while(rs.next())
        {
            companies[i] = rs.getString("company");
            domains[i] = rs.getString("ehost");
            cdList.add(new CompDom(companies[i].trim(), domains[i].trim()));
            i++;
        }



        return cdList;
    }

}
