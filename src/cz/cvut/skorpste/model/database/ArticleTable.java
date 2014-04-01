package cz.cvut.skorpste.model.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by stopka on 31.3.14.
 */
public class ArticleTable{

    public static final String ID = "_id";

    public static final String FEED_ID = "feed_id";

    public static final String LINK = "link";

    public static final String TITLE = "title";

    public static final String AUTHOR = "author";

    public static final String CONTENT = "content";

    public static final String DATE = "date";

    public static final String TABLE_NAME = "article";

    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME
            + "("
            + ID + " integer primary key autoincrement, "
            + FEED_ID + " integer,"
            + LINK + " text,"
            + TITLE + " text,"
            + AUTHOR + " text,"
            + CONTENT + " text,"
            + DATE + " integer"
            + ");";

    public static void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropAndCreateTable(db);
    }

    public static void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropAndCreateTable(db);
    }

    public static void dropAndCreateTable(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}
