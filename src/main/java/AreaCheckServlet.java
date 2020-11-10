import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AreaCheckServlet extends HttpServlet {
    private static final LinkedList<Point> points = new LinkedList();

    public AreaCheckServlet() {
    }

    public static void clearPoints() {
        points.clear();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorMessage = "";
        String messageType = "Ошибка! ";
        float y = 66.0F;

        try {
            y = Float.parseFloat(request.getParameter("y"));
            if (y >= 5.0F || y <= -3.0F) {
                errorMessage = errorMessage + messageType + "Y координата должна быть (-3 ; 5)\n";
            }
            if (request.getParameter("y").length() > 1 && request.getParameter("y").split("\\.")[1].length() > 7) {
                errorMessage = errorMessage + messageType + "Нельзя вводить более 7 цифр в дробной части числа";
            }
        } catch (NumberFormatException e) {
            errorMessage = errorMessage + messageType + "Y координата должна быть числом\n";
        }

        float r = 66.0F;

        try {
            r = Float.parseFloat(request.getParameter("r"));
            if (r < 1.0F || r > 3.0F) {
                errorMessage = errorMessage + messageType + "Радиус должен быть [1 ; 3]\n";
            }
            if (request.getParameter("r").length() > 1 && request.getParameter("r").split("\\.")[1].length() > 7) {
                errorMessage = errorMessage + messageType + "Нельзя вводить более 7 цифр в дробной части числа";
            }
        } catch (NumberFormatException e) {
            errorMessage = errorMessage + messageType + "Радиус должен быть числом\n";
        }

        String xrError = errorMessage;
        String xNum = "x0";
        float nextX = 66.0F;
        String localErrorMessage = "";

        for(int i = 0; i < 9; ++i) {

            localErrorMessage = "";

            try {
                xNum = "x" + i;
                nextX = Float.parseFloat(request.getParameter(xNum));
                if (nextX > 5.0F || nextX < -3.0F) {
                    localErrorMessage = localErrorMessage + messageType + "X координата должна быть [-3 ; 5]\n";
                }

                if (request.getParameter("r").length() > 1 && request.getParameter(xNum).split("\\.")[1].length() > 7) {
                    errorMessage = errorMessage + messageType + "Нельзя вводить более 7 цифр в дробной части числа";
                }

            } catch (NumberFormatException e) {
                localErrorMessage = localErrorMessage + messageType + "X координата должна быть числом\n";
            } catch (NullPointerException e) {
                break;
            }

            if (localErrorMessage.equals("") && xrError.equals("")) {
                Point point = new Point(nextX, y, r);
                point.setRes(this.hitsFigure(nextX, y, r));
                point.setExecutionTime((System.nanoTime() - Long.parseLong(request.getAttribute("time").toString())) / 1000L);
                points.add(point);
                messageType = "Предупреждение (неверные данные на дополнительных X координатах)... ";
            } else {
                errorMessage = errorMessage + localErrorMessage;
            }
        }

        if (!errorMessage.equals("")) {
            response.getWriter().println(errorMessage);
        } else {
            printTable(response.getWriter());
        }

    }

    public void doHead(HttpServletRequest request, HttpServletResponse response) throws IOException {
        clearPoints();
    }

    private boolean hitsFigure(float x, float y, float r) {
        return x * x + y * y <= r / 2.0F * r / 2.0F && x <= 0.0F && y <= 0.0F || y > x - r && x >= 0.0F && y <= 0.0F || x <= 0.0F && y >= 0.0F && x >= -r && y <= r;
    }

    static void printTable(PrintWriter out) {
        Iterator iter = points.iterator();

        while(iter.hasNext()) {
            Point point = (Point)iter.next();
            out.println("<tr>");
            out.println("<td>" + point.getX() + "</td>");
            out.println("<td>" + point.getY() + "</td>");
            out.println("<td>" + point.getR() + "</td>");
            out.println("<td>" + point.getRes() + "</td>");
            out.println("<td>" + point.getRequestTime() + "</td>");
            out.println("<td>" + point.getExecutionTime() + "</td>");
            out.println("</tr>");
        }

    }
}
