package com.atriks.emaildomain;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import java.sql.*;

/**
 * Created by Programmer on 2/4/2015.
 */
public class UpdateFormats {

    public static void updateFormat(String company, String format) throws SQLException{

        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setUser("sa");
        ds.setPassword("liamcow");
        ds.setServerName("li_parse");
        ds.setInstanceName("data");
        ds.setDatabaseName("li_parse");
        Connection con = ds.getConnection();

        Statement state = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

        state.executeUpdate("insert into parse_email_formats (company, email_format) values (\"" + company + "\", \"" + format + "\")");

    }
}
