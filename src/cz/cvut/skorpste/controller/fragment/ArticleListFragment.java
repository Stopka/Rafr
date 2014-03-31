package cz.cvut.skorpste.controller.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.ListView;
import android.widget.ShareActionProvider;
import cz.cvut.skorpste.model.ArticleArrayAdapter;
import cz.cvut.skorpste.model.Article;
import cz.cvut.skorpste.model.Articles;
import cz.cvut.skorpste.R;

/**
 * Created by stopka on 13.3.14.
 */
public class ArticleListFragment extends android.app.ListFragment {

    Article[] articles;

    ListListener listener;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        articles = Articles.get().getFeeds();
        ArticleArrayAdapter adapter=new ArticleArrayAdapter(this.getActivity(), R.layout.article_list_item, articles);
        setListAdapter(adapter);
        return super.onCreateView(inflater,container,savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        long feed_id= articles[position].getID();
        listener.onListItemClick(feed_id);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.setting, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_setting:
                listener.onConfigClick();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}