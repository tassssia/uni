package parser;

import prototype.Knife;
import org.xml.sax.SAXException;
import java.io.File;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.List;

public class ParserSAX {
    public List<Knife> parseSAX(File xml) throws SAXException, IOException, ParserConfigurationException {
        if (!ValidatorOfXML.isValid("src/main/resources/knives.xml", "src/main/resources/knives.xsd")) {
            throw new SAXException("XML does not conform to the XSD");
        }

        SAXParserFactory saxFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxFactory.newSAXParser();

        KnifeHandler knifeHandler = new KnifeHandler();
        saxParser.parse(xml, knifeHandler);

        return knifeHandler.getKnives();
    }
}
