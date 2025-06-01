package p2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.TreeSet;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

// Clase para gestionar el modelo de datos
public class DataModel {
    // URL del fichero XML con los datos
    public static final String URL = "https://luis.sabucedo.webs.uvigo.es/24-25/p2/mml.xml";

    // Creamos un árbol DOM
    private static Document doc = null;

    // Método para inicializar el árbol DOM
    private static void init() {
        // Si ya está inicializado, no hacemos nada
        if (doc != null)
            return;

        try {
            // Cargamos y parseamos el archivo XML, y lo convertimos al árbol DOM
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para obtener los años disponibles
    public static ArrayList<String> getYears() {
        // Inicializamos el árbol DOM
        init();

        // Creamos un TreeSet para almacenar los años ordenados y sin duplicados
        TreeSet<String> years = new TreeSet<>(Collections.reverseOrder());
        // Obtenemos todos los elementos "movie"
        NodeList movies = doc.getElementsByTagName("movie");

        // Recorremos la lista
        for (int i = 0; i < movies.getLength(); i++) {
            // Obtenemos el elemento "movie"
            Element movie = (Element) movies.item(i);
            // Obtenemos su elemento "year"
            NodeList yearNode = movie.getElementsByTagName("year");
            // Obtenemos su contenido
            String year = yearNode.item(0).getTextContent();
            // Añadimos el año al TreeSet
            years.add(year);
        }

        // Convertimos el TreeSet a ArrayList y lo devolvemos
        return new ArrayList<>(years);
    }

    public static ArrayList<Cast> getCast(String year) {
        init();

        HashMap<String, Cast> mapaCasts = new HashMap<>();
        NodeList movies = doc.getElementsByTagName("movie");

        for (int i = 0; i < movies.getLength(); i++) {
            Element movie = (Element) movies.item(i);
            NodeList yearNodes = movie.getElementsByTagName("year");

            if (yearNodes.getLength() > 0) {
                String movieYear = yearNodes.item(0).getTextContent().trim();
                if (movieYear.equals(year)) {
                NodeList casts = movie.getElementsByTagName("cast");

                for (int j = 0; j < casts.getLength(); j++) {
                    Element cast = (Element) casts.item(j);
                    String idC = cast.getAttribute("idC");

                    if (!mapaCasts.containsKey(idC)) {
                            String name = cast.getElementsByTagName("name").item(0).getTextContent().trim();
                            String role = cast.getElementsByTagName("role").item(0).getTextContent().trim();

                        mapaCasts.put(idC, new Cast(idC, name, role));
                        }
                    }
                }
            }
        }

        ArrayList<Cast> listaCast = new ArrayList<>(mapaCasts.values());
        Collections.sort(listaCast, (a, b) -> a.getId().compareTo(b.getId()));
        return listaCast;
    }

    public static ArrayList<Movie> getMovies(String idC) {
        init();

        ArrayList<Movie> moviesList = new ArrayList<>();

        NodeList movies = doc.getElementsByTagName("movie");

        for (int i = 0; i < movies.getLength(); i++) {
            Element movie = (Element) movies.item(i);
            NodeList casts = movie.getElementsByTagName("cast");

            for (int j = 0; j < casts.getLength(); j++) {
                Element cast = (Element) casts.item(j);
                if (cast.getAttribute("idC").equals(idC)) {
                    String idM = movie.getAttribute("idM");
                    String title = movie.getElementsByTagName("title").item(0).getTextContent().trim();
                    String year = movie.getElementsByTagName("year").item(0).getTextContent().trim();

                    moviesList.add(new Movie(idM, title, year));
                    break; // no hace falta revisar más cast de esta movie
                }
            }
        }

        // Ordenar por idM
        Collections.sort(moviesList, (a, b) -> a.getId().compareTo(b.getId()));

        return moviesList;
    }
}
