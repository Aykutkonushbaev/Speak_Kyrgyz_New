package com.example.shopping_list;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.example.shopping_list.dialogs.ListNewDialogFragment;

import java.util.List;

public class MainActivity extends Activity implements ListNewDialogFragment.ListNewDialogListener {

    private ShoppingDataSource dataSource;
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        listView = (ListView) findViewById(R.id.listViewGroups);

        dataSource = new ShoppingDataSource(this);
        dataSource.open();

        List<Group> groups = dataSource.getAllGroups();

        final ArrayAdapter<Group> adapter = new ArrayAdapter<Group>(this, android.R.layout.simple_list_item_1, groups);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Intent intent = new Intent(getApplicationContext(), ItemsActivity.class);
                Group group = (Group) adapterView.getItemAtPosition(position);
                intent.putExtra(DatabaseHelper.COLUMN_GROUP_ID, group.getId());
                intent.setAction(intent.ACTION_VIEW);
                startActivity(intent);
            }
        });


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.list_new:
                ShowListNewDialog();
                break;
            default:
                return super.onOptionsItemSelected(item);

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDialogPositiveClick(String item) {

        ArrayAdapter<Group> adapter = (ArrayAdapter<Group>) listView.getAdapter();

        Group group = dataSource.createGroup(item);

        adapter.add(group);
        adapter.notifyDataSetChanged();

    }

    private void ShowListNewDialog() {
        ListNewDialogFragment listNewDialogFragment = new ListNewDialogFragment();
        listNewDialogFragment.show(getFragmentManager(), "list_new");
    }
}
