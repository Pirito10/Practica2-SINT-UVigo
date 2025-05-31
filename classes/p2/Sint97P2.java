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
            FrontEnd.sendHTMLF0(response, MML_URL, request.getRemoteAddr());
        }
    }
}