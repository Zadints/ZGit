package org.zadints.zgit.presentation.terminal;




import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import org.zadints.zgit.core.application.terminal.TerminalBuffer;
import org.zadints.zgit.core.domain.entities.TerminalCell;

public class TerminalView extends StackPane {

        private final Canvas canvas;

        private TerminalBuffer buffer;

        private final Font font =
                Font.font("Consolas", FontWeight.NORMAL, 14);

        private final double cellWidth = 8.4;
        private final double cellHeight = 18;

        public TerminalView() {

            canvas = new Canvas();

            getChildren().add(canvas);

            setFocusTraversable(true);

            setStyle(
                    "-fx-background-color: #0B1020;"
            );

            widthProperty().addListener(
                    (obs, oldValue, newValue) -> resizeCanvas()
            );

            heightProperty().addListener(
                    (obs, oldValue, newValue) -> resizeCanvas()
            );

            setOnMouseClicked(event -> requestFocus());
        }

        public void setBuffer(TerminalBuffer buffer) {

            this.buffer = buffer;

            render();
        }

        private void resizeCanvas() {

            canvas.setWidth(getWidth());
            canvas.setHeight(getHeight());

            render();
        }
    public Canvas getCanvas() {
        return canvas;
    }
        public void render() {

            if (buffer == null) {
                return;
            }

            GraphicsContext gc =
                    canvas.getGraphicsContext2D();

            gc.setFill(Color.web("#0B1020"));

            gc.fillRect(
                    0,
                    0,
                    canvas.getWidth(),
                    canvas.getHeight()
            );

            gc.setFont(font);

            for (int y = 0; y < buffer.getRows(); y++) {

                for (int x = 0; x < buffer.getColumns(); x++) {

                    TerminalCell cell =
                            buffer.getCell(x, y);

                    double px =
                            x * cellWidth;

                    double py =
                            y * cellHeight;

                    if (cell.getBackground()
                            != Color.TRANSPARENT) {

                        gc.setFill(
                                cell.getBackground()
                        );

                        gc.fillRect(
                                px,
                                py,
                                cellWidth,
                                cellHeight
                        );
                    }

                    char character =
                            cell.getCharacter();

                    if (character != ' ') {

                        gc.setFill(
                                cell.getForeground()
                        );

                        gc.fillText(
                                String.valueOf(character),
                                px,
                                py + 14
                        );
                    }

                    if (cell.isUnderline()) {

                        gc.setStroke(
                                cell.getForeground()
                        );

                        gc.strokeLine(
                                px,
                                py + 16,
                                px + cellWidth,
                                py + 16
                        );
                    }
                }
            }
        }
    }
