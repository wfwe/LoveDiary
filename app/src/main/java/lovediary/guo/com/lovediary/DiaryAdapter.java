package lovediary.guo.com.lovediary;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class DiaryAdapter extends CursorAdapter{
    private LayoutInflater layoutInflater;

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.item_in_list,null);
        setChildView(view,cursor);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        setChildView(view,cursor);
    }

    public DiaryAdapter(Context context, Cursor cursor, boolean autoRequery){
        super(context,cursor,autoRequery);
        layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

    }
    private void setChildView(View view,Cursor cursor){
        TextView title = (TextView)view.findViewById(R.id.tv_titleshow);
        TextView date = (TextView)view.findViewById(R.id.tv_date);
        TextView content = (TextView)view.findViewById(R.id.tv_content);
        title.setText(cursor.getString(cursor.getColumnIndex("title")));
        date.setText(cursor.getString(cursor.getColumnIndex("date")));
        content.setText(cursor.getString(cursor.getColumnIndex("content")));
    }
}
