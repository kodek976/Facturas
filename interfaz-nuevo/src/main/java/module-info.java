module org.example.interfazempresanofraudulenta {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.swing;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    // Dependencias de iText 8
    // Nota: Si usas Maven, estos son los nombres de módulos automáticos correctos
    requires kernel;
    requires layout;
    requires io;
    requires commons;

    // Asegúrate de que el paquete ControladorVentana exista en src/main/java
    opens ControladorVentana to javafx.fxml;
    exports ControladorVentana;
}