package parser;

import org.xml.sax.SAXException;
import prototype.Knife;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

public class ParserTest {
    private final String[] ID = {"1111", "2222", "3333", "4444"};
    private final String[] TYPE = {"Knife", "Saber", "Dagger", "Machete"};
    private final Integer[] HANDY = {1, 2, 1, 1};
    private final String[] ORIGIN = {"Japan", "Spain", "India", "Colombia"};
    private final Integer[] LENGTH = {15, 30, 18, 40};
    private final Integer[] WIDTH = {20, 25, 15, 50};
    private final String[] MATERIAL = {"Steel", "Steel", "Steel", "Steel"};
    private final String[] HANDLE = {"Japanese redwood", "Metal", "Multicolored plastic", "Rubber"};
    private final boolean[] GROOVE = {false, true, false, true};
    private final boolean[] VALUE = {true, false, true, false};
    private final File xmlFile = new File("src/main/resources/knives.xml");

    private void check(List<Knife> knives) {
        int num = knives.size();
        assertEquals(num, 4);

        Knife curr;
        for (int i = 0; i < num; i++) {
            curr = knives.get(i);

            assertEquals(curr.getId(), ID[i]);
            assertEquals(curr.getType(), TYPE[i]);
            assertEquals(curr.getHandy(), HANDY[i]);
            assertEquals(curr.getOrigin(), ORIGIN[i]);
            assertEquals(curr.getVisual().getBladeLengthCm(), LENGTH[i]);
            assertEquals(curr.getVisual().getBladeWidthMm(), WIDTH[i]);
            assertEquals(curr.getVisual().getMaterial(), MATERIAL[i]);
            assertEquals(curr.getVisual().getHandle(), HANDLE[i]);
            assertEquals(curr.getVisual().getBloodGroove(), GROOVE[i]);
            assertEquals(curr.getValue(), VALUE[i]);
        }
    }

    @Test
    void parseSAX() throws IOException, SAXException, ParserConfigurationException {
        ParserSAX parser = new ParserSAX();
        List<Knife> knives = parser.parseSAX(xmlFile);

        check(knives);
    }

    @Test
    void parseDOM() throws NullPointerException, SAXException {
        ParserDOM parser = new ParserDOM();
        List<Knife> knives = parser.parseDOM(xmlFile);

        check(knives);
    }

    @Test
    void parseStAX() throws NullPointerException, FileNotFoundException, XMLStreamException, SAXException {
        ParserStAX parser = new ParserStAX();
        List<Knife> knives = parser.parseStAX(xmlFile);

        check(knives);
    }

    @Test
    public void validateXML() {
        assertTrue(ValidatorOfXML.isValid("src/main/resources/knives.xml", "src/main/resources/knives.xsd"));
    }
}