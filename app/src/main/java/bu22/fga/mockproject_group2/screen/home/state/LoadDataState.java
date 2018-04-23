package bu22.fga.mockproject_group2.screen.home.state;

import android.os.Message;

import bu22.fga.mockproject_group2.MainActivity;
import bu22.fga.mockproject_group2.controller.MainController;
import bu22.fga.mockproject_group2.entity.Lesson;

public class LoadDataState extends BaseState {

    public static final int LOAD_TIME_TABLE = 1;
    public static final int LIST_LESSON_NAME = 2;

    public LoadDataState(MainController mController) {
        super(mController);
    }

    @Override
    public void handeMessage(Message msg) {

        fakeData();

//        mController.getView().getmModel().setListLessonName(mUltilites.initListLessName());
//        mController.getView().getmModel().setFinishedLoadData(true);
//        mModel.notifyObservers();

    }

    private void fakeData() {
        mDatabaseHelper.deleteAllLesson();
        mDatabaseHelper.createLesson(new Lesson("Hoa"));
        mDatabaseHelper.createLesson(new Lesson("Ly"));
        mDatabaseHelper.createLesson(new Lesson("Sinh"));
        mDatabaseHelper.createLesson(new Lesson("Su"));
        mDatabaseHelper.createLesson(new Lesson("Dia"));
        mDatabaseHelper.createLesson(new Lesson("Toan"));
        ((MainActivity)mController.getView()).getmModel().setDataToLoad(mUltilites.initTimeTableData(), mDatabaseHelper.getAllLessons(), true);


    }
}
