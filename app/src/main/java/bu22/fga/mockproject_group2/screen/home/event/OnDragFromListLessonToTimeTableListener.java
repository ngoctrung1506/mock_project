package bu22.fga.mockproject_group2.screen.home.event;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;

import bu22.fga.mockproject_group2.R;
import bu22.fga.mockproject_group2.controller.MainController;
import bu22.fga.mockproject_group2.dialog.ConfirmDialog;

/**
 * Created by Admin on 23/04/2018.
 */

public class OnDragFromListLessonToTimeTableListener implements View.OnDragListener {

  private MainController mController;
  private int curentDrop;

  public OnDragFromListLessonToTimeTableListener(MainController mController, int curentDrop) {
    this.mController = mController;
    this.curentDrop = curentDrop;
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
        Log.d("dropFromList", "drag: ");
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
  }
}
