package bu22.fga.mockproject_group2.entity;

/**
 * Created by vuson on 4/17/2018.
 */

public class DayOfWeek {

   private int id_DayOfWeek;
   private String name;

    public DayOfWeek(int id_DayOfWeek, String name) {
        this.id_DayOfWeek = id_DayOfWeek;
        this.name = name;
    }

    public DayOfWeek(String name) {

        this.name = name;
    }

    public DayOfWeek() {

    }

    public int getId_DayOfWeek() {
        return id_DayOfWeek;
    }

    public void setId_DayOfWeek(int id_DayOfWeek) {
        this.id_DayOfWeek = id_DayOfWeek;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
