package local.benchmark.spring;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/benchmark/xxe-00")
public class BenchmarkTest03030 {
    @PostMapping(value = "/BenchmarkTest03030", consumes = MediaType.APPLICATION_XML_VALUE)
    public List<String> listFeedTitles(InputStream feedBody) throws XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        factory.setProperty(XMLInputFactory.SUPPORT_DTD, Boolean.FALSE);
        factory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, Boolean.FALSE);
        XMLStreamReader reader = factory.createXMLStreamReader(feedBody);
        List<String> titles = new ArrayList<>();
        while (reader.hasNext()) {
            if (reader.next() == XMLStreamConstants.START_ELEMENT && "title".equals(reader.getLocalName())) {
                titles.add(reader.getElementText());
            }
        }
        reader.close();
        return titles;
    }
}
