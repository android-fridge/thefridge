package fdi.ucm.thefridge.data;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
/**
 * Created by mizadri on 23/06/2016.
 */
public class DatabaseOpenHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "thefridge.db";
    private static final int DATABASE_VERSION = 6;

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

}
