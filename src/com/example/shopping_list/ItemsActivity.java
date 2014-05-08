package com.example.shopping_list;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

import java.util.List;


public class ItemsActivity extends Activity {

    private ShoppingDataSource dataSource;
    private ListView listViewItems;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.items);

        dataSource = new ShoppingDataSource(this);
        dataSource.open();
        final Intent intent = getIntent();
        final long id = intent.getLongExtra(DatabaseHelper.COLUMN_GROUP_ID, 0);
        List<ItemsModel> itemsModels = dataSource.getItemsByGroup(id);

        listViewItems = (ListView) findViewById(R.id.listViewItems);

        final ArrayAdapter<ItemsModel> adapter = new ArrayAdapter<ItemsModel>(this, android.R.layout.simple_list_item_1, itemsModels);
        listViewItems.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        dataSource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        dataSource.close();
        super.onPause();
    }

}