package controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

import data.CourseDAO;
import data.DBConnection;
import data.EnrollmentDAO;
import data.StudentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Enrollment;
import model.Student;
import model.Course;

public class EnrollmentsController {

	@FXML
	private ComboBox<String> studentComboBox;
	@FXML
	private ComboBox<String> courseComboBox;
	@FXML
	private DatePicker enrollmentDatePicker;
	@FXML
	private TableView<Enrollment> enrollmentTable;
	@FXML
	private TableView<Student> StudentsCredView;
	@FXML
	private TableColumn<Enrollment, String> studentIdColumn;
	@FXML
	private TableColumn<Enrollment, String> courseCodeColumn;
	@FXML
	private TableColumn<Enrollment, LocalDate> dateColumn;
	@FXML
	private TableColumn<Student, String> StudentsColumn2;
	@FXML
	private TableColumn<Student, String> IDstudentColumn;
	@FXML
	private TableColumn<Student, String> CreditsColumn2;
	@FXML
	private Label credits;

	private Connection connection = DBConnection.getInstance().getConnection();
	private EnrollmentDAO enrollmentDAO = new EnrollmentDAO(connection);
	private StudentDAO studentDAO = new StudentDAO(connection);
	private CourseDAO courseDAO = new CourseDAO(connection);

	@FXML
	public void initialize() {
		studentIdColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
		courseCodeColumn.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<>("enrollmentDate"));
		
		IDstudentColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		StudentsColumn2.setCellValueFactory(new PropertyValueFactory<>("name"));
		CreditsColumn2.setCellValueFactory(new PropertyValueFactory<>("totalCredits"));

		enrollmentDatePicker.setValue(LocalDate.now());
		loadComboBoxes();
		loadEnrollments();
	}

	private void loadComboBoxes() {
		studentComboBox.getItems().setAll(studentDAO.fetch().stream().map(Student::getId).toList());
		courseComboBox.getItems().setAll(courseDAO.fetch().stream().map(Course::getCode).toList());
	}

	@FXML
	private void handleSave(ActionEvent event) throws SQLException {
		Student selectedStudent = studentDAO.fetch().stream().filter(s -> s.getId().equals(studentComboBox.getValue()))
				.findFirst().orElse(null);

		Course selectedCourse = courseDAO.fetch().stream().filter(c -> c.getCode().equals(courseComboBox.getValue()))
				.findFirst().orElse(null);
		Enrollment enrollment = new Enrollment(studentComboBox.getValue(), courseComboBox.getValue(),
				enrollmentDatePicker.getValue());

		enrollmentDAO.save(enrollment, selectedStudent, selectedCourse);
		loadEnrollments();
	}

	@FXML
	private void handleRemove(ActionEvent event) throws SQLException {
		enrollmentDAO.delete(null, null, null);
		showAlert("REALIZADO!", "Informacion Borrada!", "La informacion ha sido borrada con exito!");
	}

	private void loadEnrollments() {
		enrollmentTable.getItems().setAll(enrollmentDAO.fetch());
		StudentsCredView.getItems().setAll(studentDAO.fetch());
	}

	private void showAlert(String title, String header, String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(message);
		alert.showAndWait();
	}
}