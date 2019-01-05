package lovediary.guo.com.lovediary;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class editActivity extends AppCompatActivity {
    private TextView tv_date;
    private EditText tv_title;
    private EditText et_content;
    private Button btn_ok;
    private Button btn_cancel;
    private String username;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.edit_save:
                    final String[] cars = {"20分", "40分", "60分", "80分", "100分"};
                    final AlertDialog.Builder builder = new AlertDialog.Builder(editActivity.this);
                    builder.setTitle("为今天的恋爱打分");
                    builder.setItems(cars, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String content = et_content.getText().toString();
                            String title = tv_title.getText().toString();
                            Date date = new Date();
                            SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 HH:mm");
                            String dateNum = sdf.format(date);
                            if (!content.isEmpty()&&!title.isEmpty()) {
                            dbService.add(content, title, dateNum,username,cars[i]);
                            Toast.makeText(editActivity.this,"添加成功！",Toast.LENGTH_SHORT).show();
                            finish();
                            }else {
                                Toast.makeText(editActivity.this,"内容为空！",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    builder.create().show();
                break;
            case android.R.id.home:
                //添加判断
                String content1 = et_content.getText().toString();
                String title1 = tv_title.getText().toString();
                if (!content1.isEmpty()||!title1.isEmpty()){
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(editActivity.this);
                    builder1.setTitle("未保存");

                    builder1.setMessage("内容未保存，仍要退出？");
                    builder1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();;
                        }
                    });
                    builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    });
                    builder1.create().show();
                }else{
                    finish();
                }


        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu,menu);
        return true;
    }

    private NotesDB DB;
    private SQLiteDatabase dbread;
    private DBService dbService;

    @Override
    protected void onDestroy() {
        dbService.close();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);
        Intent in = getIntent();
        username = in.getStringExtra("username");

        Toolbar toolbar = (Toolbar)findViewById(R.id.edit_toolbar);
        this.setSupportActionBar(toolbar);
        DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.edit_drawer);
        ActionBar actionBar =getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        dbService = new DBService(editActivity.this);


        tv_title = (EditText) findViewById(R.id.tv_title);
        // 设置软键盘点击后弹出
        tv_title.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
        });


        et_content = (EditText) findViewById(R.id.et_content);
        // 设置软键盘自动弹出
        et_content.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
    }

}
