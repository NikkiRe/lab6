package models;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private static final long serialVersionUID = 1L;

    public Coordinates(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Coordinates(String data) {
        try {
            try {
                this.x = Integer.parseInt(data.split(";")[0]);
            } catch (NumberFormatException e) {
            }
            try {
                this.y = Integer.parseInt(data.split(";")[1]);
            } catch (NumberFormatException e) {

            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }

    }

    private int x; //Максимальное значение поля: 232

    public int getX() {
        return x;
    }

    private int y; //Максимальное значение поля: 809

    public int getY() {
        return y;
    }
}
