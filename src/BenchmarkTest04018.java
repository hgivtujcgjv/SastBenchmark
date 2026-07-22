/*
 * OWASP Benchmark-style Java pilot case.
 */

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

@WebServlet("/benchmark/xxe-00/BenchmarkTest04018")
public class BenchmarkTest04018 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        factory.setProperty(XMLInputFactory.SUPPORT_DTD, Boolean.FALSE);
        factory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, Boolean.FALSE);

        try {
            XMLStreamReader reader = factory.createXMLStreamReader(request.getInputStream(), "UTF-8");

            response.setContentType("text/plain");
            response.getWriter().println("customer=" + readCustomerId(reader));
        } catch (XMLStreamException e) {
            throw new ServletException(e);
        }
    }

    private static String readCustomerId(XMLStreamReader reader) throws XMLStreamException {
        try {
            String value = "";
            boolean capturing = false;
            while (reader.hasNext()) {
                int event = reader.next();
                if (event == XMLStreamConstants.START_ELEMENT) {
                    capturing = "customerId".equals(reader.getLocalName());
                } else if (event == XMLStreamConstants.CHARACTERS && capturing) {
                    value = reader.getText().trim();
                    capturing = false;
                }
            }
            return value;
        } finally {
            reader.close();
        }
    }
}
