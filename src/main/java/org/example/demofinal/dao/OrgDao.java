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
             // createStatement() создаёт "пустой" объект для запроса без параметров.
             // Statement — это интерфейс
             Statement stmt = connection.createStatement();
             // executeQuery() принимает готовую SQL-строку.
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
             // prepareStatement() принимает SQL-строку с placeholders (?):
             // PreparedStatement — это интерфейс
             PreparedStatement stmt = connection.prepareStatement(GET_SALES_QUERY)) {
            // Выполнение запроса с передачей идентификатора организации в качестве параметра
            //  заменяет первый ? на значение orgId.
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
        // Вычисление размера скидки на основе суммы продаж
        return totalSales > 300000 ? 15 :
               totalSales > 50000 ? 10 :
               totalSales > 10000 ? 5 :
               0;
    }
}
