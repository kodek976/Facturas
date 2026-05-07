public class Main {
    public static void main(String[] args) throws Exception {

        // 1. Leer datos por consola
        LectorConsola lector    = new LectorConsola();
        Inquilino     inquilino = lector.leerInquilino();

        // 2. Mostrar resumen
        System.out.println();
        System.out.println("=== RESUMEN ===");
        System.out.println("Base imponible : " + inquilino.getBase());
        System.out.println("IVA (21%)      : " + inquilino.getIva());
        System.out.println("Retención (19%): " + inquilino.getRetencion());
        System.out.println("Neto           : " + inquilino.getNeto());
        System.out.println("TOTAL          : " + inquilino.getTotal());
        System.out.println();

        // 3. Generar PDF
        GeneradorFactura generador = new GeneradorFactura();
        String ruta = generador.generar(inquilino);

        System.out.println("✅ Factura generada en: " + ruta);
    }

}

