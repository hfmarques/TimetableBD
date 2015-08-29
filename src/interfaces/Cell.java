package interfaces;

public class Cell {
    public String name = "";
    @Override
    public String toString() {
        return this.name;
    }
     
    public Cell(String name) {
        this.name = name;
    }
}
