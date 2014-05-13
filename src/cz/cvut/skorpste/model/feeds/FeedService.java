package cz.cvut.skorpste.model.feeds;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndFeed;
import com.google.code.rome.android.repackaged.com.sun.syndication.fetcher.FeedFetcher;
import com.google.code.rome.android.repackaged.com.sun.syndication.fetcher.FetcherException;
import com.google.code.rome.android.repackaged.com.sun.syndication.fetcher.impl.HttpURLFeedFetcher;
import com.google.code.rome.android.repackaged.com.sun.syndication.io.FeedException;
import cz.cvut.skorpste.model.database.ArticleContentProvider;
import cz.cvut.skorpste.model.database.ArticleTable;
import cz.cvut.skorpste.model.database.FeedTable;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by stopka on 8.4.14.
 */
public class FeedService extends IntentService {

    public static final String BROADCAST ="feed_reader_progress_broadcast";
    public static final int STATE_STARTED =0;
    public static final int STATE_PROGRESS =1;
    public static final int STATE_FINISHED =2;
    public static final String BROADCAST_STATE_VALUE ="state";
    public static final String BROADCAST_PROGRESS_VALUE ="value";


    public FeedService(){
        super("FeedService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("FeedService", "Downloading feeds");
        publishState(STATE_STARTED);
        doInBackground();
        publishState(STATE_FINISHED);
    }

    private SyndFeed getMostRecentNews(final String feedUrl) {
        try {
            return retrieveFeed(feedUrl);
        } catch (Exception e) {
            System.err.println(e);
            return null;
        }
    }

    private SyndFeed retrieveFeed(final String feedUrl) throws IOException, FeedException, FetcherException {
        FeedFetcher feedFetcher = new HttpURLFeedFetcher();
        return feedFetcher.retrieveFeed(new URL(feedUrl));
    }

    protected void doInBackground() {
        List<FeedItem> feeds=getFeedList();
        publishProgress(0);
        int size=feeds.size();
        int position=0;
        for(FeedItem item:feeds){
            SyndFeed feed = getMostRecentNews(item.url);
            if(feed!=null) {
                List<SyndEntry> entries = feed.getEntries();
                storeEntries(entries, item.id);
            }
            position++;
            publishProgress((100*position)/size);
        }
    }


    private class FeedItem{
        int id;
        String url;

        FeedItem(Cursor cursor){
            id=cursor.getInt(cursor.getColumnIndex(FeedTable.ID));
            url=cursor.getString(cursor.getColumnIndex(FeedTable.URL));
        }
    }

    private List<FeedItem> getFeedList(){
        LinkedList<FeedItem> result=new LinkedList<FeedItem>();
        Cursor cursor= getApplicationContext().getContentResolver().query(ArticleContentProvider.FEED_URI,null,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            result.add(new FeedItem(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        return result;
    }

    private void storeEntries(List<SyndEntry> entries,int feed_id){
        for (SyndEntry entry:entries){
            ContentValues values=new ContentValues();
            if(entry.getAuthor()!=null) {
                values.put(ArticleTable.AUTHOR, entry.getAuthor().trim());
            }
            values.put(ArticleTable.TITLE,entry.getTitle().trim());
            values.put(ArticleTable.LINK,entry.getLink().trim());
            values.put(ArticleTable.DATE,entry.getPublishedDate().getTime());
            values.put(ArticleTable.CONTENT,entry.getDescription().getValue().trim());
            values.put(ArticleTable.FEED_ID,feed_id);
            getApplicationContext().getContentResolver().insert(ArticleContentProvider.ARTICLE_URI,values);
        }
    }

    protected void publishProgress(int values) {
        Intent intent = new Intent(BROADCAST);
        intent.putExtra(BROADCAST_STATE_VALUE, STATE_PROGRESS);
        intent.putExtra(BROADCAST_PROGRESS_VALUE, values);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    protected void publishState(int state) {
        Intent intent = new Intent(BROADCAST);
        intent.putExtra(BROADCAST_STATE_VALUE, state);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }
}
