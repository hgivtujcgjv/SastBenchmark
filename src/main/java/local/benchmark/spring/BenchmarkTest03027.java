package local.benchmark.spring;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

@RestController
@RequestMapping("/benchmark/xxe-00")
public class BenchmarkTest03027 {
    @PostMapping(value = "/BenchmarkTest03027", consumes = MediaType.APPLICATION_XML_VALUE)
    public String summarizeShipment(@RequestBody byte[] shipmentXml)
            throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        ShipmentHandler handler = new ShipmentHandler();
        parser.parse(new ByteArrayInputStream(shipmentXml), handler);
        return handler.summary();
    }

    private static final class ShipmentHandler extends DefaultHandler {
        private final StringBuilder collected = new StringBuilder();
        private int parcels;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            if ("parcel".equals(qName)) {
                parcels++;
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            collected.append(ch, start, length);
        }

        String summary() {
            return parcels + ":" + collected.toString().trim();
        }
    }
}
