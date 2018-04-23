package bu22.fga.mockproject_group2.entity;

import java.io.Serializable;

/**
 * Created by vuson on 4/13/2018.
 */

public class Lesson implements Serializable{

    private int id_lesson;
    private String name;


    public int getId_lesson() {
        return id_lesson;
    }

    public void setId_lesson(int id_lesson) {
        this.id_lesson = id_lesson;
    }



    public Lesson() {

    }

    public Lesson(int id, String name) {
        this.id_lesson = id;
        this.name = name;

    }

    public Lesson(String name) {
        this.name = name;

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




}
