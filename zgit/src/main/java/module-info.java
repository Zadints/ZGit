module org.zadints.zgit {

    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.octicons;

    requires com.techsenger.ansi4j.core.api;
    requires com.techsenger.ansi4j.core.impl;

    requires pty4j;

    requires org.slf4j;
    requires org.slf4j.simple;

    opens org.zadints.zgit to javafx.fxml;
    opens org.zadints.zgit.presentation.controller to javafx.fxml;
    opens org.zadints.zgit.presentation.terminal to javafx.fxml;

    exports org.zadints.zgit;
    exports org.zadints.zgit.presentation.controller;
    exports org.zadints.zgit.presentation.terminal;
}