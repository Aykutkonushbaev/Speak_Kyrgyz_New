package com.example.shopping_list;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_GROUP = "groups";
    public static final String TABLE_ITEMS = "items";

    public static final String COLUMN_ID = "_id";

    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_TRANSLATION = "translation";
    public static final String COLUMN_TRANSCRIPT = "transcript";
    public static final String COLUMN_AUDIO = "audio";

    public static final String COLUMN_GROUP_ID = "group_id";

    private static final String DATABASE_NAME = "education";

    private static final int DATABASE_VERSION = 10;


    private static final String TABLE_CREATE_GROUP = "create table "
            + TABLE_GROUP + "(" + COLUMN_ID
            + " integer primary key autoincrement, "
            + COLUMN_NAME
            + " text not null);";

    private static final String TABLE_CREATE_ITEM = "create table "
            + TABLE_ITEMS + "(" + COLUMN_ID
            + " integer primary key autoincrement, "
            + COLUMN_GROUP_ID +
            " integer not null, "
            + COLUMN_TRANSLATION
            + " text, "
            + COLUMN_TRANSCRIPT
            + " text, "
            + COLUMN_AUDIO
            + " text, "
            + COLUMN_NAME
            + " text not null);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_CREATE_GROUP);
        sqLiteDatabase.execSQL(TABLE_CREATE_ITEM);

        String str[] = new String[]{"Greeting","Transport"};
        ItemsModel itemsModel[][] = new ItemsModel[2][2];

        itemsModel[0][0] = new ItemsModel("Hello","Салам","Salam","salam");
        itemsModel[0][1] = new ItemsModel("I","Мен","Men","men");

        itemsModel[1][0] = new ItemsModel("Helloo","Салам","Salam","salam");
        itemsModel[1][1] = new ItemsModel("Io","Мен","Men","men");

        for(int i =0; i<2;i++){

            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_NAME,str[i]);
            long group_id = sqLiteDatabase.insert(DatabaseHelper.TABLE_GROUP,null,values);

            for(int j = 0 ; j<2; j++){
                ContentValues values1 = new ContentValues();

                values1.put(DatabaseHelper.COLUMN_NAME,itemsModel[i][j].getName());
                values1.put(DatabaseHelper.COLUMN_TRANSLATION,itemsModel[i][j].getTranslation());
                values1.put(DatabaseHelper.COLUMN_TRANSCRIPT,itemsModel[i][j].getTranscript());
                values1.put(DatabaseHelper.COLUMN_AUDIO,itemsModel[i][j].getAudio());
                values1.put(DatabaseHelper.COLUMN_GROUP_ID,group_id);

                sqLiteDatabase.insert(DatabaseHelper.TABLE_ITEMS,null,values1);
            }

        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUP);
        onCreate(sqLiteDatabase);

    }
}
