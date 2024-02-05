module org.sersun.tfcc.tfcc {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.google.gson;

    opens org.sersun.tfcc.tfcc to javafx.fxml;
    exports org.sersun.tfcc.tfcc;
}