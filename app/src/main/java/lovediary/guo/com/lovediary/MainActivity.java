package lovediary.guo.com.lovediary;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import static android.R.id.home;

public class MainActivity extends AppCompatActivity  {

    private DrawerLayout mDrawerLayout;
    private ListView listview;
    private Button addNote;
    private TextView tv_content;
    private NotesDB DB;
    private SQLiteDatabase dbread;
    private DBService  dbServicr;
    private Cursor cursor;
    private DiaryAdapter diaryAdapter;
    private TextView mEditText;
    private String username;
    private String loveDay;
    private LinearLayout  list_floatinglayout;
    private static Long stick;

    @Override
    protected void onPause() {
        dbServicr = new DBService(this);
        dbServicr.updateStick(stick,username);
        super.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        dbServicr = new DBService(this);
        switch (item.getItemId()) {
            case home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.search:
                Intent inten = new Intent(MainActivity.this,SearchActivity.class);
                inten.putExtra("username",username);
                startActivity(inten);
                break;
            case R.id.queryAll:
                refresh();
                break;
            case R.id.qureyTwenty:
                refreshCategory("20分");
                break;
            case R.id.queryForty:
                refreshCategory("40分");
                break;
            case R.id.querySixty:
                refreshCategory("60分");
                break;
            case R.id.queryEighty:
                refreshCategory("80分");
                break;
            case R.id.queryOneHunder:
                refreshCategory("100分");
                break;

            default:
        }
        return true;
    }
    protected void refreshCategory(String category) {
        DBService dbService = new DBService(this);
        Cursor cursor =dbService.queryCategroy(username,category);
        if (cursor.getCount()>0){
            diaryAdapter = new DiaryAdapter(MainActivity.this, cursor, true);
            listview.setAdapter(diaryAdapter);
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //设置相应的Intent打开对应的Activity
                    Intent openDetail = new Intent(MainActivity.this, ItemDetailInformationActivity.class);
                    openDetail.putExtra("username",username);
                    openDetail.putExtra("id", id);
                    startActivity(openDetail);
                }
            });
        }else {
            Toast.makeText(getBaseContext(), "该类为空", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    protected void onResume() {
        DBService dbService = new DBService(this);
        Cursor cursor = dbService.query(username);
        if (cursor.getCount()>0){
            diaryAdapter = new DiaryAdapter(MainActivity.this, cursor, true);
            listview.setAdapter(diaryAdapter);
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //设置相应的Intent打开对应的Activity
                    Intent openDetail = new Intent(MainActivity.this, ItemDetailInformationActivity.class);
                    openDetail.putExtra("username",username);
                    openDetail.putExtra("id", id);
                    startActivity(openDetail);
                }
            });

        }else{
            listview.setAdapter(null);
        }


        super.onResume();
    }
    public void refresh(){
        DBService dbServicr = new DBService(this);
        cursor = dbServicr.query(username);
        diaryAdapter = new DiaryAdapter(MainActivity.this, cursor, true);
        listview.setAdapter(diaryAdapter);
    }


    @Override
    protected void onDestroy() {
        dbServicr.close();
        super.onDestroy();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBService dbServicr = new DBService(this);

        Intent intent1 = getIntent();
        username = intent1.getStringExtra("username");

        //设置toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //设置滑动菜单导航按钮
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.i_menu);
        }
        //菜单项点击事件
        navigationView.setCheckedItem(R.id.nav_exit);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_changePassword:
                        Intent intent = new Intent(MainActivity.this,ChangeActivity.class);
                        intent.putExtra("username",username);
                        startActivity(intent);
                        break;
                    case R.id.nav_exit:
                        Intent in =new Intent(MainActivity.this,StartActivity.class);
                        startActivity(in);
                        finish();
                        break;
                    case R.id.nav_about:
                        showAbout();
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_collect:
                        Intent intent2 = new Intent(MainActivity.this,CollectActivity.class);
                        intent2.putExtra("username",username);
                        startActivity(intent2);
                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

        tv_content = (TextView) findViewById(R.id.tv_content);
        listview = (ListView) findViewById(R.id.listview);

        FloatingActionButton floatingActionButton = (FloatingActionButton)findViewById(R.id.addDaily);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, editActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });


        listview.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.setHeaderTitle("选择操作");
                menu.add(0,0,0,"删除");
                menu.add(0,1,0,"置顶");
                menu.add(0,2,0,"收藏");
                menu.add(0,3,0,"取消置顶");
            }
        });



        mEditText = (TextView) findViewById(R.id.editText1);
        //查询是否已设置纪念日
        Cursor cu = dbServicr.queryByUserName(username);
        if (cu.moveToFirst()){
            String date = cu.getString(cu.getColumnIndex("date"));
            if(date != null && date !="") {
                Log.i(MainActivity.this.toString(), date);
                mEditText.setText(date);
                mEditText.setTextSize(20);
                TextView textLove = (TextView) findViewById(R.id.lovingDay);
                TCalendar tCalendar = new TCalendar();
                int lovingday = tCalendar.getDay(cu.getString(cu.getColumnIndex("date")));
                textLove.setText(lovingday + "");
                textLove.setTextSize(20);
            }
        }
        //数据库中读取置顶值
        stick = cu.getLong(cu.getColumnIndex("stick"));
        mEditText.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    showDatePickDlg();
                    return true;
                }
                return false;
            }
        });


    }
    protected void showDatePickDlg() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear++;
                DBService dbServicr = new DBService(MainActivity.this);
                MainActivity.this.mEditText.setText(year+"年"+monthOfYear+"月"+dayOfMonth+"日");
                dbServicr.addDateToUser(year+"年"+monthOfYear+"月"+dayOfMonth+"日",username);
                MainActivity.this.mEditText.setTextSize(20);
                String m,d,y;
                if (monthOfYear<10){
                    m = '0'+Integer.toString(monthOfYear);
                }else m=Integer.toString(monthOfYear);
                if (dayOfMonth<10){
                    d = '0'+Integer.toString(dayOfMonth);
                }   else d=Integer.toString(dayOfMonth);
                y=Integer.toString(year);
                dbServicr.addDateToUser(y+"年"+m+"月"+d+"日",username);
                String startDay = y+"年"+m+"月"+d+"日";
                TextView textLove = (TextView)findViewById(R.id.lovingDay);
                TCalendar tCalendar = new TCalendar();
                int lovingday = tCalendar.getDay(startDay);
                textLove.setText(lovingday+"");
                textLove.setTextSize(20);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo =(AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        DBService dbServicr = new  DBService(MainActivity.this);
        switch (item.getItemId()){
            case 0:
                dbServicr.delete(adapterContextMenuInfo.id);
                refresh();
                Toast.makeText(getBaseContext(), "删除成功", Toast.LENGTH_SHORT).show();
                return true;
            case 1:
                stick++;
                dbServicr.addToStick(adapterContextMenuInfo.id,stick);
                refresh();
                Toast.makeText(getBaseContext(), "置顶成功", Toast.LENGTH_SHORT).show();
                return true;
            case 3:
                dbServicr.cancelToStick(adapterContextMenuInfo.id);
                refresh();
                Toast.makeText(getBaseContext(), "取消置顶成功", Toast.LENGTH_SHORT).show();
                return true;
            case 2:
                dbServicr.addToCollect(adapterContextMenuInfo.id);
                Toast.makeText(getBaseContext(), "收藏成功", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onContextItemSelected(item);
        }

    }
    public void showAbout(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
        builder1.setTitle("关于软件");
        builder1.setMessage("这是一款可用于记录爱情事迹的恋爱日记软件，您可以每天记录您跟您伴侣的爱情，并为您的伴侣打分。软件具有收藏，分类，置顶和搜索功能。感谢您的使用");
        builder1.setNegativeButton("关闭", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();;
            }
        });
        builder1.create().show();
    }

}
