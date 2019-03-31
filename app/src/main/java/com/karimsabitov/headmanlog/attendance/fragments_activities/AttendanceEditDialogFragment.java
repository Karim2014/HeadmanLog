package com.karimsabitov.headmanlog.attendance.fragments_activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.karimsabitov.headmanlog.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.karimsabitov.headmanlog.Utils.Constants.TAG;

/**
 * Created by User on 14.09.2018.
 */

public class AttendanceEditDialogFragment extends DialogFragment implements DialogInterface.OnMultiChoiceClickListener, DialogInterface.OnClickListener {

    // TODO: 22.12.2018 Сделать так, чтоюы список появлялся в алфавитном порядке

    public static final String ABS_STD_LIST = "UUID_LIST";
    public static final String SELECTED_UUID_LIST = "SELECTED_UUID_LIST";
    public static final String COUPLE_NUM = "COUPLE_NUM";
    private static final String STUDENTS = "STUDENTS_LIST";

    public List<String> mSelectedUUIDs = new ArrayList<>();
    private List<String> mStudents = new ArrayList<>();
    private int mCoupleNum;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        List<String> absStds = (List<String>) getArguments().getSerializable(ABS_STD_LIST); //createFromArray(uuids);
        mStudents = (List<String>) getArguments().getSerializable(STUDENTS);

        mCoupleNum = getArguments().getInt(COUPLE_NUM);

        return new AlertDialog.Builder(getContext())
                .setTitle("Выберите отсутствующих") // TODO: 16.09.2018 сделать кнопку для отметки всех сразу
                .setMultiChoiceItems(getStrings(mStudents), getSelected(mStudents, absStds), this)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(android.R.string.ok, this)
                .create();
    }

    private String[] getStrings(List<String> students) {
        return students.toArray(new String[students.size()]);
    }

    public boolean[] getSelected(List<String> students, List<String> absStds){
        boolean[] chosen = new boolean[students.size()];
        for (int i = 0; i < students.size(); i++) {
            boolean contains = absStds.contains(students.get(i));
            chosen[i] = contains;
            if (contains) {
                mSelectedUUIDs.add(students.get(i));
            }
        }
        return chosen;
    }

    public void sendResult(){
        if (getTargetFragment() == null){
            return;
        }
        Intent intent = new Intent();
        Log.d(TAG, "sendResult: " + String.valueOf(mSelectedUUIDs.size()));
        intent.putExtra(SELECTED_UUID_LIST, (Serializable) mSelectedUUIDs);
        intent.putExtra(COUPLE_NUM, mCoupleNum);
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
    }

    public static AttendanceEditDialogFragment newInstance(List<String> absStdsList, List<String> students, int coupleNum) {
        Bundle args = new Bundle();

        args.putSerializable(ABS_STD_LIST, (Serializable) absStdsList);
        args.putInt(COUPLE_NUM, coupleNum);
        args.putSerializable(STUDENTS, (Serializable) students);

        AttendanceEditDialogFragment fragment = new AttendanceEditDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
        if (isChecked) {
            mSelectedUUIDs.add(mStudents.get(which));

        }else{
            mSelectedUUIDs.remove(mStudents.get(which));

        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        sendResult();
    }
}
