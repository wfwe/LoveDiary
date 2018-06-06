package lovediary.guo.com.lovediary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class NotesDB extends SQLiteOpenHelper {
    public static final String TABLE_NAME_NOTES = "diary";
    public static final String COLUMN_NAME_ID = "_id";
    public static final String COLUMN_NAME_NOTE_CONTENT = "content";
    public static final String COLUMN_NAME_NOTE_TITLE = "title";
    public static final String COLUMN_NAME_NOTE_DATE = "date";
    private final static int DATABASE_VERSION = 1;
    private final static String DATABASE_NAME = "diary.db";
    public NotesDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME_NOTES + "("
                + COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME_NOTE_TITLE + " TEXT  DEFAULT\"\","
                + COLUMN_NAME_NOTE_CONTENT + " TEXT  DEFAULT\"\","
                + COLUMN_NAME_NOTE_DATE + " TEXT  DEFAULT\"\"" + ")";
        Log.d("SQL", sql);
        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
