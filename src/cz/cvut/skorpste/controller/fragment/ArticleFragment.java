package cz.cvut.skorpste.controller.fragment;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.*;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import cz.cvut.skorpste.model.Article;
import cz.cvut.skorpste.model.Articles;
import cz.cvut.skorpste.R;
import cz.cvut.skorpste.model.database.ArticleContentProvider;
import cz.cvut.skorpste.model.database.ArticleTable;

import java.util.Date;

/**
 * Created by stopka on 13.3.14.
 */
public class ArticleFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final String ARTICLE = "article";
    public static final String ARTICLE_ID = "article_id";

    private final int ARTICLE_LOADER = 1;

    String shareLink=null;
    String shareTitle=null;

    public ArticleFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getLoaderManager().initLoader(ARTICLE_LOADER, getArguments(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.article_fragment, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.article, menu);
        /*if (shareLink == null) {
            menu.findItem(R.id.menu_article_share).setEnabled(false);
        }*/
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case ARTICLE_LOADER:
                getActivity().setProgressBarIndeterminate(true);
                getActivity().setProgressBarVisibility(true);
                long article_id = args.getLong(ARTICLE_ID);
                CursorLoader cursorLoader = new CursorLoader(getActivity(), ContentUris.withAppendedId(ArticleContentProvider.ARTICLE_URI, article_id), null, null, null, null);
                return cursorLoader;
            default:
                return null;
        }

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case ARTICLE_LOADER:
                data.moveToFirst();
                shareLink=data.getString(data.getColumnIndex(ArticleTable.LINK));
                shareTitle=data.getString(data.getColumnIndex(ArticleTable.TITLE));
                TextView title = (TextView) getView().findViewById(R.id.title);
                title.setText(shareTitle);
                TextView author = (TextView) getView().findViewById(R.id.author);
                author.setText(data.getString(data.getColumnIndex(ArticleTable.AUTHOR)));
                TextView article = (TextView) getView().findViewById(R.id.text);
                article.setText(data.getString(data.getColumnIndex(ArticleTable.CONTENT)));
                TextView date = (TextView) getView().findViewById(R.id.date);
                Date date_value = new Date(data.getLong(data.getColumnIndex(ArticleTable.DATE)));
                date.setText(date_value.toString());

                getActivity().setProgressBarVisibility(false);
                break;
        }


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
            case ARTICLE_LOADER:

                getActivity().setProgressBarVisibility(true);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_article_share:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.share_subject, shareTitle));
                shareIntent.putExtra(Intent.EXTRA_TEXT,getString(R.string.share_text, shareLink));
                Intent chooser = Intent.createChooser(shareIntent, getString(R.string.share_chooser));
                startActivity(chooser);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}