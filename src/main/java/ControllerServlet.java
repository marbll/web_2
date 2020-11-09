import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerServlet extends HttpServlet {
    public ControllerServlet() {
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long requestStartTime = System.nanoTime();
        request.setAttribute("time", requestStartTime);
        super.service(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        String errorMessage = "";
        if (request.getParameter("x0") == null) {
            errorMessage = errorMessage + "Ошибка! Данные о координате X не получены (Передайте x0 - обязательно, x1, x2 и т.д. по желанию)\n";
        }

        if (request.getParameter("y") == null) {
            errorMessage = errorMessage + "Ошибка! Данные о координате Y не получены)\n";
        }

        if (request.getParameter("r") == null) {
            errorMessage = errorMessage + "Ошибка! Данные о координате R не получены\n";
        }

        if (errorMessage.equals("")) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("area");
            dispatcher.forward(request, response);
        } else {
            response.getWriter().println(errorMessage);
        }

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if (request.getParameter("key").equals("show")) {
                PrintWriter out = response.getWriter();
                AreaCheckServlet.printTable(out);
            }
        } catch (NullPointerException var5) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
            dispatcher.forward(request, response);
        }

    }

    public void doHead(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("area");
        dispatcher.forward(request, response);
    }
}
