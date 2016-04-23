package com.lizzardry.temporary.fragments.shared;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lizzardry.ril.RetroImageLoader;
import com.lizzardry.temporary.R;

public class SharedElementDetailActivity extends AppCompatActivity {

    public static final String SHARED_ELEMENT_DETAIL_TITLE = "title";
    public static final String SHARED_ELEMENT_DETAIL_IMAGE = "image";

    private ImageView imageView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_element_detail);
        init();

    }

    private void init() {
        imageView = (ImageView) findViewById(R.id.activity_shared_element_detail_image_view);
        textView = (TextView) findViewById(R.id.activity_shared_element_detail_title);
        Bundle bundle = getIntent().getExtras();
        RetroImageLoader.getInstance().displayImage(bundle.getString(SHARED_ELEMENT_DETAIL_IMAGE), imageView);
        textView.setText(bundle.getString(SHARED_ELEMENT_DETAIL_TITLE));
    }

}
