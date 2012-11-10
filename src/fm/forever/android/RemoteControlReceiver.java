package fm.forever.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;

public class RemoteControlReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (Intent.ACTION_MEDIA_BUTTON.equals(intent.getAction())) {
			KeyEvent event = (KeyEvent) intent
					.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
			switch (event.getKeyCode()) {
			case KeyEvent.KEYCODE_MEDIA_PLAY:
				Log.d(getClass().getCanonicalName(), "KEYCODE_MEDIA_PLAY");
				break;

			case KeyEvent.KEYCODE_MEDIA_PAUSE:
				Log.d(getClass().getCanonicalName(), "KEYCODE_MEDIA_PAUSE");
				break;

			case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
			case KeyEvent.KEYCODE_HEADSETHOOK:
				Log.d(getClass().getCanonicalName(), "KEYCODE_MEDIA_PLAY_PAUSE");
				break;

			case KeyEvent.KEYCODE_MEDIA_STOP:
				Log.d(getClass().getCanonicalName(), "KEYCODE_MEDIA_STOP");
				break;

			default:
				Log.d(getClass().getCanonicalName(), String.format("Unknown key received: %d", event.getKeyCode()));
				break;
			}
		}
	}

}
