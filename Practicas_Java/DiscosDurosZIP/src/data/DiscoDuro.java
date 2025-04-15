/*
 * Departamento de Informática y Automática
 * Universidad de Salamanca - Programación III
 * Copyright (C) J.R. García-Bermejo Giner
 */
package data;
// VERSION_05 en Español

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

public class DiscoDuro implements Serializable {

    private String fabricante;
    private float megabytes;
    private String modelo;
    private String numeroDeSerie;
    private int tasaDeTransmision;
    private String tecnologia;
    private String tipoDeInterface;
    private int velocidadDeRotacion;
    private final String codigo;

    public static final Locale SPANISH = Locale.forLanguageTag("es-ES");
    public static final NumberFormat NF = NumberFormat.getInstance(SPANISH);
    private static final List<String> nombresDeCampos = List.of("Fabricante", 
            "Megabytes", "Modelo", "# Serie",
            "Tasa de transmisión", "Tecnología", "Interface",
            "Velocidad de Rotación", "Código");

    public DiscoDuro(String fabricante, float megabytes, String modelo,
            String numeroDeSerie, int tasaDeTransmision, String tecnologia,
            String tipoDeInterface, int velocidadDeRotacion, String codigo) {
        this.fabricante = fabricante;
        this.megabytes = megabytes;
        this.modelo = modelo;
        this.numeroDeSerie = numeroDeSerie;
        this.tasaDeTransmision = tasaDeTransmision;
        this.tecnologia = tecnologia;
        this.tipoDeInterface = tipoDeInterface;
        this.velocidadDeRotacion = velocidadDeRotacion;
        this.codigo = codigo;
    }

    public String exportarComoCadenaConDelimitador(String del) {
        String resultado;
        var localeActual = Locale.getDefault();
        resultado = String.format(localeActual,
                "%s" // fabricante
                + "%s" // delimitador
                + "%6.1f" // megabytes
                + "%s" // delimitador
                + "%s" // modelo
                + "%s" // delimitador
                + "%s" // numeroDeSerie
                + "%s" // delimitador
                + "%d" // tasaDeTransmision
                + "%s" // delimitador
                + "%s" // tecnologia
                + "%s" // delimitador
                + "%s" // tipoDeInterface
                + "%s" // delimitador
                + "%d" // velocidadDeRotacion
                + "%s" // delimitador,
                + "%s", // codigo,
                this.fabricante,
                del,
                this.megabytes,
                del,
                this.modelo,
                del,
                this.numeroDeSerie,
                del,
                this.tasaDeTransmision,
                del,
                this.tecnologia,
                del,
                this.tipoDeInterface,
                del,
                this.velocidadDeRotacion,
                del,
                this.codigo);
        return resultado;
    } // Fin de exportarComoCadenaConDelimitador

    // Ready for other applications, not used in V_4
    public String exportarComoFilaHTML() {
        String resultado;
        resultado = String.format("<tr>"
                + "<td>%s</td>" // Fabricante
                + "<td>%f</td>" // megabytes
                + "<td>%s</td>" // modelo
                + "<td>%s</td>" // Número de serie
                + "<td>%d</td>" // Tasa de transmisión
                + "<td>%s</td>" // Tecnología
                + "<td>%s</td>" // Tipo de interface
                + "<td>%d</td>" // Velocidad de rotación
                + "<td>%s</td>" // codigo
                + "</tr>",
                this.fabricante,
                this.megabytes,
                this.modelo,
                this.numeroDeSerie,
                this.tasaDeTransmision,
                this.tecnologia,
                this.tipoDeInterface,
                this.velocidadDeRotacion,
                this.codigo);
        return resultado;
    }

    /*
   * Esta factoría es útil para importar de delimitado.
     */
    public static DiscoDuro factory(String linea, String del) {
        if (null == linea || null == del || del.isEmpty()) {
            return null;
        }
        String[] f = linea.split(del);
        return factory(f);
    }

