package prototype;

public class Knife {
    private String id;
    private String type;
    private Integer handy;
    private String origin;
    private Visual visual;
    private Boolean value;

    public String getId() {
        return id;
    }
    public void setId(String toSet) {
        id = toSet;
    }

    public String getType() {
        return type;
    }
    public void setType(String toSet) {
        type = toSet;
    }

    public Integer getHandy() {
        return handy;
    }
    public void setHandy(Integer toSet) {
        handy = toSet;
    }

    public String getOrigin() {
        return origin;
    }
    public void setOrigin(String toSet) {
        origin = toSet;
    }

    public Visual getVisual() {
        return visual;
    }
    public void setVisual(Visual toSet) {
        visual = toSet;
    }

    public Boolean getValue() {
        return value;
    }
    public void setValue(Boolean toSet) {
        value = toSet;
    }
}
