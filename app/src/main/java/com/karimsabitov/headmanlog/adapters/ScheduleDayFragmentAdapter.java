package com.karimsabitov.headmanlog.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.karimsabitov.headmanlog.R;
import com.karimsabitov.headmanlog.Utils.Constants;
import com.karimsabitov.headmanlog.Utils.Utils;
import com.karimsabitov.headmanlog.schedule.models.Schedule;
import com.karimsabitov.headmanlog.schedule.models.ScheduleSingle;

import java.util.List;

/**
 *  Класс адаптера, который выстравивает расписание на основе БД
 */

public class ScheduleDayFragmentAdapter extends RecyclerView.Adapter {

    private final int NO_NUMERIC_TYPE = 0;
    private final int NUMERIC_TYPE = 1;

    private Context mContext;
    private List<Schedule> mSchedulesUp, mSchedulesDown;
    private ScheduleSingle mSingle;
    private String mEmptyCouple;
    private boolean mTeacherWithRoom;
    private boolean mNumericWeek;

    public ScheduleDayFragmentAdapter(Context context, List<Schedule> schedulesUp, List<Schedule> schedulesDown) {
        mContext = context;
        mSchedulesUp = schedulesUp;
        mSchedulesDown = schedulesDown;
        mSingle = ScheduleSingle.get(context);
        mEmptyCouple = mContext.getString(R.string.empty_couple);
        // делится ли на недели
        mNumericWeek = Utils.prefReadBool(mContext, Constants.APP_NAME, Constants.NUMERIC_WEEK, true);
    }

