package p2;

import java.io.*;
import java.util.ArrayList;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

// Clase para enviar las respuestas HTML al cliente
public class FrontEnd {
    // Fase 0: página de bienvenida
    public static void sendHTMLF0(HttpServletResponse res, String URL, String IP)
            throws IOException {
        // Establecemos el tipo de contenido y la codificación
        res.setContentType("text/html");
        res.setCharacterEncoding("utf-8");
        // Obtenemos el objeto PrintWriter para escribir la respuesta
        PrintWriter out = res.getWriter();

        out.println("<html>");
        out.println("<head><title>Fase 0</title></head>");
        out.println("<body>");

        out.println("<h1>Servicio de información sobre películas</h1>");
        out.println("<h2>Fase 0</h2>");

        out.println("<p>Fichero procesado: <a href=\"" + URL + "\">" + URL + "</a></p>");
        out.println("<p>IP: " + IP + "</p>");

        out.println("<p id='navegador'></p>");
        out.println("<script>");
        out.println("document.getElementById('navegador').innerHTML = 'Navegador: ' + navigator.userAgent;");
        out.println("</script>");

        out.println("<form method='get' action='P2M'>");
        out.println("<input type='hidden' name='pphase' value='1'>");
        out.println("<input type='submit' value='Siguiente'>");
        out.println("</form>");

        out.println("<p>© Aarón Riveiro Vilar (2024-2025)</p>");
        out.println("</body>");
        out.println("</html>");
    }

    // Fase 1: página de selección de año
    public static void sendHTMLF1(HttpServletResponse res, ArrayList<String> years) throws IOException {
        // Establecemos el tipo de contenido y la codificación
        res.setContentType("text/html");
        res.setCharacterEncoding("utf-8");
        // Obtenemos el objeto PrintWriter para escribir la respuesta
        PrintWriter out = res.getWriter();

        out.println("<html>");
        out.println("<head><title>Fase 1</title></head>");
        out.println("<body>");

        out.println("<h1>Servicio de información sobre películas</h1>");
        out.println("<h2>Fase 1</h2>");

        out.println("<p>Selecciona un año:</p>");
        out.println("<ol>");
        for (String year : years) {
            out.println("<li><a href='P2M?pphase=2&pyear=" + year + "'>" + year + "</a></li>");
        }
        out.println("</ol>");

        out.println("<form method='get' action='P2M'>");
        out.println("<input type='hidden' name='pphase' value='0'>");
        out.println("<input type='submit' value='Inicio'>");
        out.println("</form>");

        out.println("<p>© Aarón Riveiro Vilar (2024-2025)</p>");
        out.println("</body>");
        out.println("</html>");
    }

    // Fase 2: página de selección de actor/actriz
    public static void sendHTMLF2(HttpServletResponse res, String year,
            ArrayList<Cast> casts) throws IOException {
        // Establecemos el tipo de contenido y la codificación
        res.setContentType("text/html");
        res.setCharacterEncoding("utf-8");
        // Obtenemos el objeto PrintWriter para escribir la respuesta
        PrintWriter out = res.getWriter();

        out.println("<html>");
        out.println("<head><title>Fase 2</title></head>");
        out.println("<body>");

        out.println("<h1>Servicio de información sobre películas</h1>");
        out.println("<h2>Fase 2 (año = " + year + ")</h2>");

        out.println("<p>Selecciona un actor/actriz:</p>");
        out.println("<ol>");
        for (Cast cast : casts) {
            out.println("<li>Nombre = <a href='P2M?pphase=3&pyear=" + year + "&pidC=" +
                    cast.getId() + "'>"
                    + cast.getName() + "</a> --- idC=" + cast.getId() + "</li>");
        }
        out.println("</ol>");

        out.println("<form method='get' action='P2M'>");
        out.println("<input type='hidden' name='pphase' value='0'>");
        out.println("<input type='submit' value='Inicio'>");
        out.println("</form>");

        out.println("<form method='get' action='P2M'>");
        out.println("<input type='hidden' name='pphase' value='1'>");
        out.println("<input type='submit' value='Atrás'>");
        out.println("</form>");

        out.println("<p>© Aarón Riveiro Vilar (2024-2025)</p>");
        out.println("</body>");
        out.println("</html>");
    }

