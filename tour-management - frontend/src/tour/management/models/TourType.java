package tour.management.models;

public class TourType {
    private Integer id;
    private String name;
    private static int nextID = 1;

    public TourType(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "TourType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
