package tour.management.models;

public class Location {
    private Integer id;
    private String name;
    private Double Xco;
    private Double YCo;
    private String desc;
    private Double time;
    private Boolean status;
    private static int nextID = 1;

    public Location(String name, Double xco, Double YCo, String desc, Double time) {
        this.name = name;
        this.Xco = xco;
        this.YCo = YCo;
        this.desc = desc;
        this.time = time;
        this.status = true;
        this.id = nextID++;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getXco() {
        return Xco;
    }

    public void setXco(Double xco) {
        Xco = xco;
    }

    public Double getYCo() {
        return YCo;
    }

    public void setYCo(Double YCo) {
        this.YCo = YCo;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", Xco=" + Xco +
                ", YCo=" + YCo +
                ", desc='" + desc + '\'' +
                ", time=" + time +
                '}';
    }
}
