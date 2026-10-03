package org.zadints.zgit.core.application.terminal;

import org.zadints.zgit.core.domain.entities.TerminalResult;
import org.zadints.zgit.infraestructure.terminal.PowerShellProcess;


public class TerminalService {

    private final CommandParser commandParser;
    private final PowerShellProcess powerShell;

    public TerminalService() {

        commandParser =
                new CommandParser();

        powerShell =
                new PowerShellProcess();
    }

    public TerminalResult execute(
            String command
    ) {

        String realCommand =
                commandParser.parse(command);

        return powerShell.execute(
                realCommand
        );
    }
}