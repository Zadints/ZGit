package org.zadints.zgit.presentation.controller;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import org.zadints.zgit.core.application.terminal.TerminalBuffer;
import org.zadints.zgit.core.application.terminal.TerminalRenderer;
import org.zadints.zgit.core.application.usecase.OpenConsole;
import org.zadints.zgit.infraestructure.terminal.AnsiTerminalParser;
import org.zadints.zgit.presentation.terminal.TerminalView;

public class IndexController {

    @FXML
    private TerminalView terminal;

    private OpenConsole console;
    private TerminalBuffer buffer;
    private TerminalRenderer renderer;
    private AnsiTerminalParser parser;

    private AnimationTimer renderTimer;

    @FXML
    public void initialize() {

        createTerminal();
        startPowerShell();
        configurarTeclado();
    }

    private void createTerminal() {

        buffer = new TerminalBuffer(120, 40);

        renderer = new TerminalRenderer(terminal.getCanvas(), buffer);

        terminal.setBuffer(buffer);

        renderer.render();
    }

    private void startPowerShell() {

        console = new OpenConsole();

        try {

            console.start();

            parser = new AnsiTerminalParser(buffer);
            parser.start(console.getInputStream());

            renderTimer = new AnimationTimer() {

                @Override
                public void handle(long now) {

                    renderer.render();
                }
            };

            renderTimer.start();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }



    private void configurarTeclado() {

        terminal.setFocusTraversable(true);

        terminal.addEventHandler(
                KeyEvent.KEY_TYPED,
                this::procesarTexto
        );

        terminal.addEventHandler(
                KeyEvent.KEY_PRESSED,
                this::procesarTecla
        );

        terminal.setOnMouseClicked(event ->
                terminal.requestFocus()
        );

        terminal.requestFocus();
    }

    private void procesarTexto(KeyEvent event) {

        String text = event.getCharacter();

        if (text == null || text.isEmpty()) {
            return;
        }
        if (text.equals("\r")
                || text.equals("\n")
                || text.equals("\t")) {

            return;
        }

        try {
            enviar(text);
        } catch (Exception e) {
            e.printStackTrace();
        }

        event.consume();
    }

    private void procesarTecla(KeyEvent event) {
        System.out.println(
                "TECLA: " + event.getCode()
        );

        try {

            KeyCode code = event.getCode();

            if (code == KeyCode.ENTER) {
                enviar("\r");
                event.consume();
                return;
            }

            if (code == KeyCode.BACK_SPACE) {
                enviar("\b");
                event.consume();
                return;
            }

            if (code == KeyCode.DELETE) {
                enviar("\u001B[3~");
                event.consume();
                return;
            }

            if (code == KeyCode.TAB) {
                enviar("\t");
                event.consume();
                return;
            }

            if (code == KeyCode.ESCAPE) {
                enviar("\u001B");
                event.consume();
                return;
            }

            if (code == KeyCode.UP) {
                enviar("\u001B[A");
                event.consume();
                return;
            }

            if (code == KeyCode.DOWN) {
                enviar("\u001B[B");
                event.consume();
                return;
            }

            if (code == KeyCode.RIGHT) {
                enviar("\u001B[C");
                event.consume();
                return;
            }

            if (code == KeyCode.LEFT) {
                enviar("\u001B[D");
                event.consume();
                return;
            }

            if (event.isControlDown()
                    && code == KeyCode.C) {

                enviar("\u0003");
                event.consume();
                return;
            }

            if (event.isControlDown()
                    && code == KeyCode.L) {

                enviar("\u000C");
                event.consume();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void enviar(String text) throws Exception {

        console.getOutputStream()
                .write(
                        text.getBytes(
                                java.nio.charset.StandardCharsets.UTF_8
                        )
                );

        console.getOutputStream().flush();
    }
}