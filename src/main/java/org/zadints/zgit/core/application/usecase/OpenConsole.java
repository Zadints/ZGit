package org.zadints.zgit.core.application.usecase;

import com.pty4j.PtyProcess;
import com.pty4j.PtyProcessBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class OpenConsole {

    private PtyProcess process;

    private OutputStream output;

    private InputStream input;

    public void start() throws IOException {

        String[] command = {
                "powershell.exe",
                "-NoLogo"
        };

        Map<String, String> environment =
                new HashMap<>(System.getenv());

        environment.putIfAbsent(
                "TERM",
                "xterm"
        );

        process =
                new PtyProcessBuilder()
                        .setCommand(command)
                        .setEnvironment(environment)
                        .start();

        output =
                process.getOutputStream();

        input =
                process.getInputStream();
    }

    public void write(
            String command
    ) throws IOException {

        output.write(
                (command + "\r\n")
                        .getBytes(
                                StandardCharsets.UTF_8
                        )
        );

        output.flush();
    }

    public InputStream getInputStream() {
        return input;
    }
    public OutputStream getOutputStream() {
        return output;
    }
    public void close() {

        if (
                process != null &&
                        process.isAlive()
        ) {

            process.destroy();
        }
    }
}