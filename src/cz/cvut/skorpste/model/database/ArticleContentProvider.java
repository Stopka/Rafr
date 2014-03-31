package cz.cvut.skorpste.model.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * Created by stopka on 31.3.14.
 */
public class ArticleContentProvider extends ContentProvider {
    private DatabaseHelper databaseHelper;

    public static final String AUTHORITY = "cz.cvut.skorpste.rafr";

    private static final int ARTICLE_LIST = 1;
    private static final int ARTICLE_ID = 2;

    private static final int FEED_LIST = 3;
    private static final int FEED_ID = 4;

    private static final String ARTICLE_PATH = "articles";
    private static final String FEED_PATH = "feeds";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" );

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(AUTHORITY, ARTICLE_PATH, ARTICLE_LIST);
        sURIMatcher.addURI(AUTHORITY, ARTICLE_PATH+ "/#", ARTICLE_ID);
        sURIMatcher.addURI(AUTHORITY, FEED_PATH, FEED_LIST);
        sURIMatcher.addURI(AUTHORITY, FEED_PATH+ "/#", FEED_ID);
    }

    @Override
    public boolean onCreate() {
        databaseHelper = new DatabaseHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case ARTICLE_LIST:
                queryBuilder.setTables(ArticleTable.TABLE_NAME);
                break;
            case ARTICLE_ID:
                queryBuilder.setTables(ArticleTable.TABLE_NAME);
                queryBuilder.appendWhere(ArticleTable.ID + "=" + uri.getLastPathSegment());
                break;
            case FEED_LIST:
                queryBuilder.setTables(FeedTable.TABLE_NAME);
                break;
            case FEED_ID:
                queryBuilder.setTables(FeedTable.TABLE_NAME);
                queryBuilder.appendWhere(FeedTable.ID + "=" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = databaseHelper.getWritableDatabase();
        long id = 0;
        switch (uriType) {
            case ARTICLE_LIST:
                id = sqlDB.insert(ArticleTable.TABLE_NAME, null, values);
                break;
            case FEED_LIST:
                id = sqlDB.insert(FeedTable.TABLE_NAME, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        switch (uriType) {
            case ARTICLE_LIST:
                return Uri.parse(ARTICLE_PATH + "/" + id);
            case FEED_LIST:
                return Uri.parse(FEED_PATH + "/" + id);
            default:
                throw new IllegalArgumentException("Missing return");
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = databaseHelper.getWritableDatabase();
        int rowsDeleted = 0;
        String id;
        switch (uriType) {
            case ARTICLE_LIST:
                rowsDeleted = sqlDB.delete(ArticleTable.TABLE_NAME, selection, selectionArgs);
                break;
            case ARTICLE_ID:
                id = uri.getLastPathSegment();
                rowsDeleted = sqlDB.delete(ArticleTable.TABLE_NAME, ArticleTable.ID+ "=" + id, null);
                break;
            case FEED_LIST:
                rowsDeleted = sqlDB.delete(FeedTable.TABLE_NAME, selection, selectionArgs);
                break;
            case FEED_ID:
                id = uri.getLastPathSegment();
                rowsDeleted = sqlDB.delete(FeedTable.TABLE_NAME, FeedTable.ID+ "=" + id, null);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
