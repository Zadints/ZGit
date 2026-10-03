package org.zadints.zgit.presentation.controllers;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import org.zadints.zgit.core.application.terminal.TerminalService;
import org.zadints.zgit.core.domain.entities.TerminalResult;

public class TerminalView extends VBox {

    private final VBox history;

    private final ScrollPane scrollPane;

    private final TextField input;

    private final TerminalService terminalService;

    public TerminalView() {

        terminalService =
                new TerminalService();

        history = new VBox(6);
        history.setPadding(new Insets(12));
        history.getStyleClass().add("terminal-history");

        scrollPane = new ScrollPane(history);
        scrollPane.getStyleClass().add("terminal-scroll");
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(
                ScrollPane.ScrollBarPolicy.NEVER
        );

        input = new TextField();

        input.setPromptText("Escribe un comando...");

        input.setOnAction(
                event -> ejecutar()
        );

        getChildren().addAll(
                scrollPane,
                input
        );

        VBox.setVgrow(
                scrollPane,
                Priority.ALWAYS
        );

        aplicarEstilos();
    }

    private void ejecutar() {

        String command =
                input.getText().trim();

        if (command.isEmpty()) {
            return;
        }

        mostrarComando(command);

        input.clear();

        Thread thread =
                new Thread(() -> {

                    TerminalResult result =
                            terminalService.execute(
                                    command
                            );

                    javafx.application.Platform.runLater(
                            () -> mostrarResultado(result)
                    );
                });

        thread.setDaemon(true);

        thread.start();
    }

    private void mostrarComando(
            String command
    ) {

        HBox line =
                new HBox(8);

        line.setAlignment(
                Pos.CENTER_LEFT
        );

        Label prompt =
                new Label("❯");

        prompt.getStyleClass().add(
                "terminal-prompt"
        );

        Label text =
                new Label(command);

        text.getStyleClass().add("terminal-command");

        line.getChildren().addAll(
                prompt,
                text
        );

        history.getChildren().add(line);
    }

    private void mostrarResultado(
            TerminalResult result
    ) {

        if (!result.getOutput().isBlank()) {

            Label output =
                    new Label(
                            result.getOutput()
                    );

            output.setWrapText(true);

            output.getStyleClass().add(
                    "terminal-output"
            );

            history.getChildren().add(
                    output
            );
        }

        if (!result.getError().isBlank()) {

            Label error =
                    new Label(
                            result.getError()
                    );

            error.setWrapText(true);

            error.getStyleClass().add(
                    "terminal-error"
            );

            history.getChildren().add(
                    error
            );
        }

        scrollAbajo();
    }

    private void scrollAbajo() {

        javafx.application.Platform.runLater(
                () -> scrollPane.setVvalue(1.0)
        );
    }

    private void aplicarEstilos() {

        setStyle(
                "-fx-background-color: #0B1020;"
        );

        history.setStyle(
                "-fx-background-color: #0B1020;"
        );

        input.setStyle("""
            -fx-background-color: #111827;
            -fx-text-fill: #E5E7EB;
            -fx-prompt-text-fill: #6B7280;
            -fx-font-family: "JetBrains Mono";
            -fx-font-size: 14px;
            -fx-padding: 12px;
        """);
    }
}