    @Override
    public int getItemViewType(int position) {
        if (mSchedulesDown.get(position).isEmpty()){
            return NO_NUMERIC_TYPE;
        }
        return NUMERIC_TYPE;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view;
        if (viewType == NUMERIC_TYPE) {
            view = inflater.inflate(R.layout.list_item_schedule_couples, parent, false);
            return new ViewHolderNumeric(view);
        } else {
            view = inflater.inflate(R.layout.list_item_schedule_couples_no_numeric, parent, false);
            return new ViewHolderNoNumeric(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderNumeric) {
            ((ViewHolderNumeric) holder).bindCouple(mSchedulesUp.get(position), mSchedulesDown.get(position), position);
        } else if (holder instanceof ViewHolderNoNumeric){
            ((ViewHolderNoNumeric) holder).bindCouple(mSchedulesUp.get(position), position);
        }
    }

    @Override
    public int getItemCount() {
        return mSchedulesUp.size();
    }

    public void setData(SparseArray<List<Schedule>> couples) {
        mSchedulesUp = couples.get(0);
        mSchedulesDown = couples.get(1);
    }

    /*
     * Устанавливает в TV имена преподов и дисциплины с учетом пустых значений
     *
     * @param schedule Объект расписания
     * @param position Номер пары
     * @param views Указ на TV (первый Subject, потом Teacher)
     *//*
    private void setTeacherAndSubj(Schedule schedule, int position, TextView... views){
        if (schedule == null) return;
        Teacher teacher = schedule.getTeacher();
        Subject subject = schedule.getSubject();

        String subjectTitle;
        String teacherUPName = teacher == null ? mEmptyCouple : teacher.getName();

        TextView teacherTV = views[1];
        TextView subjectTV = views[0];

        if (subject == null) { // есди нет предмета
            teacherTV.setVisibility(View.GONE); // скрыть учителя
            subjectTitle = mEmptyCouple; // написать ОКНО
        } else {
            teacherTV.setVisibility(View.VISIBLE);
            subjectTitle = subject.getName();
        }
        subjectTV.setText(mContext.getString(
                R.string.title_couple_n_selfnum,
                position + 1,
                subjectTitle)
        );

        teacherTV.setText(teacherUPName);
    }


     * Устанавливает в TV номер кабинета с учетом незакрепленности за преподм
     *
     * @param schedule Объект расписания
     * @param numericWeek делится ли на недели
     * @param teacherWithRoom закреплен ли за учителем кабинет
     * @param room_tv куда записать номер
     * @param LL Layout Нижней недели
     *
    public void setRooms(Schedule schedule, boolean numericWeek, boolean teacherWithRoom, TextView room_tv, View LL){
        Teacher teacher = schedule.getTeacher();
        String teacherUPRoom = teacher == null ? "" : teacher.getRoom();
        if (numericWeek) { // елси делится на недели - показать нижнюю
            if (schedule.isNumeric()) {
                if (LL != null) {
                    LL.setVisibility(View.VISIBLE);
                }

                String room;
                if (!teacherWithRoom) {
                    room = schedule.getRoomOptional();
                } else {
                    room = teacherUPRoom;
                }
                if (!room.isEmpty()) {
                    room_tv.setText(mContext.getString(
                            R.string.title_room,
                            room
                    ));
                }
            }
        }
    }
*/
    private class ViewHolderNumeric extends RecyclerView.ViewHolder {

        TextView mSubjectTV;
        TextView mTeacherTV;
        TextView mRoomTV;
        TextView mSubjectDownTV;
        TextView mTeacherDownTV;
        TextView mRoomDownTV;
        TextView mCoupleNumTV;
        TextView mTOC_TV;
        TextView mTOC_DownTV;
        View mEmptyCoupleTV;
        View mUpInfoLL;

        public ViewHolderNumeric(View itemView) {
            super(itemView);
            mSubjectTV = itemView.findViewById(R.id.list_item_schedule_couple_subject);
            mTeacherTV = itemView.findViewById(R.id.list_item_schedule_couple_teacher_n_room);
            mRoomTV = itemView.findViewById(R.id.list_item_schedule_couple_room);
            mSubjectDownTV = itemView.findViewById(R.id.list_item_schedule_couple_subject_down);
            mTeacherDownTV = itemView.findViewById(R.id.list_item_schedule_couple_teacher_n_room_down);
            mRoomDownTV = itemView.findViewById(R.id.list_item_schedule_couple_room_down);
            mCoupleNumTV = itemView.findViewById(R.id.list_item_schedule_couple_cnum_tv);
            mEmptyCoupleTV = itemView.findViewById(R.id.list_item_schedule_couple_empty_couple);
            mUpInfoLL = itemView.findViewById(R.id.list_item_schedule_couple_up_info_ll);
            mTOC_TV = itemView.findViewById(R.id.list_item_schedule_couple_toc_up);
            mTOC_DownTV = itemView.findViewById(R.id.list_item_schedule_couple_toc_down);
        }



        public void bindCouple(Schedule scheduleUp, Schedule scheduleDown, int position) {

            mCoupleNumTV.setText(mContext.getString(R.string.just_couple_num, position + 1));

            if (scheduleUp.isEmpty()) {
                mUpInfoLL.setVisibility(View.GONE);
                mEmptyCoupleTV.setVisibility(View.VISIBLE);
            } else {
                mEmptyCoupleTV.setVisibility(View.GONE);
                mUpInfoLL.setVisibility(View.VISIBLE);
                mSubjectTV.setText(scheduleUp.getSubject());
                mTeacherTV.setText(scheduleUp.getTeacher());
                mRoomTV.setText(scheduleUp.getRoomOptional());
                mTOC_TV.setText(scheduleUp.getTOC() ? mContext.getString(R.string.lecture_reducted) : mContext.getString(R.string.pract_reducted));
            }

            mSubjectDownTV.setText(scheduleDown.getSubject());
            mTeacherDownTV.setText(scheduleDown.getTeacher());
            mRoomTV.setText(scheduleDown.getRoomOptional());
            mTOC_DownTV.setText(scheduleDown.getTOC() ? mContext.getString(R.string.lecture_reducted) : mContext.getString(R.string.pract_reducted));

            /*setupTypeFace();

            mCoupleNumTV.setText(mContext.getString(R.string.just_couple_num, position+1));

            Schedule upSch = couple.getUP();
            Schedule downSch = couple.getDOWN();

            if (!upSch.isEmpty()) {
                mTeacherTV.setText(upSch.getTeacher().getName());
                mSubjectTV.setText(upSch.getSubject().getName());
                String room;
                if (!mTeacherWithRoom) {
                    room = upSch.getRoomOptional().toString();
                } else {
                    room = upSch.getTeacher().getRoom().toString();
                }
                if (!room.isEmpty()) {
                    mRoomTV.setText(mContext.getString(
                            R.string.title_room,
                            room
                    ));
                }
            }else{
                mTeacherTV.setText(mEmptyCouple);
                mSubjectTV.setText(mEmptyCouple);
                mRoomTV.setText(mEmptyCouple);
            }

            if (!downSch.isEmpty()) {
                mTeacherDownTV.setText(downSch.getTeacher().getName());
                mSubjectDownTV.setText(downSch.getSubject().getName());
                String room;
                if (!mTeacherWithRoom) {
                    room = downSch.getRoomOptional().toString();
                } else {
                    room = downSch.getTeacher().getRoom().toString();
                }
                if (!room.isEmpty()) {
                    mRoomDownTV.setText(mContext.getString(
                            R.string.title_room,
                            room
                    ));
                }
            }else{
                mTeacherDownTV.setText(mEmptyCouple);
                mSubjectDownTV.setText(mEmptyCouple);
                mRoomDownTV.setText(mEmptyCouple);
            }

            mSepar.setVisibility(couple.isNumeric() ? View.VISIBLE : View.GONE);
            mLL_DownWeek.setVisibility(couple.isNumeric() ? View.VISIBLE : View.GONE);*/
        }
    }

    private class ViewHolderNoNumeric extends RecyclerView.ViewHolder {

        TextView mSubjectTV;
        TextView mTeacherTV;
        TextView mRoomTV;
        TextView mCoupleNumTV;
        TextView mTOC_TV;
        TextView mTOC_DownTV;
        View mEmptyCoupleTV;
        View mInfoLL;

        public ViewHolderNoNumeric(View itemView) {
            super(itemView);
            mSubjectTV = itemView.findViewById(R.id.list_item_schedule_couple_subject);
            mTeacherTV = itemView.findViewById(R.id.list_item_schedule_couple_teacher_n_room);
            mRoomTV = itemView.findViewById(R.id.list_item_schedule_couple_room);
            mCoupleNumTV = itemView.findViewById(R.id.list_item_schedule_couple_cnum_tv);
            mEmptyCoupleTV = itemView.findViewById(R.id.list_item_schedule_couple_empty_couple);
            mInfoLL = itemView.findViewById(R.id.list_item_schedule_couple_info_ll);
            mTOC_TV = itemView.findViewById(R.id.list_item_schedule_couple_toc_up);
            mTOC_DownTV = itemView.findViewById(R.id.list_item_schedule_couple_toc_down);
        }


        public void bindCouple(Schedule schedule, int position) {
            mCoupleNumTV.setText(mContext.getString(R.string.just_couple_num, position + 1));

            if (schedule.isEmpty()) {
                mInfoLL.setVisibility(View.GONE);
                mEmptyCoupleTV.setVisibility(View.VISIBLE);
            } else {
                mEmptyCoupleTV.setVisibility(View.GONE);
                mInfoLL.setVisibility(View.VISIBLE);
                mSubjectTV.setText(schedule.getSubject());
                mTeacherTV.setText(schedule.getTeacher());
                mRoomTV.setText(schedule.getRoomOptional());
                mTOC_TV.setText(schedule.getTOC() ? mContext.getString(R.string.lecture_reducted) : mContext.getString(R.string.pract_reducted));
            }
        }
    }
}
