package org.zadints.zgit.infraestructure.terminal;

import com.techsenger.ansi4j.core.api.ParserFactory;
import com.techsenger.ansi4j.core.api.StreamParser;
import com.techsenger.ansi4j.core.api.function.FunctionArgument;
import com.techsenger.ansi4j.core.api.iso6429.ControlFunctionType;
import com.techsenger.ansi4j.core.api.Environment;
import com.techsenger.ansi4j.core.api.Fragment;
import com.techsenger.ansi4j.core.api.FunctionFragment;
import javafx.scene.paint.Color;
import org.zadints.zgit.core.application.terminal.TerminalBuffer;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class AnsiTerminalParser {

    private final TerminalBuffer buffer;
    private StreamParser parser;

    public AnsiTerminalParser(TerminalBuffer buffer) {
        this.buffer = buffer;
    }

    public void start(InputStream input) {

        Thread thread = new Thread(() -> {

            try {

                ParserFactory factory = new ParserFactory.Builder()
                    .environment(
                            Environment._7_BIT
                    )
                    .functionTypes(
                            ControlFunctionType.C0_SET,
                            ControlFunctionType.C1_SET,
                            ControlFunctionType.CONTROL_SEQUENCE,
                            ControlFunctionType.INDEPENDENT_FUNCTION,
                            ControlFunctionType.CONTROL_STRING
                    )
                    .build();

                parser = factory.createParser(
                                input,
                                StandardCharsets.UTF_8,
                                4096
                        );

                while (true) {

                    Fragment fragment = parser.parse();

                    if (fragment == null) {
                        break;
                    }

                    procesarFragmento(fragment);
                }

            } catch (Exception e) {

                e.printStackTrace();
            }

        });

        thread.setDaemon(true);
        thread.start();
    }

    private void procesarFragmento(Fragment fragment) {

        if (fragment instanceof FunctionFragment function) {
            procesarFuncion(function);

        } else {

            buffer.write(fragment.getText());
        }
    }
    private void borrarLinea(List<FunctionArgument> arguments) {

        if (arguments.isEmpty()) {

            buffer.eraseLineToEnd();

            return;
        }

        String value =
                arguments
                        .get(0)
                        .getValue()
                        .toString();
        String type = null;

        switch (value) {

            case "0" ->{
                buffer.eraseLineToEnd();
                type = "case 0";
            }

            default -> {
                type = "default";
            }
        }

        System.out.println("-------------- BORRAR -----------------");
        System.out.println("A borrar: "+ value);
        System.out.println("---------------------------------------");
    }
    private void procesarFuncion(FunctionFragment function) {

        String nombre = function.getFunction().toString();

        switch (nombre) {

            case "EL" ->
                    borrarLinea(
                            function.getArguments()
                    );

            case "CR" ->
                    buffer.carriageReturn();

            case "CHA" ->
                    moverCursorHorizontal(
                            function.getArguments()
                    );
            case "SGR" ->
                    aplicarSGR(
                            function.getText()
                    );
            default -> {
            }
        }
    }
    private void moverCursorHorizontal(
            List<FunctionArgument> arguments
    ) {

        if (arguments.isEmpty()) {

            buffer.setCursorX(0);

            return;
        }

        int columna =
                Integer.parseInt(
                        arguments
                                .get(0)
                                .getValue()
                                .toString()
                );

        buffer.setCursorX(
                columna - 1
        );
    }
    private void aplicarSGR(
            String sequence
    ) {

        String values =
                sequence
                        .substring(2, sequence.length() - 1);

        if (values.isBlank()) {

            resetStyle();

            return;
        }

        String[] parameters =
                values.split(";");

        for (String parameter : parameters) {

            int value =
                    Integer.parseInt(parameter);

            aplicarParametro(value);
        }
    }

    private void aplicarParametro(
            int value
    ) {

        switch (value) {

            case 0 -> resetStyle();

            case 1 ->
                    buffer.setBold(true);

            case 3 ->
                    buffer.setItalic(true);

            case 4 ->
                    buffer.setUnderline(true);

            case 22 ->
                    buffer.setBold(false);

            case 23 ->
                    buffer.setItalic(false);

            case 24 ->
                    buffer.setUnderline(false);

            case 30 ->
                    buffer.setForeground(Color.BLACK);

            case 31 ->
                    buffer.setForeground(Color.RED);

            case 32 ->
                    buffer.setForeground(Color.GREEN);

            case 33 ->
                    buffer.setForeground(Color.YELLOW);

            case 34 ->
                    buffer.setForeground(Color.BLUE);

            case 35 ->
                    buffer.setForeground(Color.MAGENTA);

            case 36 ->
                    buffer.setForeground(Color.CYAN);

            case 37 ->
                    buffer.setForeground(Color.WHITE);

            case 39 ->
                    buffer.setForeground(Color.WHITE);
        }
    }

    private void resetStyle() {

        buffer.setForeground(Color.WHITE);
        buffer.setBackground(Color.TRANSPARENT);

        buffer.setBold(false);
        buffer.setItalic(false);
        buffer.setUnderline(false);
    }
}