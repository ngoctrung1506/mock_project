package bu22.fga.mockproject_group2.controller;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.SparseArray;

import bu22.fga.mockproject_group2.MainActivity;
import bu22.fga.mockproject_group2.constant.Constant;
import bu22.fga.mockproject_group2.model.ListLessonModel;
import bu22.fga.mockproject_group2.model.TimeTableModel;
import bu22.fga.mockproject_group2.screen.home.state.BaseState;
import bu22.fga.mockproject_group2.screen.home.state.DragAndDropState;
import bu22.fga.mockproject_group2.screen.home.state.LoadDataState;
import bu22.fga.mockproject_group2.screen.home.state.SaveDataState;


public class MainController {

    private BaseState mCurrentState;
    private SparseArray<BaseState> mStates;
    private MyHandler myHandler;
    private Activity mView;
    private Object mModel;

    public MainController(Activity mView) {
        this.mView = mView;
        if(mView instanceof MainActivity)
        mModel = TimeTableModel.newInstance();
        else mModel = ListLessonModel.newInstance();
        myHandler = new MyHandler(this);
        initStates();
    }

    public Activity getView() {
        return mView;
    }

    public Object getModel() {
        return mModel;
    }

    public void sendMessage(Message msg) {
        myHandler.sendMessage(msg);
    }

    private void handleMessage(Message msg) {
        transitionState(msg.what);
        mCurrentState.handeMessage(msg);
    }

    private void transitionState(int state) {
        switch (state) {
            case Constant.LOAD_DATA:
                mCurrentState = mStates.get(state);
                break;
            case Constant.SAVE_DATA:
                mCurrentState = mStates.get(state);
                break;
            case Constant.DRAP_AND_DROP:
                mCurrentState = mStates.get(state);
            default:
                break;
        }
    }

    private void initStates() {
        mStates = new SparseArray<>();
        mStates.put(Constant.LOAD_DATA, new LoadDataState(this));
        mStates.put(Constant.SAVE_DATA, new SaveDataState(this));
        mStates.put(Constant.DRAP_AND_DROP, new DragAndDropState(this));
    }

    public class MyHandler extends Handler {
        private MainController mController;

        public MyHandler(MainController mController) {
            this.mController = mController;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mController.handleMessage(msg);
        }
    }
}
