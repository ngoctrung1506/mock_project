package bu22.fga.mockproject_group2.util;

/**
 * Created by CTC_TRAINING on 4/23/2018.
 */

public class GridItemUtil {

    private int day;
    private int position;

    public static int getPosition(int dropPosition) {
        return (int) dropPosition / 7;
    }

    public static String getDay(int dropPosition) {
        int day = (dropPosition % 7) + 1;
        String dayOfWeek = null;
        switch (day) {
            case 1:
                dayOfWeek = "Monday";
                break;
            case 2:
                dayOfWeek = "Tuesday";
                break;
            case 3:
                dayOfWeek = "Wednesday";
                break;
            case 4:
                dayOfWeek = "Thurday";
                break;
            case 5:
                dayOfWeek = "Friday";
                break;
            case 6:
                dayOfWeek = "Saturday";
                break;
        }

        return dayOfWeek;
    }



}
