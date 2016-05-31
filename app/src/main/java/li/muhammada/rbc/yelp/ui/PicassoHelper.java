package li.muhammada.rbc.yelp.ui;

import android.content.Context;
import android.util.Log;

import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import li.muhammada.rbc.yelp.BuildConfig;

public class PicassoHelper {

    private static final String TAG = "PicassoHelper";
    private Picasso mPicasso;

    public static final PicassoHelper INSTANCE = new PicassoHelper();

    private PicassoHelper() {}

    public void init(Context context) {
        mPicasso = new Picasso.Builder(context)
                .downloader(new OkHttpDownloader(context))
                .loggingEnabled(BuildConfig.DEBUG)
                .listener((picasso, uri, exception) -> {
                    Log.e(TAG, String.format("Picasso[%s]: %s", uri.toString(), exception.toString()));
                    exception.printStackTrace();
                })
                .build();
    }

    public Picasso picasso() {
        if (mPicasso == null) {
            throw new RuntimeException("Picasso hasn't been initialized yet");
        }
        return mPicasso;
    }
}
