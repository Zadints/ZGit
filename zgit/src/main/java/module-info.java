module org.zadints.zgit {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.zadints.zgit to javafx.fxml;
    exports org.zadints.zgit;
}