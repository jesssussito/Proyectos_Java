/*
 * Departamento de Informática y Automática
 * Universidad de Salamanca - Programación III
 * Copyright (C) J.R. García-Bermejo Giner
 */
package data;

import com.coti.tools.OpMat;
import static com.coti.tools.OpMat.importFromDisk;
import com.coti.tools.Rutas;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
//
// Branch: VERSION_05 en Español
//

/**
 *
 * @author coti
 */
public class Model {

    private List<DiscoDuro> lista;
    private final Map<String, DiscoDuro> mapa;
    private final String NOMBRE_ARCHIVO_DELIMITADO = "archivodelimitado.txt";
    private final String NOMBRE_ARCHIVO_BINARIO = "archivobinario.bin";
    private final String NOMBRE_CARPETA_DATOS = "DISK_FOLDER";
    private final String NOMBRE_ARCHIVO_RECHAZADOS = "archivo_rechazados.txt";

    private final File fileDatosDelimitados = Rutas.fileToFileInFolderOnDesktop(this.NOMBRE_CARPETA_DATOS, this.NOMBRE_ARCHIVO_DELIMITADO);
    private final File fileDatosBinarios = Rutas.fileToFileInFolderOnDesktop(this.NOMBRE_CARPETA_DATOS, this.NOMBRE_ARCHIVO_BINARIO);

    public Model() {
        this.lista = new ArrayList<>();
        mapa = new HashMap<>();
    }

    public String[][] listaComoTablaDeCadenas() {
        return lista.stream().map(hd -> hd.exportarComoArrayDeCadenas()).toArray(String[][]::new);
    }

    public String exportarListAarchivoDelimitado(File fileDeDestino, String del) {
        List<String> listaDeLineas = new ArrayList<>();
        String mensajeDeError = "";
        for (DiscoDuro hd : lista) {
            listaDeLineas.add(hd.exportarComoCadenaConDelimitador(del));
        }
        try {
            Files.write(fileDeDestino.toPath(), listaDeLineas, Charset.forName("UTF8"));
        } catch (IOException ex) {
            mensajeDeError = "%n%nERROR: no fue posible exportar%n%n" + ex.getLocalizedMessage();
        }
        return mensajeDeError;
    } // Fin de exportarListAarchivoDelimitado

    public String importarListaDeArchivoDelimitado(File fileDelimitado, String del) {
        String[][] tabla;
        String mensajeDeError = "";
        try {
            tabla = importFromDisk(fileDelimitado, del);
        } catch (Exception ex) {
            mensajeDeError = "%n%nERROR: no fue posible importar el archivo%n%n" + ex.getLocalizedMessage();
            return mensajeDeError;
        }
        List<DiscoDuro> resultado = new ArrayList<>();
        DiscoDuro hd;
        for (String[] fila : tabla) {
            hd = DiscoDuro.factory(fila);
            if (null != hd) {
                resultado.add(hd);
            } else {
                this.agregarArechazados(fila);
            }
        } // for que añade a lista los registros viables importados
        lista.clear();
        // Actualizar la lista
        lista.addAll(resultado);
        // Actualizar el mapa
        this.lista.forEach(h -> this.mapa.put(h.getCodigo(), h));

        return mensajeDeError;
    } // Fin de importarListaDeArchivoDelimitado()

    public List<DiscoDuro> getLista() {
        return lista;
    }

    public void ordenarPor(String opcion) {
        Comparator<DiscoDuro> c;
        c = switch (opcion) {
            case "1" ->
                Comparator.comparing(DiscoDuro::getFabricante);
            case "2" ->
                Comparator.comparing(DiscoDuro::getMegabytes);
            case "3" ->
                Comparator.comparing(DiscoDuro::getModelo);
            case "4" ->
                Comparator.comparing(DiscoDuro::getNumeroDeSerie);
            case "5" ->
                Comparator.comparing(DiscoDuro::getTasaDeTransmision);
            case "6" ->
                Comparator.comparing(DiscoDuro::getTecnologia);
            case "7" ->
                Comparator.comparing(DiscoDuro::getTipoDeInterface);
            case "8" ->
                Comparator.comparing(DiscoDuro::getVelocidadDeRotacion);
            case "9" ->
                Comparator.comparing(DiscoDuro::getCodigo);
            case "0" ->
                Comparator.comparing(DiscoDuro::getFabricante)
                .thenComparing(DiscoDuro::getModelo)
                .thenComparing(DiscoDuro::getMegabytes);
            default ->
                Comparator.comparing(DiscoDuro::getFabricante);
        }; // End of switch
        lista.sort(c);

    } // Fin de ordenarPor()

