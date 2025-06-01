package p2;

import java.io.*;
import java.util.ArrayList;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class Sint97P2 extends HttpServlet {
    private static final String URL = "https://luis.sabucedo.webs.uvigo.es/24-25/p2/mml.xml";

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String fase = req.getParameter("pphase");

        if (fase == null) {
            fase = "0";
        }

        switch (fase) {
            case "0":
                this.doGetFase0(req, res);
                break;
            case "1":
                this.doGetFase1(req, res);
                break;
            case "2":
                this.doGetFase2(req, res);
                break;
            case "3":
                this.doGetFase3(req, res);
                break;
            default:
                this.doGetFase0(req, res);
                break;
        }
    }

    public void doGetFase0(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String IP = req.getRemoteAddr();
        FrontEnd.sendHTMLF0(res, URL, IP);
    }

    public void doGetFase1(HttpServletRequest req, HttpServletResponse res) throws IOException {
        ArrayList<String> listaLangs = DataModel.getLangs();
        FrontEnd.sendHTMLF1(res, listaLangs);
    }

    public void doGetFase2(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String lang = req.getParameter("plang");
        ArrayList<Cast> listaCast = DataModel.getCast(lang);
        FrontEnd.sendHTMLF2(res, lang, listaCast);
    }

    public void doGetFase3(HttpServletRequest req, HttpServletResponse res) throws IOException {
        FrontEnd.sendHTMLF3(res);
    }
}