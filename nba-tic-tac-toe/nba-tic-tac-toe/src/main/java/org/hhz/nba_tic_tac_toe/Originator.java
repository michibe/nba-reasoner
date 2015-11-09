package org.hhz.nba_tic_tac_toe;

/**
 * Created by mbehr on 14.10.2015.
 */
public class Originator {

    private final String name;
    private final String color;


    private Originator() {
        this.name =null;
        this.color = null;
    }

    public Originator(String name, String color) {
        this.name = name;
        this.color = color;
    }



    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Originator{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                '}';
    }

    public String getColor() {
        return color;
    }
}
