package cz.cvut.skorpste;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

public class FeedListActivity extends Activity implements ListFragment.ListListener {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    @Override
    public void onListItemClick(long id) {
        if(false){
            Fragment f = new DetailFragment(id);
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.detailFragment, f)
                    .addToBackStack(null)
                    .commit();
        }
        Intent i = new Intent(this, FeedActivity.class);
        i.putExtra(FeedActivity.EXTRA_FEED_ID, id);
        startActivity(i);
    }
}
