/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servlets.documentos;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ihuegal
 */
@WebServlet("/documento.pdf")
public class PdfReportServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        byte[] content = (byte[]) request.getSession().getAttribute("reportBytes");
        if (content != null) {
            request.getSession().removeAttribute("reportBytes");

            response.setContentType("application/pdf");
            response.setContentLength(content.length);
            response.getOutputStream().write(content);
        }
    }
}
