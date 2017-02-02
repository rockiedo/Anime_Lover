package thachdd.vuighenet.app;

import android.app.Application;
import android.util.Log;

/**
 * Created by cpu60011-local on 02/02/2017.
 */

public class CustomApplication extends Application {
    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d("mylog", "app closed");
    }
}
