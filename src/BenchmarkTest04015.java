/*
 * OWASP Benchmark-style Java pilot case.
 */

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

@WebServlet("/benchmark/xxe-00/BenchmarkTest04015")
public class BenchmarkTest04015 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);
        CustomerIdHandler handler = new CustomerIdHandler();

        try {
            SAXParser parser = factory.newSAXParser();

            parser.parse(new InputSource(request.getReader()), handler);

            response.setContentType("text/plain");
            response.getWriter().println("customer=" + handler.customerId());
        } catch (ParserConfigurationException | SAXException e) {
            throw new ServletException(e);
        }
    }

    private static final class CustomerIdHandler extends DefaultHandler {
        private final StringBuilder value = new StringBuilder();
        private boolean capturing;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            capturing = "customerId".equals(localName) || "customerId".equals(qName);
        }

        @Override
        public void characters(char[] chars, int start, int length) {
            if (capturing) {
                value.append(chars, start, length);
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) {
            capturing = false;
        }

        String customerId() {
            return value.toString().trim();
        }
    }
}
