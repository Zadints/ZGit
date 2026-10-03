package org.zadints.zgit.presentation.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import org.zadints.zgit.presentation.utils.MouseMove;


public class IndexController {

    @FXML private TerminalView terminal;
    @FXML private HBox top;
    private MouseMove mouseMove;

    @FXML
    public void initialize() {

        this.mouseMove = new MouseMove(top);
        mouseMove.start();
    }
}