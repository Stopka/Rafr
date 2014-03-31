package cz.cvut.skorpste.controller.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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
}