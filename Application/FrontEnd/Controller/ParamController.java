package Application.FrontEnd.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import java.util.prefs.BackingStoreException;

/**
 * Cette classe représente le controller des paramètres
 * @author Remi Ilango Allan Jarrier Alex Choux Anna Beranger Arthur Boullier Alexis Guimbert Mohamed Al Aftan Thibaut Duchesne
 * @version 1.0
 * @since 2023-04-02
 */

// ...

public class ParamController extends MenuController{
    boolean aide_text_box;
    boolean aide_visu_box;
    boolean mode_css_box;
    boolean aides_box;

    @FXML protected CheckBox mode_css;
    @FXML private CheckBox aides;
    @FXML private CheckBox aide_text;
    @FXML private CheckBox aide_visu;

    /**
     * Méthode setter de mode_ss
     * @param event
     * @throws BackingStoreException
     */
    @FXML
    public void set_mode_css(ActionEvent event) throws BackingStoreException {
        this.scene = this.mode_css.getScene();
        if (mode_css.isSelected()) {
            this.scene.getStylesheets().remove(this.css);
            this.css = getClass().getResource("../assets/dark_mode.css").toExternalForm();
            this.scene.getStylesheets().add(this.css);
            prefs.putBoolean("mode_css", true); // enregistre la valeur true dans les préférences
        } else {
            this.scene.getStylesheets().remove(this.css);
            this.css = getClass().getResource("../assets/light_mode.css").toExternalForm();
            this.scene.getStylesheets().add(this.css);
            prefs.putBoolean("mode_css", false); // enregistre la valeur false dans les préférences
        }
        this.mode_css_box = mode_css.isSelected();
        prefs.put("mon_css", css);

        String[] keys = prefs.keys();
        for (String key : keys) {
            System.out.println(key + " : " + prefs.get(key, ""));
        }

    }

    /**
     * Méthode setter de Aides
     * @param event
     */
    @FXML
    public void setAides(ActionEvent event) {
        this.aides_box = aides.isSelected();
        this.aide_text.setSelected(aides_box);
        this.aide_visu.setSelected(aides_box);
        setAide_text(event);
        setAide_visu(event);
        prefs.putBoolean("aides", aides_box);
    }

    /**
     * Méthode setter de Aide_text
     * @param event
     */
    @FXML
    public void setAide_text(ActionEvent event) {
        this.aide_text_box = aide_text.isSelected();
        prefs.putBoolean("aide_text", aide_text_box);
    }

    /**
     * Méthode setter de Aide_visu
     * @param event
     */
    @FXML
    public void setAide_visu(ActionEvent event) {
        this.aide_visu_box = aide_visu.isSelected();
        prefs.putBoolean("aide_visu", aide_visu_box);
    }

    /**
     * Méthode permettant d'initialiser le mode sombre et le mode clair
     */
    @FXML
    public void initialize(){
        this.aides.setSelected(prefs.getBoolean("aides", aides_box));
        this.aide_text.setSelected(prefs.getBoolean("aide_text", aide_text_box));
        this.aide_visu.setSelected(prefs.getBoolean("aide_visu", aide_visu_box));
        this.mode_css.setSelected(prefs.getBoolean("mode_css", mode_css_box));
        if (this.mode_css.isSelected()) {
            this.css = getClass().getResource("../assets/dark_mode.css").toExternalForm();
        } else {
            this.css = getClass().getResource("../assets/light_mode.css").toExternalForm();
        }
    }
}

