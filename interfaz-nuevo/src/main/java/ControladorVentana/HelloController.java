package ControladorVentana;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HelloController {

    @FXML private TextField textoNombre;
    @FXML private TextField textoCif;
    @FXML private TextField textoFecha;
    @FXML private TextField textoRenta;
    @FXML private TextField textoComunidad;
    @FXML private TextField textoOtros;
    @FXML private Label     labelMensaje;

    @FXML
    private void confirmar() {

        // Leer los datos de la interfaz
        String nombre    = textoNombre.getText().trim();
        String cif       = textoCif.getText().trim();
        String fecha     = textoFecha.getText().trim();
        String rentaStr  = textoRenta.getText().trim().replace(",", ".");
        String comunStr  = textoComunidad.getText().trim().replace(",", ".");
        String otrosStr  = textoOtros.getText().trim().replace(",", ".");

        // Validación básica: campos obligatorios no vacíos
        if (nombre.isEmpty() || cif.isEmpty() || fecha.isEmpty() || rentaStr.isEmpty()) {
            labelMensaje.setText("⚠ Rellena al menos nombre, CIF, fecha y renta.");
            labelMensaje.setStyle("-fx-text-fill: red;");
            return;
        }

        // Convertir importes a número
        double renta, comunidad, otros;
        try {
            renta     = Double.parseDouble(rentaStr);
            comunidad = comunStr.isEmpty() ? 0 : Double.parseDouble(comunStr);
            otros     = otrosStr.isEmpty() ? 0 : Double.parseDouble(otrosStr);
        } catch (NumberFormatException e) {
            labelMensaje.setText("⚠ Los importes deben ser números (ej: 800 o 800,50).");
            labelMensaje.setStyle("-fx-text-fill: red;");
            return;
        }

        // Crear el inquilino y generar el PDF
        try {
            Inquilino        inquilino  = new Inquilino(nombre, cif, fecha, renta, comunidad, otros);
            GeneradorFactura generador  = new GeneradorFactura();
            String           ruta       = generador.generar(inquilino);

            labelMensaje.setText("✅ Factura generada en: " + ruta);
            labelMensaje.setStyle("-fx-text-fill: green;");
        } catch (Exception e) {
            labelMensaje.setText("❌ Error al generar la factura: " + e.getMessage());
            labelMensaje.setStyle("-fx-text-fill: red;");
        }
    }
}
