package cz.cvut.skorpste.view.ArticleList;

import android.content.Context;
import android.database.Cursor;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cz.cvut.skorpste.R;
import cz.cvut.skorpste.model.database.ArticleTable;

/**
 * Created by stopka on 3.4.14.
 */
public class ArticleAdapter extends CursorAdapter {
    private static final int DESC_LENGTH=160;

    public ArticleAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(R.layout.article_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView title = (TextView)view.findViewById(R.id.title);
        title.setText(cursor.getString(cursor.getColumnIndex(ArticleTable.TITLE)));
        TextView desc = (TextView)view.findViewById(R.id.description);
        String content=cursor.getString(cursor.getColumnIndex(ArticleTable.CONTENT));
        content= Html.fromHtml(content).toString();
        desc.setText(content);
    }
}
