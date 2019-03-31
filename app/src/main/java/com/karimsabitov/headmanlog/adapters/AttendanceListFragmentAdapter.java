package com.karimsabitov.headmanlog.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.karimsabitov.headmanlog.Utils.CalendarParser;
import com.karimsabitov.headmanlog.R;
import com.karimsabitov.headmanlog.Utils.Utils;
import com.karimsabitov.headmanlog.attendance.fragments_activities.BottomSheetFragment;
import com.karimsabitov.headmanlog.attendance.model.AttSingle;

import java.util.Date;
import java.util.List;

/**
 * Created by User on 26.09.2018.
 */

public class AttendanceListFragmentAdapter extends RecyclerView.Adapter<AttendanceListFragmentAdapter.ViewHolder> {

    private Context ctx;
    private List<Date> mDays;
    private FragmentManager mManager;

    public AttendanceListFragmentAdapter(Context ctx, List<Date> days, FragmentManager manager) {
        this.ctx = ctx;
        mDays = days;
        mManager = manager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.list_item_attendance_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Date attDay = mDays.get(position);
        holder.bindView(attDay);

        //mWeek = CalendarParser.getWeekInMonth(attDay.getDate());
    }

    @Override
    public int getItemCount() {
        return mDays.size();
    }

    public void setDays(List<Date> days) {
        mDays = days;
    }

    private void deleteItem(final View v, final ViewHolder viewHolder, final int pos){
        String[] items = new String[]{ctx.getString(R.string.confDlg_lookup), ctx.getString(R.string.confDlg_del)};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(ctx, R.layout.dialog_item, items);
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder
                .setAdapter(adapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                viewHolder.onClick(v);
                                break;
                            case 1:
                                AlertDialog.Builder deldialog = new AlertDialog.Builder(ctx);
                                deldialog
                                        .setTitle(R.string.title_confirmationDlg_del)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                AttSingle.get(ctx).removeDay(
                                                        mDays.get(pos)
                                                );
                                                mDays.remove(pos);
                                                notifyItemRemoved(pos);
                                            }
                                        })
                                        .setNegativeButton(android.R.string.cancel, null)
                                        .show();
                                break;
                            default:
                                break;
                        }
                    }
                })
                //.setTitle(R.string.title_dlg_chooseAction)
                .show();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView mTitle;
        ImageView mImageView;
        Date attday;
        //TextView mNewWeek;

        public ViewHolder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.list_item_attendance_list_title_tv);
            mImageView = itemView.findViewById(R.id.list_item_attendance_list_image_iv);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            //mNewWeek = itemView.findViewById(R.id.list_item_attendance_list_new_week_tv);
        }

        public void bindView(final Date attDay) {
            /*if (!CalendarParser.isTheSameWeek(attDay.getDate(), mWeek)){
                mNewWeek.setVisibility(View.VISIBLE);
            }else{
                mNewWeek.setVisibility(View.GONE);
            }*/
            attday = attDay;
            mTitle.setText(
                    CalendarParser.formatDate("EEEE", attDay)
            );
            Drawable drawable = Utils.roundTextIcon(CalendarParser.formatDate("d", attDay));
            mImageView.setImageDrawable(drawable);
        }

        @Override
        public void onClick(View v) {
            BottomSheetFragment dialog = BottomSheetFragment.newInstance(attday);
            dialog.show(mManager, dialog.getTag());
        }

        @Override
        public boolean onLongClick(final View v) {
            deleteItem(v, this, getAdapterPosition());
            return false;
        }
    }

}
