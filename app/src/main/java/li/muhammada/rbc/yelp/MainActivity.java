package li.muhammada.rbc.yelp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import li.muhammada.rbc.yelp.provider.YelpRetrofit;
import li.muhammada.rbc.yelp.provider.model.Business;
import li.muhammada.rbc.yelp.provider.model.ResponseWrapper;
import li.muhammada.rbc.yelp.ui.BusinessListAdapter;
import li.muhammada.rbc.yelp.ui.PicassoHelper;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @BindView(R.id.main_recyclerview) RecyclerView recyclerView;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.fab) FloatingActionButton fab;
    private BusinessListAdapter businessListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PicassoHelper.INSTANCE.init(this);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, R.string.loading, Snackbar.LENGTH_SHORT).show();
            }
        });

        setupRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadResults();
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        businessListAdapter = new BusinessListAdapter();
        recyclerView.setAdapter(businessListAdapter);
        recyclerView.setItemAnimator(new SlideInUpAnimator());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadResults() {
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {
                ResponseWrapper response = YelpRetrofit.INSTANCE.search("Ethiopian", "Toronto");
                if (response == null || response.getBusinesses() == null) {
                    return 0;
                }

                businessListAdapter.setAdapterData(response.getBusinesses());
                return response.getBusinesses().size();
            }

            @Override
            protected void onPostExecute(@NonNull Integer size) {
                if (size > 0) {
                    businessListAdapter.notifyItemRangeInserted(0, size);
                }
            }
        }.execute();
    }
}
