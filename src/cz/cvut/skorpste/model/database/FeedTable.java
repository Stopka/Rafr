package cz.cvut.skorpste.model.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by stopka on 31.3.14.
 */
public class FeedTable {

    public static final String ID = "_id";

    public static final String URL = "url";

    public static final String TABLE_NAME = "feed";

    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME
            + "("
            + ID + " integer primary key autoincrement, "
            + URL + " text unique"
            + ");";

    public static void onCreate(SQLiteDatabase db) {

        db.execSQL(DATABASE_CREATE);
        db.execSQL("insert into feed (url) values ('http://servis.idnes.cz/rss.aspx?c=zpravodaj');");
        db.execSQL("insert into feed (url) values ('http://www.ceskatelevize.cz/ct24/rss/vsechny-zpravy/');");
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
