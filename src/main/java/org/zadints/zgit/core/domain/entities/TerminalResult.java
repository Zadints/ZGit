package org.zadints.zgit.core.domain.entities;

public class TerminalResult {

    private final String output;
    private final String error;
    private final int exitCode;

    public TerminalResult(
            String output,
            String error,
            int exitCode
    ) {
        this.output = output;
        this.error = error;
        this.exitCode = exitCode;
    }

    public String getOutput() {
        return output;
    }

    public String getError() {
        return error;
    }

    public int getExitCode() {
        return exitCode;
    }

    public boolean isSuccess() {
        return exitCode == 0;
    }
}