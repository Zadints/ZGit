package org.zadints.zgit.core.application.terminal;

public class CommandParser {

    public String parse(String command) {

        command = command.trim();

        return switch (command) {

            case "status" ->
                    "git status";

            case "branches" ->
                    "git branch";

            case "log" ->
                    "git log --oneline";

            case "pull" ->
                    "git pull";

            case "push" ->
                    "git push";

            default ->
                    command;
        };
    }
}
