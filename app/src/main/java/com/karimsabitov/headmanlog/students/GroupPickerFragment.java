package com.karimsabitov.headmanlog.students;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.karimsabitov.headmanlog.R;

/**
 * Created by User on 08.03.2018.
 */

public class GroupPickerFragment extends DialogFragment {

    private static final String ARG_GROUP = "GROUP";

    public static final String EXTRA_GROUP_NAME = "com.knl.karim.praepostor.group_name";

    private EditText mGroupName_TV;

    public static GroupPickerFragment newInstance(String group_name){
        Bundle args = new Bundle();
        args.putSerializable(ARG_GROUP, group_name);

        GroupPickerFragment fragment = new GroupPickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.group_picker, null);
        final String group_name = (String) getArguments().getSerializable(ARG_GROUP);
        mGroupName_TV = (EditText) view.findViewById(R.id.group_name_et_dialog);
        mGroupName_TV.setText(group_name);
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("Введите название Вашей группы")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendResult(Activity.RESULT_OK, mGroupName_TV.getText().toString());
                    }
                })
                .create();
    }

    private void sendResult(int resultCode, String groupName){
        if (getTargetFragment() == null){
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_GROUP_NAME, groupName);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
