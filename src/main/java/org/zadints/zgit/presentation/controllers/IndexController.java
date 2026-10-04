package org.zadints.zgit.presentation.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.zadints.zgit.core.config.AppLoadManager;
import org.zadints.zgit.presentation.utils.MouseMove;


public class IndexController {

    @FXML private TerminalView terminal;
    @FXML private HBox top;
    @FXML private Label appVersion;
    @FXML private Label appName;
    @FXML private Button btnClose;


    private MouseMove mouseMove;

    @FXML
    public void initialize() {
        this.mouseMove = new MouseMove(top);
        mouseMove.start();
    }

    private void loadAppSetting(){
        this.appVersion.setText(AppLoadManager.getVersion());
        this.appName.setText(AppLoadManager.getName());
    }

    @FXML protected void btnCloseOnAction(){
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }
}