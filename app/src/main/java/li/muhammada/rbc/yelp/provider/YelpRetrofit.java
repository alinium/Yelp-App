package li.muhammada.rbc.yelp.provider;

import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;

import li.muhammada.rbc.yelp.provider.model.ResponseWrapper;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;

public class YelpRetrofit {

    private static final String TAG = "YelpRetrofit";
    public static YelpRetrofit INSTANCE = new YelpRetrofit();
    private final YelpService mService;

    /* In a production quality app, these keys should be obfuscated */
    private static final String CONSUMER_KEY = "QAp3lKE-klN0GM686xHiAg";
    private static final String CONSUMER_SECRET = "NmDyBwNDArfHqPMIn9-npfi0yAw";
    private static final String TOKEN = "-FbwFOTtKurTX6JUHhCSjaIZGnoJB9Xc";
    private static final String TOKEN_SECRET = "jxoxGWkclNSDE41aiG1-Ox0e0tE";

    private YelpRetrofit() {
        OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
        consumer.setTokenWithSecret(TOKEN, TOKEN_SECRET);

        OkHttpClient okClient = new OkHttpClient.Builder()
                .addInterceptor(new SigningInterceptor(consumer))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.yelp.com/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okClient)
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
