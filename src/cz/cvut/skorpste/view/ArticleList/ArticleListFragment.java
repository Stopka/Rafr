package cz.cvut.skorpste.view.ArticleList;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.*;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.*;
import android.widget.*;
import cz.cvut.skorpste.R;
import cz.cvut.skorpste.model.database.ArticleContentProvider;
import cz.cvut.skorpste.model.database.ArticleTable;
import cz.cvut.skorpste.model.database.FeedTable;
import cz.cvut.skorpste.model.feeds.FeedLoader;

/**
 * Created by stopka on 13.3.14.
 */
public class ArticleListFragment extends android.app.ListFragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private final int ARTICLE_LOADER=1;
    private final int READER_LOADER=2;

    private BroadcastReceiver progressReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            int progress = intent.getIntExtra(FeedLoader.PROGRESS_BROADCAST_VALUE, -1);
            TextView text = (TextView)refresh_button.getActionView().findViewById(R.id.progressbar_text);
            if(progress==-1){
                text.setText("");
                return;
            }
            text.setText(progress+"%");
        }
    };

    ListListener listener;

    private FeedLoader refreshTask;

    private MenuItem refresh_button;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

            ArticleAdapter adapter = new ArticleAdapter(getActivity(),null,CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
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
            case READER_LOADER:
                LocalBroadcastManager.getInstance(getActivity()).registerReceiver(progressReceiver, new IntentFilter(FeedLoader.PROGRESS_BROADCAST));
                refresh_button.setActionView(R.layout.actionbar_indeterminate_progress);
                return new FeedLoader(getActivity());
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case ARTICLE_LOADER:
                ((ArticleAdapter)getListAdapter()).swapCursor(data);
                getActivity().setProgressBarVisibility(false);
                break;
            case READER_LOADER:
                refresh_button.setActionView(null);
                LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(progressReceiver);
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
            case ARTICLE_LOADER:
                ((ArticleAdapter)getListAdapter()).swapCursor(null);
                getActivity().setProgressBarVisibility(false);
                break;
            case READER_LOADER:
                refresh_button.setActionView(null);
                LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(progressReceiver);
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
        refresh_button = menu.findItem(R.id.menu_item_refresh);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_setting:
                listener.onConfigClick();
                return true;
            case R.id.menu_item_refresh:
                getLoaderManager().initLoader(READER_LOADER, null, this).forceLoad();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    /*
    @Override
    public void onRefreshStart(){
        refresh_button.setActionView(R.layout.actionbar_indeterminate_progress);
    }

    @Override
    public void onRefreshFinished(){
        refresh_button.setActionView(null);
    }

    @Override
    public void onRefreshUpdate(int value) {
        TextView text = (TextView)refresh_button.getActionView().findViewById(R.id.progressbar_text);
        text.setText(value+"%");
    }*/
}