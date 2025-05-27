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
            throw new RuntimeException(e);
        }
    }

    /**
     * Загружает список организаций из базы данных и отображает их в интерфейсе.
     * Метод выполняет следующие действия:
     * 1. Получает список организаций из базы данных через DAO
     * 2. Обновляет ObservableList orgsData новыми данными
     * 3. Очищает контейнер с карточками организаций
     * 4. Создает и добавляет карточки для каждой организации
     */
    private void loadOrgs(){
        try {
            // Получаем список всех организаций из базы данных
            List<Org> orgs = orgDao.getAllOrgs();
            
            // Обновляем ObservableList новыми данными
            // setAll() заменяет все элементы в списке на новые
            // Это эффективнее, чем clear() + addAll()
            orgsData.setAll(orgs);
            
            // Очищаем контейнер с карточками
            orgContainer.getChildren().clear();
            
            // Для каждой организации в списке создаем и добавляем карточку
            // forEach - это метод, который применяет указанную функцию к каждому элементу списка
            // this::addOrgCard - это ссылка на метод addOrgCard текущего класса
            // Это эквивалентно записи: for (Org org : orgsData) { addOrgCard(org); }
            orgsData.forEach(this::addOrgCard);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void addOrgCard(Org org){
        try {
            FXMLLoader loader = new FXMLLoader(MasterApp.class.getResource("org-card.fxml"));
            HBox card = loader.load();
            OrgCardController controller = loader.getController();
            controller.setOrg(org, orgDao);
            orgContainer.getChildren().add(card);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
