package p2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class DataModel {
    private static final String URL = "https://luis.sabucedo.webs.uvigo.es/24-25/p2/mml.xml";
    private static Document doc = null;

    private static void init() {
        if (doc != null)
            return;

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(URL);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize DataModel: " + e.getMessage());
        }

    }

    public static ArrayList<String> getYears() {
        init();

        HashSet<String> setYears = new HashSet<>();
        NodeList movies = doc.getElementsByTagName("movie");

        for (int i = 0; i < movies.getLength(); i++) {
            Element movie = (Element) movies.item(i);
            NodeList yearNodes = movie.getElementsByTagName("year");

            if (yearNodes.getLength() > 0) {
                String year = yearNodes.item(0).getTextContent().trim();
                if (!year.isEmpty()) {
                    setYears.add(year);
                }
            }
        }

        ArrayList<String> listaYears = new ArrayList<>(setYears);
        Collections.sort(listaYears, Collections.reverseOrder());
        return listaYears;
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
