package li.muhammada.rbc.yelp.provider;

import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class YelpRetrofit {

    private static final String TAG = "YelpRetrofit";
    public static YelpRetrofit INSTANCE = new YelpRetrofit();
    private final YelpService mService;

    private YelpRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.yelp.com/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mService = retrofit.create(YelpService.class);
    }

    @Nullable
    public ResponseWrapper search(String searchTerm, String location) {
        try {
            return mService.search(searchTerm, location).execute().body();
        } catch (IOException e) {
            Log.e(TAG, "Couldn't search Yelp", e);
        }
        return null;
    }

}
