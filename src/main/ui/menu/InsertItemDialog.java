package main.ui.menu;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.businesslogic.CatERing;
import main.businesslogic.procedure.Procedure;

import java.util.Optional;

public class InsertItemDialog {
    Stage myStage;

    @FXML
    ComboBox<Procedure> recipeCombo;

    @FXML
    CheckBox descCheck;

    @FXML
    TextField descField;

    @FXML
    Button insertButton;

    // Results
    private Procedure selectedProcedure;
    private boolean hasDescription;
    private String description;
    private boolean confirmed;

    public void initialize() {
        recipeCombo.setItems(CatERing.getInstance().getProcedureManager().getRecipes());
        confirmed = false;
    }

    public void setOwnStage(Stage stage) {
        myStage = stage;
    }

    @FXML
    public void recipeComboSelection() {
        Procedure sel = recipeCombo.getValue();
        descCheck.setDisable(sel == null);
        descField.setDisable(sel == null || !descCheck.isSelected());
        insertButton.setDisable(sel == null);

    }

    @FXML
    public void descCheckSelection() {
        if (descCheck.isSelected()) {
            descField.setDisable(false);
        } else {
            descField.setText("");
            descField.setDisable(true);
        }
    }

    @FXML
    public void aggiungiButtonPressed() {
        confirmed = true;
        selectedProcedure = recipeCombo.getValue();
        hasDescription = descCheck.isSelected();
        description = descField.getText();
        myStage.close();
    }

    @FXML
    public void annullaButtonPressed() {
        confirmed = false;
        myStage.close();
    }

    public Optional<Procedure> getSelectedProcedure() {
        if (!confirmed) selectedProcedure = null;
        return Optional.ofNullable(selectedProcedure);
    }

    public Optional<String> getDescription() {
        if (!confirmed || !hasDescription) description = null;
        return Optional.ofNullable(description);
    }
}
