package com.zhy.holmestoolset;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yalantis.beamazingtoday.interfaces.AnimationType;
import com.yalantis.beamazingtoday.interfaces.BatModel;
import com.yalantis.beamazingtoday.listeners.BatListener;
import com.yalantis.beamazingtoday.listeners.OnItemClickListener;
import com.yalantis.beamazingtoday.listeners.OnOutsideClickedListener;
import com.yalantis.beamazingtoday.ui.adapter.BatAdapter;
import com.yalantis.beamazingtoday.ui.animator.BatItemAnimator;
import com.yalantis.beamazingtoday.ui.callback.BatCallback;
import com.yalantis.beamazingtoday.ui.widget.BatRecyclerView;
import com.yalantis.beamazingtoday.util.TypefaceUtil;
import com.yalantis.jellytoolbar.listener.JellyListener;
import com.yalantis.jellytoolbar.widget.JellyToolbar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhy on 2018/2/10 0010.
 * email: 760982661@qq.com
 * dec:
 */

public class JellyActivity extends AppCompatActivity implements BatListener, OnItemClickListener, OnOutsideClickedListener {
    private static final String TEXT_KEY = "text";
    private JellyToolbar toolbar;
    private AppCompatEditText editText;
    private BatRecyclerView mRecyclerView;
    private BatAdapter mAdapter;
    private List<BatModel> mGoals;
    private BatItemAnimator mAnimator;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_jelly_toolbar);

        toolbar = (JellyToolbar) findViewById(R.id.toolbar);
        toolbar.setJellyListener(jellyListener);
        toolbar.getToolbar().setPadding(0, getStatusBarHeight(), 0, 0);

        editText = (AppCompatEditText) LayoutInflater.from(this).inflate(R.layout.edit_text, null);
        editText.setBackgroundResource(R.color.colorTransparent);
        toolbar.setContentView(editText);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

//        ((TextView) findViewById(R.id.text_title)).setTypeface(TypefaceUtil.getAvenirTypeface(this));

        mRecyclerView = (BatRecyclerView) findViewById(R.id.bat_recycler_view);
        mAnimator = new BatItemAnimator();

        mRecyclerView.getView().setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.getView().setAdapter(mAdapter = new BatAdapter(mGoals = new ArrayList<BatModel>() {{
            add(new Goal("first"));
            add(new Goal("second"));
            add(new Goal("third"));
            add(new Goal("fourth"));
            add(new Goal("fifth"));
            add(new Goal("sixth"));
            add(new Goal("seventh"));
            add(new Goal("eighth"));
            add(new Goal("ninth"));
            add(new Goal("tenth"));
        }}, this, mAnimator).setOnItemClickListener(this).setOnOutsideClickListener(this));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new BatCallback(this));
        itemTouchHelper.attachToRecyclerView(mRecyclerView.getView());
        mRecyclerView.getView().setItemAnimator(mAnimator);
        mRecyclerView.setAddItemListener(this);

        findViewById(R.id.root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerView.revertAnimation();
            }
        });
    }

    private JellyListener jellyListener = new JellyListener() {
        @Override
        public void onCancelIconClicked() {
            if (TextUtils.isEmpty(editText.getText())) {
                toolbar.collapse();
            } else {
                editText.getText().clear();
            }
        }
    };

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(TEXT_KEY, editText.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        editText.setText(savedInstanceState.getString(TEXT_KEY));
        editText.setSelection(editText.getText().length());
    }

    @Override
    public void add(String string) {
        mGoals.add(0, new Goal(string));
        mAdapter.notify(AnimationType.ADD, 0);
    }

    @Override
    public void delete(int position) {
        mGoals.remove(position);
        mAdapter.notify(AnimationType.REMOVE, position);
    }

    @Override
    public void move(int from, int to) {
        if (from >= 0 && to >= 0) {
            mAnimator.setPosition(to);
            BatModel model = mGoals.get(from);
            mGoals.remove(model);
            mGoals.add(to, model);
            mAdapter.notify(AnimationType.MOVE, from, to);

            if (from == 0 || to == 0) {
                mRecyclerView.getView().scrollToPosition(Math.min(from, to));
            }
        }
    }

    @Override
    public void onClick(BatModel item, int position) {
        Toast.makeText(this, item.getText(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onOutsideClicked() {
        mRecyclerView.revertAnimation();
    }
}
