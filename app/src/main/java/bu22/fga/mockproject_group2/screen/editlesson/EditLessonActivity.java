package bu22.fga.mockproject_group2.screen.editlesson;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.List;

import bu22.fga.mockproject_group2.MainActivity;
import bu22.fga.mockproject_group2.R;
import bu22.fga.mockproject_group2.constant.Constant;
import bu22.fga.mockproject_group2.controller.MainController;
import bu22.fga.mockproject_group2.entity.Lesson;
import bu22.fga.mockproject_group2.model.ListLessonModel;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditLessonActivity extends AppCompatActivity {

  @BindView(R.id.edit_lesson_txt_old_name)
  TextView mTxtOldName;

  @BindView(R.id.edit_lesson_edt_new_name)
  EditText mEdtNewName;

  @BindView(R.id.edit_lesson_btn_ok)
  Button mBtnOk;

  @BindView(R.id.edit_lesson_btn_cancel)
  Button mBtnCancel;

  private ListLessonModel mListLessonModel;
  private MainController mMainController;
  private String mOldLessonName;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_lesson);
    ButterKnife.bind(this);
    handleDataFromMain();
    initModelAndController();
  }

  private void initModelAndController() {
    mMainController = new MainController(this);
    mListLessonModel = ListLessonModel.newInstance();
    mListLessonModel.setPropertyChangeSupportListenner(new PropertyChangeListener() {
      @Override
      public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
          case ListLessonModel.EVENT_SAVE_DATA:
            onUpdateModel();
            break;
            default:
              break;
        }
      }
    });
  }

  private void onUpdateModel() {
    List<Lesson> lessonList = mListLessonModel.getmListLesson();
    Intent intent = new Intent(this, MainActivity.class);
    Bundle bundle = new Bundle();
    bundle.putSerializable("listLesson", (Serializable) lessonList);
    intent.putExtras(bundle);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    startActivity(intent);

  }

  // Todo get real data
  private void handleDataFromMain() {
    mOldLessonName = getIntent().getStringExtra(Constant.KEY_LESSON_NAME);
    if (mOldLessonName != null) {
      mTxtOldName.setText(mOldLessonName);
    }
  }

  @OnClick(R.id.edit_lesson_btn_cancel)
  void onClickCancel() {
    Intent intent = new Intent(this, MainActivity.class);
    startActivity(intent);
  }

  @OnClick(R.id.edit_lesson_btn_ok)
  void onClickOk() {
    String newName = mEdtNewName.getText().toString().trim();
    if (newName != null && !newName.equals("")) {
      Message msg = new Message();
      msg.what = Constant.SAVE_DATA;
      msg.arg1 = Constant.LIST_LESSON;
      msg.obj = mOldLessonName + " " + newName;
      mMainController.sendMessage(msg);

    }
  }

}
