package com.karimsabitov.headmanlog.schedule.fragment_activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.karimsabitov.headmanlog.R;

/**
 * Created by User on 23.02.2019.
 */

public class EditDialog extends DialogFragment {

    private static final String TITLE_KEY = "title_key";
    public static final String EXTRA_NEW_VALUE = "com.karimsabitov.headmanlog.extra_new_value";
    public static final String EXTRA_OLD_VALUE = "com.karimsabitov.headmanlog.extra_old_value";
    private static final String OLD_VALUE_KEY = "OLD_VALUE_KEY";

    public static EditDialog newInstance(String room,String title) {

        Bundle args = new Bundle();
        args.putSerializable(TITLE_KEY, title);
        args.putSerializable(OLD_VALUE_KEY, room);
        EditDialog fragment = new EditDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString(TITLE_KEY);
        final String oldValue = getArguments().getString(OLD_VALUE_KEY);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.simple_edit_text, null);
        final EditText editText = view.findViewById(R.id.editText);
        editText.setText(oldValue);
        return new AlertDialog.Builder(getContext())
                .setView(view)
                .setTitle(title)
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!TextUtils.isEmpty(editText.getText().toString())) {
                            sendResult(Activity.RESULT_OK,  editText.getText().toString(), oldValue);
                        }
                    }
                })
                .create();

    }

    private void sendResult(int resultOk, String s, String old_value) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_NEW_VALUE, s);
        intent.putExtra(EXTRA_OLD_VALUE, old_value);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultOk, intent);
    }
}
