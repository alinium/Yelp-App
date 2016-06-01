package li.muhammada.rbc.yelp.ui;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import li.muhammada.rbc.yelp.R;
import li.muhammada.rbc.yelp.provider.model.Business;

public class BusinessListAdapter extends RecyclerView.Adapter<BusinessListAdapter.ViewHolder>{

    @Nullable
    private List<Business> mDataset;
    private Picasso mPicasso;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = ((CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.business_list_item, parent, false));
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mDataset == null) {
            return;
        }
        Business business = mDataset.get(position);

        TextView textView = holder.mTextView;
        textView.setText(business.getName());

        CardView cardView = holder.mCardView;
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) cardView.getLayoutParams();
        if (position == mDataset.size() - 1) {
            layoutParams.bottomMargin = cardView.getContext().getResources().getDimensionPixelSize(R.dimen.md_padding_16);
        } else {
            layoutParams.bottomMargin = 0;
        }

        ImageView imageView = holder.mImageView;
        PicassoHelper.INSTANCE.picasso().load(business.getImageUrl()).into(imageView);

        cardView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), DetailActivity.class);
            intent.putExtra(DetailActivity.INTENT_PARAM_BUSINESS, business);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mDataset == null ? 0 : mDataset.size();
    }

    public void setAdapterData(List<Business> adapterData) {
        this.mDataset = adapterData;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public CardView mCardView;
        public ImageView mImageView;

        public ViewHolder(CardView v) {
            super(v);
            mTextView = ButterKnife.findById(v, R.id.card_textview);
            mImageView = ButterKnife.findById(v, R.id.card_image);
            mCardView = v;
        }
    }
}

