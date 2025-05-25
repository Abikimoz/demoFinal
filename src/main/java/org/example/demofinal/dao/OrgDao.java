package org.example.demofinal.dao;

import org.example.demofinal.config.DBConnect;
import org.example.demofinal.models.Org;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrgDao {
    public List<Org> getAllOrgs() throws SQLException {
        List<Org> orgs = new ArrayList<>();
        Connection connection = null;

        try {
            connection = DBConnect.getConnection();
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM partners ORDER BY name")) {

                while (rs.next()) {
                    Org org = new Org(
                            rs.getInt("id"),
                            rs.getString("orgType"),
                            rs.getString("name"),
                            rs.getString("ceo"),
                            rs.getString("phone"),
                            rs.getString("email"),
                            rs.getString("address"),
                            rs.getInt("raiting")
                    );
                    orgs.add(org);
                }
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (connection != null) {
                DBConnect.closeConnection(connection);
            }
        }
        return orgs;
    }
}
