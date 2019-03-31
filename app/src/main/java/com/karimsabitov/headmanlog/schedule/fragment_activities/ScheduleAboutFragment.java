package com.karimsabitov.headmanlog.schedule.fragment_activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.karimsabitov.headmanlog.R;
import com.karimsabitov.headmanlog.Utils.CalendarParser;

import java.util.Date;

/**
 * Created by User on 08.08.2018.
 */

public class ScheduleAboutFragment extends Fragment implements View.OnClickListener {

    public static final int REQUEST_UPDATE = 1;
    public static final int RESULT_TOOLBAR = 12;

    //ScheduleDayFragment[] mScheduleDayFragments = new ScheduleDayFragment[7];

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_schedule_about, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewPager viewPager = getView().findViewById(R.id.fragment_schedule_about_view_pager);
        PagerAdapter pagerAdapter = new PagerAdapter(getChildFragmentManager());
        TabLayout tabLayout = getView().findViewById(R.id.tab_layout_sch_ab);
        TextView textView = getView().findViewById(R.id.fragment_attendance_edit_date_pick_edit_text);

        // TODO: 24.03.2019 textView.setOnClickListener(this);
        textView.setText(getString(R.string.title_couple_num_day_week,
                CalendarParser.formatDate("dd.MM.yyyy", new Date()),
                CalendarParser.isUpWeek() ? "Числитель" : "Знаменатель"));

        viewPager.setAdapter(pagerAdapter);

        int day = CalendarParser.getDayOfWeek() - 2;
        viewPager.setCurrentItem(day);

        tabLayout.setupWithViewPager(viewPager);
    }

    private void updateUI() {
        updateList();
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_schedule_about, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            /*case R.id.menu_item_settings:
                startActivityForResult(new Intent(getContext(), SettingsActivity.class), REQUEST_UPDATE);
                break;*/
            case R.id.menu_item_schedule:
                startActivity(new Intent(getActivity(), BellActivity.class));
                break;
            case R.id.menu_item_lists:
                startActivityForResult(new Intent(getContext(), ScheduleTabViewPager.class), REQUEST_UPDATE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showBellFragment() {
        /*Fragment bellFragment = BellFragment.newInstance(false);
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            fragmentManager.beginTransaction()
                    //.addToBackStack("")
                    .add(R.id.fragment_schedule_about_container, bellFragment)
                    .
                    .commit();
            mViewPager.setVisibility(View.INVISIBLE);
        }*/
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_UPDATE){
            if (resultCode == Activity.RESULT_OK) {
                updateList();
            }
            if (resultCode == RESULT_TOOLBAR){
                updateUI();
            }
        }
    }

    private void updateList() {
        /*ScheduleDayFragment fragment = (ScheduleDayFragment) mPagerAdapter.getItem(mViewPager.getCurrentItem());
        fragment.updateList();*/
    }

    @Override
    public void onClick(View v) {
        // TODO: 24.03.2019 Расширить определние
    }

    private class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return ScheduleDayFragment.newInstance(i);
        }

        @Override
        public int getCount() {
            return 7;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0: return "Пн";
                case 1: return "ВТ";
                case 2: return "СР";
                case 3: return "ЧТ";
                case 4: return "Пт";
                case 5: return "Сб";
                case 6: return "Вс";
                default: return "other";
            }
        }
    }
    /*




    private void updateUI(){

    }*/

/*
    private void updateToolBar(HashMap<Integer, List<Schedule>> hashMap) { // todo по-хорошеому, делать в отдельном потоке
        Calendar calendar = Calendar.getInstance();

        boolean flag = Utils.prefReadBool(getContext(), Constants.APP_NAME, Constants.NUMERIC_WEEK, true);

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy (MMM, E)");
        mWeek.setText(getString(
                R.string.title_couple_num_day_week,
                sdf.format(calendar.getTime()), // TODO: 31.08.2018 Проверочку делать с преф
                flag ? CalendarParser.isUpWeek() ? "\nЧислитель" : "\nЗнаменатель" : ""
        ));

        String[] strings = CalendarParser.getSubjectAndTeacher(getContext(), hashMap);
        mCouple.setText(strings[CalendarParser.INDEX_SUBJECT]);

        if (strings[CalendarParser.INDEX_TEACHER] != null) {
            mTeacherAndRoom.setText(strings[CalendarParser.INDEX_TEACHER]);
        }
    }

    @Override
    public void onClick(View v) {
        */
