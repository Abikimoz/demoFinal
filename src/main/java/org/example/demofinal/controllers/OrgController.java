package org.example.demofinal.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.demofinal.MasterApp;
import org.example.demofinal.dao.OrgDao;
import org.example.demofinal.models.Org;

import java.sql.SQLException;
import java.util.List;

public class OrgController {
    @FXML private VBox orgContainer;
    private ObservableList<Org> orgsData = FXCollections.observableArrayList();
    private OrgDao orgDao;

    @FXML
    private void initialize(){
        try {
            orgDao = new OrgDao();
            loadOrgs();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void loadOrgs(){
        orgContainer.getChildren().clear();
        orgsData.clear();

        try {
            List<Org> orgs = orgDao.getAllOrgs();
            orgsData.addAll(orgs);

            for (Org org : orgsData) {
                addOrgCard(org);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void addOrgCard(Org org){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("org-card.fxml"));
            HBox card = loader.load();
            OrgCardController controller = loader.getController();
            controller.setOrg(org, orgDao, this);
            orgContainer.getChildren().add(card);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
