package projetannuel.esgi4moc1.distributorcat;

/**
 * Created by inas on 08/07/2017.
 */

import java.util.Date;


public class Sensor {
    private int id;
    private String name;
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

}
