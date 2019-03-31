package com.hrithik.android.bongaf;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.List;
class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {

    Context mCtx;
    List<Product> productList;
    private InterstitialAd interstitialAd;
    String video;
    int rate;


    public ProductsAdapter(final Context mCtx, List<Product> productList) {
        this.mCtx = mCtx;
        this.productList = productList;



        interstitialAd = new InterstitialAd(mCtx);
        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        interstitialAd.loadAd(new AdRequest.Builder().build());
        interstitialAd.setAdListener(new AdListener(){
    @Override
    public void onAdClosed() {
        Intent i = new Intent(mCtx.getApplicationContext(),Play.class);
        i.putExtra("message",video );
        mCtx.startActivity(i);
        interstitialAd.loadAd(new AdRequest.Builder().build());
    }
});

    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.product_layout,
                parent, false);
        ProductViewHolder productViewHolder = new ProductViewHolder(view);
        return productViewHolder;


    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        final Product product = productList.get(position);
        holder.textViewTitle.setText(product.getTitle());

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);



        Glide.with(mCtx).load(product.getImage()).apply(options).into(holder.imageView);

      //  video=product.getVideo();
      //  holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(product.getImage(), null));

holder.parentLayout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {



        video = product.getVideo();

        if(interstitialAd.isLoaded())
        {

            interstitialAd.show();
        }

        else{
            Intent i = new Intent(mCtx.getApplicationContext(),Play.class);
            i.putExtra("message",video );
            mCtx.startActivity(i);
        }
    }
});


    }


    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textViewTitle;
        CardView cardView;
        RelativeLayout parentLayout;


        public ProductViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            cardView = (CardView) itemView.findViewById(R.id.cardview);
            parentLayout = itemView.findViewById(R.id.cardlayout);

        }
        }

    }




