package cz.cvut.skorpste.controller.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.*;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import cz.cvut.skorpste.model.ArticleArrayAdapter;
import cz.cvut.skorpste.model.Article;
import cz.cvut.skorpste.model.Articles;
import cz.cvut.skorpste.R;
import cz.cvut.skorpste.model.database.ArticleContentProvider;
import cz.cvut.skorpste.model.database.ArticleTable;
import cz.cvut.skorpste.model.database.FeedTable;
import cz.cvut.skorpste.model.feeds.FeedReader;

/**
 * Created by stopka on 13.3.14.
 */
public class ArticleListFragment extends android.app.ListFragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private final int ARTICLE_LOADER=1;
    ListListener listener;

    private FeedReader refreshTask;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

            SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(),R.layout.article_list_item,null,
                    new String[]{ArticleTable.TITLE},
                    new int[]{R.id.title},
                    CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
            setListAdapter(adapter);
            getLoaderManager().initLoader(ARTICLE_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case ARTICLE_LOADER:
                getActivity().setProgressBarIndeterminate(true);
                getActivity().setProgressBarVisibility(true);
                return new CursorLoader(getActivity(), ArticleContentProvider.ARTICLE_URI,null,null,null, null);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case ARTICLE_LOADER:
                ((SimpleCursorAdapter)getListAdapter()).swapCursor(data);
                getActivity().setProgressBarVisibility(false);
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
            case ARTICLE_LOADER:
                ((SimpleCursorAdapter)getListAdapter()).swapCursor(null);
                getActivity().setProgressBarVisibility(false);
                break;
        }
    }


    public static interface ListListener {
        public void onListItemClick(long id);

        public void onConfigClick();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (ListListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()+" must implement ListListener");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Cursor cursor=(Cursor)l.getItemAtPosition(position);
        int feed_col=cursor.getColumnIndex(FeedTable.ID);
        long feed_id=cursor.getLong(feed_col);
        listener.onListItemClick(feed_id);
        super.onListItemClick(l, v, position, id);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.articlelist, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_setting:
                listener.onConfigClick();
                return true;
            case R.id.menu_item_refresh:
                refreshTask=new FeedReader(getActivity());
                refreshTask.execute();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onRefreshStart(){
        View refreshView;
        LayoutInflater inflater = (LayoutInflater) getActivity().getActionBar().getThemedContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        /*
        if(refreshing)
            refreshView = inflater.inflate(R.layout.actionbar_indeterminate_progress, null);
        else
            refreshView = inflater.inflate(R.layout.refresh_icon, null);

        menuItem.setActionView(refreshView);*/
    }
}