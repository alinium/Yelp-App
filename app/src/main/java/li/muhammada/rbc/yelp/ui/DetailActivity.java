package li.muhammada.rbc.yelp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import li.muhammada.rbc.yelp.R;
import li.muhammada.rbc.yelp.provider.model.Business;

public class DetailActivity extends AppCompatActivity {

    public static final String INTENT_PARAM_BUSINESS = "Business";

    private Business mBusiness;

    @BindView(R.id.hero_imageview) ImageView heroImageView;
    @BindView(R.id.rating_imageview) ImageView ratingImageView;
    @BindView(R.id.rating_count) TextView ratingCount;
    @BindView(R.id.avatar) ImageView avatar;
    @BindView(R.id.snippet_text) TextView snippetText;
    @BindView(R.id.address) TextView address;
    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        //noinspection ConstantConditions
        getSupportActionBar().setTitle(mBusiness.getName());
        PicassoHelper.INSTANCE.picasso().load(mBusiness.getImageUrl()).into(heroImageView);
        PicassoHelper.INSTANCE.picasso().load(mBusiness.getRatingImgUrlLarge()).into(ratingImageView);
        ratingCount.setText(String.format(Locale.getDefault(), getString(R.string.brackets), mBusiness.getReviewCount()));
        PicassoHelper.INSTANCE.picasso().load(mBusiness.getSnippetImageUrl()).into(avatar);
        snippetText.setText(mBusiness.getSnippetText());
        address.setText(getAddress());
    }

    private String getAddress() {
        if (mBusiness == null || mBusiness.getLocation() == null) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        for (String line : mBusiness.getLocation().getDisplayAddress()) {
            builder.append(line).append('\n');
        }
        return builder.toString();
    }

}
