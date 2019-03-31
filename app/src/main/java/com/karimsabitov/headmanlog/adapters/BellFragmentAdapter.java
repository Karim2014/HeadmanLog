package com.karimsabitov.headmanlog.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.karimsabitov.headmanlog.R;
import com.karimsabitov.headmanlog.schedule.models.Bell;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by User on 10.11.2018.
 */

public class BellFragmentAdapter extends RecyclerView.Adapter<BellFragmentAdapter.ViewHolder>{

    private List<Bell> mBells;
    private Context mContext;

    public BellFragmentAdapter(Context context, List<Bell> bells) {
        mBells = bells;
        mContext = context;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bell bell = mBells.get(position);
        holder.BindBell(bell,  position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list_item_bell, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mBells.size();
    }

    public void setBells(List<Bell> bells) {
        mBells = bells;
    }

    public Bell getBell(int index){
        return mBells.get(index);
    }

    public void removeBell(int index){
        mBells.remove(index);
    }

    public void addBell(Bell bell){
        mBells.add(bell);
    }

    private String formatDate(final Time startTime, Time endTime){
        String formatString = "HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatString);
        return simpleDateFormat.format(startTime) + " - " + simpleDateFormat.format(endTime);
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        Bell mBell;

        TextView mTime;
        TextView mCoupleNum;

        public ViewHolder(View itemView) {
            super(itemView);
            mTime =  itemView.findViewById(R.id.list_item_bell_time_tv);
            mCoupleNum = itemView.findViewById(R.id.item_bell_couple_num);
        }

        void BindBell(Bell bell, final int position){
            mTime.setText(formatDate(bell.getStartTime(), bell.getEndTime()));
            mCoupleNum.setText(mContext.getString(R.string.couple_num_ft, bell.getCoupleNum()+1)); // номер пары
            mBell = bell;

            //CalendarParser.checkBells(mContext, mBell, coupleNum, position);


            // region СЛУШАТЕЛЬ НА КЛИК И РЕГИСТРАЦИЯ ПИКЕРА START_TIME
            /*startTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    Calendar calendar = Calendar.getInstance();
                    if (mBell.getStartTime() != null){
                        calendar.setTime(mBell.getStartTime());
                    }

                    int hour = calendar.get(Calendar.HOUR_OF_DAY);
                    int minute = calendar.get(Calendar.MINUTE);

                    mBellStartTime = mBell.getStartTime();

                    TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(null, hour, minute, true);
                    timePickerDialog.setOnTimeSetListener(new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                            Calendar calendar = Calendar.getInstance();
                            if (mBellStartTime != null){
                                calendar.setTime(mBellStartTime);
                            }

                            calendar.set(2018, 1, 1, hourOfDay, minute, 0);

                            mBellStartTime = calendar.getTime();

                            startTime.setText(formatDate(mBellStartTime));

                            mBell = mBells.get(mBell.getCoupleNum() - 1);
                            mBell.setStartTime(mBellStartTime);

                            mSingle
                                    .updateBell(mBell);

                            CalendarParser.checkBells(getContext(), mBell, coupleNum, position);

                        }
                    });

                    timePickerDialog.show(getActivity().getFragmentManager(), "TimeFragment");

                }
            });
            //endregion

            //  region СЛУШАТЕЛЬ НА КЛИК И РЕГИСТРАЦИЯ ПИКЕРА END_TIME
            endTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    mBellEndTime = mBell.getEndTime();

                    Calendar calendar = Calendar.getInstance();
                    if (mBellEndTime != null){
                        calendar.setTime(mBellEndTime);
                    }

                    int hour = calendar.get(Calendar.HOUR_OF_DAY);
                    int minute = calendar.get(Calendar.MINUTE);

                    TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(null, hour, minute, true);
                    timePickerDialog.setOnTimeSetListener(new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                            Calendar calendar = Calendar.getInstance();
                            if (mBellEndTime != null){
                                calendar.setTime(mBellEndTime);
                            }

                            calendar.set(2018, 1, 1, hourOfDay, minute, 0);

                            mBellEndTime = calendar.getTime();

                            endTime.setText(formatDate(mBellEndTime));

                            mBell = mBells.get(mBell.getCoupleNum() - 1);
                            mBell.setEndTime(mBellEndTime);

                            mSingle.updateBell(mBell);

                            CalendarParser.checkBells(getContext(), mBell, coupleNum, position);

                        }
                    });

                    timePickerDialog.show(getActivity().getFragmentManager(), "TimeFragment");
                }
            });*/
            // endregion
        }
    }
}
