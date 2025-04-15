/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package exportacionhtml;

import com.coti.tools.Rutas;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Este proyecto busca ejemplificar como exportar a HTML de forma un tanto
 * arcaica con el objeto de trabajar el uso de métodos de Strings (Tema 14
 * Teoría) y ficheros (Tema 11)
 *
 * Disclaimer: El HTML que generaréis vuestro futuro próximo no se hará de esta
 * forma.
 *
 * Deberemos tener unos mínimos conocimientos de HTML para comprender estos
 * ejemplos por lo que se recomienda al estudiantado revisar por su cuenta la
 * sintaxis de este lenguaje de marcado
 *
 * @author Loza
 */
public class ExportacionHTML {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // Creamos unos cuantos libros en una colección
        List<Book> books = new ArrayList<>();

        // Top 3 amazon nov 2022
        books.add(new Book("Las Madres", "Carmen Mola",
                "8420456027", 2022,
                "https://m.media-amazon.com/images/I/5188iR0YB2L._SX314_BO1,204,203,200_.jpg",
                19.85f));

        books.add(new Book("Revolución", "Arturo Pérez Reverte",
                "8420461466", 2022,
                "https://m.media-amazon.com/images/I/41rdzOoc8cL._SX309_BO1,204,203,200_.jpg",
                21.75f));

        books.add(new Book("Todo Arde", "Juan Gómez Jurado",
                "8466672478", 2022,
                "https://m.media-amazon.com/images/I/4155Bpa9TNL._SX325_BO1,204,203,200_.jpg",
                21.75f));

