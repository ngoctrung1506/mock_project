package bu22.fga.mockproject_group2.util;

import java.util.ArrayList;

import bu22.fga.mockproject_group2.entity.DayWithRegistedLesson;
import bu22.fga.mockproject_group2.entity.Lesson;

public class Ultilites {

    private static Ultilites mUltilites=null;

    public ArrayList<Lesson> initListLessName(){
        ArrayList<Lesson> lessons = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            lessons.add(new Lesson("mon " + (i + 1)));
        }
        return lessons;
    }

    public ArrayList<DayWithRegistedLesson> initTimeTableData(){
        ArrayList<DayWithRegistedLesson> ttDatasource = new ArrayList<>();
        for (int i = 0; i < 49; i++) {
            ttDatasource.add(new DayWithRegistedLesson(new Lesson()));
        }
        return ttDatasource;
    }
    public static Ultilites newInstance() {

        if (mUltilites == null) {
            mUltilites = new Ultilites();
        }
        return mUltilites;
    }
}
