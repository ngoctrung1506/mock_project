package bu22.fga.mockproject_group2.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import bu22.fga.mockproject_group2.entity.DayOfWeek;
import bu22.fga.mockproject_group2.entity.DayWithRegistedLesson;
import bu22.fga.mockproject_group2.entity.Lesson;
import bu22.fga.mockproject_group2.entity.Week;


public class DatabaseHelper extends SQLiteOpenHelper {

    // Logcat week
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "contactsManager";

    // Table Names
    private static final String TABLE_LESSON = "lesson";
    private static final String TABLE_WEEK = "week";
    private static final String TABLE_DAYOFWEEK = "dayofweek";
    private static final String TABLE_DAYWITHLESSON = "dayWithRegistedLesson";

    // TABLE_LESSON Table - column nmaes
    private static final String KEY_LESSON_ID = "lesson_id";
    private static final String KEY_NAME_LESSON = "name_lesson";


    // TABLE_WEEK  Table - column names

    private static final String KEY_WEEK_ID = "week_id";
    private static final String KEY_START_DAY = "start_day";
    private static final String KEY_END_DAY = "end_day";

    // TABLE_DAYOFWEEK  Table - column names

    private static final String KEY_DAYOFWEEK_ID = "dayofweek_id";
    private static final String KEY_DAYOFWEEK_WEEK_ID = "dayofweek_week_id";
    private static final String KEY_DAYOFWEEK_NAME = "day_of_week_name";

    // TABLE_DAYWITHREGISTELESSON Table - column nmaes
    private static final String KEY_ID = "id";
    private static final String KEY_DAYWITHLESSON_ID_LESSON = "lesson_id";
    private static final String KEY_DAYWITHLESSON_ID_DAYOFWEED = "dayofweek_id";
    private static final String KEY_DAYWITHLESSON_POSITION = "position";


    // Table Create Lesson

    private static final String CREATE_TABLE_LESSON = "CREATE TABLE "
            + TABLE_LESSON + "(" + KEY_LESSON_ID + " INTEGER PRIMARY KEY, "
            + KEY_NAME_LESSON + " TEXT" + ")";


    // Table Create Week
    private static final String CREATE_TABLE_WEEK = "CREATE TABLE "
            + TABLE_WEEK + "(" + KEY_WEEK_ID + " INTEGER PRIMARY KEY,"
            + KEY_START_DAY + " DATETIME,"
            + KEY_END_DAY + " DATETIME" + ")";

    // Table Create TABLE_DAYOFWEEK;

    private static final String CREATE_TABLE_DAYOFWEEK = "CREATE TABLE "
            + TABLE_DAYOFWEEK + "(" + KEY_DAYOFWEEK_ID + " INTEGER PRIMARY KEY ,"
            + KEY_DAYOFWEEK_WEEK_ID + " INTEGER,"
            + KEY_DAYOFWEEK_NAME + " TEXT " + ")";


    // Table Create dayWithRegistedLesson

