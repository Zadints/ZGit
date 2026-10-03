module org.zadints.zgit {

    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.octicons;

    requires com.techsenger.ansi4j.core.api;
    requires com.techsenger.ansi4j.core.impl;

    requires pty4j;

    requires org.slf4j;

    exports org.zadints.zgit;
    opens org.zadints.zgit to javafx.fxml;


    exports org.zadints.zgit.presentation.controllers;
    opens org.zadints.zgit.presentation.controllers to javafx.fxml;
}