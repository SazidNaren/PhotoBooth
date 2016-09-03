package com.ar.mystyle.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.ar.mystyle.adapters.MyPagerAdapter;
import com.style.facechanger.R;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by SAZID ALI on 29/08/2016.
 */
public class GalleryImages extends FragmentActivity implements View.OnClickListener{
    private ArrayList<String> imageArra;
    private int selectedPosition=0;
    private ImageView imgDelete,imgShare,imgHome;
    private ViewPager myPager;
    private MyPagerAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        imgDelete=(ImageView)findViewById(R.id.img_delete);
        imgShare=(ImageView)findViewById(R.id.img_share);
        imgHome=(ImageView)findViewById(R.id.img_home);
        imgShare.setOnClickListener(this);
        imgDelete.setOnClickListener(this);
        imgHome.setOnClickListener(this);
        imageArra=getIntent().getStringArrayListExtra("data");
        selectedPosition=getIntent().getIntExtra("selected_position",0);
        adapter = new MyPagerAdapter(imageArra,getSupportFragmentManager());
        myPager = (ViewPager) findViewById(R.id.myfivepanelpager);
        myPager.setAdapter(adapter);
        myPager.setCurrentItem(selectedPosition);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0,R.anim.top_to_bottom);

    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id)
        {
            case R.id.img_delete:
                File file = new File(imageArra.get(myPager.getCurrentItem()));
                boolean deleted = file.delete();
                if(deleted) {
                    imageArra.remove(myPager.getCurrentItem());
                    adapter.notifyDataSetChanged();
                }
                break;
            case R.id.img_share:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("image/jpg");
               // shareIntent.
                final File photoFile = new File(imageArra.get(myPager.getCurrentItem()));
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(photoFile));
                startActivity(Intent.createChooser(shareIntent, "Share image using"));
                break;
            case R.id.img_home:
                finish();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}

