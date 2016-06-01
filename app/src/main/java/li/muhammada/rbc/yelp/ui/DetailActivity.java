package li.muhammada.rbc.yelp.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URI;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import li.muhammada.rbc.yelp.R;
import li.muhammada.rbc.yelp.provider.model.Business;

public class DetailActivity extends AppCompatActivity {

    public static final String INTENT_PARAM_BUSINESS = "Business";

    private Business business;

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
        business = ((Business) intent.getSerializableExtra(INTENT_PARAM_BUSINESS));
        loadPage();
    }

    private void loadPage() {
        if (business == null) {
            return;
        }

        //noinspection ConstantConditions
        getSupportActionBar().setTitle(business.getName());
        PicassoHelper.INSTANCE.picasso().load(business.getImageUrl()).into(heroImageView);
        PicassoHelper.INSTANCE.picasso().load(business.getRatingImgUrlLarge()).into(ratingImageView);
        ratingCount.setText(String.format(Locale.getDefault(), getString(R.string.brackets), business.getReviewCount()));
        PicassoHelper.INSTANCE.picasso().load(business.getSnippetImageUrl()).into(avatar);
        snippetText.setText(business.getSnippetText());
        setupAddress();
    }

    private void setupAddress() {
        if (business == null || business.getLocation() == null) {
            address.setText("");
            return;
        }

        StringBuilder builder = new StringBuilder();
        for (String line : business.getLocation().getDisplayAddress()) {
            builder.append(line).append('\n');
        }
        address.setText(builder.toString());

        if (business.getLocation().getCoordinate() != null) {
            address.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            address.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                String uri = String.format(Locale.US, "geo:%f,%f?q=%s",
                        business.getLocation().getCoordinate().getLatitude(),
                        business.getLocation().getCoordinate().getLongitude(),
                        Uri.encode(builder.toString()));
                intent.setData(Uri.parse(uri));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            });
        }
    }

}
