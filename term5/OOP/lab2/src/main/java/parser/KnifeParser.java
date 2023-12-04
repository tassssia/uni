package parser;

import org.xml.sax.SAXException;
import prototype.Knife;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface KnifeParser {
    public List<Knife> parse(File xml) throws SAXException, ParserConfigurationException, IOException, XMLStreamException;
}