    // Fase 3: página de películas
    public static void sendHTMLF3(HttpServletResponse res, String year, String cast, ArrayList<Movie> movies)
            throws IOException {
        // Establecemos el tipo de contenido y la codificación
        res.setContentType("text/html");
        res.setCharacterEncoding("utf-8");
        // Obtenemos el objeto PrintWriter para escribir la respuesta
        PrintWriter out = res.getWriter();

        out.println("<html>");
        out.println("<head><title>Fase 3</title></head>");
        out.println("<body>");

        out.println("<h1>Servicio de información sobre películas</h1>");
        out.println("<h2>Fase 3 (cast = " + cast + ")</h2>");

        out.println("<p>Películas disponibles:</p>");
        out.println("<ol>");
        for (Movie movie : movies) {
            out.println("<li><strong>Título</strong> = '" + movie.getTitle() +
                    "' --- <strong>Año</strong> = " + movie.getYear() +
                    " --- <strong>idM</strong> = " + movie.getId() + "</li>");
        }
        out.println("</ol>");

        out.println("<form method='get' action='P2M'>");
        out.println("<input type='hidden' name='pphase' value='0'>");
        out.println("<input type='submit' value='Inicio'>");
        out.println("</form>");

        out.println("<form method='get' action='P2M'>");
        out.println("<input type='hidden' name='pphase' value='2'>");
        out.println("<input type='hidden' name='pyear' value='" + year + "'>");
        out.println("<input type='submit' value='Atrás'>");
        out.println("</form>");

        out.println("<p>© Aarón Riveiro Vilar (2024-2025)</p>");
        out.println("</body>");
        out.println("</html>");
    }

    // Solicitud GET /years
    public static void sendJSONYears(HttpServletResponse res, ArrayList<String> years) throws IOException {
        // Establecemos el tipo de contenido y la codificación
        res.setContentType("application/json");
        res.setCharacterEncoding("utf-8");
        // Obtenemos el objeto PrintWriter para escribir la respuesta
        PrintWriter out = res.getWriter();

        // Creamos un JSONArray para almacenar los años
        JSONArray array = new JSONArray();

        // Recorremos la lista de años
        for (String year : years) {
            // Creamos un objeto para cada año
            JSONObject obj = new JSONObject();
            obj.put("year", year);
            // Lo añadimos al array
            array.put(obj);
        }

        // Escribimos el array en la respuesta
        out.println(array);
    }

    // Solicitud GET /cast?year={year}
    public static void sendJSONCast(HttpServletResponse res, ArrayList<Cast> casts) throws IOException {
        // Comprobamos si la lista de actores/actrices está vacía
        if (casts.isEmpty()) {
            res.sendError(404, "No se encontraron actores/actrices para el año solicitado");
            return;
        }

        // Establecemos el tipo de contenido y la codificación
        res.setContentType("application/json");
        res.setCharacterEncoding("utf-8");
        // Obtenemos el objeto PrintWriter para escribir la respuesta
        PrintWriter out = res.getWriter();

        // Creamos un JSONArray para almacenar los actores/actrices
        JSONArray array = new JSONArray();

        // Recorremos la lista de actores/actrices
        for (Cast cast : casts) {
            // Creamos un objeto para cada actor/actriz
            JSONObject obj = new JSONObject();
            obj.put("name", cast.getName());
            obj.put("idC", cast.getId());
            // Lo añadimos al array
            array.put(obj);
        }

        // Escribimos el array en la respuesta
        out.println(array);
    }

    // Solicitud GET /cast/{id}/movies
    public static void sendJSONMovies(HttpServletResponse res, ArrayList<Movie> movies) throws IOException {
        // Comprobamos si la lista de películas está vacía
        if (movies.isEmpty()) {
            res.sendError(404, "No se encontraron películas para el actor/actriz solicitado");
            return;
        }

        // Establecemos el tipo de contenido y la codificación
        res.setContentType("application/json");
        res.setCharacterEncoding("utf-8");
        // Obtenemos el objeto PrintWriter para escribir la respuesta
        PrintWriter out = res.getWriter();

        // Creamos un JSONArray para almacenar las películas
        JSONArray array = new JSONArray();

        // Recorremos la lista de películas
        for (Movie movie : movies) {
            // Creamos un objeto para cada película
            JSONObject obj = new JSONObject();
            obj.put("title", movie.getTitle());
            obj.put("idM", movie.getId());
            obj.put("year", movie.getYear());
            // Lo añadimos al array
            array.put(obj);
        }

        // Escribimos el array en la respuesta
        out.println(array);
    }
}