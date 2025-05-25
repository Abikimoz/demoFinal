package org.example.demofinal.dao;

import org.example.demofinal.config.DBConnect;
import org.example.demofinal.models.Org;

import java.sql.*;
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
                            rs.getString("organization_type"),
                            rs.getString("name"),
                            rs.getString("ceo"),
                            rs.getString("phone"),
                            rs.getString("email"),
                            rs.getString("address"),
                            rs.getInt("rating")
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
        System.out.println(orgs);
        return orgs;
    }

    public long getSalesOrg(int orgId) throws SQLException {
        Connection connection = null;

        try {
            connection = DBConnect.getConnection();
            try (PreparedStatement stmt = connection.prepareStatement(
                    "SELECT SUM(production_quantity) AS total FROM sales WHERE partner_id = ?")) {

                stmt.setInt(1, orgId);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    return rs.getLong("total");
                }
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (connection != null) {
                DBConnect.closeConnection(connection);
            }
        }

        return 0;
    }

    public int calculateDiscount(int orgId) throws SQLException {
        long totalSales = getSalesOrg(orgId);
        int discount;

        if (totalSales > 300000) discount = 15;
        else if (totalSales > 50000) discount = 10;
        else if (totalSales > 10000) discount = 5;
        else discount = 0;

        return discount;
    }
}
