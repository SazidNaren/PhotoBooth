package com.ar.mystyle.activities;
import java.io.File;
import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.ar.mystyle.Util.Constants;
import com.ar.mystyle.adapters.MyCollectionAdapter;
import com.ar.mystyle.interfaces.ClickListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.style.facechanger.R;

public class MyCollectionActivity extends Activity implements ClickListener{
	private DisplayMetrics dm;
	private String url[];
	private ArrayList<String> images;
	private int i,count = 0;
	private MyCollectionAdapter showSavedImage;
	private RecyclerView recyclerView;
	private AdView mAdView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_saved_image);
	//	setScreenActionBar();
		getImagesList();
		mAdView = (AdView) findViewById(R.id.adView);
		showSavedImage=new MyCollectionAdapter(images,this,this);
		recyclerView=(RecyclerView)findViewById(R.id.recyclerview_show_images);
		GridLayoutManager mLayoutManager = new GridLayoutManager(this, 2); // (Context context, int spanCount)
		/*LinearLayoutManager mLayoutManager
				= new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);*/
		//StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL); // (int spanCount, int orientation)

		recyclerView.setLayoutManager(mLayoutManager);
		recyclerView.setAdapter(showSavedImage);
		AdRequest adRequest = new AdRequest.Builder()
				.build();
		mAdView.loadAd(adRequest);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(0,R.anim.top_to_bottom);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (mAdView != null) {
			mAdView.resume();
		}
		getImagesList();
		showSavedImage=new MyCollectionAdapter(images,this,this);
		recyclerView.setAdapter(showSavedImage);
	}


	private void getImagesList() {
		images = new ArrayList<>();
		String iconsStoragePath = Environment.getExternalStorageDirectory()
				+ Constants.imageLocation;
		File sdIconStorageDir = new File(iconsStoragePath);
		if (!sdIconStorageDir.exists())
			sdIconStorageDir.mkdir();
		url = sdIconStorageDir.list();
		if (url == null) {
			Toast.makeText(getApplicationContext(), "No image and directory available ", Toast.LENGTH_SHORT).show();
			return;
		} else {
			for (i = 0; i < url.length; i++) {
				if (url[i].endsWith(".png")) {
					count++;
				}
			}
		}
		if (count == 0) {
			Toast.makeText(getApplicationContext(), "No image and directory available to show..", Toast.LENGTH_SHORT).show();
			return;
		}
		for (i = 0; i < url.length; i++) {
			if (url[i].endsWith(".png")) {
				images.add(Environment.getExternalStorageDirectory() + Constants.imageLocation + url[i]);
			}
		}
	}

	@Override
	public void onItemClick(int position,int flag) {
	/*	Uri uri = Uri.fromFile(new File(images.get(position)));
		Intent intent=new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(uri, "image*//*");
		startActivity(intent);*/
		Intent intent1=new Intent(this,GalleryImages.class);
		intent1.putStringArrayListExtra("data",images);
		intent1.putExtra("selected_position",position);
		startActivity(intent1);
		overridePendingTransition(R.anim.bottom_to_top, 0);
	}

	@Override
	public void onLongItemClick(int position) {

	}
	@Override
	public void onPause() {
		if (mAdView != null) {
			mAdView.pause();
		}
		super.onPause();
	}


	@Override
	public void onDestroy() {
		if (mAdView != null) {
			mAdView.destroy();
		}
		super.onDestroy();
	}
}