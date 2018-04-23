package bu22.fga.mockproject_group2.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import bu22.fga.mockproject_group2.entity.Lesson;

/**
 * Created by Admin on 22/04/2018.
 */

public class ListLessonModel {

    public static final String TAG = ListLessonModel.class.getName();

    public static final String EVENT_SAVE_DATA = "EVENT_SAVE_DATA";

    private PropertyChangeSupport mPropertyChangeSupport;

    private ArrayList<Lesson> mListLesson;

    private static ListLessonModel mModel = null;

    private TimeTableModel mTimeTableModel = TimeTableModel.newInstance();

    public static ListLessonModel newInstance() {
        if (mModel == null) {
            mModel = new ListLessonModel();
        }
        return mModel;
    }

    public ListLessonModel() {
        this.mPropertyChangeSupport = new PropertyChangeSupport(this);
    }

    public void setPropertyChangeSupportListenner(PropertyChangeListener listenner) {
        mPropertyChangeSupport.addPropertyChangeListener(listenner);
    }

    public void setResultListData(ArrayList<Lesson> listLesson) {
        this.mListLesson = listLesson;
        this.mTimeTableModel.setmListLessonName(listLesson);
        mPropertyChangeSupport.firePropertyChange(EVENT_SAVE_DATA, null, null);
    }

    public ArrayList<Lesson> getmListLesson() {
        return mListLesson;
    }
}
