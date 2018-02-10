package com.zhy.holmestoolset;

import android.content.res.AssetManager;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.igalata.bubblepicker.BubblePickerListener;
import com.igalata.bubblepicker.adapter.BubblePickerAdapter;
import com.igalata.bubblepicker.model.BubbleGradient;
import com.igalata.bubblepicker.model.PickerItem;
import com.igalata.bubblepicker.rendering.BubblePicker;

import org.jetbrains.annotations.NotNull;

/**
 * Created by zhy on 2018/2/10 0010.
 * email: 760982661@qq.com
 * dec:
 */

public class BubblePickerActivity extends AppCompatActivity {

    private BubblePicker picker;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        final String[] titles = getApplicationContext().getResources().getStringArray(R.array.countries);
        final TypedArray colors = getApplicationContext().getResources().obtainTypedArray(R.array.colors);
        final TypedArray images = getApplicationContext().getResources().obtainTypedArray(R.array.images);

        Typeface boldTypeface = Typeface.createFromAsset(getResources().getAssets(), "roboto_bold.ttf");
        final Typeface mediumTypeface = Typeface.createFromAsset(getResources().getAssets(), "roboto_medium.ttf");
        Typeface regularTypeface = Typeface.createFromAsset(getResources().getAssets(), "roboto_regular.ttf");

        picker = findViewById(R.id.picker);
        TextView titleTextView = findViewById(R.id.titleTextView);
        titleTextView.setTypeface(mediumTypeface);
        picker.setAdapter(new BubblePickerAdapter() {
            @Override
            public int getTotalCount() {
                return titles.length;
            }

            @NotNull
            @Override
            public PickerItem getItem(int position) {
                PickerItem item = new PickerItem();
                item.setTitle(titles[position]);
                item.setGradient(new BubbleGradient(colors.getColor((position * 2) % 8, 0),
                        colors.getColor((position * 2) % 8 + 1, 0), BubbleGradient.VERTICAL));
                item.setTypeface(mediumTypeface);
                item.setTextColor(ContextCompat.getColor(BubblePickerActivity.this, android.R.color.white));
                item.setBackgroundImage(ContextCompat.getDrawable(BubblePickerActivity.this, images.getResourceId(position, 0)));
                return item;
            }
        });

        colors.recycle();
        images.recycle();
        picker.setBubbleSize(20);
        picker.setListener(new BubblePickerListener() {
            @Override
            public void onBubbleSelected(PickerItem pickerItem) {
                Toast.makeText(BubblePickerActivity.this, pickerItem.getTitle() + " onBubbleSelected ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBubbleDeselected(PickerItem pickerItem) {
                Toast.makeText(BubblePickerActivity.this, pickerItem.getTitle() + " onBubbleDeselected ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        picker.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        picker.onPause();
    }
}
