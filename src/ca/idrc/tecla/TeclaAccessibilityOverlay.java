package ca.idrc.tecla;

import android.content.Context;
import android.graphics.Color;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityNodeInfo;

public class TeclaAccessibilityOverlay extends SimpleOverlay {

    private static TeclaAccessibilityOverlay sInstance;

    private final HighlightBoundsView mAnnounceBounds;
    private final HighlightBoundsView mBounds;
    
	public TeclaAccessibilityOverlay(Context context) {
		super(context);
		final WindowManager.LayoutParams params = getParams();
		params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
		params.flags |= WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
		params.flags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
		// params.flags |= WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
		setParams(params);
		
		setContentView(R.layout.tecla_accessibility_overlay);
		
		mAnnounceBounds = (HighlightBoundsView) findViewById(R.id.announce_bounds);
		mAnnounceBounds.setHighlightColor(Color.YELLOW);
		
		mBounds = (HighlightBoundsView) findViewById(R.id.bounds);
		mBounds.setHighlightColor(Color.RED);
	}

	@Override
	protected void onShow() {
		sInstance = this;
	}

	@Override
	protected void onHide() {
        sInstance = null;
        mBounds.clear();
        mAnnounceBounds.clear();
	}
	

    public static void removeInvalidNodes() {
        if (sInstance == null) {
            return;
        }

        sInstance.mBounds.removeInvalidNodes();
        sInstance.mBounds.postInvalidate();

        sInstance.mAnnounceBounds.removeInvalidNodes();
        sInstance.mAnnounceBounds.postInvalidate();
    }

    public static void updateNodes(AccessibilityNodeInfo source, AccessibilityNodeInfo announced) {
        if (sInstance == null) {
            return;
        }

        sInstance.mBounds.clear();
        if(source != null) {
            sInstance.mBounds.add(source);
            sInstance.mBounds.postInvalidate();        	
        }
        
        sInstance.mAnnounceBounds.clear();
        if(announced != null) {
            sInstance.mAnnounceBounds.add(announced);
            sInstance.mAnnounceBounds.postInvalidate();
        	
        }
    }
    
}