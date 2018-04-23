package bu22.fga.mockproject_group2.screen.home.event;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.DragEvent;
import android.view.View;
import android.widget.Toast;

import bu22.fga.mockproject_group2.R;
import bu22.fga.mockproject_group2.controller.MainController;
import bu22.fga.mockproject_group2.dialog.ConfirmDialog;


public class DragDropListenter implements View.OnDragListener {

    private int mEventType;
    private MainController mController;
    private int curentDrop;

    public DragDropListenter(MainController mController, int curentDrop, int typeTimeTable) {
        this.mController = mController;
        this.curentDrop = curentDrop;
        this.mEventType = typeTimeTable;
    }

    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {
        switch (dragEvent.getAction()) {
            case DragEvent.ACTION_DRAG_LOCATION:
                view.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.colorBlack));
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                view.setBackgroundColor(Color.WHITE);
                break;
            case DragEvent.ACTION_DRAG_ENDED:
                break;
            case DragEvent.ACTION_DROP:
                view.setBackgroundColor(Color.WHITE);
                onDrop(view, curentDrop);
                break;
            default:
                view.setBackgroundColor(Color.WHITE);
                break;
        }
        return true;
    }

    private void onDrop(View view, int curentDrop) {

        new ConfirmDialog(mController.getView(), mController, curentDrop).show();
        int lessonPosition = (int) curentDrop / 7;
        int day = (curentDrop % 7) + 1;
        Toast.makeText(mController.getView(), "position: " + lessonPosition + "day: " + day, Toast.LENGTH_LONG).show();
//    Message msg = new Message();
//      msg.what = Constant.DRAP_AND_DROP;
//      msg.obj = Constant.EVENT_DROP;
//      msg.arg1 = Constant.EDIT_LESSON;
//      msg.arg2 = curentDrop;
//      mController.sendMessage(msg);
    }

}
