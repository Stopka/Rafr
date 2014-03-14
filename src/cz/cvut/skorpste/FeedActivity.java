package cz.cvut.skorpste;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by stopka on 14.3.14.
 */
public class FeedActivity extends Activity {
    public static final String EXTRA_FEED_ID = "article";

    private Long feed_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        Intent i = getIntent();
        Long feed_id = i.getLongExtra(EXTRA_FEED_ID, -1);

        DetailFragment f = new DetailFragment(feed_id);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.detailFragment,f)
                .commit();
    }

}