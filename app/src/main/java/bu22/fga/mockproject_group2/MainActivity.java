package bu22.fga.mockproject_group2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import bu22.fga.mockproject_group2.constant.Constant;
import bu22.fga.mockproject_group2.controller.MainController;
import bu22.fga.mockproject_group2.dialog.ConfirmDialog;
import bu22.fga.mockproject_group2.entity.DayWithRegistedLesson;
import bu22.fga.mockproject_group2.entity.Lesson;
import bu22.fga.mockproject_group2.model.TimeTableModel;
import bu22.fga.mockproject_group2.screen.editlesson.EditLessonActivity;
import bu22.fga.mockproject_group2.screen.home.adapter.ListLessonAdapter;
import bu22.fga.mockproject_group2.screen.home.adapter.ListLessonAdapter.OnSendLessonNameBackToMainScreen;
import bu22.fga.mockproject_group2.screen.home.adapter.TimeTableAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements OnSendLessonNameBackToMainScreen, ConfirmDialog.OnSendDataBackToMain {

    @BindView(R.id.main_grv_time_table)
    GridView mGrvTimeTable;

    @BindView(R.id.main_btn_previous)
    ImageView mBtnPrevious;

    @BindView(R.id.main_btn_next)
    ImageView mBtnNext;

    @BindView(R.id.main_txt_time_period)
    TextView mTxtTimePeriod;

    @BindView(R.id.main_grv_list_lesson)
    GridView mGrvListLesson;

    @BindView(R.id.main_btn_edit_lesson)
    Button mBtnEditLesson;

    @BindView(R.id.main_btn_add_lesson)
    Button mBtnAddLesson;

    @BindView(R.id.main_btn_ok)
    Button mBtnOk;

    @BindView(R.id.main_btn_cancel)
    Button mBtnCancel;

    @BindView(R.id.main_fr_dim_view)
    FrameLayout mFrDimView;

    @BindView(R.id.main_img_recycle_bin)
    ImageView mImgRecycleBin;

    private boolean mIsEditting = false;
    private ArrayList<Lesson> mLessons = new ArrayList<>();
    private ArrayList<DayWithRegistedLesson> mTimeTableDatasource = new ArrayList<>();
    private TimeTableAdapter mTimeTableAdapter;
    private ListLessonAdapter mListLessonAdapter;
    private TimeTableModel mModel;
    private MainController mController;

    private Calendar mStartDay;
    private Calendar mEndDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initMVC();
        initView();
        addListener();
        fakeCalendar();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null && intent.getExtras() != null) {
            List<Lesson> lessons = (List<Lesson>) intent.getExtras().getSerializable("listLesson");
            if (lessons != null) {
                mListLessonAdapter.setListLesson(lessons);
                Log.e("in", "getDataFromEditScreen: ");
            }
        }
    }

    public TimeTableModel getmModel() {
        return mModel;
    }

    private void initMVC() {
        mModel = TimeTableModel.newInstance();
        mController = new MainController(this);
        mModel.setPropertyChangeSupportListenner(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent event) {
                onUpdateModel(event);
            }
        });
    }

    private void onUpdateModel(PropertyChangeEvent event) {
        Log.d("MainActivity", "onUpdateModel ");
        switch (event.getPropertyName()) {
            case TimeTableModel.EVENT_LOAD_DATA:
                handleLoadData();
                break;
            default:
                break;
        }
    }

    private void handleLoadData() {
        if (mModel.isFinishedLoadData()) {
            mTimeTableDatasource.clear();
            mTimeTableDatasource.addAll(mModel.getTimeTable());
            mTimeTableAdapter.notifyDataSetChanged();
            mLessons.clear();
            mLessons.addAll(mModel.getListLessonName());
            mListLessonAdapter.notifyDataSetChanged();
        }
    }

    private void initView() {
        mTimeTableAdapter = new TimeTableAdapter(mTimeTableDatasource, mController, this);
        mGrvTimeTable.setAdapter(mTimeTableAdapter);
        mListLessonAdapter = new ListLessonAdapter(mLessons, mController, this);
        mListLessonAdapter.setEditable(false);
        mGrvListLesson.setAdapter(mListLessonAdapter);
        Message msg = new Message();
        msg.what = Constant.LOAD_DATA;
        mController.sendMessage(msg);
    }

    private void addListener() {
        mBtnEditLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIsEditting = !mIsEditting;
                if (mIsEditting) {
                    dimView();
                } else {
                    reActiveView();
                }

            }
        });

        mGrvTimeTable.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    Log.d("======", "======");
                }
                return false;
            }
        });

        mImgRecycleBin.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {
                switch (dragEvent.getAction()) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        mImgRecycleBin.animate().scaleX(1.2f).scaleY(1.2f);
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        mImgRecycleBin.animate().scaleX(1).scaleY(1);
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        mImgRecycleBin.animate().scaleX(1).scaleY(1);
                        break;
                    case DragEvent.ACTION_DROP:
                        onRecycleBinDrop();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    private void onRecycleBinDrop() {
        Message msg = new Message();
        msg.what = Constant.DRAP_AND_DROP;
        msg.obj = Constant.EVENT_DROP;
        msg.arg1 = Constant.DELETE_LESSON;
        msg.sendingUid = Constant.LIST_LESSON;
        mController.sendMessage(msg);
    }

    private void dimView() {
        mListLessonAdapter.setEditable(true);
        mFrDimView.setVisibility(View.VISIBLE);
        mBtnEditLesson.setText(getResources().getText(R.string.cancel_edit));
        mBtnOk.setBackgroundColor(ContextCompat.getColor(this, R.color.colorDim));
        mBtnCancel.setBackgroundColor(ContextCompat.getColor(this, R.color.colorDim));
        mGrvTimeTable.setEnabled(false);
        mBtnOk.setEnabled(false);
        mBtnCancel.setEnabled(false);
        disableView(mGrvTimeTable, false);
    }

    private void disableView(GridView mGrTimeTable, boolean enableView) {
        for (int i = 0; i < mGrTimeTable.getChildCount(); i++) {
            mGrTimeTable.getChildAt(i).setEnabled(enableView);
        }
    }

    private void reActiveView() {
        mFrDimView.setVisibility(View.GONE);
        mListLessonAdapter.setEditable(false);
        mBtnOk.setBackgroundColor(ContextCompat.getColor(this, R.color.colorHeaderCell));
        mBtnCancel.setBackgroundColor(ContextCompat.getColor(this, R.color.colorHeaderCell));
        mBtnEditLesson.setText(getResources().getText(R.string.edit_lesson_name));
        disableView(mGrvTimeTable, true);
        mGrvTimeTable.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    Log.d("======", "======");
                }
                return false;
            }
        });
        mBtnOk.setEnabled(true);
        mBtnCancel.setEnabled(true);
    }

    private void startEditActivity(String lessonName) {

    }

    private void fakeCalendar() {
        mStartDay = Calendar.getInstance();
        mStartDay.set(2018, 3, 23);

        mEndDay = Calendar.getInstance();
        mEndDay.set(2018, 3, 27);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        mTxtTimePeriod.setText(simpleDateFormat.format(mStartDay.getTime()) + " - " + simpleDateFormat.format(mEndDay.getTime()));
    }

    @Override
    public void onSendLessonName(String lessonName) {
        if (lessonName != null) {
            Intent intent = new Intent(this, EditLessonActivity.class);
            intent.putExtra(Constant.KEY_LESSON_NAME, lessonName);
            MainActivity.this.startActivity(intent);
        }
    }

    @Override
    public void onSendBack(Date startDate, Date endDate) {
        if (startDate != null && endDate != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Toast.makeText(this, simpleDateFormat.format(startDate), Toast.LENGTH_SHORT).show();
            mStartDay.setTime(startDate);
            mEndDay.setTime(endDate);

            int range = mStartDay.getTime().getDay() - mStartDay.getFirstDayOfWeek();
            Toast.makeText(this, "range: " + range, Toast.LENGTH_SHORT).show();
        }
    }
}
