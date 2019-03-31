package com.karimsabitov.headmanlog.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.karimsabitov.headmanlog.R;
import com.karimsabitov.headmanlog.UI.RecyclerViewEmptySupport;
import com.karimsabitov.headmanlog.Utils.Utils;
import com.karimsabitov.headmanlog.attendance.fragments_activities.AttendanceEditDialogFragment;
import com.karimsabitov.headmanlog.attendance.fragments_activities.AttendanceEditFragment;
import com.karimsabitov.headmanlog.attendance.model.AttDay;
import com.karimsabitov.headmanlog.schedule.models.ScheduleSingle;
import com.karimsabitov.headmanlog.students.StudentGroup;

import java.util.HashMap;
import java.util.List;

/**
 * Created by User on 16.09.2018.
 */

public class AttendanceEditFragmentAdapterCouples extends RecyclerView.Adapter<AttendanceEditFragmentAdapterCouples.ViewHolder> {

    private Context ctx;
    private HashMap<Integer, List<String>> mMap;
    private List<String> mStudents;
    private ScheduleSingle mScheduleSingle;
    private Fragment mTargetFragment;

    public AttendanceEditFragmentAdapterCouples(@NonNull Context ctx, @NonNull HashMap<Integer, List<String>> map, @NonNull List<String> students, @NonNull Fragment targetFragment) {
        this(ctx, map, students);
        mTargetFragment = targetFragment;
    }

    public AttendanceEditFragmentAdapterCouples(@NonNull Context ctx, @NonNull HashMap<Integer, List<String>> map, @NonNull List<String> students){
        this.ctx = ctx;
        mMap = map;
        mScheduleSingle = ScheduleSingle.get(ctx);
        mTargetFragment = null;
        mStudents = students;
    }

    public void setMap(HashMap<Integer, List<String>> map, List<String> students) {
        mMap = map;
        mStudents = students;
    }

    @Override
    public int getItemCount() {
        return mMap.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.list_item_attendance_quick_edit_couple, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        List<String> uuids = mMap.get(position);
        holder.bindView(uuids, position);
    }

    public void addDay(AttDay attDay) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mAbsanceCount;
        RecyclerViewEmptySupport mRecyclerView;
        Button mChange;
        AttendanceEditFragmentAdapter mAdapter;
        TextView mEmptyView;

        List<String> mAbsStudents;

        public ViewHolder(View itemView) {
            super(itemView);
            mAbsanceCount = itemView.findViewById(R.id.fragment_attendance_edit_couple_text_view_count_absence);
            mRecyclerView = itemView.findViewById(R.id.fragment_attendance_edit_couple_recycler_v);
            mChange = itemView.findViewById(R.id.fragment_attendance_edit_couple_button_edit);
            mEmptyView = itemView.findViewById(R.id.list_item_attendance_quick_edit_couple_empty_view);
        }

        public void bindView(List<String> uuids, final int pos) {
            mAbsStudents = uuids;
            mRecyclerView.setEmptyView(mEmptyView);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false));
            updateAdapter();
            mAbsanceCount.setText(ctx.getString(
                    R.string.title_absance_count,
                    (pos + 1),
                    uuids.size(),
                    mStudents.size()
                    )
            );
            if (mTargetFragment != null) {
                mChange.setVisibility(View.VISIBLE);
                mChange.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AttendanceEditDialogFragment dialogFragment = AttendanceEditDialogFragment.newInstance(mAbsStudents, mStudents, pos);
                        dialogFragment.setTargetFragment(mTargetFragment, AttendanceEditFragment.REQUEST_LIST);
                        dialogFragment.show(mTargetFragment.getFragmentManager(), "TAG");
                    }
                });
            }else{
                mChange.setVisibility(View.GONE);
            }
        }

        private void updateAdapter() {
            if (mAdapter == null) {
                mAdapter = new AttendanceEditFragmentAdapter(ctx, mAbsStudents);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            } else {
                mAdapter.setAbsStd(mAbsStudents);
                mAdapter.notifyDataSetChanged();
            }
        }


    }

    class AttendanceEditFragmentAdapter extends RecyclerView.Adapter<AttendanceEditFragmentAdapter.ViewHolder> {

        List<String> mAbsStd;
        Context ctx;

        StudentGroup mStudentSingle;

        public AttendanceEditFragmentAdapter(Context ctx, List<String> UUIDs) {
            mAbsStd = UUIDs;
            mStudentSingle = StudentGroup.get(ctx);
            this.ctx = ctx;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            String student = mAbsStd.get(position);

            holder.bindView(student);
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(ctx).inflate(R.layout.list_item_attendance_quick_edit_bitmap, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public int getItemCount() {
            return mAbsStd.size();
        }

        public void setAbsStd(List<String> absStd) {
            mAbsStd = absStd;
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            ImageView mImageView;
            TextView mFIO;

            public ViewHolder(View itemView) {
                super(itemView);
                mImageView = itemView.findViewById(R.id.list_item_attendance_quick_edit_bitmap_image_v);
                mFIO = itemView.findViewById(R.id.list_item_attendance_quick_edit_bitmap_text_v);
            }

            public void bindView(String student) {
                mFIO.setText(student);

                TextDrawable drawable = Utils.roundTextIcon(student.substring(0, 1));

                mImageView.setImageDrawable(drawable);
            }
        }
    }
}