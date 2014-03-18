package cz.cvut.skorpste;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by stopka on 13.3.14.
 */
public class ListFragment extends android.app.ListFragment {
    //TODO nahradit objektem

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

        ArticleArrayAdapter adapter=new ArticleArrayAdapter(this.getActivity(), R.layout.listitem, Feeds.get().getFeeds());
        setListAdapter(adapter);
        return super.onCreateView(inflater,container,savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Feed[] f=Feeds.get().getFeeds();
        listener.onListItemClick(f[position].getID());
    }
}