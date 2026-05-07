public class Inquilino {
    String nombre;
    String cif;
    String fecha;
    double renta;
    double comunidad;
    double otros;

    public Inquilino(String nombre, String cif, String fecha,
                     double renta, double comunidad, double otros) {
        this.nombre    = nombre;
        this.cif       = cif;
        this.fecha     = fecha;
        this.renta     = renta;
        this.comunidad = comunidad;
        this.otros     = otros;
    }

    public double getBase() {
        return renta + comunidad + otros;
    }

    public double getIva() {
        return getBase() * 0.21;
    }

    public double getRetencion() {
        return getBase() * 0.19;
    }

    public double getNeto() {
        return getBase() - getRetencion();
    }

    public double getTotal() {
        return getBase() + getIva() - getRetencion();
    }
}
