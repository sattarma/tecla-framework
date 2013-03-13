package ca.idrc.tecla;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityNodeInfo;

public class TeclaHUDController extends SimpleOverlay {

	protected final TeclaHUD mHUD;
	private static TeclaHUDController sInstance;
	
	public TeclaHUDController(Context context) {
		super(context);
		final WindowManager.LayoutParams params = getParams();
		params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
		params.flags |= WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
		params.flags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
		setParams(params);

		setContentView(R.layout.tecla_controller);

		mHUD = (TeclaHUD) findViewById(R.id.teclaHUD_control);
		getRootView().setOnTouchListener(mOverlayTouchListener);
	}

	@Override
	protected void onShow() {
		sInstance = this;
	}

	@Override
	protected void onHide() {
        sInstance = null;
	}
	
	private View.OnTouchListener mOverlayTouchListener = new View.OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				//Log.v("TeclaA11y", "Tecla Overlay Touch Down! " + Float.toString(logicalX) + " " + Float.toString(logicalY));
				break;
			case MotionEvent.ACTION_UP:
				//Log.v("TeclaA11y", "Tecla Overlay Touch Up! " + Float.toString(logicalX) + " " + Float.toString(logicalY));
				mHUD.scanTrigger();
			default:
				break;
			}
			return true;
		}
		
	};
}