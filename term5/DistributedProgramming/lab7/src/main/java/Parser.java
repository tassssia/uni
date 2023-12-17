import data.*;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXParseException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

public class Parser {
    private static DocumentBuilder db = null;
    private static String filePath = "src/main/resources/data.xml";

    public Software readFromFile() throws SAXException {
        Software software = new Software();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = sf.newSchema(new File("src/main/resources/data.xsd"));

        dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(false);
        dbf.setSchema(schema);
        try {
            db = dbf.newDocumentBuilder();
            db.setErrorHandler(new SimpleErrorHandler());
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document doc = null;
        try {
            doc = db.parse(new File(filePath));
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }

        Element root = doc.getDocumentElement();
        if (root.getTagName().equals("Software")) {
            NodeList developersList = root.getElementsByTagName("Developer");
            for (int i = 0; i < developersList.getLength(); i++) {
                Element developerElement = (Element) developersList.item(i);

                Developer developer = new Developer(Integer.parseInt(developerElement.getAttribute("id")),
                        developerElement.getAttribute("name"),
                        developerElement.getAttribute("founder"),
                        Integer.parseInt(developerElement.getAttribute("year")));
                software.addDeveloper(developer);

                NodeList softwareProductList = developerElement.getElementsByTagName("SoftwareProduct");
                for (int j = 0; j < softwareProductList.getLength(); j++) {
                    Element productElement = (Element) softwareProductList.item(j);

                    SoftwareProduct product = new SoftwareProduct(
                            Integer.parseInt(productElement.getAttribute("id")),
                            productElement.getAttribute("name"),
                            Integer.parseInt(productElement.getAttribute("cost")),
                            developer
                    );

                    software.addProduct(product);
                }
            }
        }
        return software;
    }

    public static class SimpleErrorHandler implements ErrorHandler {

        public void warning(SAXParseException e) throws SAXException {
            System.out.println("Row" + e.getLineNumber() + ":");
            System.out.println(e.getMessage());
        }

        public void error(SAXParseException e) throws SAXException {
            System.out.println("Row" + e.getLineNumber() + ":");
            System.out.println(e.getMessage());
        }

        public void fatalError(SAXParseException e) throws SAXException {
            System.out.println("Row" + e.getLineNumber() + ":");
            System.out.println(e.getMessage());
        }

    }

    public static void saveToFile(Software Software) throws TransformerException {
        Document doc = db.newDocument();
        Element root = doc.createElement("Software");
        doc.appendChild(root);

        for (Developer developer : Software.getDevelopers()) {
            Element productDeveloper = doc.createElement("Developer");
            productDeveloper.setAttribute("id", String.valueOf(developer.getId()));
            productDeveloper.setAttribute("name", developer.getName());
            root.appendChild(productDeveloper);

            for (SoftwareProduct product : Software.getProducts()) {
                if(Objects.equals(product.getDeveloper().getId(), developer.getId())) {
                    Element productElement = doc.createElement("SoftwareProduct");
                    productElement.setAttribute("id", String.valueOf(product.getId()));
                    productElement.setAttribute("name", product.getName());
                    productElement.setAttribute("cost", String.valueOf(product.getCost()));
                    productDeveloper.appendChild(productElement);
                }
            }
        }
        Source domSource = new DOMSource(doc);
        Result fileResult = new StreamResult(new File(filePath));
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "Windows-1251");
        transformer.transform(domSource, fileResult);
    }

}
