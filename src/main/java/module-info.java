module org.example.fourthlab {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.mariadb.jdbc;
    requires static lombok;


    opens org.example.fourthlab to javafx.fxml;
    exports org.example.fourthlab;
}