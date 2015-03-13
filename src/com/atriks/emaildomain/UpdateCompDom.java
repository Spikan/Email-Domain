package com.atriks.emaildomain;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Dan Chick on 3/12/2015.
 * For Atriks, LLC
 */
public class UpdateCompDom {

    public static void updateCD(String domain, String company) throws SQLException {

        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setUser("sa");
        ds.setPassword("liamcow");
        ds.setServerName("li_parse");
        ds.setInstanceName("data");
        ds.setDatabaseName("li_parse");
        Connection con = ds.getConnection();

        Statement state = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

        String statement = "insert into parse_compdom values (\'" + domain + "\', \'" + company + "\' )";

        state.execute(statement);

        con.close();
    }
}
