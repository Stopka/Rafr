package cz.cvut.skorpste.view.ArticleList;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import cz.cvut.skorpste.R;
import cz.cvut.skorpste.model.feeds.ScheduleBroadcastReceiver;
import cz.cvut.skorpste.view.Article.ArticleActivity;
import cz.cvut.skorpste.view.Article.ArticleFragment;
import cz.cvut.skorpste.view.FeedList.FeedListActivity;

public class ArticleListActivity extends Activity implements ArticleListFragment.ListListener {
    boolean wide_layout;
    private static final long DOWNLOAD_INTERVAL = AlarmManager.INTERVAL_FIFTEEN_MINUTES / 15;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
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

    @Override
    public void onConfigClick() {
        Intent i = new Intent(this, FeedListActivity.class);
        startActivity(i);
    }
}
