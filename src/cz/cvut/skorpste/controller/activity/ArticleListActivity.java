package cz.cvut.skorpste.controller.activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import cz.cvut.skorpste.R;
import cz.cvut.skorpste.controller.fragment.ArticleFragment;
import cz.cvut.skorpste.controller.fragment.ArticleListFragment;

public class ArticleListActivity extends Activity implements ArticleListFragment.ListListener {
    boolean wide_layout;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        wide_layout = findViewById(R.id.detailFragment) != null;
    }

    @Override
    public void onListItemClick(long id) {
        if (wide_layout) {
            Fragment f = new ArticleFragment();
            Bundle b=new Bundle();
            b.putLong(ArticleFragment.ARTICLE_ID,id);
            f.setArguments(b);
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.detailFragment, f)
                    .addToBackStack(null)
                    .commit();
        } else {
            Intent i = new Intent(this, ArticleActivity.class);
            i.putExtra(ArticleActivity.EXTRA_FEED_ID, id);
            startActivity(i);
        }
    }
}
