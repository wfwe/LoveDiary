package lovediary.guo.com.lovediary;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;
import android.widget.Toast;

import java.util.Date;

public class DBService{
    private NotesDB notesDB;
    private static final String TWENTY = "20分";
    private static final String FORTY = "40分";
    private static final String SIXTY = "60分";
    private static final String EIGHTY = "80分";
    private static final String ONEHUNDRED = "100分";
        public DBService(Context context){
            this.notesDB = new NotesDB(context);
        }
        public void close(){
            notesDB.close();
        }
        public Cursor query(String username){
            //先按照是否置顶来排列，再按照_id排
            String sql = "select * from diary where username = ? order by stick desc,_id desc";
            SQLiteDatabase db = notesDB.getReadableDatabase();
            Cursor cursor = db.rawQuery(sql,new String[]{username});
            return cursor;
        }
    public Cursor queryCategroy(String username,String categoyr){
        //先按照是否置顶来排列，再按照_id排
        String sql = "select * from diary where  username = ? and categoyr = ?  order by stick desc,_id desc";
        SQLiteDatabase db = notesDB.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,new String[]{username,categoyr});
        return cursor;
    }
    public Cursor queryCollect(String username){
        //先按照是否置顶来排列，再按照_id排
        String sql = "select * from diary where username = ? and collect = 1 order by _id desc";
        SQLiteDatabase db = notesDB.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,new String[]{username});
        return cursor;
    }
        public Cursor query(Long id){
            String sql = "select * from diary where _id = ?";
            SQLiteDatabase db = notesDB.getReadableDatabase();
            Cursor cursor = db.rawQuery(sql,new String[]{id.toString()});
            return cursor;
        }
    public Cursor queryIsExist(String name,String password){
        String sql = "select * from tb_user where name = ? and password = ?";
        SQLiteDatabase db = notesDB.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, new String []{name,password});
        return cursor;
    }
    public Cursor queryByUserName(String name){
        String sql = "select * from tb_user where name = ?";
        SQLiteDatabase db = notesDB.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, new String []{name});
        return cursor;
    }

    public Cursor queryByFindpassword(String name,String lovingname){
        String sql = "select * from tb_user where name = ? and lovingname = ?";
        SQLiteDatabase db = notesDB.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, new String []{name,lovingname});
        return cursor;
    }
    public Cursor queryLike(String name,String s){
        String sql = "select * from diary where username = ? and (content like '%"+s+"%'or title like '%"+s+"%') order by stick desc,_id desc";
        SQLiteDatabase db = notesDB.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, new String []{name});
        return cursor;
    }

        public void add(String content, String title, String date,String username,String categoyr){
            String sql = "insert into diary (content,title,date,username,categoyr) values (?,?,?,?,?)";
            SQLiteDatabase db = notesDB.getWritableDatabase();
            db.execSQL(sql,new Object[]{content,title,date,username,categoyr});
        }

    public void addToUser(String name, String password,String lovingname){
        String sql = "insert into tb_user(name,password,lovingname) values(?,?,?)";
        SQLiteDatabase db = notesDB.getWritableDatabase();
        db.execSQL(sql,new String[]{name,password,lovingname});
    }
    public boolean updateToUser(String password,String lovingname,String name){
        String sql = "update tb_user set password = ? ,lovingname = ? where name = ?";
        SQLiteDatabase db = notesDB.getWritableDatabase();
        db.execSQL(sql,new String[]{password,lovingname,name});
        return true;
    }
    public boolean updateStick(Long stick,String name){
        String sql = "update tb_user set stick = ? where name = ?";
        SQLiteDatabase db = notesDB.getWritableDatabase();
        db.execSQL(sql,new Object[]{stick,name});
        return true;
    }
    public void addDateToUser(String date,String name){
        String sql = "update tb_user set date = ? where name = ?";
        SQLiteDatabase db = notesDB.getWritableDatabase();
        db.execSQL(sql,new Object[]{date,name});
    }
    public void addToCollect(Long _id){
        String sql = "update diary set collect = 1 where _id = ?";
        SQLiteDatabase db = notesDB.getWritableDatabase();
        db.execSQL(sql,new Object[]{_id});
    }
    public void addToStick(Long _id,Long number){
        String sql = "update diary set stick = ? where _id = ?";
        SQLiteDatabase db = notesDB.getWritableDatabase();
        db.execSQL(sql,new Object[]{number,_id});
    }
    public void cancelToStick(Long _id){
        String sql = "update diary set stick = 0 where _id = ?";
        SQLiteDatabase db = notesDB.getWritableDatabase();
        db.execSQL(sql,new Object[]{_id});
    }
    public void CancelToCollect(Long _id){
        String sql = "update diary set collect = 0 where _id = ?";
        SQLiteDatabase db = notesDB.getWritableDatabase();
        db.execSQL(sql,new Object[]{_id});
    }
        public void delete(Long id){
            String sql = "delete from diary where _id = ?";
            SQLiteDatabase db = notesDB.getReadableDatabase();
            if (db !=null)
            db.execSQL(sql,new Object[]{id.toString()});
        }

}
