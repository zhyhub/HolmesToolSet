package com.zhy.holmestoolset.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhy.holmestoolset.R;


/**
 * Created by zhy on 2018/2/9 0009.
 * email: 760982661@qq.com
 * dec:
 */

public class ContextMenuFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.context_fragment, container, false);
    }
}
