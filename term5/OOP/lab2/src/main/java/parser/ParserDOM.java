package parser;

import org.xml.sax.SAXException;
import prototype.Knife;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParserDOM {
    public List<Knife> parseDOM(File xml) throws SAXException {
        if (!ValidatorOfXML.isValid("src/main/resources/knives.xml", "src/main/resources/knives.xsd")) {
            throw new SAXException("XML does not conform to the XSD");
        }

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xml);
            document.getDocumentElement().normalize();
            Element rootNode = document.getDocumentElement();

            KnifeHandler knifeHandler = new KnifeHandler();
            NodeList knives = rootNode.getElementsByTagName(knifeHandler.getName());

            for (int i = 0; i < knives.getLength(); i++) {
                Element deviceElement = (Element) knives.item(i);
                readTreeNodes(deviceElement, knifeHandler);
            }

            return knifeHandler.getKnives();
        } catch (Exception e) {
            e.getStackTrace();
        }
        return null;
    }

    private void readTreeNodes(Node node, KnifeHandler knifeHandler) {
        if(node.getNodeType() == Node.ELEMENT_NODE) {
            Map<String, String> attrMap = new HashMap<>();
            if (node.getAttributes() != null) {
                for (int i = 0; i < node.getAttributes().getLength(); i++) {
                    attrMap.put(node.getAttributes().item(i).getNodeName(), node.getAttributes().item(i).getTextContent());
                }
            }
            knifeHandler.setField(node.getNodeName(), node.getTextContent(), attrMap);
            if(node.getChildNodes() != null){
                for(int i = 0; i < node.getChildNodes().getLength(); i++) {
                    readTreeNodes(node.getChildNodes().item(i), knifeHandler);
                }
            }
        }
    }
}
