module com.example.sample {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;
    requires javafx.media;


    opens com.example.sample to javafx.fxml;
    exports com.example.sample;
}