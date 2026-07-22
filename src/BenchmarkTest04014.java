/*
 * OWASP Benchmark-style Java pilot case.
 */

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

@WebServlet("/benchmark/xxe-00/BenchmarkTest04014")
public class BenchmarkTest04014 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document invoice = builder.parse(request.getInputStream());

            response.setContentType("text/plain");
            response.getWriter().println("customer=" + firstElementText(invoice, "customerId"));
        } catch (ParserConfigurationException | SAXException e) {
            throw new ServletException(e);
        }
    }

    private static String firstElementText(Document document, String tagName) {
        NodeList matches = document.getElementsByTagName(tagName);
        return matches.getLength() == 0 ? "" : matches.item(0).getTextContent();
    }
}
