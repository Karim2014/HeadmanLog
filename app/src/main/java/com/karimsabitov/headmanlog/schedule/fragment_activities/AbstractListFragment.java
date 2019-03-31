package com.karimsabitov.headmanlog.schedule.fragment_activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.karimsabitov.headmanlog.R;

import java.util.List;

/**
 * Created by User on 24.02.2019.
 */

public abstract class AbstractListFragment extends ListFragment implements AdapterView.OnItemClickListener, IListsFragment {

    private static final int REQUEST_UPDATE = 0;
    private static final String EDIT_DIALOG_TAG = "EDIT_DIALOG_TAG";
    private static final int REQUEST_ADD = 1;

    protected abstract void addItem(String value);
    protected abstract List<String> getList();
    protected abstract void update(String oldValue, String newValue);
    protected abstract String getTitle();
    protected abstract Fragment getTargetFragmentForDialog();
    protected abstract void deleteItem(String item);

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupAdapter();
        getListView().setOnItemClickListener(this);
        setHasOptionsMenu(true);
        getActivity().invalidateOptionsMenu();
        setEmptyText("Список пуст");
    }

    @Override
    public void addItem(FragmentManager fragmentManager) {
        EditDialog dialog1 = EditDialog.newInstance("", getTitle());
        dialog1.setTargetFragment(this, REQUEST_ADD);
        dialog1.show(fragmentManager, EDIT_DIALOG_TAG);
    }

    protected void setupAdapter() {
        ListAdapter adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, getList());
        setListAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_UPDATE) {
            String newRoom = data.getStringExtra(EditDialog.EXTRA_NEW_VALUE);
            String oldValue = data.getStringExtra(EditDialog.EXTRA_OLD_VALUE);
            update(oldValue, newRoom);
            setupAdapter();
        }
        if (requestCode == REQUEST_ADD) {
            String value = data.getStringExtra(EditDialog.EXTRA_NEW_VALUE);
            addItem(value);
            setupAdapter();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.schedule_tab_view_pager, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //addItem(getFragmentManager());
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
        new AlertDialog.Builder(getContext())
                .setItems(R.array.context_simple, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                EditDialog dialog1 = EditDialog.newInstance(((TextView) view).getText().toString(), getTitle());
                                dialog1.setTargetFragment(getTargetFragmentForDialog(), REQUEST_UPDATE);
                                dialog1.show(getFragmentManager(), EDIT_DIALOG_TAG);
                                break;
                            case 1:
                                new AlertDialog.Builder(getActivity())
                                        .setTitle("Удалить?")
                                        .setMessage("Вы действительно хотите удалить?")
                                        .setNegativeButton(android.R.string.cancel, null)
                                        .setPositiveButton(android.R.string.ok, new AlertDialog.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                String item = ((TextView) view).getText().toString();
                                                deleteItem(item);
                                                setupAdapter();
                                            }
                                        })
                                        .show();
                                break;
                        }
                    }
                }).show();
    }
}
