package cz.cvut.skorpste.controller.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
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
}
