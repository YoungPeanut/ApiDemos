package com.example.android.dao.demo0;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.apis.ApiDemos;
import com.example.android.apis.R;
import com.example.android.dao.demo0.db.DataBean;
import com.example.android.dao.demo0.db.DataProviderHelper;
import com.example.android.supportv7.recyclerview.OnMyItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by idisfkj on 16/4/2.
 * Email : idisfkj@qq.com.
 */
public class CursorFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    RecyclerView recyclerView;
    private DataProviderHelper mHelper;
    private CursorAdapter mAdapter;
    private String[] item;

    private static class CursorFragmentHolder {
        private static CursorFragment instance = new CursorFragment();
    }

    public static CursorFragment newInstance() {
        return CursorFragmentHolder.instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.recycler_view, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mHelper = new DataProviderHelper(getActivity());
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new CursorAdapter(getActivity());
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new OnMyItemClickListener(recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                Intent intent = new Intent();
                intent.putExtra("type", vh.getLayoutPosition());
                intent.setClass(getActivity(), ApiDemos.class);
                startActivity(intent);
            }

            @Override
            public void onItemLongPressClick(RecyclerView.ViewHolder vh) {

            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(0, null, this);
    }

    public void initData() {
        List<DataBean> list = new ArrayList<>();
        item = this.getResources().getStringArray(R.array.item);
        for (int i = 0; i < item.length; i++) {
            DataBean info = new DataBean();
            info.setContetn(item[i]);
            list.add(info);
        }
        mHelper.bulkInsert(list);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return mHelper.getCursorLoader();
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data == null || data.getCount() == 0) {
            initData();
        } else {
            mAdapter.changeCursor(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.changeCursor(null);
    }
}
