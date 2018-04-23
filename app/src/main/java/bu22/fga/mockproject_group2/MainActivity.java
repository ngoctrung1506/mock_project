package bu22.fga.mockproject_group2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import bu22.fga.mockproject_group2.constant.Constant;
import bu22.fga.mockproject_group2.controller.MainController;
import bu22.fga.mockproject_group2.entity.DayWithRegistedLesson;
import bu22.fga.mockproject_group2.entity.Lesson;
import bu22.fga.mockproject_group2.model.TimeTableModel;
import bu22.fga.mockproject_group2.screen.editlesson.EditLessonActivity;
import bu22.fga.mockproject_group2.screen.home.adapter.ListLessonAdapter;
import bu22.fga.mockproject_group2.screen.home.adapter.ListLessonAdapter.OnSendLessonNameBackToMainScreen;
import bu22.fga.mockproject_group2.screen.home.adapter.TimeTableAdapter;
import bu22.fga.mockproject_group2.util.DatabaseHelper;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements OnSendLessonNameBackToMainScreen {

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
    private DatabaseHelper mDatabase = new DatabaseHelper(this);
    private Lesson mLesson;
    private ArrayList<Lesson> mListLessons = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initMVC();
        initView();
        addListener();
    //    showLesson();


    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null && intent.getExtras() != null) {
            ArrayList<Lesson> lessons = (ArrayList<Lesson>) intent.getExtras().getSerializable("listLesson");
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


            mListLessons.clear();
            mListLessons.addAll(mModel.getListLessonName());
            mListLessonAdapter.notifyDataSetChanged();
        }
    }

    private void initView() {
        mTimeTableAdapter = new TimeTableAdapter(mTimeTableDatasource, mController);
        mGrvTimeTable.setAdapter(mTimeTableAdapter);


        mListLessonAdapter = new ListLessonAdapter(mListLessons, mController, this);
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
        mBtnAddLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addLesson();
            }
        });
        mBtnCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                removeChange();
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

    @Override
    public void onSendLessonName(String lessonName) {
        if (lessonName != null) {
            Intent intent = new Intent(this, EditLessonActivity.class);
            intent.putExtra(Constant.KEY_LESSON_NAME, lessonName);
            MainActivity.this.startActivity(intent);
        }
    }

    private void addLesson() {
        final
        AlertDialog.Builder alertDialogBuilder =
                new AlertDialog.Builder(this);
        View mView = this.getLayoutInflater()
                .inflate(R.layout.custom_dialog_add_lesson, null);
        final EditText editTextAddLesson = mView.findViewById(R.id.edit_addLessonName);
        Button btnAdd = mView.findViewById(R.id.btnAdd);
        Button btnCancel = mView.findViewById(R.id.btnCancel);

        alertDialogBuilder.setView(mView);
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextAddLesson.getText().toString().length() > 10) {
                    Toast.makeText(MainActivity.this, "Lesson only 10 character",
                            Toast.LENGTH_SHORT).show();
                } else if (editTextAddLesson.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "Lesson cannot be empty",
                            Toast.LENGTH_SHORT).show();
                } else {

                    mLesson = new Lesson(editTextAddLesson.getText().toString());
                    mDatabase.addLesson(mLesson);
                    mListLessons.add(mLesson);
                    mListLessonAdapter.notifyDataSetChanged();
//                  mListLessonAdapter.setListData(mDatabase.getAllLessons());
//                  mListLessonAdapter.notifyDataSetChanged();

                    Log.e("Add Lesson", "" + mDatabase.getAllLessons());
                    for (int i = 0; i < mDatabase.getAllLessons().size(); i++) {
                        Log.e("Add Lesson " + i, "" + mDatabase.getAllLessons().get(i).getName());
                    }
                    alertDialog.dismiss();

                }

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    /////git

    private void removeChange() {

        mTimeTableDatasource.clear();
        mTimeTableDatasource.addAll(mModel.getTimeTable());
        mGrvTimeTable.setAdapter(mTimeTableAdapter);
        mTimeTableAdapter.notifyDataSetChanged();
        Log.e("Cancel","Cancellll");

    }

    private void showLesson() {
        mLessons.clear();
        ArrayList<Lesson> listLessons = new ArrayList<>();
        mLessons = mDatabase.getAllLessons();
            for (int i = 0; i < mLessons.size(); i++) {
                listLessons.add(mLessons.get(i));
            }

            for (int i = listLessons.size(); i < 15; i++) {
                listLessons.add(new Lesson(""));
            }
        mListLessonAdapter.setListData(listLessons);

        mGrvListLesson.setAdapter(mListLessonAdapter);

    }


}
