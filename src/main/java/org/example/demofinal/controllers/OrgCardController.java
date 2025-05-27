package org.example.demofinal.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.example.demofinal.dao.OrgDao;
import org.example.demofinal.models.Org;

import java.sql.SQLException;

public class OrgCardController {

    @FXML private Label orgTypeNameLabel;
    @FXML private Label ceoLabel;
    @FXML private Label phoneLabel;
    @FXML private Label rateLabel;
    @FXML private Label saleStatusLabel;

    private Org org;
    private OrgDao orgDao;

    /**
     * Устанавливает данные организации и инициализирует карточку.
     * Вызывается при создании карточки для отображения данных организации.
     * 
     * @param org организация, данные которой нужно отобразить
     * @param orgDao объект для работы с базой данных
     */
    public void setOrg(Org org, OrgDao orgDao) {
        this.org = org;
        this.orgDao = orgDao;
        initializeCard();
    }

    /**
     * Инициализирует карточку организации, заполняя все поля данными.
     * Устанавливает значения для всех меток (labels) в карточке.
     */
    private void initializeCard() {
        orgTypeNameLabel.setText(org.getOrgType() + " | " + org.getName());
        ceoLabel.setText(org.getCeo());
        phoneLabel.setText("+7 " + org.getPhone());
        rateLabel.setText("Рейтинг: " + org.getRating());
        calculateSalesStatus();
    }

    /**
     * Рассчитывает и отображает скидку для организации.
     * Получает данные о продажах из базы данных и вычисляет размер скидки.
     * В случае ошибки отображает сообщение об ошибке.
     */
    private void calculateSalesStatus() {
        try {
            int discount = orgDao.calculateDiscount(org.getId());
            saleStatusLabel.setText("Скидка " + discount + "%");
        } catch (SQLException e) {
            saleStatusLabel.setText(e.getMessage());
        }
    }
}

