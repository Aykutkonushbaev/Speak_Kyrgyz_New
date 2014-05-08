package com.example.shopping_list.dialogs;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import com.example.shopping_list.Group;
import com.example.shopping_list.R;
import com.example.shopping_list.ShoppingDataSource;

public class ListNewDialogFragment extends DialogFragment {

    public interface ListNewDialogListener {
        public void onDialogPositiveClick(String item);
    }

    ListNewDialogListener listNewDialogListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final EditText editText = (EditText) getActivity().findViewById(R.id.editTextGroupNew);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_list_new, null);

        final EditText editTextItem = (EditText) v.findViewById(R.id.editTextGroupNew);

        builder.setView(v);
        builder.setMessage(R.string.list_new_dialog_message);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                listNewDialogListener.onDialogPositiveClick(editTextItem.getText().toString());
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ListNewDialogFragment.this.getDialog().cancel();
            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            listNewDialogListener = (ListNewDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + "must implement ListNewDialogListener");
        }
    }
}
