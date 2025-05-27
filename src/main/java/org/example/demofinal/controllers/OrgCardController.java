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

    public void setOrg(Org org, OrgDao orgDao) {
        this.org = org;
        this.orgDao = orgDao;

        initializeCard();
    }

    private void initializeCard() {

        orgTypeNameLabel.setText(org.getOrgType() + " | " + org.getName());
        ceoLabel.setText(org.getCeo());
        phoneLabel.setText("+7 " + org.getPhone());
        rateLabel.setText("Рейтинг: " + org.getRating());

        calculateSalesStatus();
    }

    private void calculateSalesStatus() {
        try {
            int discount = orgDao.calculateDiscount(org.getId());
            saleStatusLabel.setText("Скидка " + discount + "%");
        } catch (SQLException e) {
            saleStatusLabel.setText(e.getMessage());
        }
    }
}

