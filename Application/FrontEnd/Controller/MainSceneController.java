package Application.FrontEnd.Controller;

import java.io.IOException;
import java.util.Objects;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;

/**
 * This class is the controller of the main scene
 * It allows to switch between the different scenes of the application
 * It also allows to quit the application
 * @author Remi Ilango Allan Jarrier Alex Choux Anna Beranger Arthur Boullier Alexis Guimbert Mohamed Al Aftan Thibaut Duchesne
 * @version 1.0
 * @since 2023-04-02
 */
public class MainSceneController{
	
	// Attributes
	Stage window;
	Parent root;
	Scene scene;
	
	/**
	 * Switch between the different scenes of the application
	 * @param event : the event that triggers the switch
	 * @throws IOException Exception if the file is not found
	 */
	public void scene(String file, ActionEvent event) throws IOException{
        window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(file)));
        scene = new Scene(root);
		window.setResizable(false);
        window.setScene(scene);
        window.show();
	}
	
	/**
	 * Switch between the different scenes of the application with an image
	 * @param file : the file of the scene to switch to 
	 * @param event : the event that triggers the switch 
	 * @throws IOException Exception if the file is not found
	 */
	public void img_scene(String file, MouseEvent event) throws IOException{
        window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(file)));
        scene = new Scene(root);
		window.setResizable(false);
        window.setScene(scene);
        window.show();
	}

}
