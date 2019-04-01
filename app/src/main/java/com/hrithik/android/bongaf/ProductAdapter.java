package com.hrithik.android.bongaf;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
    public void onBindViewHolder(final ProductViewHolder holder, int position) {
        int type;
        final Product product = productList.get(position);

        final RequestOptions options = new RequestOptions();





      //  video=product.getVideo();
      //  holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(product.getImage(), null));

        type=product.getType();

        if(type==1) {

            Glide.with(mCtx).load(product.getImage()).override(1280, 720).apply(options).into(holder.imageViewVideo);

            holder.parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    video = product.getVideo();

                    if (interstitialAd.isLoaded()) {

                        interstitialAd.show();
                    } else {
                        Intent i = new Intent(mCtx.getApplicationContext(), Play.class);
                        i.putExtra("message", video);
                        mCtx.startActivity(i);
                    }
                }
            });

        }

        else if(type==0){

            //meme
            Glide.with(mCtx).load(product.getImage()).override(1024, 1024).apply(options).into(holder.imageViewMeme);

        }

        else if(type==2){
            Glide.with(mCtx).load(product.getImage()).override(1280, 720).apply(options).into(holder.imageViewVideo);


            holder.parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=null;
                    intent =new Intent(Intent.ACTION_VIEW);
                    intent.setPackage("com.google.android.youtube");
                    intent.setData(Uri.parse("https://www.youtube.com/channel/UC67oXKcRUlUoW_Np8N9gabA/videos"));
                    if (intent != null) {
                        mCtx.startActivity(intent);//null pointer check in case package name was not found
                    }
                    else{
                        Toast.makeText(mCtx.getApplicationContext(), "Youtube App Not Installed", Toast.LENGTH_LONG).show();
                    }

                }
            });

        }


    }


    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewVideo;
        ImageView imageViewMeme;
        TextView textViewTitle;
        CardView cardView;
        RelativeLayout parentLayout;
        ImageView play;


        public ProductViewHolder(View itemView) {
            super(itemView);

            imageViewVideo = itemView.findViewById(R.id.imageViewVideo);
            imageViewMeme = itemView.findViewById(R.id.imageViewMeme);
            play = itemView.findViewById(R.id.playbtn);
            cardView = (CardView) itemView.findViewById(R.id.cardview);
            parentLayout = itemView.findViewById(R.id.cardlayout);

        }
        }

    }