    /*
   * Esta factoría sirve para crear directamente un disco a partir
   * de una línea de tabla importada.
     */
    public static DiscoDuro factory(String[] f) {
        if (f.length != 9) {
            return null;
        }
        DiscoDuro hd;
        float frag1;
        int frag4, frag7;
        var locale = Locale.getDefault();
        NumberFormat nf = NumberFormat.getInstance(locale);

        try {
            frag1 = nf.parse(f[1].trim()).floatValue();
            frag4 = nf.parse(f[4].trim()).intValue();
            frag7 = nf.parse(f[7].trim()).intValue();
        } catch (ParseException e) {
            // explicarPorQueSeRechaza() :-) System.out.println(e.toString());
            return null;
        }
        hd = new DiscoDuro(f[0], // Fabricante
                // NF.parse(f[1]).floatValue(), // Megabytes, when locale is Spanish
                frag1, // Megabytes, when locale is US English
                f[2], // Modelo
                f[3], // Número de serie
                frag4, // Tasa de transmisión
                f[5], // Tecnología
                f[6], // Tipo de interface
                frag7, // Velocidad de rotación
                f[8] // Codigo
        );

        return hd;
    }

    public static String explicarPorQueSeRechaza(String[] f) {
        if (f.length != 9) {
            return "El número de fragmentos no es correcto";
        }
        float frag1;
        int frag4, frag7;
        var locale = Locale.getDefault();
        NumberFormat nf = NumberFormat.getInstance(locale);

        try {
            frag1 = nf.parse(f[1].trim()).floatValue();
            frag4 = nf.parse(f[4].trim()).intValue();
            frag7 = nf.parse(f[7].trim()).intValue();
        } catch (ParseException e) {
            return e.getLocalizedMessage();
        }
        return "";
    }

    //
    // A continuación se tienen los métodos de acceso, que han sido creados
    // automáticamente.
    /**
     * @return the fabricante
     */
    public String getFabricante() {
        return fabricante;
    }

    /**
     * @param fabricante the fabricante to set
     */
    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    /**
     * @return the modelo
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * @param modelo the modelo to set
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     * @return the tecnologia
     */
    public String getTecnologia() {
        return tecnologia;
    }

    /**
     * @param tecnologia the tecnologia to set
     */
    public void setTecnologia(String tecnologia) {
        this.tecnologia = tecnologia;
    }

    /**
     * @return the megabytes
     */
    public float getMegabytes() {
        return megabytes;
    }

    /**
     * @param megabytes the megabytes to set
     */
    public void setMegabytes(float megabytes) {
        this.megabytes = megabytes;
    }

    /**
     * @return the velocidadDeRotacion
     */
    public int getVelocidadDeRotacion() {
        return velocidadDeRotacion;
    }

    /**
     * @param velocidadDeRotacion the velocidadDeRotacion to set
     */
    public void setVelocidadDeRotacion(int velocidadDeRotacion) {
        this.velocidadDeRotacion = velocidadDeRotacion;
    }

    /**
     * @return the tasaDeTransmision
     */
    public int getTasaDeTransmision() {
        return tasaDeTransmision;
    }

    /**
     * @param tasaDeTransmision the tasaDeTransmision to set
     */
    public void setTasaDeTransmision(int tasaDeTransmision) {
        this.tasaDeTransmision = tasaDeTransmision;
    }

    /**
     * @return the tipoDeInterface
     */
    public String getTipoDeInterface() {
        return tipoDeInterface;
    }

    /**
     * @param tipoDeInterface the tipoDeInterface to set
     */
    public void setTipoDeInterface(String tipoDeInterface) {
        this.tipoDeInterface = tipoDeInterface;
    }

    /**
     * @return the numeroDeSerie
     */
    public String getNumeroDeSerie() {
        return this.numeroDeSerie;
    }

    /**
     * @param numeroDeSerie the numeroDeSerie to set
     */
    public void setNumeroDeSerie(String numeroDeSerie) {
        this.numeroDeSerie = numeroDeSerie;
    }

    String[] exportarComoArrayDeCadenas() {
        String[] resultado = {
            this.fabricante,
            String.format("%.1f", this.megabytes),
            this.modelo,
            this.numeroDeSerie,
            this.tasaDeTransmision + "",
            this.tecnologia,
            this.tipoDeInterface,
            this.velocidadDeRotacion + "",
            this.codigo};
        return resultado;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @return the nombresDeCampos
     */
    public static List<String> getNombresDeCampos() {
        return nombresDeCampos;
    }

} // End of class DiscoDuro
