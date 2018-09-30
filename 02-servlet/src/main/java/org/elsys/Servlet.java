package org.elsys;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/Servlet")
public class Servlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private Map<String, String> data = new HashMap<>();

    public Servlet() { }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        ServletOutputStream responseOutputStream = response.getOutputStream();

        responseOutputStream.println("<html><body>");

        if (data.isEmpty()) {
            responseOutputStream.println("There is no data on the server yet. Add some below...");
        } else {
            responseOutputStream.println("Current server data:");

            responseOutputStream.println("<ul>");
            for (Map.Entry<String, String> entry : data.entrySet()) {
                String dataKey = entry.getKey();
                String dataValue = entry.getValue();

                String outputString = "<li>" + dataKey + " -> " + dataValue + "</li>";

                responseOutputStream.println(outputString);
            }
            responseOutputStream.println("</ul>");
        }

        response.getOutputStream().println(
                "<form method='POST'>" +
                    "<input type='text' name='key' placeholder='Key' />" +
                    "<input type='text' name='value' placeholder='Value' /> " +
                    "<input type='submit' value='Submit key/value pair' />" +
                "</form>");
        responseOutputStream.println("</body></html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        String key = request.getParameter("key");
        String value = request.getParameter("value");
        data.put(key, value);

        doGet(request, response);
    }

}
