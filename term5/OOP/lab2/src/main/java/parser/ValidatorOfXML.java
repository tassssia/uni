package parser;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

public class ValidatorOfXML {
    public static boolean isValid(String xml, String xsd) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(xsd));

            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xml));

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