    private static final String CREATE_TABLE_DAYWITHLESSON = "CREATE TABLE "
            + TABLE_DAYWITHLESSON + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DAYWITHLESSON_ID_DAYOFWEED
            + " INTEGER," + KEY_DAYWITHLESSON_ID_LESSON
            + " INTEGER," + KEY_DAYWITHLESSON_POSITION
            + " INTEGER" + ")";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_LESSON);
        db.execSQL(CREATE_TABLE_WEEK);
        db.execSQL(CREATE_TABLE_DAYOFWEEK);
        db.execSQL(CREATE_TABLE_DAYWITHLESSON);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", CREATE_TABLE_LESSON));
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", CREATE_TABLE_WEEK));
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", CREATE_TABLE_DAYOFWEEK));
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", CREATE_TABLE_DAYWITHLESSON));
        // create new tables
        onCreate(db);
    }

    // ------------------------ "Lessons" table methods ----------------//

    /*
     * Updating a Lesson ok
     */
    public long createLesson(Lesson lesson) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME_LESSON, lesson.getName());
        // insert row
        return db.insert(TABLE_LESSON, null, values);
    }

    /*
     * get single Lesson ok
     */
    public Lesson getLesson(long Lesson_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_LESSON + " WHERE "
                + KEY_LESSON_ID + " = " + Lesson_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Lesson td = new Lesson();
        td.setId_lesson(c.getInt(c.getColumnIndex(KEY_LESSON_ID)));
        td.setName((c.getString(c.getColumnIndex(KEY_NAME_LESSON))));

        return td;
    }

    public int getLessonIdByName(String lessonName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT " + KEY_LESSON_ID + " FROM " + TABLE_LESSON + " WHERE "
                + KEY_NAME_LESSON + " = '" + lessonName + "'";

        Log.e(LOG, selectQuery);


        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();
        return c.getInt(c.getColumnIndex(KEY_LESSON_ID));
    }

    /**
     * getting all Lessons
     */
    public ArrayList<Lesson> getAllLessons() {
        ArrayList<Lesson> Lessons = new ArrayList<Lesson>();
        String selectQuery = "SELECT  * FROM " + TABLE_LESSON;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Lesson td = new Lesson();
                td.setId_lesson(c.getInt(c.getColumnIndex(KEY_LESSON_ID)));
                td.setName((c.getString(c.getColumnIndex(KEY_NAME_LESSON))));


                // adding to Lesson list
                Lessons.add(td);
            } while (c.moveToNext());
        }

        return Lessons;
    }


    /*
     * getting Lesson count ok
     */
    public int getLessonCount() {
        String countQuery = "SELECT  * FROM " + TABLE_LESSON;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    /*
     * Updating a Lesson ok
     */
    public void updateLesson(Lesson lesson, int lesson_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME_LESSON, lesson.getName());

        // updating row
        db.update(TABLE_LESSON, values, KEY_LESSON_ID + " = ?",
                new String[]{String.valueOf(lesson_id)});
    }

    public void deleteAllLesson() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LESSON, null, null);
    }

    /*
       * Deleting a Lesson
       */
    public void deleteLesson(int lesson_id, boolean should_delete_all_week_Lessons) {
        SQLiteDatabase db = this.getWritableDatabase();

        // before deleting Lesson
        // check if week under this Lesson should also be deleted
        if (should_delete_all_week_Lessons) {
            // get all week under this Lesson
            List<DayWithRegistedLesson> allDayWithLessons = getALLDayWithRegistedLesson(lesson_id);

            // delete all week
            for (DayWithRegistedLesson dayWithRegistedLesson : allDayWithLessons) {
                // delete week
                deleteweek(dayWithRegistedLesson.getId_DayWithRegistedLesson());
            }
        }

        // now delete the week
        db.delete(TABLE_LESSON, KEY_ID + " = ?",
                new String[]{String.valueOf(lesson_id)});
    }


    // ------------------------ "weeks" table methods ----------------//

    /*
     * Creating week 0k
     */
    public void createweek(Week week) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_START_DAY, week.getStartDay());
        values.put(KEY_END_DAY, week.getEndDay());
        // insert row
        db.insert(TABLE_WEEK, null, values);


    }


    /*
     * Updating a week ok
     */
    public int updateweek(Week week, int week_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_START_DAY, week.getStartDay());
        values.put(KEY_END_DAY, week.getEndDay());
        Log.e(LOG, String.valueOf(week_id));
        // updating row
        return db.update(TABLE_WEEK, values, KEY_WEEK_ID + " = ?",
                new String[]{String.valueOf(week_id)});

    }

    /*
     * Deleting a week ok
     */
    public void deleteweek(int week_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_WEEK, KEY_WEEK_ID + " = ?",
                new String[]{String.valueOf(week_id)});
    }


    /*
   * get single week ok
   */
    public Week getWeek(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_WEEK + " WHERE "
                + KEY_WEEK_ID + " = " + id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Week td = new Week();
        td.setId_Week(c.getInt(c.getColumnIndex(KEY_WEEK_ID)));
        td.setStartDay(c.getString(c.getColumnIndex(KEY_START_DAY)));
        td.setEndDay(c.getString(c.getColumnIndex(KEY_END_DAY)));
        td.setArrayList((ArrayList<DayOfWeek>) this.getAllDayOfWeek(c.getInt(c.getColumnIndex(KEY_WEEK_ID))));
        return td;
    }


    // ------------------------ "Day Of Week" table methods ----------------//

    /*
     * Creating DayOfWeek ok
     */
    public void createDayOfWeek(int week_id, DayOfWeek dayOfWeek) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DAYOFWEEK_NAME, dayOfWeek.getName());
        values.put(KEY_DAYOFWEEK_WEEK_ID, week_id);
        // insert row
        db.insert(TABLE_DAYOFWEEK, null, values);


    }


    /*
     * Updating a DayOfWeek ok
     */
    public int updateDayOfWeek(int week_id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_DAYOFWEEK_NAME, name);
        values.put(KEY_DAYOFWEEK_WEEK_ID, week_id);
        Log.e(LOG, String.valueOf(week_id));
        // updating row
        return db.update(TABLE_DAYOFWEEK, values, KEY_DAYOFWEEK_ID + " = ?",
                new String[]{String.valueOf(week_id)});

    }

    /*
     * Deleting a DayOfWeek
     */
    public void deleteDayOfWeek(int DayOfWeek_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DAYOFWEEK, KEY_DAYOFWEEK_ID + " = ?",
                new String[]{String.valueOf(DayOfWeek_id)});
    }


    /*
   * get single DayOfWeek  ok
   */
    public DayOfWeek getDayOfWeek(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_DAYOFWEEK + " WHERE "
                + KEY_DAYOFWEEK_ID + " = " + id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        DayOfWeek day = new DayOfWeek();
        day.setId_DayOfWeek(c.getInt(c.getColumnIndex(KEY_DAYOFWEEK_ID)));
        day.setName((c.getString(c.getColumnIndex(KEY_DAYOFWEEK_NAME))));

        return day;
    }


    /**
     * getting all DayOfWeek ok
     */
    public List<DayOfWeek> getAllDayOfWeek(long id) {
        List<DayOfWeek> List = new ArrayList<DayOfWeek>();

        String selectQuery = "SELECT  * FROM " + TABLE_DAYOFWEEK + " WHERE "
                + KEY_DAYOFWEEK_WEEK_ID + " = " + id;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {

                DayOfWeek dayOfWeek = new DayOfWeek();
                dayOfWeek.setId_DayOfWeek(c.getInt((c.getColumnIndex(KEY_DAYOFWEEK_ID))));
                dayOfWeek.setName(c.getString((c.getColumnIndex(KEY_DAYOFWEEK_NAME))));


                // adding to todo list
                List.add(dayOfWeek);
            } while (c.moveToNext());
        }

        return List;
    }


    // ------------------------ "Day With Registed Lesson" table methods ----------------//

    /*
     * Creating DayRegistedLesson ok
     */
    public void createDayRegistedLesson(DayWithRegistedLesson dayWithRegistedLesson) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DAYWITHLESSON_ID_LESSON, dayWithRegistedLesson.getLesson().getId_lesson());
        values.put(KEY_DAYWITHLESSON_ID_DAYOFWEED, dayWithRegistedLesson.getDayOfWeek().getId_DayOfWeek());
        values.put(KEY_DAYWITHLESSON_POSITION, dayWithRegistedLesson.getPosition());
        // insert row
        db.insert(TABLE_DAYWITHLESSON, null, values);


    }


    /*
     * Updating a DayRegistedLesson
     */
    public int updateDayRegistedLesson(DayWithRegistedLesson dayWithRegistedLesson, int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_DAYWITHLESSON_ID_LESSON, dayWithRegistedLesson.getLesson().getId_lesson());
        values.put(KEY_DAYWITHLESSON_ID_DAYOFWEED, dayWithRegistedLesson.getDayOfWeek().getId_DayOfWeek());
        values.put(KEY_DAYWITHLESSON_POSITION, dayWithRegistedLesson.getPosition());
        Log.e(LOG, String.valueOf(dayWithRegistedLesson.getId_DayWithRegistedLesson()));
        // updating row
        return db.update(TABLE_DAYWITHLESSON, values, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});

    }

    /**
     * getting all DayWithRegistedLesson ok
     */
    public List<DayWithRegistedLesson> getALLDayWithRegistedLesson(long id) {
        List<DayWithRegistedLesson> List = new ArrayList<DayWithRegistedLesson>();

        String selectQuery = "SELECT  * FROM " + TABLE_DAYWITHLESSON + " WHERE "
                + KEY_DAYWITHLESSON_ID_DAYOFWEED + " = " + id;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {

                DayWithRegistedLesson dayWithRegistedLesson = new DayWithRegistedLesson();
                dayWithRegistedLesson.setId_DayWithRegistedLesson(c.getInt((c.getColumnIndex(KEY_ID))));
                dayWithRegistedLesson.setLesson(this.getLesson(c.getInt((c.getColumnIndex(KEY_DAYWITHLESSON_ID_LESSON)))));
                dayWithRegistedLesson.setDayOfWeek(this.getDayOfWeek(c.getInt((c.getColumnIndex(KEY_DAYWITHLESSON_ID_DAYOFWEED)))));
                ;
                dayWithRegistedLesson.setPosition(c.getInt((c.getColumnIndex(KEY_DAYWITHLESSON_POSITION))));

                // adding to todo list
                List.add(dayWithRegistedLesson);
            } while (c.moveToNext());
        }

        return List;
    }

	/*
   * Deleting a DayRegistedLesson ok
	 */

    public DayWithRegistedLesson getDayWithRegistedLesson(long id) {
        List<DayWithRegistedLesson> List = new ArrayList<DayWithRegistedLesson>();

        String selectQuery = "SELECT  * FROM " + TABLE_DAYWITHLESSON + " WHERE "
                + KEY_ID + " = " + id;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);


        if (c != null)
            c.moveToFirst();

        DayWithRegistedLesson dayWithRegistedLesson = new DayWithRegistedLesson();
        dayWithRegistedLesson.setId_DayWithRegistedLesson(c.getInt((c.getColumnIndex(KEY_ID))));
        dayWithRegistedLesson.setLesson(this.getLesson(c.getInt((c.getColumnIndex(KEY_DAYWITHLESSON_ID_LESSON)))));
        dayWithRegistedLesson.setDayOfWeek(this.getDayOfWeek(c.getInt((c.getColumnIndex(KEY_DAYWITHLESSON_ID_DAYOFWEED)))));
        ;
        dayWithRegistedLesson.setPosition(c.getInt((c.getColumnIndex(KEY_DAYWITHLESSON_POSITION))));


        return dayWithRegistedLesson;
    }

    /*
     * Deleting a DayRegistedLesson
     */
    public void deleteDayRegistedLesson(int _id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DAYWITHLESSON, KEY_ID + " = ?",
                new String[]{String.valueOf(_id)});
    }


    /**
     * get datetime
     */
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd ", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    //delete lesson in ListLesson
    public void delete(Lesson lesson) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LESSON, KEY_LESSON_ID + " = ?",
                new String[]{String.valueOf(lesson.getId_lesson())});
        db.close();
    }

    //add new Lesson
    public void addLesson(Lesson lesson) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME_LESSON, lesson.getName());
        // insert row
        db.insert(TABLE_LESSON, null, values);

    }
}

