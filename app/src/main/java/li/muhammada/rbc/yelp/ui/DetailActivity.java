package li.muhammada.rbc.yelp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import li.muhammada.rbc.yelp.R;
import li.muhammada.rbc.yelp.provider.model.Business;

public class DetailActivity extends AppCompatActivity {

    public static final String INTENT_PARAM_BUSINESS = "Business";

    private Business mBusiness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(view -> {});
        }

        handleIntent(getIntent());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        mBusiness = ((Business) intent.getSerializableExtra(INTENT_PARAM_BUSINESS));
        loadPage();
    }

    private void loadPage() {
        if (mBusiness == null) {
            return;
        }

        getSupportActionBar().setTitle(mBusiness.getName());
    }

}
