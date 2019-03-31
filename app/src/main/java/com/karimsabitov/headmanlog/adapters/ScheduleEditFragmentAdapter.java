package com.karimsabitov.headmanlog.adapters;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.expansionpanel.ExpansionHeader;
import com.github.florent37.expansionpanel.ExpansionLayout;
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection;
import com.karimsabitov.headmanlog.R;
import com.karimsabitov.headmanlog.Utils.Constants;
import com.karimsabitov.headmanlog.Utils.Utils;
import com.karimsabitov.headmanlog.schedule.models.Schedule;
import com.karimsabitov.headmanlog.schedule.models.ScheduleSingle;

import java.util.List;

/**
 * Created by User on 18.10.2018.
 */

public class ScheduleEditFragmentAdapter extends RecyclerView.Adapter<ScheduleEditFragmentAdapter.CoupleTitleViewHolder> {

    /*private boolean mTeacherWithRoom = true;

    private List<Couple> mCouples;
   *//**//* private final List<Teacher> mTeachers;
    private final List<Subject> mSubjects;*//**//*

*/
    private boolean mIsNumericWeek = true;
    private ScheduleSingle mSingle;
    private int mDayNum;
    private Context mContext;
    private final ExpansionLayoutCollection mExpansionLayoutCollection = new ExpansionLayoutCollection();
    private List<Schedule> mSchedulesUp;
    private List<Schedule> mSchedulesDown;

    public ScheduleEditFragmentAdapter(Context context, int dayNum, List<Schedule> scheduleUp, List<Schedule> scheduleDown) {
        mContext = context;
        mDayNum = dayNum;
        mExpansionLayoutCollection.openOnlyOne(true);
        mSingle = ScheduleSingle.get(mContext);
        mSchedulesUp = scheduleUp;
        mSchedulesDown = scheduleDown;

        //mTeacherWithRoom = Utils.prefReadBool(mContext, Constants.APP_NAME, Constants.ROOM_WITH_TEACHER, true);
        mIsNumericWeek = Utils.prefReadBool(mContext, Constants.APP_NAME, Constants.NUMERIC_WEEK, true);

        /*mSubjects = mSingle.getSubjects();
        mTeachers = mSingle.getTeachers();*//**/
    }

    @Override
    public int getItemCount() {
        return mSchedulesUp.size();
    }

    @Override
    public void onBindViewHolder(@NonNull CoupleTitleViewHolder holder, int position) {
        holder.bindCouple(position, mSchedulesUp.get(position), mSchedulesDown.get(position));//schedule, position);

        mExpansionLayoutCollection.add(holder.getExpansionLayout());
    }

