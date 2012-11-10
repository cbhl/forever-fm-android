package fm.forever.android;

import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class RadioActivity extends Activity {

	OnAudioFocusChangeListener audioFocusChangeListener = new OnAudioFocusChangeListener() {

		@Override
		public void onAudioFocusChange(int focusChange) {
			AudioManager am = (AudioManager) RadioActivity.this
					.getSystemService(Context.AUDIO_SERVICE);

			switch (focusChange) {
			case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
				Log.d(getClass().getCanonicalName(),
						"AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK");
				break;
			case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
				Log.d(getClass().getCanonicalName(),
						"AUDIOFOCUS_LOSS_TRANSIENT");
				break;
			case AudioManager.AUDIOFOCUS_GAIN:
				Log.d(getClass().getCanonicalName(), "AUDIOFOCUS_GAIN");
				am.registerMediaButtonEventReceiver(new ComponentName(
						"fm.forever.android",
						"fm.forever.android.RemoteControlReceiver"));
				break;
			case AudioManager.AUDIOFOCUS_LOSS:
				Log.d(getClass().getCanonicalName(), "AUDIOFOCUS_LOSS");
				am.unregisterMediaButtonEventReceiver(new ComponentName(
						"fm.forever.android",
						"fm.forever.android.RemoteControlReceiver"));
				break;
			default:
				break;
			}
		}
	};

	OnClickListener playPauseButtonClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			mediaPlayer.stop();
		}
	};

	OnPreparedListener preparedListener = new OnPreparedListener() {

		@Override
		public void onPrepared(MediaPlayer mediaPlayer) {
			Log.d(getClass().getCanonicalName(), "beginning playback...");
			mediaPlayer.start();

		}
	};

	MediaPlayer mediaPlayer;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setVolumeControlStream(AudioManager.STREAM_MUSIC);

		setContentView(R.layout.activity_radio);
		Button playPauseButton = (Button) findViewById(R.id.RadioActivity_PlayPauseButton);
		playPauseButton.setOnClickListener(playPauseButtonClickListener);

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		int result = am.requestAudioFocus(audioFocusChangeListener,
		// Use the music stream.
				AudioManager.STREAM_MUSIC,
				// Request permanent focus.
				AudioManager.AUDIOFOCUS_GAIN);

		if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
			Log.d(getClass().getCanonicalName(), "AUDIOFOCUS_REQUEST_GRANTED");
			am.registerMediaButtonEventReceiver(new ComponentName(
					"fm.forever.android",
					"fm.forever.android.RemoteControlReceiver"));
			// Start playback.
			String url = "http://forever.fm/all.mp3"; // your URL here
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			try {
				mediaPlayer.setLooping(true);
				mediaPlayer.setOnPreparedListener(preparedListener);
				mediaPlayer.setDataSource(url);
				mediaPlayer.prepareAsync();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			Log.d(getClass().getCanonicalName(), "audiofocus request failed");
		}
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_radio, menu);
		return true;
	}
}
