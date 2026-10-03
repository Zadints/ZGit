package org.zadints.zgit.infraestructure.terminal;

import org.zadints.zgit.core.domain.entities.TerminalResult;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class PowerShellProcess {

    public TerminalResult execute(String command) {

        try {

            ProcessBuilder builder =
                    new ProcessBuilder(
                            "powershell.exe",
                            "-NoLogo",
                            "-NoProfile",
                            "-Command",
                            command
                    );

            Process process =
                    builder.start();

            StringBuilder output =
                    new StringBuilder();

            StringBuilder error =
                    new StringBuilder();

            try (
                    BufferedReader stdout =
                            new BufferedReader(
                                    new InputStreamReader(
                                            process.getInputStream(),
                                            StandardCharsets.UTF_8
                                    )
                            );

                    BufferedReader stderr =
                            new BufferedReader(
                                    new InputStreamReader(
                                            process.getErrorStream(),
                                            StandardCharsets.UTF_8
                                    )
                            )
            ) {

                String line;

                while ((line = stdout.readLine()) != null) {

                    output
                            .append(line)
                            .append("\n");
                }

                while ((line = stderr.readLine()) != null) {

                    error
                            .append(line)
                            .append("\n");
                }
            }

            int exitCode =
                    process.waitFor();

            return new TerminalResult(
                    output.toString(),
                    error.toString(),
                    exitCode
            );

        } catch (Exception e) {

            return new TerminalResult(
                    "",
                    e.getMessage(),
                    -1
            );
        }
    }
}