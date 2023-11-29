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
        SAXParserFactory saxFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxFactory.newSAXParser();

        Handler handler = new Handler();
        saxParser.parse(xml, handler);

        return handler.getKnives();
    }
}
