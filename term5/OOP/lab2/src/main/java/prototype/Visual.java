package prototype;

public class Visual {
    private Integer bladeLengthCm;
    private Integer bladeWidthMm;
    private String material;
    private String handle;
    private Boolean bloodGroove;

    @Override
    public String toString() {
        return "Visual Characteristics {" +
                "blade: " + bladeLengthCm + "cm x " + bladeWidthMm + "mm, " + material + "; " +
                "handle: " + handle + "; " +
                (bloodGroove ? "has" : "doesn't have") + " a blood groove" +
                "} ";
    }

    public Integer getBladeLengthCm() {
        return bladeLengthCm;
    }
    public void setBladeLengthCm(Integer toSet) {
        bladeLengthCm = toSet;
    }

    public Integer getBladeWidthMm() {
        return bladeWidthMm;
    }
    public void setBladeWidthMm(Integer toSet) {
        bladeWidthMm = toSet;
    }

    public String getMaterial() {
        return material;
    }
    public void setMaterial(String toSet) {
        material = toSet;
    }

    public String getHandle() {
        return handle;
    }
    public void setHandle(String toSet) {
        handle = toSet;
    }

    public Boolean getBloodGroove() {
        return bloodGroove;
    }
    public void setBloodGroove(Boolean toSet) {
        bloodGroove = toSet;
    }
}
