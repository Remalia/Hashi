package application;
	
import java.io.IOException;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class MainSceneController{
	
	Stage window;
	Parent root;
	Scene scene;
	
	public void scene(String file, ActionEvent event) throws IOException{
        window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		root = FXMLLoader.load(getClass().getResource(file));
        scene = new Scene(root);
		window.setResizable(false);
        window.setScene(scene);
        window.show();
	}
	
	public void img_scene(String file, MouseEvent event) throws IOException{
        window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		root = FXMLLoader.load(getClass().getResource(file));
        scene = new Scene(root);
		window.setResizable(false);
        window.setScene(scene);
        window.show();
	}
	
	@FXML
	public void switchmenu(ActionEvent event) throws IOException{
        scene("menu_s.fxml",event);
	}
	
	@FXML
	public void switchtuto(ActionEvent event) throws IOException{
        scene("tutoriel.fxml",event);
	}
	
	@FXML
	public void switchlibre(ActionEvent event) throws IOException{
		scene("jeulibre.fxml",event);
	}
	
	@FXML
	public void switchaventure(ActionEvent event) throws IOException{
		scene("jeuaventure.fxml",event);
	}
	
	@FXML
	public void retour_mode(MouseEvent event) throws IOException {
	    img_scene("menu_s.fxml",event);
	}
	
	@FXML
	public void retour_menu_p(MouseEvent event) throws IOException {
	    img_scene("menu_p.fxml",event);
	}
	
	@FXML
	public void credits(MouseEvent event) throws IOException {
	    img_scene("crédits.fxml",event);
	}
	
	@FXML
	public void quitter(ActionEvent event) {
		window = (Stage) ((Node)event.getSource()).getScene().getWindow();
	    window.close();
	}
}
