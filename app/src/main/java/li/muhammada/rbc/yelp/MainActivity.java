package li.muhammada.rbc.yelp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import li.muhammada.rbc.yelp.provider.YelpRetrofit;
import li.muhammada.rbc.yelp.provider.model.Business;
import li.muhammada.rbc.yelp.provider.model.ResponseWrapper;
import li.muhammada.rbc.yelp.ui.BusinessListAdapter;
import li.muhammada.rbc.yelp.ui.PicassoHelper;
import li.muhammada.rbc.yelp.util.Preferences;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @BindView(R.id.main_recyclerview) RecyclerView recyclerView;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.loading_textview) TextView loadingTextView;
    @BindView(R.id.try_again_button) View tryAgainButton;
    @BindView(R.id.loading_layout) View loadingLayout;
    @BindView(R.id.loading_spinner) View loadingSpinner;
    private BusinessListAdapter businessListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Preferences.INSTANCE.init(this);
        PicassoHelper.INSTANCE.init(this);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        fab.setOnClickListener(view -> showSortDialog());

        setupRecyclerView();
    }

    private void showSortDialog() {
        String[] sortTypes = getResources().getStringArray(R.array.sort_types);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.sort_by));
        builder.setSingleChoiceItems(sortTypes, Preferences.INSTANCE.getSortType(),
                (dialog, which) -> {
                    Preferences.INSTANCE.setSortType(which);
                    loadResults();
                    dialog.dismiss();
                });
        builder.setPositiveButton(getString(R.string.ok), (dialog, id) -> {
            dialog.dismiss();
        });

        builder.create().show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadResults();
    }

    private void setupRecyclerView() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        final int displayWidth  = displayMetrics.widthPixels;
        int cardViewMinWidth = getResources().getDimensionPixelSize(R.dimen.cardview_min_width);
        int width = Math.max(1, displayWidth / cardViewMinWidth);

        recyclerView.setLayoutManager(new GridLayoutManager(this, width));
        businessListAdapter = new BusinessListAdapter();
        recyclerView.setAdapter(businessListAdapter);
        recyclerView.setItemAnimator(new SlideInUpAnimator());
    }

    @OnClick(R.id.try_again_button)
    public void OnClickTryAgain() {
        loadResults();
    }

    private void loadResults() {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected void onPreExecute() {
                fab.hide();
                tryAgainButton.setVisibility(View.GONE);
                loadingTextView.setText(R.string.loading);
                loadingSpinner.setVisibility(View.VISIBLE);
                loadingLayout.setVisibility(View.VISIBLE);
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                ResponseWrapper response = YelpRetrofit.INSTANCE.search("Ethiopian", "Toronto");
                if (response == null || response.getBusinesses() == null) {
                    return false;
                }

                List<Business> businesses = response.getBusinesses();
                businessListAdapter.setAdapterData(businesses);
                return businesses.size() > 0;
            }

            @Override
            protected void onPostExecute(@NonNull Boolean success) {
                if (success) {
                    loadingLayout.setVisibility(View.GONE);
                    fab.show();
                    businessListAdapter.notifyDataSetChanged();
                } else {
                    loadingSpinner.setVisibility(View.GONE);
                    loadingTextView.setText(R.string.failed_msg);
                    tryAgainButton.setVisibility(View.VISIBLE);
                }
            }
        }.execute();
    }
}
