package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	private static BorderPane rootLayout;

	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Enrollments.fxml"));
			rootLayout = loader.load();
			Scene scene = new Scene(rootLayout);
			scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Enrollment Software");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void loadView(String fxmlFile) {
		try {
		FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxmlFile));
		rootLayout.setCenter(loader.load());
		} catch (Exception e) {
		e.printStackTrace();
		}
		}


	public static void main(String[] args) {
		launch(args);
	}
}
