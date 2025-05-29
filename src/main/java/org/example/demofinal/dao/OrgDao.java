package org.example.demofinal.dao;

import org.example.demofinal.config.DBConnect;
import org.example.demofinal.models.Org;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrgDao {
    // SQL запросы
    // выводит список всех организаций из базы данных и сортировка по наименованию
    private static final String GET_ALL_ORGS_QUERY = "SELECT * FROM partners ORDER BY name";
    // запрос для получения общего количества продаж по партнеру и сохранение в total
    private static final String GET_SALES_QUERY = "SELECT SUM(production_quantity) AS total FROM sales WHERE partner_id = ?";
    
    // Пороговые значения для скидок
    private static final long HIGH_SALES_THRESHOLD = 300000;
    private static final long MEDIUM_SALES_THRESHOLD = 50000;
    private static final long LOW_SALES_THRESHOLD = 10000;
    
    // Процент скидок
    private static final int HIGH_DISCOUNT = 15;
    private static final int MEDIUM_DISCOUNT = 10;
    private static final int LOW_DISCOUNT = 5;
    private static final int NO_DISCOUNT = 0;

    /**
     * Получает список всех организаций из базы данных.
     * 
     * @return список организаций
     * @throws SQLException при ошибке работы с базой данных
     */
    public List<Org> getAllOrgs() throws SQLException {
        // Создание списка для хранения организаций
        List<Org> orgs = new ArrayList<>();
        try (Connection connection = DBConnect.getConnection();
             // Выполнение запроса на получение всех организаций из базы данных
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(GET_ALL_ORGS_QUERY)) {
            // Итерация по результатам запроса и добавление организаций в список
            while (rs.next()) {
                // Создание новой организации и добавление ее в список
                orgs.add(new Org(
                    rs.getInt("id"),
                    rs.getString("organization_type"),
                    rs.getString("name"),
                    rs.getString("ceo"),
                    rs.getString("phone"),
                    rs.getString("email"),
                    rs.getString("address"),
                    rs.getInt("rating")
                ));
            }
        }
        return orgs;
    }

    /**
     * Получает общую сумму продаж для организации.
     * 
     * @param orgId идентификатор организации
     * @return общая сумма продаж
     * @throws SQLException при ошибке работы с базой данных
     */
    public long getSalesOrg(int orgId) throws SQLException {
        try (Connection connection = DBConnect.getConnection();
             // Подготовка запроса с использованием
             PreparedStatement stmt = connection.prepareStatement(GET_SALES_QUERY)) {
            // Выполнение запроса с передачей идентификатора организации в качестве параметра
            stmt.setInt(1, orgId);
            // Выполнение запроса и получение результата
            try (ResultSet rs = stmt.executeQuery()) {
                // Если есть результат, возвращаем общую сумму продаж, иначе возвращаем 0
                return rs.next() ? rs.getLong("total") : 0;
            }
        }
    }

    /**
     * Рассчитывает скидку для организации на основе суммы продаж.
     * 
     * @param orgId идентификатор организации
     * @return размер скидки в процентах
     * @throws SQLException при ошибке работы с базой данных
     */
    public int calculateDiscount(int orgId) throws SQLException {
        long totalSales = getSalesOrg(orgId);
        
        return totalSales > HIGH_SALES_THRESHOLD ? HIGH_DISCOUNT :
               totalSales > MEDIUM_SALES_THRESHOLD ? MEDIUM_DISCOUNT :
               totalSales > LOW_SALES_THRESHOLD ? LOW_DISCOUNT :
               NO_DISCOUNT;
    }
}
