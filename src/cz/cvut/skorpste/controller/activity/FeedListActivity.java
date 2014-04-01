package cz.cvut.skorpste.controller.activity;

import android.app.*;
import android.content.*;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import cz.cvut.skorpste.R;
import cz.cvut.skorpste.controller.fragment.ArticleFragment;
import cz.cvut.skorpste.controller.fragment.ArticleListFragment;
import cz.cvut.skorpste.model.database.ArticleContentProvider;
import cz.cvut.skorpste.model.database.ArticleTable;
import cz.cvut.skorpste.model.database.FeedTable;

public class FeedListActivity extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int FEED_LOADER=0;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(FEED_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case FEED_LOADER:
                setProgressBarIndeterminate(true);
                setProgressBarVisibility(true);
                return new CursorLoader(this, ArticleContentProvider.FEED_URI,null,null,null, null);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        setListAdapter(new SimpleCursorAdapter(this,android.R.layout.simple_list_item_1,data,
                new String[]{FeedTable.URL},
                new int[]{android.R.id.text1},
                0));
        setProgressBarVisibility(false);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        setProgressBarVisibility(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.feedlist, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_add_feed:
                showAddDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showAddDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.add_feed);

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_WEB_EDIT_TEXT);
        builder.setView(input);

        builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ContentValues cv=new ContentValues();
                cv.put("url",input.getText().toString());
                getContentResolver().insert(ArticleContentProvider.FEED_URI,cv);
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    @Override
    protected void onListItemClick(ListView l, View v, final int position, long id) {
        final int pos=position;
        Cursor cursor=(Cursor)l.getItemAtPosition(position);
        int feed_col=cursor.getColumnIndex(FeedTable.ID);
        final String feed_id=cursor.getString(feed_col);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.feed_delete_confirm);

        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getContentResolver().delete(ArticleContentProvider.FEED_URI,FeedTable.ID+"=?",new String[]{feed_id});
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
        super.onListItemClick(l, v, position, id);
    }
}
