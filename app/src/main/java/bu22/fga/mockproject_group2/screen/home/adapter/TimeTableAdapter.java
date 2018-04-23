package bu22.fga.mockproject_group2.screen.home.adapter;

import android.content.ClipData;
import android.content.Context;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import bu22.fga.mockproject_group2.R;
import bu22.fga.mockproject_group2.constant.Constant;
import bu22.fga.mockproject_group2.controller.MainController;
import bu22.fga.mockproject_group2.entity.DayWithRegistedLesson;
import bu22.fga.mockproject_group2.entity.Lesson;
import bu22.fga.mockproject_group2.screen.home.event.DragDropListenter;

public class TimeTableAdapter extends BaseAdapter {

    private ArrayList<DayWithRegistedLesson> mDatasource;
    public static final int MAX_COLUMN = 7;
    public static final String COLUMN_HEADER_PREFIX = "Day";
    public static final String ROW_HEADER_PREFIX = "Lesson";
    private MainController mController;

    public TimeTableAdapter(ArrayList<DayWithRegistedLesson> mDatasource, MainController mController) {
        this.mDatasource = mDatasource;
        this.mController = mController;
    }
    public void setListData(ArrayList<DayWithRegistedLesson> mDatasource){
        this.mDatasource = mDatasource;
        notifyDataSetChanged();
    }

    public TimeTableAdapter(ArrayList<DayWithRegistedLesson> lessons) {
        this.mDatasource = lessons;
    }


    @Override
    public int getCount() {
        return mDatasource.size();
    }

    @Override
    public DayWithRegistedLesson getItem(int i) {
        return mDatasource.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Context context = viewGroup.getContext();
        final Lesson lesson = getItem(i).getLesson();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TimeTableAdapter.ViewHolder vh = null;
        if (view == null) {
            vh = new ViewHolder();
            view = inflater.inflate(R.layout.item_row, viewGroup, false);
            vh.mTvName = view.findViewById(R.id.it_tv_lesson_name);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }
        initColumnHeader(i, vh.mTvName);
        initRowHeader(i, vh.mTvName);
        initData(i, vh.mTvName);
        addListener(view, i);
        return view;
    }

    private void addListener(View view, final int i) {
        if ( i > MAX_COLUMN && i % MAX_COLUMN != 0) {
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                        ClipData data = ClipData.newPlainText("", "");
                        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                            onDragBegin(view,i);
                            view.startDrag(data, shadowBuilder, view, 0);
                    }
                    return true;
                }
            });
            view.setOnDragListener(new DragDropListenter(mController,i, Constant.TYPE_TIME_TABLE));
        }
    }

    private void onDragBegin(View view, int curentDrag) {
        Message msg=new Message();
        msg.what= Constant.DRAP_AND_DROP;
        msg.obj = Constant.EVENT_DRAP;
        msg.arg2=curentDrag;
        msg.sendingUid= Constant.TIME_TABLE;
        mController.sendMessage(msg);
    }
    private void initData(int i, TextView mTvName) {
        if (i > MAX_COLUMN && i % MAX_COLUMN != 0) {
            if(getItem(i).getLesson() != null)
            mTvName.setText(getItem(i).getLesson().getName());
        } else return;
    }

    private void initRowHeader(int i, TextView mTvName) {
        if (i > 0 && i % MAX_COLUMN == 0) {
            mTvName.getRootView().setBackgroundColor(ContextCompat.getColor(mTvName.getContext(), R.color.colorHeaderCell));
            mTvName.setTextColor(ContextCompat.getColor(mTvName.getContext(), R.color.colorWhite));
            mTvName.setText(ROW_HEADER_PREFIX);
        } else
            return;

    }

    private void initColumnHeader(int i, TextView mTvName) {
        if (i > 0 && i < MAX_COLUMN) {
            mTvName.getRootView().setBackgroundColor(ContextCompat.getColor(mTvName.getContext(), R.color.colorHeaderCell));
            mTvName.setTextColor(ContextCompat.getColor(mTvName.getContext(), R.color.colorWhite));
            mTvName.setText(COLUMN_HEADER_PREFIX + i);
        } else
            return;
    }

    public class ViewHolder {
        private TextView mTvName;
    }


}
