package com.lizzardry.temporary;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.lizzardry.ril.RetroImageLoader;

public class MainActivity extends AppCompatActivity {


    private ImageView imageView;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        init();
    }

    private void init() {
        imageView = (ImageView) findViewById(R.id.content_main_image);
        imageView2 = (ImageView) findViewById(R.id.content_main_image2);
        imageView3 = (ImageView) findViewById(R.id.content_main_image3);
        imageView4 = (ImageView) findViewById(R.id.content_main_image4);
        RetroImageLoader.getInstance().displayImage("http://vignette4.wikia.nocookie.net/epic-rap-battles-of-cartoons/images/c/cb/SpongeBob.png/revision/latest?cb=20131030015532", imageView);
        RetroImageLoader.getInstance().displayImage("http://i.stack.imgur.com/ILTQq.png", imageView2);
        RetroImageLoader.getInstance().displayImage("http://pixel.nymag.com/imgs/daily/vulture/2015/09/04/04-captain-america-age-of-ultron.w529.h529.jpg", imageView3);
        RetroImageLoader.getInstance().displayImage("http://i1.wp.com/cdn.bgr.com/2014/04/captain-america.jpg?w=625", imageView4);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
