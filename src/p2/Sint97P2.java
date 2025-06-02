package p2;

import java.io.*;
import java.util.ArrayList;
import jakarta.servlet.http.*;

// Clase principal del servlet
public class Sint97P2 extends HttpServlet {
    // Método que se invoca al recibir una solicitud al servlet
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        // Obtenemos la URI de la solicitud
        String uri = req.getRequestURI();

        // Comprobamos si es una solicitud a la API
        if (uri.contains("v1")) {
            // Obtenemos la ruta de la solicitud
            String path = req.getPathInfo();

            // LLamamos al método correspondiente según la ruta
            if (path.equals("/years")) {
                doGetRESTYears(req, res);
            } else if (path.equals("/cast")) {
                doGetRESTCast(req, res, path);
            } else {
                doGetRESTMovies(req, res, path);
            }

            // Finalizamos la solicitud
            return;
        }

        // Obtenemos el parámetro de la fase
        String fase = req.getParameter("pphase");

        // Si no existe, lo inicializamos a fase 0
        if (fase == null) {
            fase = "0";
        }

        // Llamamos al método correspondiente según la fase
        switch (fase) {
            case "0":
                doGetFase0(req, res);
                break;
            case "1":
                doGetFase1(req, res);
                break;
            case "2":
                doGetFase2(req, res);
                break;
            case "3":
                doGetFase3(req, res);
                break;
        }
    }

    // Fase 0: página de bienvenida
    public void doGetFase0(HttpServletRequest req, HttpServletResponse res) throws IOException {
        // Obtenemos la IP del cliente
        String IP = req.getRemoteAddr();
        // Obtenemos la URL del fichero XML
        String URL = DataModel.URL;
        // Generamos y enviamos el HTML correspondiente
        FrontEnd.sendHTMLF0(res, URL, IP);
    }

    // Fase 1: página de selección de año
    public void doGetFase1(HttpServletRequest req, HttpServletResponse res) throws IOException {
        // Obtenemos la lista de años disponibles
        ArrayList<String> years = DataModel.getYears();
        // Generamos y enviamos el HTML correspondiente
        FrontEnd.sendHTMLF1(res, years);
    }

    // Fase 2: página de selección de actor/actriz
    public void doGetFase2(HttpServletRequest req, HttpServletResponse res) throws IOException {
        // Obtenemos el parámetro del año seleccionado
        String year = req.getParameter("pyear");
        // Obtenemos la lista de actores/actrices para ese año
        ArrayList<Cast> casts = DataModel.getCast(year);
        // Generamos y enviamos el HTML correspondiente
        FrontEnd.sendHTMLF2(res, year, casts);
    }

    // Fase 3: página de películas
    public void doGetFase3(HttpServletRequest req, HttpServletResponse res) throws IOException {
        // Obtenemos los parámetros del año y del actor/actriz seleccionado
        String year = req.getParameter("pyear");
        String cast = req.getParameter("pidC");
        // Obtenemos la lista de películas para el actor/actriz seleccionado
        ArrayList<Movie> movies = DataModel.getMovies(cast);
        // Generamos y enviamos el HTML correspondiente
        FrontEnd.sendHTMLF3(res, year, cast, movies);
    }

    // Solicitud GET /years
    public void doGetRESTYears(HttpServletRequest req, HttpServletResponse res) throws IOException {
        // Obtenemos la lista de años disponibles
        ArrayList<String> years = DataModel.getYears();
        // Generamos y enviamos el JSON correspondiente
        FrontEnd.sendJSONYears(res, years);
    }

    // Solicitud GET /cast?year={year}
    public void doGetRESTCast(HttpServletRequest req, HttpServletResponse res, String path) throws IOException {
        // Obtenemos el parámetro del año seleccionado
        String year = req.getParameter("year");
        // Obtenemos la lista de actores/actrices para ese año
        ArrayList<Cast> casts = DataModel.getCast(year);
        // Generamos y enviamos el JSON correspondiente
        FrontEnd.sendJSONCast(res, casts);
    }

    // Solicitud GET /cast/{id}/movies
    public void doGetRESTMovies(HttpServletRequest req, HttpServletResponse res, String path) throws IOException {
        // Obtenemos el parámetro del actor/actriz seleccionado
        String[] partes = path.split("/");
        String cast = partes[2];
        // Obtenemos la lista de películas para el actor/actriz seleccionado
        ArrayList<Movie> movies = DataModel.getMovies(cast);
        // Generamos y enviamos el JSON correspondiente
        FrontEnd.sendJSONMovies(res, movies);
    }
}