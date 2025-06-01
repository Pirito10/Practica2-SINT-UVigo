package p2;

// Clase que representa un actor/actriz
public class Cast {
    private String id;
    private String name;

    // Constructor
    public Cast(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
