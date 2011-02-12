package com.myapps;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;

public class Video extends Activity implements SurfaceHolder.Callback {
	private MediaPlayer mediaPlayer;
	private String uri;
	private PowerManager.WakeLock wakeLock;
	private String wakeLockTag = "video";
	private String logTag = "AppLog";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video);
		setRequestedOrientation(0);
		/*
		 * PowerManager pm = (PowerManager) getApplicationContext()
		 * .getSystemService(Context.POWER_SERVICE); wakeLock = pm.newWakeLock(
		 * PowerManager.SCREEN_DIM_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE,
		 * wakeLockTag); wakeLock.acquire();
		 */
		mediaPlayer = new MediaPlayer();

		SurfaceView surface = (SurfaceView) findViewById(R.id.surfaceView1);
		SurfaceHolder holder = surface.getHolder();
		holder.addCallback(this);
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		holder.setFixedSize(400, 300);
		Log.i(logTag, "Surface View Initialis�");

		Bundle extras = getIntent().getExtras();
		uri = extras.getString("uri");
		Log.i(logTag, "Demande lecture " + uri);
		mediaPlayer.setDisplay(holder);

		Button buttonPlay = (Button) findViewById(R.id.Play);
		buttonPlay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mediaPlayer.isPlaying()){
					mediaPlayer.pause();
					Log.i(logTag, "video en pause");
				}else{
					mediaPlayer.start();
					Log.i(logTag, "video en cours de lecture");
			}}
		});
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		try {
			Log.i(logTag, "demarrage video");
			mediaPlayer.setDataSource(uri);
			mediaPlayer.setScreenOnWhilePlaying(true);
			mediaPlayer.prepare();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			Log.i(logTag, "No video found");
			e.printStackTrace();

		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		mediaPlayer.release();
		// wakeLock.release();
	}
}