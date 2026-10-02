package org.zadints.zgit;

import javafx.application.Application;
import org.kordamp.ikonli.octicons.Octicons;

public class Launcher {
    public static void main(String[] args) {
        /*
        for (Octicons icon : Octicons.values()) {
            System.out.println(icon.name());
        }*/
        Application.launch(HelloApplication.class, args);
    }
}