/*switch (v.getId()){
            case R.id.fab_fragment_schedule_about:
                startActivityForResult(new Intent(getContext(), ScheduleTabViewPager.class), REQUEST_UPDATE);
                break;
        }*//*

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_UPDATE){
            if (resultCode == Activity.RESULT_OK) {
                updateUI();
            }
            if (resultCode == RESULT_TOOLBAR){
                updateUI();
            }
        }
    }

    private class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder>{

        HashMap<Integer, List<Schedule>> mCouples;

        public ScheduleAdapter(HashMap<Integer, List<Schedule>> schedules) {
            mCouples = schedules;
        }

        //region OVERRIDES
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.fragment_schedule_day, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            List<Schedule> schedules = mCouples.get(position);
            holder.bindSchedule(schedules, position);
        }

        @Override
        public int getItemCount() {
            return 5;
        }

        public void setSchedule(HashMap<Integer, List<Schedule>> schedules) {
            mCouples = schedules;
        }

        //endregion

        // ************VIEW HOLDER SCHEDULE *******

        class ViewHolder extends RecyclerView.ViewHolder{

            private static final String DIALOG_DAY_NUM = "DIALOG_DAY_NUM";

            TextView mDayTitle;
            RecyclerView mCouple;
            ImageView mOptions;

            public ViewHolder(View itemView) {
                super(itemView);
                mDayTitle = itemView.findViewById(R.id.list_item_schedule_day_day_title);
                mCouple = itemView.findViewById(R.id.list_item_schedule_day_day_couples);
                mOptions = itemView.findViewById(R.id.list_item_schedule_day_options);
            }

            public void bindSchedule(List<Schedule> schedules, final int position){
                mDayTitle.setText(Schedule.StringDays[position]);
                CouplesAdapter couplesAdapter = new CouplesAdapter(schedules);
                mCouple.setLayoutManager(new LinearLayoutManager(getContext()));
                mCouple.setAdapter(couplesAdapter);
                mOptions.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivityForResult(ScheduleEditActivity.newIntent(getContext(), position), REQUEST_UPDATE);
                    }
                });
            }

            //  *********COUPLES ADAPTER  *******

            class CouplesAdapter extends RecyclerView.Adapter<CouplesAdapter.ViewHolderCouples>{

                List<Schedule> mCouples;

                public CouplesAdapter(List<Schedule> schedules) {
                    mCouples = schedules;
                }

                @NonNull
                @Override
                public ViewHolderCouples onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    LayoutInflater inflater = LayoutInflater.from(getActivity());
                    View view = inflater.inflate(R.layout.list_item_schedule_couples, parent, false);
                    return new ViewHolderCouples(view);
                }

                @Override
                public void onBindViewHolder(@NonNull ViewHolderCouples holder, int position) {
                    Schedule schedule = mCouples.get(position);
                    holder.bindCouple(schedule, position);
                }

                @Override
                public int getItemCount() {
                    return mCouples.size();
                }

                class ViewHolderCouples extends RecyclerView.ViewHolder{

                    TextView mSubject;
                    TextView mTeacher;
                    TextView mRoom;
                    TextView mSubjectDown;
                    TextView mTeacherDown;
                    TextView mRoomDown;
                    LinearLayout mLL_DownWeek;
                    View mView;

                    public ViewHolderCouples(View itemView) {
                        super(itemView);
                        mSubject = itemView.findViewById(R.id.list_item_schedule_couple_subject);
                        mTeacher = itemView.findViewById(R.id.list_item_schedule_couple_teacher_n_room);
                        mRoom = itemView.findViewById(R.id.list_item_schedule_couple_room);
                        mView = itemView.findViewById(R.id.list_item_schedule_couple_view);
                        mSubjectDown = itemView.findViewById(R.id.list_item_schedule_couple_subject_down);
                        mTeacherDown = itemView.findViewById(R.id.list_item_schedule_couple_teacher_n_room_down);
                        mRoomDown = itemView.findViewById(R.id.list_item_schedule_couple_room_down);
                        mLL_DownWeek = itemView.findViewById(R.id.list_item_schedule_couple_lay_down_week);
                    }

                    public void bindCouple(Schedule schedule, int position){
                        mSubject.setText(getString(
                                R.string.title_couple_n_selfnum,
                                position + 1,
                                schedule.getSubject().getName())
                        );

                        mTeacher.setText(schedule.getTeacher().getName());

                        boolean teacherWithRoom = Utils.prefReadBool(getContext(), Constants.APP_NAME, Constants.ROOM_WITH_TEACHER, true);
                        boolean numericWeek = Utils.prefReadBool(getContext(), Constants.APP_NAME, Constants.NUMERIC_WEEK, true);

                        String room;
                        if ( !teacherWithRoom){
                            room = schedule.getRoomOptional();
                        }else {
                            room = schedule.getTeacher().getRoom();
                        }
                        if (!room.isEmpty()){
                            mRoom.setText(getString(
                                    R.string.title_room,
                                    room
                            ));
                        }

                        if (position+1 == mCouples.size() && numericWeek){
                            mView.setVisibility(View.INVISIBLE);
                        }

                        mLL_DownWeek.setVisibility(View.GONE);
                        if (numericWeek){
                            if (schedule.isNumeric()){
                                mLL_DownWeek.setVisibility(View.VISIBLE);
                                mTeacherDown.setText(schedule.getTeacherDown().getName());
                                mSubjectDown.setText(getString(
                                        R.string.title_couple_n_selfnum,
                                        position + 1,
                                        schedule.getSubjectDown().getName()
                                ));
                                if ( !teacherWithRoom){
                                    room = schedule.getRoomOptionalDown();
                                }else {
                                    room = schedule.getTeacherDown().getRoom();
                                }
                                if (!room.isEmpty()){
                                    mRoomDown.setText(getString(
                                            R.string.title_room,
                                            room
                                    ));
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    // endregion

*/


}
