package lovediary.guo.com.lovediary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class NotesDB extends SQLiteOpenHelper {
    public static final String TABLE_NAME_NOTES = "diary";
    private final static int DATABASE_VERSION = 1;
    private final static String DATABASE_NAME = "diary.db";
    public NotesDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table diary( _id INTEGER primary key,content TEXT,title varchar(15),"+
                "date varchar(15),username varchar(15),categoyr varchar(15),stick INTEGER default 0,collect INTEGER default 0)";
        Log.d("SQL", sql);
        db.execSQL(sql);
        db.execSQL("create table tb_user( name varchar(15) primary key,password varchar(15),lovingname varchar(15),stick INTEGER default 0,date varchar(15))");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
