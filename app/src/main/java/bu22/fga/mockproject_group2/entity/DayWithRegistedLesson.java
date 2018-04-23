package bu22.fga.mockproject_group2.entity;

/**
 * Created by vuson on 4/17/2018.
 */

public class DayWithRegistedLesson {

    private int id_DayWithRegistedLesson;
    private DayOfWeek dayOfWeek;
    private Lesson lesson;
    private int position;

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public DayWithRegistedLesson(int id_DayWithRegistedLesson, DayOfWeek dayOfWeek, Lesson lesson, int position) {
        this.id_DayWithRegistedLesson = id_DayWithRegistedLesson;
        this.dayOfWeek = dayOfWeek;
        this.lesson = lesson;
        this.position = position;
    }

    public DayWithRegistedLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public DayWithRegistedLesson(DayOfWeek dayOfWeek, Lesson lesson, int position) {

        this.dayOfWeek = dayOfWeek;
        this.lesson = lesson;
        this.position = position;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public DayWithRegistedLesson() {
    }

    public int getId_DayWithRegistedLesson() {
        return id_DayWithRegistedLesson;
    }

    public void setId_DayWithRegistedLesson(int id_DayWithRegistedLesson) {
        this.id_DayWithRegistedLesson = id_DayWithRegistedLesson;
    }


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


}
