package lovediary.guo.com.lovediary;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;
import android.widget.Toast;

import java.util.Date;

public class DBService{
        private NotesDB notesDB;

        public DBService(Context context){
            this.notesDB = new NotesDB(context);
        }
        public Cursor query(){
            String sql = "select * from diary";
            SQLiteDatabase db = notesDB.getReadableDatabase();
            Cursor cursor = db.rawQuery(sql,null);
            return cursor;
        }
        public void add(String content, String title, String date){
            String sql = "insert into diary (content,title,date) values (?,?,?)";
            SQLiteDatabase db = notesDB.getWritableDatabase();
            db.execSQL(sql,new Object[]{content,title,date});
        }
        public void delete(){
            String sql = "delete from diary where title = ?";
        }

}
