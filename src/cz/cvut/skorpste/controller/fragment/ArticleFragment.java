package cz.cvut.skorpste.controller.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import cz.cvut.skorpste.model.Article;
import cz.cvut.skorpste.model.Articles;
import cz.cvut.skorpste.R;

/**
 * Created by stopka on 13.3.14.
 */
public class ArticleFragment extends Fragment {
    public static final String ARTICLE = "article";
    public static final String ARTICLE_ID = "article_id";

    private Article article;

    public ArticleFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long id=getArguments().getLong(ARTICLE_ID);
        this.article = Articles.get().getFeed(id);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.article_fragment, container, false);
        TextView title=(TextView)view.findViewById(R.id.title);
        title.setText(article.getTitle());
        TextView author=(TextView)view.findViewById(R.id.author);
        author.setText(article.getAuthor());
        TextView article=(TextView)view.findViewById(R.id.text);
        article.setText(this.article.getText());
        TextView date=(TextView)view.findViewById(R.id.date);
        date.setText(this.article.getDate().toString());

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.article, menu);
        MenuItem item = menu.findItem(R.id.menu_item_share);

        ShareActionProvider shareActionProvider = (ShareActionProvider) menu.findItem(R.id.menu_item_share).getActionProvider();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, R.string.share_subject);
        intent.putExtra(Intent.EXTRA_TEXT, article.getTitle());

        shareActionProvider.setShareIntent(intent);
    }
}