package io.happybugs.happybugs.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;

public class KeyboardActionResult extends ResultReceiver {
    /**
     * Create a new ResultReceive to receive results.  Your
     * {@link #onReceiveResult} method will be called from the thread running
     * <var>handler</var> if given, or from an arbitrary thread if null.
     *
     * @param handler
     */
    public int result = -1;
    public KeyboardActionResult(Handler handler) {
        super(handler);
    }
    public KeyboardActionResult() {
        super(null);
    }

    @Override
    public void onReceiveResult(int r, Bundle data) {
        result = r;
    }

    public int getResult() {
        return result;
    }
}
