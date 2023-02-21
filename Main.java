package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	
	Parent root;
	Scene scene;
	
	@Override
	public void start(Stage window) {
		try {
			root = FXMLLoader.load(getClass().getResource("menu_p.fxml"));
			scene = new Scene(root);
			window.setTitle("Hashi");
			window.setScene(scene);
			window.setResizable(false);
			window.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
