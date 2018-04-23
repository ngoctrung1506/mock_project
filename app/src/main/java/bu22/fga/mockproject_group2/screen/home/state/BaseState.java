package bu22.fga.mockproject_group2.screen.home.state;

import android.os.Message;

import bu22.fga.mockproject_group2.controller.MainController;
import bu22.fga.mockproject_group2.util.DatabaseHelper;
import bu22.fga.mockproject_group2.util.Ultilites;

public abstract class BaseState {

    protected MainController mController;
    protected Ultilites mUltilites;
    protected DatabaseHelper mDatabaseHelper;

    protected Object mModel;

    public BaseState(MainController mController) {
        this.mController = mController;
        mModel = mController.getModel();
        mUltilites = Ultilites.newInstance();
        mDatabaseHelper = new DatabaseHelper(mController.getView());
    }

    public abstract void handeMessage(Message msg);
}
