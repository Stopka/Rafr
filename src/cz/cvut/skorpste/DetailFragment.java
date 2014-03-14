package cz.cvut.skorpste;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by stopka on 13.3.14.
 */
public class DetailFragment extends Fragment {
    public static final String ARTICLE = "article";

    private long id;

    public DetailFragment(long id) {
        this.id = id;
    }

    public DetailFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.detailfragment, container, false);
        Feed item=Feeds.get().getFeed(id);
        TextView title=(TextView)view.findViewById(R.id.title);
        title.setText(item.getTitle());
        TextView author=(TextView)view.findViewById(R.id.author);
        author.setText(item.getAuthor());
        TextView article=(TextView)view.findViewById(R.id.text);
        article.setText(item.getArticle());
        TextView date=(TextView)view.findViewById(R.id.date);
        date.setText(item.getDate().toString());

        return view;
    }
}