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
        ArrayList<String> listaYears = DataModel.getYears();
        FrontEnd.sendHTMLF1(res, listaYears);
    }

    public void doGetFase2(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String year = req.getParameter("pyear");
        ArrayList<Cast> listaCast = DataModel.getCast(year);
        FrontEnd.sendHTMLF2(res, year, listaCast);
    }

    public void doGetFase3(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String year = req.getParameter("pyear");
        String cast = req.getParameter("pidC");
        ArrayList<Movie> moviesList = DataModel.getMovies(cast);
        FrontEnd.sendHTMLF3(res, year, cast, moviesList);
    }
}