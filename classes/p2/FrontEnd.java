package p2;

import java.io.*;
import jakarta.servlet.http.HttpServletResponse;

public class FrontEnd {
    public static void sendHTMLF0(HttpServletResponse response, String resourceUrl, String clientIp)
            throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head><title>Fase 0</title></head>");
        out.println("<body>");

        out.println("<h2>Bienvenido al catálogo de películas</h2>");
        out.println("<p>Fichero procesado: <a href=\"" + resourceUrl + "\">" + resourceUrl + "</a></p>");
        out.println("<p>Tu IP es: " + clientIp + "</p>");

        out.println("<p id='navegador'></p>");
        out.println("<script>");
        out.println("document.getElementById('navegador').innerHTML = 'Navegador: ' + navigator.userAgent;");
        out.println("</script>");

        out.println("<p>Autor: Aarón</p>");
        out.println("</body></html>");
    }
}
