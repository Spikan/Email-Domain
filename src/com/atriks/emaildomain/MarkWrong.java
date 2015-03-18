package com.atriks.emaildomain;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Dan Chick on 2/4/2015.
 * For Atriks, LLC
 */
public class MarkWrong {

    public static void markWrong(String company, String domain) throws SQLException {

        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setUser("sa");
        ds.setPassword("liamcow");
        ds.setServerName("li_parse");
        ds.setInstanceName("data");
        ds.setDatabaseName("li_parse");
        Connection con = ds.getConnection();

        company = company.replace("\'", "\'\'");
        company = company.replace("`", "");

        PreparedStatement ps = con.prepareStatement("exec s_compdom_mark_wrong ?, ?");
        ps.setEscapeProcessing(true);
        ps.setString(1, company);
        ps.setString(2, domain);

        ps.execute();

        con.close();
    }
}