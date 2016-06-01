package li.muhammada.rbc.yelp.util;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    public static final Preferences INSTANCE = new Preferences();
    public static final String PREFS_NAME = "Prefs";
    public static final String SORT_TYPE = "SortType";
    private SharedPreferences prefs;

    private Preferences() {}

    public void init(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public int getSortType() {
        return prefs.getInt(SORT_TYPE, 0);
    }

    public void setSortType(int value) {
        prefs.edit().putInt(SORT_TYPE, value).apply();
    }

}
