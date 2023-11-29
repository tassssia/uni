package parser;

import prototype.Knife;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import prototype.KnifeAttributes;
import prototype.Visual;

import java.util.ArrayList;
import java.util.List;

public class KnifeHandler extends DefaultHandler {
    private List<Knife> knives = new ArrayList<>();
    private String attrValue;

    @Override
    public void startDocument() throws SAXException {
        knives = new ArrayList<>();
    }
    @Override
    public void startElement(String url, String name, String attrName, Attributes attributes) {
        switch(attrName){
            case KnifeAttributes.KNIFE:
                Knife knife = new Knife();
                knives.add(knife);
                break;
            case KnifeAttributes.VISUAL:
                Visual visual = new Visual();
                getLast().setVisual(visual);
                break;
        }
    }
    @Override
    public void endElement(String uri, String name, String attrName) {
        switch(attrName) {
            case KnifeAttributes.ID:
                getLast().setId(attrValue);
                break;
            case KnifeAttributes.TYPE:
                getLast().setType(attrValue);
                break;
            case KnifeAttributes.HANDY:
                getLast().setHandy(Integer.valueOf(attrValue));
                break;
            case KnifeAttributes.ORIGIN:
                getLast().setOrigin(attrValue);
                break;
            case KnifeAttributes.BLADE_LENGTH_CM:
                getLast().getVisual().setBladeLengthCm(Integer.valueOf(attrValue));
                break;
            case KnifeAttributes.BLADE_WIDTH_MM:
                getLast().getVisual().setBladeWidthMm(Integer.valueOf(attrValue));
                break;
            case KnifeAttributes.MATERIAL:
                getLast().getVisual().setMaterial(attrValue);
                break;
            case KnifeAttributes.HANDLE:
                getLast().getVisual().setHandle(attrValue);
                break;
            case KnifeAttributes.BLOOD_GROOVE:
                getLast().getVisual().setBloodGroove(Boolean.parseBoolean(attrValue));
                break;
            case KnifeAttributes.VALUE:
                getLast().setValue(Boolean.parseBoolean(attrValue));
                break;
        }
    }
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        attrValue = new String(ch, start, length);
    }
    public void setField(String attrName, String attrValue) {
        switch(attrName) {
            case KnifeAttributes.KNIFE:
                Knife knife = new Knife();
                knives.add(knife);
                break;
            case KnifeAttributes.ID:
                getLast().setId(attrValue);
                break;
            case KnifeAttributes.TYPE:
                getLast().setType(attrValue);
                break;
            case KnifeAttributes.HANDY:
                getLast().setHandy(Integer.valueOf(attrValue));
                break;
            case KnifeAttributes.ORIGIN:
                getLast().setOrigin(attrValue);
                break;
            case KnifeAttributes.BLADE_LENGTH_CM:
                getLast().getVisual().setBladeLengthCm(Integer.valueOf(attrValue));
                break;
            case KnifeAttributes.BLADE_WIDTH_MM:
                getLast().getVisual().setBladeWidthMm(Integer.valueOf(attrValue));
                break;
            case KnifeAttributes.MATERIAL:
                getLast().getVisual().setMaterial(attrValue);
                break;
            case KnifeAttributes.HANDLE:
                getLast().getVisual().setHandle(attrValue);
                break;
            case KnifeAttributes.BLOOD_GROOVE:
                getLast().getVisual().setBloodGroove(Boolean.parseBoolean(attrValue));
                break;
            case KnifeAttributes.VALUE:
                getLast().setValue(Boolean.parseBoolean(attrValue));
                break;
            case KnifeAttributes.VISUAL:
                Visual visual = new Visual();
                getLast().setVisual(visual);
                break;
        }
    }

    public List<Knife> getKnives() { 
        return knives;
    }
    private Knife getLast() {
        return knives.get(knives.size() - 1);
    }
    public String getName() {
        return KnifeAttributes.KNIFE;
    };
}