        try {
            // Ejemplo simple en el que se exporta esta colección de libros
            // como una tabla html básica

            ejemploSimple1(books, "ejemplobasico1.html");
            ejemploSimple2(books, "ejemplobasico2.html");
            ejemploConCSS(books, "ejemploCSS.html");
            ejemploConPlantillaHTMLyCSS(books, "ejemploPlantillaHTMLyCSS.html");
            
        } catch (IOException ex) {
            Logger.getLogger(ExportacionHTML.class.getName()).log(Level.SEVERE, "Problema relacionado con ficheros", ex);
        }

    }

    /**
     * *
     *
     * Primera aproximación de como componer un documento HTML y luego volcalo a
     * fichero
     *
     * @param books
     * @throws IOException
     */
    private static void ejemploSimple1(List<Book> books, String filename) throws IOException {

        // Componer HTML que se quiere volcar a un fichero
        // Aquí quizás es útil que cada libro se pueda representar como
        // una fila de una tabla de HTML (creamos y utilizamos dicho método)
        // HTML
        String htmlDocString = "<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "<title>%%title%%</title>\n"
                + "</head>\n"
                + "<body>\n"
                + "\n"
                + "<h1>%%Header1%%</h1>\n"
                + "<p>%%description1%%</p>\n"
                + "%%TABLE%%\n"
                + "\n"
                + "</body>\n"
                + "</html>";

        // Ejemplos de string replace (reemplazamos en la string anterior como si
        // fuera una plantilla
        htmlDocString = htmlDocString.replace("%%title%%", "Libros");
        htmlDocString = htmlDocString.replace("%%Header1%%", "Mejores libros:");
        htmlDocString = htmlDocString.replace("%%description1%%", "Estos son los mejores libros de Noviembre de 2022 en Amazon");

        // Abrir tabla
        String table = "<table>";
        // Incluir cabecera de tabla
        table += Book.getHTMLRowHeader();
        // Crear filas de la tabla concatenando cadenas (muy ineficiente ya que se crean nuevas cadenas)
        for (Book book : books) {
            // Añadir fila
            table += book.asHTMLRow();
        }
        // Cerrar tabla
        table += "</table>";

        htmlDocString = htmlDocString.replace("%%TABLE%%", table);

        // Escribir string en fichero
        writeToFileOnDesktop(filename, htmlDocString);

    }

    /**
     * *
     *
     * Empleando StringBuilder
     *
     * @param books
     * @param ejemplobasico2html
     */
    private static void ejemploSimple2(List<Book> books, String filename) throws IOException {

        // Componer HTML que se quiere volcar a un fichero
        // Aquí quizás es útil que cada libro se pueda representar como
        // una fila de una tabla de HTML (creamos y utilizamos dicho método)
        // HTML
        String htmlDocString = "<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "<title>%%title%%</title>\n"
                + "</head>\n"
                + "<body>\n"
                + "\n"
                + "<h1>%%Header1%%</h1>\n"
                + "<p>%%description1%%</p>\n"
                + "%%TABLE%%\n"
                + "\n"
                + "</body>\n"
                + "</html>";

        // Ejemplos de string replace (reemplazamos en la string anterior como si
        // fuera una plantilla
        htmlDocString = htmlDocString.replace("%%title%%", "Libros");
        htmlDocString = htmlDocString.replace("%%Header1%%", "Mejores libros:");
        htmlDocString = htmlDocString.replace("%%description1%%", "Estos son los mejores libros de Noviembre de 2022 en Amazon");

        // Se creará el string correspondiente a la tabla esta vez con un StringBuilder
        StringBuilder tableStringBuilder = new StringBuilder();
        tableStringBuilder.append("<table style=\"border:1px solid;\">"); // Inline CSS con el atributo style

        // Incluir cabecera de tabla
        tableStringBuilder.append(Book.getHTMLRowHeader());
        // Crear filas de la tabla concatenando al string builder
        for (Book book : books) {
            // Añadir fila
            tableStringBuilder.append(book.asHTMLRow2());
        }
        // Cerrar tabla
        tableStringBuilder.append("</table>");

        htmlDocString = htmlDocString.replace("%%TABLE%%", tableStringBuilder);

        // Escribir string en fichero
        writeToFileOnDesktop(filename, htmlDocString);

    }

    /***
     * 
     * Empleando etiqueta CSS (internal)
     * 
     * @param books
     * @param filename
     * @throws IOException 
     */
    private static void ejemploConCSS(List<Book> books, String filename) throws IOException {

        // Componer HTML que se quiere volcar a un fichero
        // Aquí quizás es útil que cada libro se pueda representar como
        // una fila de una tabla de HTML (creamos y utilizamos dicho método)
        // HTML
        String htmlDocString = "<!DOCTYPE html>\n"
                + "<html>\n"
                + "%%INTERNAL_CSS%%" // Pondremos una etiqueta style (Internal CSS)
                + "<head>\n"
                + "<title>%%title%%</title>\n"
                + "</head>\n"
                + "<body>\n"
                + "\n"
                + "<h1>%%Header1%%</h1>\n"
                + "<p>%%description1%%</p>\n"
                + "%%TABLE%%\n"
                + "\n"
                + "</body>\n"
                + "</html>";

        // Ejemplos de string replace (reemplazamos en la string anterior como si
        // fuera una plantilla
        htmlDocString = htmlDocString.replace("%%title%%", "Libros");
        htmlDocString = htmlDocString.replace("%%Header1%%", "Mejores libros:");
        htmlDocString = htmlDocString.replace("%%description1%%", "Estos son los mejores libros de Noviembre de 2022 en Amazon");

        // Añadir Internal CSS (como etiqueta)
        // Añadimos esta etiqueta CSS como una string
        // (Parece más indicado cargarlo de fichero que tenerlo aquí ¿no?)
        String CSS = "<style>\n"
                + "table {\n"
                + "  text-align: left;\n"
                + "  position: relative;\n"
                + "  border-collapse: collapse; \n"
                + "  background-color: #f6f6f6;\n"
                + "}\n"
                + "td, th {\n"
                + "  border: 1px solid #999;\n"
                + "  padding: 20px;\n"
                + "}\n"
                + "th {\n"
                + "  background: brown;\n"
                + "  color: white;\n"
                + "  border-radius: 0;\n"
                + "  position: sticky;\n"
                + "  top: 0;\n"
                + "  padding: 10px;\n"
                + "}\n"
                + "</style>";

        htmlDocString = htmlDocString.replace("%%INTERNAL_CSS%%", CSS);

        // Se creará el string correspondiente a la tabla esta vez con un StringBuilder
        StringBuilder tableStringBuilder = new StringBuilder();
        tableStringBuilder.append("<table>"); // Inline CSS con el atributo style

        // Incluir cabecera de tabla
        tableStringBuilder.append(Book.getHTMLRowHeader());
        // Crear filas de la tabla concatenando al string builder
        for (Book book : books) {
            // Añadir fila
            tableStringBuilder.append(book.asHTMLRow3());
        }
        // Cerrar tabla
        tableStringBuilder.append("</table>");

        htmlDocString = htmlDocString.replace("%%TABLE%%", tableStringBuilder);

        // Escribir string en fichero
        writeToFileOnDesktop(filename, htmlDocString);

    }
    
    
    /***
     * 
     * Cargando de archivos template.html y myCustom.css del escritorio
     * (Leer todo el contenido de un fichero como un String)
     * 
     * @param books
     * @param filename
     * @throws IOException 
     */
    private static void ejemploConPlantillaHTMLyCSS(List<Book> books, String filename) throws IOException {

        // Leemos esta plantilla de fichero (escritorio) (Necesitamos crear este fichero en el escritorio antes!)
        // HTML
        String htmlDocString = readFromFileOnDesktop("template.html");

        // Ejemplos de string replace (reemplazamos en la string anterior como si
        // fuera una plantilla
        htmlDocString = htmlDocString.replace("%%title%%", "Libros");
        htmlDocString = htmlDocString.replace("%%Header1%%", "Mejores libros:");
        htmlDocString = htmlDocString.replace("%%description1%%", "Estos son los mejores libros de Noviembre de 2022 en Amazon");

        // Añadir Internal CSS (como etiqueta)
        // Leemos de fichero (Necesitamos crear este fichero en el escritorio antes!)
        String CSS = "<style>"+readFromFileOnDesktop("myCustom.css")+"</style>";

        htmlDocString = htmlDocString.replace("%%INTERNAL_CSS%%", CSS);

        // Se creará el string correspondiente a la tabla esta vez con un StringBuilder
        StringBuilder tableStringBuilder = new StringBuilder();
        tableStringBuilder.append("<table>"); // Inline CSS con el atributo style

        // Incluir cabecera de tabla
        tableStringBuilder.append(Book.getHTMLRowHeader());
        // Crear filas de la tabla concatenando al string builder
        for (Book book : books) {
            // Añadir fila
            tableStringBuilder.append(book.asHTMLRow3());
        }
        // Cerrar tabla
        tableStringBuilder.append("</table>");

        htmlDocString = htmlDocString.replace("%%TABLE%%", tableStringBuilder);

        // Escribir string en fichero
        writeToFileOnDesktop(filename, htmlDocString);

    }
    
    public static void writeToFileOnDesktop(String filename, String html) throws IOException {

        // Obtener referencia al fichero en el escritorio (por ejemplo)
        File f = Rutas.fileToFileOnDesktop(filename);
        // Este método de la clase Files (clase de utilidad para trabajar con ficheros)
        // Permite escribir en un fichero definido por un Path (1parametro) 
        // el string pasado (2parametro) con la codificación Charset establecido
        // y abrir ese fichero en un modo determinado por StandarOpenOption
        Files.writeString(f.toPath(), html,
                Charset.forName("UTF-8"),
                StandardOpenOption.CREATE);

        System.out.println("Exportado con exito en:" + f.getAbsolutePath());

    }
    
    public static String readFromFileOnDesktop(String filename) throws IOException{
        
        File f = Rutas.fileToFileOnDesktop(filename);
        if(f.exists()){
            String fileAsString = Files.readString(f.toPath());
            return fileAsString;
        }else{
            throw new IOException("Fichero no encontrado:"+f.getAbsoluteFile());
        }  
    }

}
