package exportacionhtml;

/**
 *
 * @author Loza
 */
public class Book {

    private String title;
    private String author;
    private String ISBN;
    private int year;
    private String imageUrl;
    private float price;

    public Book(String title, String author, String ISBN, int year, String imageUrl, float price) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.year = year;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public static String getHTMLRowHeader() {

        return "<tr>\n"
                + "    <th>Título</th>\n"
                + "    <th>Autor</th>\n"
                + "    <th>ISBN</th>\n"
                + "    <th>Año</th>\n"
                + "    <th>URL imagen</th>\n"
                + "    <th>Precio (€)</th>\n"
                + "  </tr>";
    }
    
        public String asHTMLRow() {

        //https://www.w3schools.com/html/html_tables.asp
        // Podría establecerse como text-block Java 15
        return String.format("<tr>\n"
                + "    <td>%s</td>\n"
                + "    <td>%s</td>\n"
                + "    <td>%s</td>\n"
                + "    <td>%d</td>\n"
                + "    <td>%s</td>\n"
                + "    <td>%.2f€</td>\n"
                + "  </tr>", this.title, this.author, this.ISBN, this.year, this.imageUrl, this.price);

    }

    public String asHTMLRow2() {

        //https://www.w3schools.com/html/html_tables.asp
        // Podría establecerse como text-block Java 15
        return String.format("<tr>\n" 
                + "    <td style=\"border:1px solid;\">%s</td>\n" // Inline css
                + "    <td style=\"border:1px solid;\">%s</td>\n"
                + "    <td style=\"border:1px solid;\">%s</td>\n"
                + "    <td style=\"border:1px solid;\">%d</td>\n"
                + "    <td style=\"border:1px solid;\"><a href=\"%s\">Imagen</a></td>\n" //Añadimos un link (aprovechando que es una URL) "escapad" las comillas  https://www.w3schools.com/html/html_links.asp
                + "    <td style=\"border:1px solid;\">%.2f€</td>\n"
                + "  </tr>", this.title, this.author, this.ISBN, this.year, this.imageUrl, this.price);
    }
    
    
    
    
    public String asHTMLRow3() {

        //https://www.w3schools.com/html/html_tables.asp
        // Podría establecerse como text-block Java 15
        return String.format("<tr>\n"
                + "    <td>%s</td>\n"
                + "    <td>%s</td>\n"
                + "    <td>%s</td>\n"
                + "    <td>%d</td>\n"
                + "    <td><img src=\"%s\" width=\"60\" height=\"100\"></td>\n" // podríamos incluirlo como imagen https://www.w3schools.com/tags/tag_img.asp
                                                                                 // Cuidado con las comillas hay que "escaparlas" \"
                + "    <td>%.2f€</td>\n"
                + "  </tr>", this.title, this.author, this.ISBN, this.year, this.imageUrl, this.price);
    }
    

   
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

}
