package com.atriks.emaildomain;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Dan Chick on 2/4/2015.
 * For Atriks, LLC
 */
public class UpdateFormats {

    public static void updateFormat(String domain, String format) throws SQLException {

        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setUser("sa");
        ds.setPassword("liamcow");
        ds.setServerName("li_parse");
        ds.setInstanceName("data");
        ds.setDatabaseName("li_parse");
        Connection con = ds.getConnection();

        Statement state = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

        String statement = "insert into parse_email_formats values (\'" + domain + "\', \'" + format + "\' )";

        state.execute(statement);

        con.close();
    }
}