    @NonNull
    @Override
    public CoupleTitleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_expandable_edit_schedule, parent, false);
        return new CoupleTitleViewHolder(view);
    }

    public void addSchedule(Schedule schUp, Schedule schDown) {
        mSchedulesUp.add(schUp);
        mSchedulesDown.add(schDown);
    }

    public void removeSchedule(int index) {
        mSchedulesUp.remove(index);
        mSchedulesDown.remove(index);
    }

    class CoupleTitleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ExpansionLayout mExpansionLayout;
        ExpansionLayout mExpansionLayout2;
        TextView mCoupleTitle;
        ImageView mArrow;
        ExpansionHeader mHeader;
        TextView mTeacher;
        TextView mSubject;
        Button mSave;
        TextView mRoom;
        TextView mTeacherDown;
        TextView mSubjectDown;
        TextView mRoomDown;
        CheckBox mDownCB;
        LinearLayout mLL_down;
        String mEmptySchStr;

        //private Couple mCouple;
        private Schedule mScheduleDown;
        private Schedule mScheduleUp;
        int mPosition;

        int mTeacherPos, mSubjectPos, mTeacherPosDown, mSubjectPosDown = -1;


        public CoupleTitleViewHolder(View itemView) {
            super(itemView);
            mHeader = itemView.findViewById(R.id.list_item_expandable_edit_schedule_expansion_header);
            mCoupleTitle = itemView.findViewById(R.id.list_item_expandeble_edit_schedule_title);
            mArrow = itemView.findViewById(R.id.list_item_expandable_edit_schedule_arrow);
            mExpansionLayout = itemView.findViewById(R.id.list_item_expandable_edit_schedule_expansion_lay);
            mExpansionLayout2 = itemView.findViewById(R.id.list_item_expandable_edit_schedule_expanded_exp_lay);

            mTeacher = itemView.findViewById(R.id.list_item_expandeble_edit_schedule_expanded_teacher);
            mSubject = itemView.findViewById(R.id.list_item_expandeble_edit_schedule_expanded_title);
            mRoom = itemView.findViewById(R.id.list_item_expandable_edit_schedule_expanded_room);

            mTeacherDown = itemView.findViewById(R.id.list_item_expandeble_edit_schedule_expanded_teacher_down);
            mSubjectDown = itemView.findViewById(R.id.list_item_expandeble_edit_schedule_expanded_title_down);
            mRoomDown = itemView.findViewById(R.id.list_item_expandable_edit_schedule_expanded_room_down);

            mDownCB = itemView.findViewById(R.id.list_item_expandable_edit_schedule_checkbox_down);
            mLL_down = itemView.findViewById(R.id.list_item_expandable_edit_schedule_lin_lay_down);
            mSave = itemView.findViewById(R.id.list_item_expandable_edit_schedule_save_btn);

            mEmptySchStr = mContext.getString(R.string.empty_couple);
        }

        private void hideViews(View...views) {
            for (View view :
                    views) {
                view.setVisibility(View.GONE);
            }
        }

        private void showViews(View...views) {
            for (View view:
                 views) {
                view.setVisibility(View.VISIBLE);
            }
        }

        public void bindCouple(int position, Schedule scheduleUp, Schedule scheduleDown) {
            mPosition = position;

            mScheduleDown = scheduleDown;
            mScheduleUp = scheduleUp;

            mCoupleTitle.setText(mContext.getString(R.string.title_couple_num, position + 1));
            mExpansionLayout.collapse(false);
            mHeader.setHeaderRotationExpanded(180);
            mHeader.setExpansionLayout(mExpansionLayout);
            mHeader.setExpansionHeaderIndicator(mArrow);

            updateUpWeek(scheduleUp);

            mTeacherDown.setText(scheduleDown.getTeacher());
            mSubjectDown.setText(scheduleDown.getSubject());
            mRoomDown.setText(scheduleDown.getRoomOptional());

            mTeacher.setOnClickListener(this);
            mSubject.setOnClickListener(this);
            mRoom.setOnClickListener(this);
            mSave.setOnClickListener(this);

            setupNumericWeek(scheduleDown);
        }

        private void updateUpWeek(Schedule schedule) {
            if (!schedule.isEmpty()) {
                mTeacher.setText(schedule.getTeacher());
                if (!TextUtils.isEmpty(schedule.getSubject())) {
                    mSubject.setText(schedule.getSubject() + " (" + (schedule.getTOC() ? "Лек.)" : "Практ.)"));
                }else{
                    mSubject.setText("");
                }
                mRoom.setText(schedule.getRoomOptional());
                showViews(mTeacher, mRoom);
            }else{
                mSubject.setText(mEmptySchStr);
                hideViews(mTeacher, mRoom);
            }
        }

        private void updateDownWeek(Schedule schedule) {
            if (!schedule.isEmpty()) {
                mTeacherDown.setText(schedule.getTeacher());
                if (!TextUtils.isEmpty(schedule.getSubject())) {
                    mSubjectDown.setText(schedule.getSubject() + " (" + (schedule.getTOC() ? "Лек.)" : "Практ.)"));
                }else{
                    mSubjectDown.setText("");
                }
                mRoomDown.setText(schedule.getRoomOptional());
                showViews(mTeacherDown, mRoomDown);
            }else{
                mSubject.setText(mEmptySchStr);
                hideViews(mTeacherDown, mRoomDown);
            }
        }

        private void setupNumericWeek(final Schedule scheduleDown) {
            if (!mIsNumericWeek) {
                mExpansionLayout2.collapse(true);
                hideViews(mDownCB);
            } else {
                Log.d("Test", "setupNumericWeek: " + String.valueOf(scheduleDown.isNumeric() ? 1 : 0));
                mDownCB.setChecked(!scheduleDown.isEmpty());
                if (!scheduleDown.isEmpty()) mExpansionLayout2.expand(true);
                else mExpansionLayout2.collapse(true);

                mTeacherDown.setOnClickListener(this);
                mSubjectDown.setOnClickListener(this);
                mRoomDown.setOnClickListener(this);

                mDownCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        mScheduleDown.setEmpty(!isChecked);
                        if (isChecked) {
                            mExpansionLayout2.expand(true);
                        } else {
                            mExpansionLayout2.collapse(true);
                        }
                    }
                });
            }
        }

        public ExpansionLayout getExpansionLayout() {
            return mExpansionLayout;
        }

        @Override
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.list_item_expandeble_edit_schedule_expanded_teacher :
                case R.id.list_item_expandeble_edit_schedule_expanded_teacher_down :
                    MakeDialog(new IEditTextDialog() {
                        @Override
                        public int getView() {
                            return R.layout.dialog_teacher;
                        }

                        @Override
                        public List<String> getStrings() {
                            return mSingle.getTeachers();
                        }

                        @Override
                        public ListView getListView(View view) {
                            return view.findViewById(R.id.dialog_teacher_list_view);
                        }

                        @Override
                        public EditText getEditText(View view) {
                            return view.findViewById(R.id.dialog_teacher_name);
                        }

                        @Override
                        public void addData(String s) {
                            mSingle.addTeacher(s);
                        }

                        @Override
                        public TextView getHostTextView() {
                            return (TextView) v;
                        }

                        @Override
                        public String getDialogTitle() {
                            return "Выберите преподавателя";
                        }

                        @Override
                        public String getHint() {
                            return "Новый преподаватель";
                        }

                        @Override
                        public void setSchedule(String item) {
                            if (v.getId() ==  R.id.list_item_expandeble_edit_schedule_expanded_teacher ) {
                                mScheduleUp.setTeacher(item);
                            } else {
                                mScheduleDown.setTeacher(item);
                            }
                        }
                    });
                    break;

                case R.id.list_item_expandable_edit_schedule_expanded_room:
                case R.id.list_item_expandable_edit_schedule_expanded_room_down:
                    MakeDialog(new IEditTextDialog() {
                        @Override
                        public int getView() {
                            return R.layout.dialog_teacher;
                        }

                        @Override
                        public List<String> getStrings() {
                            return mSingle.getRooms();
                        }

                        @Override
                        public ListView getListView(View view) {
                            return view.findViewById(R.id.dialog_teacher_list_view);
                        }

                        @Override
                        public EditText getEditText(View view) {
                            return view.findViewById(R.id.dialog_teacher_name);
                        }

                        @Override
                        public void addData(String s) {
                            mSingle.addRoom(s);
                        }

                        @Override
                        public TextView getHostTextView() {
                            return (TextView) v;
                        }

                        @Override
                        public String getDialogTitle() {
                            return "Выберите аудиторию";
                        }

                        @Override
                        public String getHint() {
                            return "Новая аудитория";
                        }

                        @Override
                        public void setSchedule(String item) {
                            if (v.getId() ==  R.id.list_item_expandable_edit_schedule_expanded_room) {
                                mScheduleUp.setRoomOptional(item);
                            } else {
                                mScheduleDown.setRoomOptional(item);
                            }
                        }
                    });
                    break;

                case R.id.list_item_expandeble_edit_schedule_expanded_title:

                    MakeDialog(new IEditTextDialogExt() {
                        @Override
                        public Schedule getSchedule() {
                            return mScheduleUp;
                        }

                        @Override
                        public int getView() {
                            return R.layout.dialog_subject;
                        }

                        @Override
                        public List<String> getStrings() {
                            return mSingle.getSubjects();
                        }

                        @Override
                        public ListView getListView(View view) {
                            return view.findViewById(R.id.dialog_subject_list_view);
                        }

                        @Override
                        public EditText getEditText(View view) {
                            return view.findViewById(R.id.dialog_subject_title);
                        }

                        @Override
                        public void addData(String s) {
                            mSingle.addSubject(s);
                        }

                        @Override
                        public TextView getHostTextView() {
                            return (TextView) v;
                        }

                        @Override
                        public String getDialogTitle() {
                            return "Выберите дисциплину";
                        }

                        @Override
                        public String getHint() {
                            return "Новая дисциплина";
                        }

                        @Override
                        public void setSchedule(String item) {
                            mScheduleUp.setSubject(item);
                        }
                    });
                    break;
                case R.id.list_item_expandeble_edit_schedule_expanded_title_down:
                    MakeDialog(new IEditTextDialogExt() {
                        @Override
                        public Schedule getSchedule() {
                            return mScheduleDown;
                        }

                        @Override
                        public int getView() {
                            return R.layout.dialog_subject;
                        }

                        @Override
                        public List<String> getStrings() {
                            return mSingle.getSubjects();
                        }

                        @Override
                        public ListView getListView(View view) {
                            return view.findViewById(R.id.dialog_subject_list_view);
                        }

                        @Override
                        public EditText getEditText(View view) {
                            return view.findViewById(R.id.dialog_subject_title);
                        }

                        @Override
                        public void addData(String s) {
                            mSingle.addSubject(s);
                        }

                        @Override
                        public TextView getHostTextView() {
                            return (TextView) v;
                        }

                        @Override
                        public String getDialogTitle() {
                            return "Выберите дисциплину";
                        }

                        @Override
                        public String getHint() {
                            return "Новая дисциплина";
                        }

                        @Override
                        public void setSchedule(String item) {
                            mScheduleDown.setSubject(item);
                        }
                    });
                    break;
                case R.id.list_item_expandable_edit_schedule_save_btn:
                    Log.d("Test", "onClick: up " + mScheduleUp.toString());
                    Log.d("Test", "onClick: down " + mScheduleDown.toString());
                    mSingle.updateSchedule(mScheduleUp);
                    mSingle.updateSchedule(mScheduleDown);
                    Toast.makeText(mContext, R.string.changes_apply, Toast.LENGTH_SHORT).show();
                    mExpansionLayout.collapse(true);
                    break;
            }
        }

        private void MakeDialog(final IEditTextDialog iEditTextDialog) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            if (iEditTextDialog != null) {
                View view = LayoutInflater.from(mContext).inflate(iEditTextDialog.getView(), null); //view = LayoutInflater.from(mContext).inflate(R.layout.dialog_teacher, null);
                final ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1);
                adapter.add(mContext.getString(R.string.empty));
                adapter.addAll(iEditTextDialog.getStrings());

                ListView listView = iEditTextDialog.getListView(view);
                listView.setAdapter(adapter);
                //listView.setDivider(null);
                final EditText editText = iEditTextDialog.getEditText(view);
                editText.setHint(iEditTextDialog.getHint());

                Button button = view.findViewById(R.id.dialog_teacher_add_btn);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!TextUtils.isEmpty(editText.getText())) {
                            adapter.add(editText.getText().toString());
                            adapter.notifyDataSetChanged();
                            iEditTextDialog.addData(editText.getText().toString());
                            //mSingle.addTeacher(v.getText().toString());
                            editText.setText("");
                        }
                    }
                });

                editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (!TextUtils.isEmpty(v.getText())) {
                            adapter.add(v.getText().toString());
                            adapter.notifyDataSetChanged();
                            iEditTextDialog.addData(v.getText().toString());
                            //mSingle.addTeacher(v.getText().toString());
                            v.setText("");
                            return false;
                        }
                        return true;
                    }
                });

                builder
                        .setTitle(iEditTextDialog.getDialogTitle())
                        .setView(view)
                        .setPositiveButton(android.R.string.ok, null);

                final AlertDialog dialog = builder.create();

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 0) {
                            iEditTextDialog.setSchedule("");
                            iEditTextDialog.getHostTextView().setText("");
                        } else {
                            iEditTextDialog.setSchedule(adapter.getItem(position));
                            iEditTextDialog.getHostTextView().setText(adapter.getItem(position));
                        }
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        }

        private void MakeDialog(final IEditTextDialogExt iEditTextDialog) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            if (iEditTextDialog != null) {
                View view = LayoutInflater.from(mContext).inflate(iEditTextDialog.getView(), null); //view = LayoutInflater.from(mContext).inflate(R.layout.dialog_teacher, null);
                final ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1);
                adapter.add(mContext.getString(R.string.empty));
                adapter.addAll(iEditTextDialog.getStrings());

                ListView listView = iEditTextDialog.getListView(view);
                listView.setAdapter(adapter);
                //listView.setDivider(null);
                final EditText editText = iEditTextDialog.getEditText(view);
                editText.setHint(iEditTextDialog.getHint());

                Button appendButton = view.findViewById(R.id.dialog_subject_add_button);
                appendButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!TextUtils.isEmpty(editText.getText())) {
                            adapter.add(editText.getText().toString());
                            adapter.notifyDataSetChanged();
                            iEditTextDialog.addData(editText.getText().toString());
                            editText.setText("");
                        }
                    }
                });

                editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (!TextUtils.isEmpty(v.getText())) {
                            adapter.add(v.getText().toString());
                            adapter.notifyDataSetChanged();
                            iEditTextDialog.addData(v.getText().toString());
                            v.setText("");
                            return false;
                        }
                        return true;
                    }
                });


                RadioButton radioButton1 = view.findViewById(R.id.dialog_subject_rb1);
                RadioButton radioButton2 = view.findViewById(R.id.dialog_subject_rb2);
                RadioButton radioButton3 = view.findViewById(R.id.dialog_subject_rb3);

                final Schedule schedule = iEditTextDialog.getSchedule();

                if (schedule.isNumeric()){ // TODO: 23.03.2019 Сделать так чтоб знаменатель тоже мог быть окном
                    radioButton1.setVisibility(View.GONE);
                }

                if (schedule.isEmpty()){
                    radioButton1.setChecked(true);
                }else{
                    if (schedule.getTOC()) {
                        radioButton2.setChecked(true);
                    }else{
                        radioButton3.setChecked(true);
                    }
                }

                builder
                        .setTitle(iEditTextDialog.getDialogTitle())
                        .setView(view)
                        .setNegativeButton(android.R.string.cancel, null);

                final AlertDialog dialog = builder.create();

                RadioGroup radioGroup = view.findViewById(R.id.dialog_subject_radio_group);
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                        switch (checkedId) {
                            case R.id.dialog_subject_rb1:
                                schedule.setEmpty(true);
                                dialog.dismiss();
                                break;
                            case R.id.dialog_subject_rb2:
                                schedule.setEmpty(false);
                                schedule.setTOC(true);
                                break;
                            case R.id.dialog_subject_rb3:
                                schedule.setEmpty(false);
                                schedule.setTOC(false);
                                break;
                        }
                        if (schedule.isNumeric()) {
                            updateDownWeek(schedule);
                        } else {
                            updateUpWeek(schedule);
                        }
                    }
                });

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 0) {
                            iEditTextDialog.setSchedule("");
                        } else {
                            iEditTextDialog.setSchedule(adapter.getItem(position));
                        }
                        schedule.setEmpty(false);
                        if (schedule.isNumeric()) {
                            updateDownWeek(schedule);
                        } else {
                            updateUpWeek(schedule);
                        }
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        }
    }

    /**
     * Created by User on 07.02.2019.
     */

    public interface IEditTextDialog {

        @LayoutRes
        int getView();

        List<String> getStrings();

        ListView getListView(View view);

        EditText getEditText(View view);

        void addData(String s);

        TextView getHostTextView();

        String getDialogTitle();

        String getHint();

        void setSchedule(String item);
    }

    /**
     * Created by User on 07.02.2019.
     */

    public static interface IEditTextDialogExt extends IEditTextDialog {

        Schedule getSchedule();
    }
}
