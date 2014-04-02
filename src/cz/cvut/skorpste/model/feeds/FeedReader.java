package cz.cvut.skorpste.model.feeds;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndFeed;
import com.google.code.rome.android.repackaged.com.sun.syndication.fetcher.FeedFetcher;
import com.google.code.rome.android.repackaged.com.sun.syndication.fetcher.FetcherException;
import com.google.code.rome.android.repackaged.com.sun.syndication.fetcher.impl.HttpURLFeedFetcher;
import com.google.code.rome.android.repackaged.com.sun.syndication.io.FeedException;
import cz.cvut.skorpste.R;
import cz.cvut.skorpste.model.database.ArticleContentProvider;
import cz.cvut.skorpste.model.database.ArticleTable;
import cz.cvut.skorpste.model.database.FeedTable;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by stopka on 1.4.14.
 */
public class FeedReader extends AsyncTask<Object,Integer,List> {
    Context context;

    StatusListener listener;

    public FeedReader(Context context,StatusListener listener){
       this.context=context;
       this.listener=listener;
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





    @Override
    protected List doInBackground(Object... params) {
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
        return null;
    }

    @Override
    protected void onPreExecute() {
        listener.onRefreshStart();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(List list) {
        listener.onRefreshFinished();
        super.onPostExecute(list);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        listener.onRefreshUpdate(values[0]);
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
        Cursor cursor= context.getContentResolver().query(ArticleContentProvider.FEED_URI,null,null,null,null);
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
            context.getContentResolver().insert(ArticleContentProvider.ARTICLE_URI,values);
        }
    }
    public static interface StatusListener {
        public void onRefreshStart();

        public void onRefreshFinished();

        public void onRefreshUpdate(int value);
    }
}
