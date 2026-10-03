package org.zadints.zgit.core.domain.entities;

public class TerminalCommand {

    private final String text;

    public TerminalCommand(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}