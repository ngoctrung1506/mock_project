package bu22.fga.mockproject_group2.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import bu22.fga.mockproject_group2.R;
import bu22.fga.mockproject_group2.constant.Constant;
import bu22.fga.mockproject_group2.controller.MainController;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Admin on 22/04/2018.
 */

public class ConfirmDialog extends Dialog {

  @BindView(R.id.dialog_confirm_btn_period)
  Button mBtnPeriod;

  @BindView(R.id.dialog_confirm_view_from_to)
  LinearLayout mViewFromTo;

  @BindView(R.id.dialog_confirm_txt_from)
  TextView mTxtFrom;

  @BindView(R.id.dialog_confirm_txt_to)
  TextView mTxtTo;

  @BindView(R.id.dialog_confirm_btn_ok)
  Button mBtnOk;

  @BindView(R.id.dialog_confirm_btn_cancel)
  Button mBtnCancel;

  private Context mContext;
  private Calendar mCalendar;
  private MainController mMainController;
  private int mCurentDrop;

  public ConfirmDialog(@NonNull Context context, MainController mController, int curentDrop) {
    super(context);
    this.mContext = context;
    this.mMainController = mController;
    this.mCurentDrop = curentDrop;
    initUI();
  }

  private void initUI() {
    setContentView(R.layout.dialog_confirm);
    getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    ButterKnife.bind(this);
  }

  @OnClick(R.id.dialog_confirm_btn_period)
  public void onClickPeriod() {
    mViewFromTo.setVisibility(View.VISIBLE);
    Toast.makeText(mContext, "choose period", Toast.LENGTH_SHORT).show();

  }

  @OnClick(R.id.dialog_confirm_btn_ok)
  public void onClickOk() {
    Toast.makeText(mContext, "success", Toast.LENGTH_SHORT).show();
    if (mViewFromTo.isShown()) mViewFromTo.setVisibility(View.GONE);
    sendMessage();
    dismiss();
  }

  private void sendMessage() {
    Message msg = new Message();
    msg.what = Constant.DRAP_AND_DROP;
    msg.obj = Constant.EVENT_DROP;
    msg.arg1 = Constant.EDIT_LESSON;
    msg.arg2 = mCurentDrop;
    mMainController.sendMessage(msg);
  }

  @OnClick(R.id.dialog_confirm_btn_cancel)
  public void onClickCancel() {
    dismiss();
  }

  @OnClick(R.id.dialog_confirm_txt_from)
  public void onChooseFrom() {
    int year = 2017;
    int month = 7;
    int dayOfMonth = 5;
    mCalendar = Calendar.getInstance();
    onShowDatePickerDialog(year, month, dayOfMonth);
  }

  @OnClick(R.id.dialog_confirm_txt_to)
  public void onChooseTo() {
    // Todo take real data from table
    int year = 2017;
    int month = 7;
    int dayOfMonth = 5;
    mCalendar = Calendar.getInstance();
    onShowDatePickerDialog(year, month, dayOfMonth);
  }

  private void onShowDatePickerDialog(int year, int month, int dayOfMonth) {
    new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
      @Override
      public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mCalendar.set(year, month, dayOfMonth);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        mTxtFrom.setText(simpleDateFormat.format(mCalendar.getTime()));
      }
    }, year, month, dayOfMonth).show();
  }

    public interface OnSendDataBackToMain {
        void onSendBack(Date startDate, Date endDate);
    }

}
