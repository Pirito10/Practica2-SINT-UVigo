package p2;

import java.io.*;
import java.util.ArrayList;

import jakarta.servlet.http.HttpServletResponse;

public class FrontEnd {
    public static void sendHTMLF0(HttpServletResponse res, String URL, String IP)
            throws IOException {
        res.setContentType("text/html");
        res.setCharacterEncoding("utf-8");
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

        out.println("<p>Aarón Riveiro Vilar (2024-2025)</p>");
        out.println("</body></html>");
    }

    public static void sendHTMLF1(HttpServletResponse res, ArrayList<String> listaLangs) throws IOException {
        res.setContentType("text/html");
        res.setCharacterEncoding("utf-8");
        PrintWriter out = res.getWriter();

        out.println("<html>");
        out.println("<head><title>Fase 1</title></head>");
        out.println("<body>");

        out.println("<h1>Servicio de información sobre películas</h1>");
        out.println("<h2>Fase 1</h2>");
        out.println("<p>Selecciona un idioma:</p>");
        out.println("<ol>");

        for (String lang : listaLangs) {
            out.println("<li><a href='P2M?pphase=2&plang=" + lang + "'>" + lang + "</a></li>");
        }

        out.println("</ol>");

        out.println("<form method='get' action='P2M'>");
        out.println("<input type='hidden' name='pphase' value='0'>");
        out.println("<input type='submit' value='Inicio'>");
        out.println("</form>");

        out.println("<p>Aarón Riveiro Vilar (2024-2025)</p>");
        out.println("</body></html>");
    }

    public static void sendHTMLF2(HttpServletResponse res) throws IOException {

    }

    public static void sendHTMLF3(HttpServletResponse res) throws IOException {

    }
}