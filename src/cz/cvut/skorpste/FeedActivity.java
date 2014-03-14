package cz.cvut.skorpste;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by stopka on 14.3.14.
 */
public class FeedActivity extends Activity {
    public static final String ARTICLE = "article";


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        Intent i = getIntent();
        String title = i.getStringExtra(ARTICLE);

    }
}