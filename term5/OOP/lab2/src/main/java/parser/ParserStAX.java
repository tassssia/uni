package parser;

import prototype.Knife;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class ParserStAX {
    public List<Knife> parseStAX(File xml) throws XMLStreamException, FileNotFoundException {
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLEventReader reader;

        KnifeHandler knifeHandler = new KnifeHandler();
        reader = xmlInputFactory.createXMLEventReader(new FileInputStream(xml));

        while(reader.hasNext()) {
            XMLEvent nextXMLEvent = reader.nextEvent();

            if(nextXMLEvent.isStartElement()){
                StartElement startElement = nextXMLEvent.asStartElement();
                nextXMLEvent = reader.nextEvent();
                String name = startElement.getName().getLocalPart();

                if(nextXMLEvent.isCharacters()) {
                    List<Attribute> attrList = new ArrayList<>();
                    Iterator<Attribute> iter = startElement.getAttributes();

                    while(iter.hasNext()) {
                        attrList.add(iter.next());
                    }
                    Map<String, String> attrMap = new HashMap<>();

                    for(Attribute attribute : attrList) {
                        attrMap.put(attribute.getName().getLocalPart(), attribute.getValue());
                    }
                    knifeHandler.setField(name, nextXMLEvent.asCharacters().getData(), attrMap);
                }
            }
        }

        return knifeHandler.getKnives();
    }
}
