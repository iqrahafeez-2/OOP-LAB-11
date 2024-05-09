import javafx.application.Application;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {

    private TableView<Student> tableView;
    private TextField nameField;
    private TextField idField;
    private TextField emailField;
    private ListView<String> courseListView;

    @Override
    public void start(Stage primaryStage) {
        // Create form fields
        Label nameLabel = new Label("Name:");
        nameField = new TextField();
        Label idLabel = new Label("ID:");
        idField = new TextField();
        Label emailLabel = new Label("Email:");
        emailField = new TextField();

        // Create table view
        tableView = new TableView<>();
        TableColumn<Student, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Student, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Student, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        TableColumn<Student, List<String>> coursesColumn = new TableColumn<>("Courses");
        coursesColumn.setCellValueFactory(new PropertyValueFactory<>("courses"));
        tableView.getColumns().addAll(nameColumn, idColumn, emailColumn, coursesColumn);

        // Create course selection list
        Label courseLabel = new Label("Select Courses:");
        courseListView = new ListView<>();
        courseListView.getItems().addAll("ECS", "ISE", "OOP", "AP", "CP");
        courseListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Listen for selection changes and display the selected student's information in the form
        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Student>() {
            @Override
            public void changed(ObservableValue<? extends Student> observable, Student oldValue, Student newValue) {
                if (newValue != null) {
                    nameField.setText(newValue.getName());
                    idField.setText(newValue.getId());
                    emailField.setText(newValue.getEmail());
                }
            }
        });

        // Create save button
        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> saveToFile());

        // Create layout
        VBox formLayout = new VBox(10);
        formLayout.setPadding(new Insets(10));
        formLayout.getChildren().addAll(nameLabel, nameField, idLabel, idField, emailLabel, emailField, courseLabel, courseListView);

        HBox buttonLayout = new HBox(10);
        buttonLayout.getChildren().addAll(saveButton);

        VBox root = new VBox(10);
        root.getChildren().addAll(formLayout, buttonLayout, tableView);

        // Set up scene and stage
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Student Registration Form");
        primaryStage.show();
    }

    private void saveToFile() {
        String name = nameField.getText();
        String id = idField.getText();
        String email = emailField.getText();
        List<String> selectedCourses = new ArrayList<>(courseListView.getSelectionModel().getSelectedItems());

        if (!name.isEmpty() && !id.isEmpty() && !email.isEmpty() && !selectedCourses.isEmpty()) {
            // Create a new Student object
            Student student = new Student(name, id, email, selectedCourses);

            // Add the student to the table view
            tableView.getItems().add(student);

            // Clear form fields
            nameField.clear();
            idField.clear();
            emailField.clear();
            courseListView.getSelectionModel().clearSelection();
        } else {
            // Display an error message if any field is empty or no course is selected
            System.out.println("Please fill out all fields and select at least one course.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

class Student {
    private String name;
    private String id;
    private String email;
    private List<String> courses;

    // Constructor
    public Student(String name, String id, String email, List<String> courses) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.courses = courses;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getCourses() {
        return courses;
    }

    public void setCourses(List<String> courses) {
        this.courses = courses;
    }
}
