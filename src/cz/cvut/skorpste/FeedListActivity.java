package cz.cvut.skorpste;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

public class FeedListActivity extends Activity implements ListFragment.ListListener {
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
            Fragment f = new DetailFragment(id);
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.detailFragment, f)
                    .addToBackStack(null)
                    .commit();
        } else {
            Intent i = new Intent(this, FeedActivity.class);
            i.putExtra(FeedActivity.EXTRA_FEED_ID, id);
            startActivity(i);
        }
    }
}
