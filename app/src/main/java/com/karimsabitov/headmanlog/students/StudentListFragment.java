package com.karimsabitov.headmanlog.students;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.karimsabitov.headmanlog.UI.CustomRecyclerViewScrollListener;
import com.karimsabitov.headmanlog.R;
import com.karimsabitov.headmanlog.Utils.Utils;

import java.util.List;
import java.util.UUID;

/**
 * Created by User on 01.02.2018.
 */

public class StudentListFragment extends Fragment {

    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";
    private static final int REQUEST_GROUP_NAME = 0;
    public static final int REQUEST_IS_CORRECT = 1;
    public static final String SERIAL_IS_CORRECT = "is_correct";
    public static final String SERIAL_STUD_TO_DEL = "student_to_delete";
    public static final String KEY = "StudentList_Fragment";

    private RecyclerView mStudentRecyclerView;
    private StudentAdapter mStudentAdapter;
    private FloatingActionButton fab;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_list, container, false);
        mStudentRecyclerView = (RecyclerView) view.findViewById(R.id.student_recycler_view);
        mStudentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        fab = (FloatingActionButton) view.findViewById(R.id.student_list_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student student = new Student();
                StudentGroup.get(getActivity()).addStudent(student);
                Intent intent = StudentViewPager.newIntent(getActivity(), student.getId());
                startActivityForResult(intent, REQUEST_IS_CORRECT);
            }
        });

        //region СЛУШАТЕЛЬ НА СКРОЛ RECYCLERVIEW
        CustomRecyclerViewScrollListener mStudentRecyclerViewScrollListener = new CustomRecyclerViewScrollListener() {
            @Override
            public void show() {
                fab.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
            }

            @Override
            public void hide() {
                CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
                int fabMargin = params.bottomMargin;
                fab.animate().translationY(fab.getHeight() + fabMargin).setInterpolator(new DecelerateInterpolator(2)).start();
            }
        };

        mStudentRecyclerView.addOnScrollListener(mStudentRecyclerViewScrollListener);
        //endregion

        updateUI();

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)  {
        if (resultCode != Activity.RESULT_OK){
            return;
        }
        if (requestCode == REQUEST_GROUP_NAME){
            String group_name = (String) data.getSerializableExtra(GroupPickerFragment.EXTRA_GROUP_NAME);
            StudentGroup.get(getActivity()).setGroup(group_name);
        }
        if (requestCode == REQUEST_IS_CORRECT){
            if (!((boolean) data.getSerializableExtra(SERIAL_IS_CORRECT))){
                UUID stud_to_del = (UUID) data.getSerializableExtra(SERIAL_STUD_TO_DEL);
                StudentGroup studentGroup = StudentGroup.get(getContext());
                studentGroup.removeStudent(studentGroup.getStudent(stud_to_del));
                Toast.makeText(getContext(), "Введены пустые данные. Студент удален", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI(){
        StudentGroup studentGroup = StudentGroup.get(getActivity());
        List<Student> students = studentGroup.getStudents();
        if (mStudentAdapter == null) {
            mStudentAdapter = new StudentAdapter(students);
            mStudentRecyclerView.setAdapter(mStudentAdapter);
        }else{
            mStudentAdapter.setStudents(students);
            mStudentAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_student_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_pick_group:
                getActivity().invalidateOptionsMenu();
                FragmentManager manager = getFragmentManager();
                GroupPickerFragment dialog = GroupPickerFragment.newInstance(StudentGroup.get(getActivity()).getGroup());
                dialog.setTargetFragment(StudentListFragment.this, REQUEST_GROUP_NAME);
                dialog.show(manager, "DIALOG_GROUP");
                return true;
            default:
            return super.onOptionsItemSelected(item);
        }
    }

    // region АДАПТЕР
    private class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentHolder>  {

        // region Методы Адаптера
        private List<Student> mStudents;

        public StudentAdapter(List<Student> students){
            mStudents = students;

        }

        @Override
        public StudentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_item_student, parent, false);
            view.setOnLongClickListener(mLongClickListener);
            return new StudentHolder(view);
        }

        @Override
        public void onBindViewHolder(StudentHolder holder, int position) {
            Student student = mStudents.get(position);
            holder.bindStudent(student, position);
        }

        @Override
        public int getItemCount() {
            return mStudents.size();
        }

        public void setStudents(List<Student> students) {
            mStudents = students;
        }

        private void deleteItem(View view){
            int pos = mStudentRecyclerView.getChildAdapterPosition(view);
            if (pos != RecyclerView.NO_POSITION) {
                StudentGroup.get(getContext()).removeStudent(mStudents.get(pos));
                mStudents.remove(pos);
                notifyItemRemoved(pos);
            }
        }
        //endregion

        //region СЛУШАТЕЛЬ LONG_CLICK
        private View.OnLongClickListener mLongClickListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                PopupMenu popupMenu = new PopupMenu(getContext(), v);
                popupMenu.getMenuInflater().inflate(R.menu.students_list_popup, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        deleteItem(v);
                        return false;
                    }
                });
                popupMenu.show();
                return false;
            }
        };
        //endregion

        // region HOLDER
        class StudentHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            private TextView mNameTextView;
            private TextView mPhoneTextView;
            public ImageView mIconText;

            private Student mStudent;

            public void bindStudent(Student student, int position){
                mStudent = student;
                if (mStudent.getName().isEmpty()) {
                    mStudent.setName("Новый студент");
                }
                mNameTextView.setText(mStudent.getName());
                String StudentPhone = mStudent.getPhone();
                mPhoneTextView.setText("Телефон: " + (StudentPhone.equals("") ? "Неизвестно" : StudentPhone));

                TextDrawable myDrawable = Utils.roundTextIcon(student.getName().substring(0, 1));

                mIconText.setImageDrawable(myDrawable);

            }

            public StudentHolder(final View itemView){
                super(itemView);
                itemView.setOnClickListener(this);
                mNameTextView = (TextView) itemView.findViewById(R.id.list_item_student_name_tv);
                mPhoneTextView = (TextView) itemView.findViewById(R.id.list_item_student_phone_number);
                mIconText = itemView.findViewById(R.id.list_item_image_student);
            }

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.cardview_student_item:
                        Intent intent = StudentViewPager.newIntent(getActivity(), mStudent.getId());
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        }
        // endregion HOLDER
    }

    //endregion
}
