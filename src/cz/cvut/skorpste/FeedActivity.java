package cz.cvut.skorpste;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ShareActionProvider;

/**
 * Created by stopka on 14.3.14.
 */
public class FeedActivity extends Activity {
    public static final String EXTRA_FEED_ID = "article";

    private long feed_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        Intent i = getIntent();
        Long feed_id = i.getLongExtra(EXTRA_FEED_ID, -1);

        DetailFragment f = new DetailFragment();
        Bundle b=new Bundle();
        b.putLong(DetailFragment.ARTICLE_ID,feed_id);
        f.setArguments(b);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.detailFragment, f)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share, menu);
        MenuItem item = menu.findItem(R.id.menu_item_share);

        ShareActionProvider shareActionProvider = (ShareActionProvider) menu.findItem(R.id.menu_item_share).getActionProvider();
        Feed feed = Feeds.get().getFeed(feed_id);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, R.string.share_subject);
        intent.putExtra(Intent.EXTRA_TEXT, feed.getTitle());

        shareActionProvider.setShareIntent(intent);

        return true;
    }

}