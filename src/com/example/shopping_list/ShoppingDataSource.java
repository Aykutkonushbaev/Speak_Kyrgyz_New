package com.example.shopping_list;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ShoppingDataSource {

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private String[] allColumns = {DatabaseHelper.COLUMN_ID,
            DatabaseHelper.COLUMN_NAME};

    private String[] allColumnsItem = {
            DatabaseHelper.COLUMN_ID,
            DatabaseHelper.COLUMN_NAME,
            DatabaseHelper.COLUMN_TRANSLATION,
            DatabaseHelper.COLUMN_TRANSCRIPT,
            DatabaseHelper.COLUMN_AUDIO,

    };

    public ShoppingDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Group createGroup(String name) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, name);
        long insertId = database.insert(DatabaseHelper.TABLE_GROUP, null,
                values);
        Cursor cursor = database.query(DatabaseHelper.TABLE_GROUP,
                allColumns, DatabaseHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Group newGroup = cursorToGroup(cursor);
        cursor.close();
        return newGroup;
    }

    public ItemsModel createItem(String name, long groupId) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_GROUP_ID, groupId);
        values.put(DatabaseHelper.COLUMN_NAME, name);
        values.put(DatabaseHelper.COLUMN_TRANSLATION, "");
        values.put(DatabaseHelper.COLUMN_AUDIO, "");
        values.put(DatabaseHelper.COLUMN_TRANSCRIPT, "");

        long insertId = database.insert(DatabaseHelper.TABLE_ITEMS, null, values);
        Cursor cursor = database.query(DatabaseHelper.TABLE_ITEMS, allColumnsItem, DatabaseHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        ItemsModel itemsModel = cursorToItem(cursor);
        cursor.close();
        return itemsModel;
    }

    public void deleteGroup(Group group) {
        long id = group.getId();
        System.out.println("Group deleted with id: " + id);
        database.delete(DatabaseHelper.TABLE_GROUP, DatabaseHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Group> getAllGroups() {
        List<Group> groups = new ArrayList<Group>();

        Cursor cursor = database.query(DatabaseHelper.TABLE_GROUP,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Group comment = cursorToGroup(cursor);
            groups.add(comment);
            cursor.moveToNext();
        }

        cursor.close();
        return groups;
    }

    public List<ItemsModel> getItemsByGroup(long group_id) {
        List<ItemsModel> itemsModels = new ArrayList<ItemsModel>();

        Cursor cursor = database.query(DatabaseHelper.TABLE_ITEMS, allColumnsItem, "group_id = ?", new String[]{Long.toString(group_id)}, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ItemsModel itemsModel = cursorToItem(cursor);
            itemsModels.add(itemsModel);
            cursor.moveToNext();
        }

        return itemsModels;
    }

    private Group cursorToGroup(Cursor cursor) {
        Group group = new Group();
        group.setId(cursor.getLong(0));
        group.setName(cursor.getString(1));
        return group;
    }

    private ItemsModel cursorToItem(Cursor cursor) {
        ItemsModel itemsModel = new ItemsModel();
        itemsModel.setId(cursor.getLong(0));
        itemsModel.setName(cursor.getString(1));
        itemsModel.setTranslation(cursor.getString(2));
        itemsModel.setTranscript(cursor.getString(3));
        itemsModel.setAudio(cursor.getString(4));
        return itemsModel;
    }

    public void updateItem(ItemsModel itemsModel) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(DatabaseHelper.COLUMN_NAME, itemsModel.getName());
        contentValues.put(DatabaseHelper.COLUMN_TRANSLATION, itemsModel.getTranslation());
        contentValues.put(DatabaseHelper.COLUMN_AUDIO, itemsModel.getAudio());
        contentValues.put(DatabaseHelper.COLUMN_TRANSCRIPT, itemsModel.getTranscript());
        database.update(DatabaseHelper.TABLE_ITEMS, contentValues, "_id = ?", new String[]{Long.toString(itemsModel.getId())});
    }
}
