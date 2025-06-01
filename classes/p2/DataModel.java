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

    public static ArrayList<String> getLangs() {
        init();

        HashSet<String> setLangs = new HashSet<>();
        NodeList movies = doc.getElementsByTagName("movie");

        for (int i = 0; i < movies.getLength(); i++) {
            Element movie = (Element) movies.item(i);
            String attrLangs = movie.getAttribute("langs");
            if (attrLangs != null && !attrLangs.isEmpty()) {
                String[] langs = attrLangs.trim().split("\\s+");
                for (String lang : langs) {
                    setLangs.add(lang);
                }
            }
        }

        ArrayList<String> listaLangs = new ArrayList<>(setLangs);
        Collections.sort(listaLangs);
        return listaLangs;
    }

    public static ArrayList<Cast> getCast(String lang) {
        init();

        HashMap<String, Cast> mapaCasts = new HashMap<>();
        NodeList movies = doc.getElementsByTagName("movie");

        for (int i = 0; i < movies.getLength(); i++) {
            Element movie = (Element) movies.item(i);
            String attrLangs = movie.getAttribute("langs");
            if (attrLangs != null && attrLangs.matches(".*\\b" + lang + "\\b.*")) {
                NodeList casts = movie.getElementsByTagName("cast");

                for (int j = 0; j < casts.getLength(); j++) {
                    Element cast = (Element) casts.item(j);
                    String idC = cast.getAttribute("idC");

                    if (!mapaCasts.containsKey(idC)) {
                        String name = cast.getElementsByTagName("name").item(0).getTextContent();
                        String role = cast.getElementsByTagName("role").item(0).getTextContent();

                        mapaCasts.put(idC, new Cast(idC, name, role));
                    }
                }
            }
        }

        ArrayList<Cast> listaCast = new ArrayList<>(mapaCasts.values());

        Collections.sort(listaCast, (a, b) -> a.getId().compareTo(b.getId()));

        return listaCast;
    }
}
