package org.zadints.zgit.core.application.terminal;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.zadints.zgit.core.domain.entities.TerminalCell;

public class TerminalRenderer {

    private final Canvas canvas;

    private final TerminalBuffer buffer;
    private final Font normalFont =
            Font.font(
                    "JetBrains Mono",
                    14
            );

    private final Font boldFont =
            Font.font(
                    "JetBrains Mono",
                    FontWeight.BOLD,
                    14
            );

    private final double cellWidth = 9;
    private final double cellHeight = 18;

    public TerminalRenderer(Canvas canvas, TerminalBuffer buffer) {

        this.canvas = canvas;
        this.buffer = buffer;

        render();
    }

    public void render() {

        GraphicsContext graphics = canvas.getGraphicsContext2D();

        graphics.setFill(Color.rgb(11, 16, 32));

        graphics.fillRect(
                0,
                0,
                canvas.getWidth(),
                canvas.getHeight()
        );

        for (int y = 0; y < buffer.getRows(); y++) {

            for (int x = 0; x < buffer.getColumns(); x++) {

                TerminalCell cell = buffer.getCell(x, y);

                double px = x * cellWidth;
                double py = y * cellHeight;

                if (!cell.getBackground().equals(Color.TRANSPARENT)) {

                    graphics.setFill(
                            cell.getBackground()
                    );

                    graphics.fillRect(
                            px,
                            py,
                            cellWidth,
                            cellHeight
                    );
                }

                if (cell.getCharacter() != ' ') {

                    Font font = cell.isBold()
                            ? boldFont
                            : normalFont;

                    graphics.setFont(font);

                    graphics.setFill(
                            cell.getForeground()
                    );

                    graphics.fillText(
                            String.valueOf(cell.getCharacter()),
                            px,
                            py + 14
                    );
                }
            }
        }

        // Cursor

        double cursorX =
                buffer.getCursorX() * cellWidth;

        double cursorY =
                buffer.getCursorY() * cellHeight;

        graphics.setFill(Color.rgb(0, 166, 255));

        graphics.fillRect(
                cursorX,
                cursorY,
                2,
                cellHeight
        );
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
