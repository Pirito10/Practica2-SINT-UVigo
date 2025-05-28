package p2;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class Sint97P2 extends HttpServlet {
    private static final String MML_URL = "https://luis.sabucedo.webs.uvigo.es/24-25/p2/mml.xml";

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pphase = request.getParameter("pphase");

        if (pphase == null || pphase.equals("0")) {
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();

            out.println("<html>");
            out.println("<head><title>Fase 0</title></head>");
            out.println("<body>");

            out.println("<h2>Bienvenido al catálogo de películas</h2>");
            out.println("<p>Fichero procesado: <a href=\"" + MML_URL + "\">" + MML_URL + "</a></p>");
            out.println("<p>Tu IP es: " + request.getRemoteAddr() + "</p>");

            out.println("<p id='navegador'></p>");
            out.println("<script>");
            out.println("document.getElementById('navegador').innerHTML = 'Navegador: ' + navigator.userAgent;");
            out.println("</script>");

            out.println("<p>Autor: Aarón</p>");
            out.println("</body></html>");
        }
    }
}