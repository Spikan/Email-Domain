package com.atriks.emaildomain;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Dan Chick on 2/4/2015.
 * For Atriks, LLC
 */
public class MarkComplete {

    public static void markComplete(String domain) throws SQLException {

        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setUser("sa");
        ds.setPassword("liamcow");
        ds.setServerName("li_parse");
        ds.setInstanceName("data");
        ds.setDatabaseName("li_parse");
        Connection con = ds.getConnection();


        PreparedStatement ps = con.prepareStatement("exec s_ef_mark_complete_no_company ?");
        ps.setEscapeProcessing(true);
        ps.setString(1, domain);

        ps.execute();

        con.close();
    }
}
