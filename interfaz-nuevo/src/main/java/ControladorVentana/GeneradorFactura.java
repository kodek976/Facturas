package ControladorVentana;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import java.io.File;
import java.io.IOException;

public class GeneradorFactura {

    public String generar(Inquilino inquilino) throws IOException {

        // Crear carpeta de salida si no existe
        new File("facturas_generadas").mkdirs();

        // Nombre del archivo
        String archivo = "facturas_generadas/factura_" + inquilino.fecha.replace("/", "-") + ".pdf";

        PdfWriter   writer  = new PdfWriter(archivo);
        PdfDocument pdf     = new PdfDocument(writer);
        Document    doc     = new Document(pdf, PageSize.A4);
        doc.setMargins(40, 40, 40, 40);

        // ── CABECERA ──────────────────────────────────────────────────────
        doc.add(new Paragraph("MARÍA JOSÉ LÓPEZ LÓPEZ")
            .setBold().setFontSize(18));
        doc.add(new Paragraph("SANTA HORTENSIA, 15, 28002-MADRID - NIF: 12345678X")
            .setFontSize(9));

        doc.add(new Paragraph(" "));

        // ── NÚMERO Y FECHA ────────────────────────────────────────────────
        Table tabFecha = new Table(UnitValue.createPercentArray(new float[]{50, 50}))
            .setWidth(UnitValue.createPercentValue(100));
        tabFecha.addCell(celda("NÚMERO FACTURA:  024/2025", true));
        tabFecha.addCell(celda("FECHA:  " + inquilino.fecha, true).setTextAlignment(TextAlignment.RIGHT));
        doc.add(tabFecha);

        doc.add(new Paragraph(" "));

        // ── FACTURA PARA ──────────────────────────────────────────────────
        Table tabPara = new Table(UnitValue.createPercentArray(new float[]{100}))
            .setWidth(UnitValue.createPercentValue(100))
            .setBorder(new SolidBorder(1));

        tabPara.addCell(celda("FACTURA PARA:", true).setBorder(Border.NO_BORDER));
        tabPara.addCell(celda(inquilino.nombre + "    CIF: " + inquilino.cif, false).setBorder(Border.NO_BORDER));
        tabPara.addCell(celda("C/ CUESTA DEL ROSARIO Nº8, CASA 1, 2ºA, 41004 Sevilla", false).setBorder(Border.NO_BORDER));
        tabPara.addCell(celda("LOCAL OBJETO DEL CONTRATO:", true).setBorder(Border.NO_BORDER));
        tabPara.addCell(celda("C/ CUESTA DEL ROSARIO Nº8, CASA 1, 2ºA    REF. CATASTRAL: 5225004TG3452C0007EH", false).setBorder(Border.NO_BORDER));
        doc.add(tabPara);

        doc.add(new Paragraph(" "));

        // ── CONCEPTOS ─────────────────────────────────────────────────────
        Table tabConceptos = new Table(UnitValue.createPercentArray(new float[]{75, 25}))
            .setWidth(UnitValue.createPercentValue(100));

        // Cabecera
        tabConceptos.addCell(celdaGris("CONCEPTOS"));
        tabConceptos.addCell(celdaGris("IMPORTE").setTextAlignment(TextAlignment.RIGHT));

        // Filas
        tabConceptos.addCell(celda("RENTA", false));
        tabConceptos.addCell(celda(euros(inquilino.renta), false).setTextAlignment(TextAlignment.RIGHT));

        tabConceptos.addCell(celda("COMUNIDAD Y VARIOS", false));
        tabConceptos.addCell(celda(euros(inquilino.comunidad), false).setTextAlignment(TextAlignment.RIGHT));

        if (inquilino.otros > 0) {
            tabConceptos.addCell(celda("OTROS CONCEPTOS", false));
            tabConceptos.addCell(celda(euros(inquilino.otros), false).setTextAlignment(TextAlignment.RIGHT));
        }



        doc.add(tabConceptos);

        doc.add(new Paragraph(" "));

        // ── BASE / IVA / RETENCIÓN / NETO ─────────────────────────────────
        Table tabCalculo = new Table(UnitValue.createPercentArray(new float[]{25, 25, 25, 25}))
            .setWidth(UnitValue.createPercentValue(100));

        tabCalculo.addCell(celdaGris("BASE IMP."));
        tabCalculo.addCell(celdaGris("IVA 21%"));
        tabCalculo.addCell(celdaGris("RETENCIÓN 19%"));
        tabCalculo.addCell(celdaGris("NETO"));

        tabCalculo.addCell(celda(euros(inquilino.getBase()), false).setTextAlignment(TextAlignment.RIGHT));
        tabCalculo.addCell(celda(euros(inquilino.getIva()), false).setTextAlignment(TextAlignment.RIGHT));
        tabCalculo.addCell(celda(euros(inquilino.getRetencion()), false).setTextAlignment(TextAlignment.RIGHT));
        tabCalculo.addCell(celda(euros(inquilino.getNeto()), false).setTextAlignment(TextAlignment.RIGHT));

        doc.add(tabCalculo);

        doc.add(new Paragraph(" "));

        // ── OBSERVACIONES / TOTAL ─────────────────────────────────────────
        Table tabTotal = new Table(UnitValue.createPercentArray(new float[]{75, 25}))
            .setWidth(UnitValue.createPercentValue(100));

        tabTotal.addCell(celdaGris("OBSERVACIONES"));
        tabTotal.addCell(celdaGris("TOTAL").setTextAlignment(TextAlignment.RIGHT));

        tabTotal.addCell(celda("Forma de pago: Transferencia", false));
        tabTotal.addCell(celda(euros(inquilino.getTotal()), false).setTextAlignment(TextAlignment.RIGHT));

        tabTotal.addCell(celda("IBAN: ES65 0182 6000 1602 1125 0061", false));
        tabTotal.addCell(celda(" ", false));

        doc.add(tabTotal);

        doc.add(new Paragraph(" "));

        // ── PIE LEGAL ─────────────────────────────────────────────────────
        String pieLegal = "Los datos personales del cliente, contenidos en el presente contrato, serán incluidos en un fichero " +
            "de datos de carácter personal cuyo responsable será MARÍA JOSÉ LÓPEZ LÓPEZ, con la finalidad de realizar la gestión " +
            "de los servicios prestados, el seguimiento y mantenimiento de nuestra relación comercial y el envío de información " +
            "acerca de nuestros servicios que puedan ajustarse a sus necesidades. Así mismo, los datos de carácter personal del " +
            "cliente necesarios para la prestación del servicio podrán ser cedidos a las Administraciones Públicas u otras entidades " +
            "de derecho público cuando la legislación correspondiente así lo prevea. El pago de esta factura se acredita mediante el " +
            "correspondiente adeudo bancario o recibí de caja. Dicho pago no prejuzga la liquidación de las anteriores facturas no " +
            "cobradas. El cliente podrá ejercer sus derechos de acceso, rectificación, cancelación y oposición, sin coste alguno. " +
            "Para ello podrá dirigirse a MARÍA JOSÉ LÓPEZ LÓPEZ, en la dirección SANTA HORTENSIA, 15, 28002 - MADRID, indicando " +
            "en la comunicación la referencia \"LOPD\".";
        doc.add(new Paragraph(pieLegal).setFontSize(7));

        doc.add(new Paragraph(" "));
        doc.add(new Paragraph("Ejemplar Arrendatario")
            .setTextAlignment(TextAlignment.CENTER).setFontSize(9));

        doc.close();
        return new File(archivo).getAbsolutePath();
    }

    // Celda normal
    private Cell celda(String texto, boolean negrita) {
        Paragraph p = new Paragraph(texto).setFontSize(9);
        if (negrita) p.setBold();
        return new Cell().add(p).setPadding(4);
    }

    // Celda con fondo gris (para cabeceras)
    private Cell celdaGris(String texto) {
        return new Cell()
            .add(new Paragraph(texto).setFontSize(9).setBold())
            .setBackgroundColor(ColorConstants.LIGHT_GRAY)
            .setPadding(4);
    }

    // Formatea un número como "850,00 €"
    private String euros(double importe) {
        return String.format("%.2f €", importe).replace(".", ",");
    }
}
