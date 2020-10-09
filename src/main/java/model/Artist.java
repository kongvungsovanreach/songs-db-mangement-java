package model;

public class Artist {
    private int id;
    private String name;
    private String type;
    private String nationality;

    public Artist() {
    }

    public Artist(int id, String name, String type, String nationality) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.nationality = nationality;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", nationality='" + nationality + '\'' +
                '}';
    }
}
