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

    public static String[] retrieveCDDispatch(String s) throws SQLException {
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setUser("sa");
        ds.setPassword("liamcow");
        ds.setServerName("li_parse");
        ds.setInstanceName("data");
        ds.setDatabaseName("li_parse");
        Connection con = ds.getConnection();

        PreparedStatement ps = con.prepareStatement("exec s_ef_get_work_no_company ?");
        ps.setEscapeProcessing(true);
        ps.setString(1, s);
        ResultSet rs = ps.executeQuery();

        int i = 0;
        String[] domains = new String[3000];

        while (rs.next()) {
            domains[i] = rs.getString("ehost");
            i++;
        }

        con.close();

        return domains;
    }

}
