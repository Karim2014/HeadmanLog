package com.karimsabitov.headmanlog.students;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.karimsabitov.headmanlog.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Класс для фрагмента для редактирования студента
 */

public class StudentFragment extends Fragment {

    public static final String ARGS_STUDENTS_ID = "student_id";
    public static final String DIALOG_DATE = "dialog_date";
    private static final int REQUEST_DATE = 0;

    private Student mStudent;

    private boolean isCorrect;

    private EditText mBirthdayBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        UUID student_id = (UUID) getArguments().getSerializable(ARGS_STUDENTS_ID);

        mStudent = StudentGroup.get(getActivity()).getStudent(student_id);


    }

    @Override
    public void onPause() {
        super.onPause();

        StudentGroup.get(getActivity())
                .updateStudent(mStudent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_student, container, false);

        // region Находим поля ввода
        final EditText mNameField = (EditText) v.findViewById(R.id.student_name);
        final EditText mPhoneField = (EditText) v.findViewById(R.id.student_phone);
        Spinner mMarks = (Spinner) v.findViewById(R.id.fragment_student_spinner);
        mBirthdayBtn = v.findViewById(R.id.btn_birthday);
        //endregion

        //region Адаптер к спиннеру
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.marks, android.R.layout.simple_spinner_dropdown_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mMarks.setAdapter(adapter);
        //endregion

        //region  установка текста
        mNameField.setText(mStudent.getName());
        mPhoneField.setText(mStudent.getPhone());

        checkChar(mNameField);

        getActivity().setTitle("Студент");
        //endregion

        // region слушатели
        mNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {  }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkChar(mNameField);
                mStudent.setName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {  }
        });

        if (mNameField.toString().isEmpty())
            isCorrect = false;

        mPhoneField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mStudent.setPhone(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mMarks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mStudent.setMarkId(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mBirthdayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerDialogFragment dialog = DatePickerDialogFragment.newInstance(mStudent.getBirthday());
                dialog.setTargetFragment(StudentFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        //endregion

        mMarks.setSelection(mStudent.getMarkId());
        updateBirthday();

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_DATE){
            Date date = (Date) data.getSerializableExtra(DatePickerDialogFragment.EXTRA_DATE);
            mStudent.setBirthday(date);
            updateBirthday();
        }
    }

    private void updateBirthday() {
        SimpleDateFormat sdf = new SimpleDateFormat( "dd MMMM yyyy");
        Date birthday = mStudent.getBirthday();
        if (birthday.getTime() == 0) {
            mBirthdayBtn.setText("Изменить");
        }else
            mBirthdayBtn.setText(sdf.format(birthday));
    }

    private void checkChar(EditText editText){
        if (editText.getText().toString().equals("")) {
            isCorrect = false;
        }else {
            isCorrect = true;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_student, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_item_accept_student:
                Intent intent = new Intent();
                intent.putExtra(StudentListFragment.SERIAL_IS_CORRECT, isCorrect);
                intent.putExtra(StudentListFragment.SERIAL_STUD_TO_DEL, mStudent.getId());
                getActivity().setResult(Activity.RESULT_OK, intent);
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static StudentFragment newInstance(UUID student_id){
        Bundle args = new Bundle();
        args.putSerializable(ARGS_STUDENTS_ID, student_id);

        StudentFragment fragment = new StudentFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
