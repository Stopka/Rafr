package cz.cvut.skorpste.view.Article;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import cz.cvut.skorpste.R;

/**
 * Created by stopka on 14.3.14.
 */
public class ArticleActivity extends Activity {
    public static final String EXTRA_FEED_ID = "article";

    private long feed_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article);

        Intent i = getIntent();
        feed_id = i.getLongExtra(EXTRA_FEED_ID, -1);

        ArticleFragment f = new ArticleFragment();
        Bundle b=new Bundle();
        b.putLong(ArticleFragment.ARTICLE_ID,feed_id);
        f.setArguments(b);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.detailFragment, f)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        return true;
    }

}