    public String guardar(File fp) {
        ObjectOutputStream oos;
        FileOutputStream fos;
        BufferedOutputStream bos;
        String mensajeDeError = "";
        try {
            fos = new FileOutputStream(fp);
            bos = new BufferedOutputStream(fos);
            oos = new ObjectOutputStream(bos);
            oos.writeObject(lista);
            oos.close();
        } catch (Exception ex) {
            mensajeDeError = "%n%nERROR: no fue posible guardar%n%n" + ex.getLocalizedMessage();
        }
        return mensajeDeError;
    } // End of method guardar()

    public String leer(File bf) {
        List<DiscoDuro> listaTemporal;
        FileInputStream fis;
        BufferedInputStream bis;
        ObjectInputStream ois;
        String mensajeDeError = "";
        try {
            fis = new FileInputStream(bf);
            bis = new BufferedInputStream(fis);
            ois = new ObjectInputStream(bis);
            listaTemporal = (ArrayList<DiscoDuro>) ois.readObject();
            // Actualizar la lista
            this.lista = listaTemporal;
            // Actualizar el mapa
            this.lista.forEach(h -> this.mapa.put(h.getCodigo(), h));
        } catch (Exception ex) {
            mensajeDeError = "%n%nERROR: no fue posible leer%n%n" + ex.getLocalizedMessage();
        }
        return mensajeDeError;
    } // End of method leer()

    /**
     * @return the fileDatosDelimitados
     */
    public File getFileDatosDelimitados() {
        return fileDatosDelimitados;
    }

    /**
     * @return the fileDatosBinarios
     */
    public File getFileDatosBinarios() {
        return fileDatosBinarios;
    }

    public boolean hemosAgregadoDiscoApartirDe(List<String> datosProporcionadosPorElUsuario) {
        DiscoDuro discoNuevo = DiscoDuro.factory(datosProporcionadosPorElUsuario.toArray(String[]::new));
        if (null != discoNuevo) {
            // Actualizarla lista
            this.lista.add(discoNuevo);
            // Actualizar el mapa
            mapa.put(discoNuevo.getCodigo(), discoNuevo);
            return true;
        } else {
            return false;
        }
    }

    public List<String> listaDeCadenasDelDiscoConCodigo(String codigo) {
        // Búsqueda rapida!
        DiscoDuro disco = this.mapa.get(codigo);
        // Paso de array a lista
        return List.of(disco.exportarComoArrayDeCadenas());
    }

    public boolean hemosEliminadoElDiscoDeCuyoCodigoEs(String posibleCodigo) {
        var discoEliminado = mapa.remove(posibleCodigo);
        var seHaEliminadoDelMapa = (null != discoEliminado);
        if (seHaEliminadoDelMapa) {
            // Actualizar la lista
            this.lista.remove(discoEliminado);
        }
        return seHaEliminadoDelMapa;
    }

    private void agregarArechazados(String[] registro) {
        var ruta = Rutas.fileToFileInFolderOnDesktop(this.NOMBRE_CARPETA_DATOS, this.NOMBRE_ARCHIVO_RECHAZADOS);
        String[] causa = {DiscoDuro.explicarPorQueSeRechaza(registro)};
        var registroYcausa = String.join("\t", OpMat.join(registro, causa));
        try {
            // Se abre el FileWrite en modo de adición (true)
            FileWriter pw = new FileWriter(ruta, true);
            pw.append(registroYcausa + "\n");
            pw.close();
        } catch (Exception ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<String> getNombresDeCampos() {
        return DiscoDuro.getNombresDeCampos();
    }

    public String porQueSeDescarta(List<String> datos) {
        return DiscoDuro.explicarPorQueSeRechaza(datos.toArray(String[]::new));
    }
} // Fin de Model
