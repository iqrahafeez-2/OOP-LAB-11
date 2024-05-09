package com.example.demo;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    private TableView<Contact> tableView;
    private TextField nameField;
    private TextField phoneNumberField;
    private TextField emailField;

    private ObservableList<Contact> contacts = FXCollections.observableArrayList();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Address Book");

        // Name input
        Label nameLabel = new Label("Name:");
        nameField = new TextField();
        nameField.setPromptText("Enter name");

        // Phone number input
        Label phoneNumberLabel = new Label("Phone Number:");
        phoneNumberField = new TextField();
        phoneNumberField.setPromptText("Enter phone number");

        // Email input
        Label emailLabel = new Label("Email:");
        emailField = new TextField();
        emailField.setPromptText("Enter email");

        // Add button
        Button addButton = new Button("Add Contact");
        addButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        addButton.setOnAction(e -> addContact());

        // Edit button
        Button editButton = new Button("Edit Contact");
        editButton.setStyle("-fx-background-color: #007BFF; -fx-text-fill: white;");
        editButton.setOnAction(e -> editContact());

        // Delete button
        Button deleteButton = new Button("Delete Contact");
        deleteButton.setStyle("-fx-background-color: #F5F5F5; -fx-text-fill: white;");
        deleteButton.setOnAction(e -> deleteContact());

        // Select last contact button
        Button selectLastContactButton = new Button("Select Last Contact");
        selectLastContactButton.setOnAction(e -> selectContact());

        // Table
        tableView = new TableView<>();
        TableColumn<Contact, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Contact, String> phoneNumberColumn = new TableColumn<>("Phone Number");
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        TableColumn<Contact, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        tableView.getColumns().addAll(nameColumn, phoneNumberColumn, emailColumn);
        tableView.setItems(contacts);

        VBox inputLayout = new VBox(10);
        inputLayout.getChildren().addAll(nameLabel, nameField, phoneNumberLabel, phoneNumberField, emailLabel, emailField, addButton, editButton, deleteButton, selectLastContactButton);

        BorderPane layout = new BorderPane();
        layout.setLeft(inputLayout);
        layout.setCenter(tableView);

        layout.setStyle("-fx-background-color: #F5F5F5;");
        nameLabel.setStyle("-fx-font-weight: bold;");
        phoneNumberLabel.setStyle("-fx-font-weight: bold;");
        emailLabel.setStyle("-fx-font-weight: bold;");
        addButton.setStyle("-fx-padding: 10px 20px; -fx-font-size: 14px;");
        editButton.setStyle("-fx-padding: 10px 20px; -fx-font-size: 14px;");
        deleteButton.setStyle("-fx-padding: 10px 20px; -fx-font-size: 14px;");
        selectLastContactButton.setStyle("-fx-padding: 10px 20px; -fx-font-size: 14px;");
        tableView.setStyle("-fx-background-color: white;");
        nameColumn.setStyle("-fx-alignment: CENTER;");
        phoneNumberColumn.setStyle("-fx-alignment: CENTER;");
        emailColumn.setStyle("-fx-alignment: CENTER;");

        Scene scene = new Scene(layout, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addContact() {
        String name = nameField.getText();
        String phoneNumber = phoneNumberField.getText();
        String email = emailField.getText();

        Contact contact = new Contact(name, phoneNumber, email);
        contacts.add(contact);

        // Clear input fields
        nameField.clear();
        phoneNumberField.clear();
        emailField.clear();
    }

    private void editContact() {
        Contact selectedContact = tableView.getSelectionModel().getSelectedItem();
        if (selectedContact != null) {
            int index = contacts.indexOf(selectedContact);
            String newName = nameField.getText();
            String newPhoneNumber = phoneNumberField.getText();
            String newEmail = emailField.getText();
            contacts.set(index, new Contact(newName, newPhoneNumber, newEmail));
            nameField.clear();
            phoneNumberField.clear();
            emailField.clear();
        }
    }

    private void deleteContact() {
        Contact selectedContact = tableView.getSelectionModel().getSelectedItem();
        if (selectedContact != null) {
            contacts.remove(selectedContact);
            nameField.clear();
            phoneNumberField.clear();
            emailField.clear();
        }
    }

    private void selectContact() {
        tableView.getSelectionModel().select(contacts.size() - 1);
    }

    public static class Contact {
        private SimpleStringProperty name;
        private SimpleStringProperty phoneNumber;
        private SimpleStringProperty email;

        public Contact(String name, String phoneNumber, String email) {
            this.name = new SimpleStringProperty(name);
            this.phoneNumber = new SimpleStringProperty(phoneNumber);
            this.email = new SimpleStringProperty(email);
        }

        public String getName() {
            return name.get();
        }

        public void setName(String name) {
            this.name.set(name);
        }

        public String getPhoneNumber() {
            return phoneNumber.get();
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber.set(phoneNumber);
        }

        public String getEmail() {
            return email.get();
        }

        public void setEmail(String email) {
            this.email.set(email);
        }
    }
}
