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

        // Recorremos la lista de películas
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

    // Método para obtener los actores/actrices de un año
    public static ArrayList<Cast> getCast(String year) {
        // Inicializamos el árbol DOM
        init();

        // Creamos un HashMap para almacenar los actores/actrices
        HashMap<String, Cast> castsMap = new HashMap<>();
        // Obtenemos todos los elementos "movie"
        NodeList movies = doc.getElementsByTagName("movie");

        // Recorremos la lista de películas
        for (int i = 0; i < movies.getLength(); i++) {
            // Obtenemos el elemento "movie"
            Element movie = (Element) movies.item(i);
            // Obtenemos su elemento "year"
            NodeList yearNode = movie.getElementsByTagName("year");
            // Obtenemos su contenido
            String movieYear = yearNode.item(0).getTextContent();
                if (movieYear.equals(year)) {
                // Obtenemos sus elementos "cast"
                NodeList casts = movie.getElementsByTagName("cast");

                // Recorremos la lista de actores/actrices
                for (int j = 0; j < casts.getLength(); j++) {
                    // Obtenemos el elemento "cast"
                    Element cast = (Element) casts.item(j);
                    // Obtenemos su atributo "idC"
                    String idC = cast.getAttribute("idC");

                    // Comprobamos si el ID del actor/actriz ya está en el HashMap
                    if (!castsMap.containsKey(idC)) {
                        // Obtenemos su elemento "name"
                        NodeList nameNode = cast.getElementsByTagName("name");
                        // Obtenemos su contenido
                        String name = nameNode.item(0).getTextContent();
                        // Creamos un nuevo objeto Cast y lo añadimos al HashMap
                        castsMap.put(idC, new Cast(idC, name));
                        }
                    }
                }
            }

        // Convertimos el HashMap a ArrayList
        ArrayList<Cast> casts = new ArrayList<>(castsMap.values());
        // Ordenamos la lista por ID
        Collections.sort(casts, (a, b) -> a.getId().compareTo(b.getId()));
        // Devolvemos la lista
        return casts;
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
