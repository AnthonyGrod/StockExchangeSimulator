package Agenci;

public abstract class Agent {

    private final int id;

    public Agent(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
