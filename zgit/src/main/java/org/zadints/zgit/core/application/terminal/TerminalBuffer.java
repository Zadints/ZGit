package org.zadints.zgit.core.application.terminal;
import javafx.scene.paint.Color;
import org.zadints.zgit.core.domain.entities.TerminalCell;

public class TerminalBuffer {

    private final int columns;
    private final int rows;

    private final TerminalCell[][] cells;

    private int cursorX;
    private int cursorY;

    private Color foreground = Color.WHITE;
    private Color background = Color.TRANSPARENT;

    private boolean bold;
    private boolean italic;
    private boolean underline;

    public TerminalBuffer(int columns, int rows) {

        this.columns = columns;
        this.rows = rows;

        cells = new TerminalCell[rows][columns];

        buildTerminal();
    }

    /*
     *  This class build a terminal
     *  @author Cesar
     */

    private void buildTerminal() {

        for (int y = 0; y < rows; y++) {

            for (int x = 0; x < columns; x++) {

                cells[y][x] = new TerminalCell();
            }
        }

        cursorX = 0;
        cursorY = 0;
    }


    public void eraseLineToEnd() {

        for (
                int x = cursorX;
                x < columns;
                x++
        ) {

            cells[cursorY][x].setCharacter(' ');

            cells[cursorY][x].setForeground(
                    foreground
            );

            cells[cursorY][x].setBackground(
                    background
            );

            cells[cursorY][x].setBold(false);
            cells[cursorY][x].setItalic(false);
            cells[cursorY][x].setUnderline(false);
        }
    }
    public void moveCursorLeft(int amount) {

        cursorX = Math.max(
                0,
                cursorX - amount
        );
    }

    public void eraseCharacter(int amount) {

        for (int i = 0; i < amount; i++) {

            if (cursorX < columns) {

                TerminalCell cell =
                        cells[cursorY][cursorX];

                cell.setCharacter(' ');

                cursorX++;
            }
        }
    }
    /*
    * Recorremos carácter por carácter
    * @param text
    */
    public void write(String text) {
        for (char character : text.toCharArray()) {
            write(character);
        }
    }
    public void carriageReturn() {
        cursorX = 0;
    }
    public void setCursorX(int x) {
        cursorX = Math.max(
                0,
                Math.min(
                        x,
                        columns - 1
                )
        );
    }
    private void write(char character) {

        switch (character) {

            case '\n' -> newLine();

            case '\r' -> cursorX = 0;

            case '\b' -> {

                System.out.println(
                        "BACKSPACE -> cursorX="
                                + cursorX
                                + " cursorY="
                                + cursorY
                );

                if (cursorX > 0) {

                    cursorX--;

                    System.out.println(
                            "Borrando celda X="
                                    + cursorX
                                    + " Y="
                                    + cursorY
                                    + " caracter="
                                    + cells[cursorY][cursorX].getCharacter()
                    );

                    TerminalCell cell =
                            cells[cursorY][cursorX];

                    cell.setCharacter(' ');
                    cell.setForeground(foreground);
                    cell.setBackground(background);
                    cell.setBold(false);
                    cell.setItalic(false);
                    cell.setUnderline(false);
                }
            }

            case '\t' -> {

                int spaces = 4 - (cursorX % 4);

                for (int i = 0; i < spaces; i++) {
                    write(' ');
                }
            }

            default -> {

                if (character < 32) {
                    return;
                }

                if (cursorX >= columns) {
                    newLine();
                }

                TerminalCell cell =
                        cells[cursorY][cursorX];

                cell.setCharacter(character);
                cell.setForeground(foreground);
                cell.setBackground(background);
                cell.setBold(bold);
                cell.setItalic(italic);
                cell.setUnderline(underline);

                System.out.println(character + " ->Escrito en:" + cursorY + ", "+ cursorX);

                cursorX++;
            }
        }
    }

    private void newLine() {

        cursorX = 0;

        cursorY++;

        if (cursorY >= rows) {

            scroll();

            cursorY = rows - 1;
        }
    }

    private void scroll() {

        for (int y = 1; y < rows; y++) {

            cells[y - 1] = cells[y];
        }

        cells[rows - 1] =
                new TerminalCell[columns];

        for (int x = 0; x < columns; x++) {

            cells[rows - 1][x] =
                    new TerminalCell();
        }
    }

    public void setForeground(Color color) {
        this.foreground = color;
    }

    public void setBackground(Color color) {
        this.background = color;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public void setItalic(boolean italic) {
        this.italic = italic;
    }

    public void setUnderline(boolean underline) {
        this.underline = underline;
    }

    public TerminalCell getCell(int x, int y) {
        return cells[y][x];
    }

    public int getCursorX() {
        return cursorX;
    }

    public int getCursorY() {
        return cursorY;
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }
}
