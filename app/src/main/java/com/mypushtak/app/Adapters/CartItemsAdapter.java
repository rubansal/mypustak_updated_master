package com.mypushtak.app.Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mypushtak.app.R;
import com.mypushtak.app.Singleton.CartItems;
import com.mypushtak.app.Singleton.ProductviewSignleton;
import com.squareup.picasso.Picasso;

import java.util.List;


/*
 ****@author Anubhav Kumar
 * *****
 */

public class CartItemsAdapter extends RecyclerView.Adapter<CartItemsAdapter.ViewHolder> {

    private Context context;
    private List<CartItems> cartItems;

    public CartItemsAdapter(Context context, List<CartItems> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
    }


    @NonNull
    @Override
    public CartItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_container,parent,false);

        return new CartItemsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemsAdapter.ViewHolder holder, int position) {

        final CartItems cartItem=cartItems.get(position);

        String thumb=cartItem.getThumb();
        int book_id=cartItem.getBook_id();
        String name=cartItem.getTitle();
        String authors=cartItem.getAuthor();
        int price=cartItem.getPrice();
        int qty=cartItem.getQty();
        int shipping=cartItem.getShipping();
        int handling=cartItem.getHandelling();

        int ship_hand=shipping+handling;
        holder.title.setText(name);
        holder.mrp.setText("MRP: "+context.getResources().getString(R.string.Rs)+price);
        holder.shipping.setText("SHIPPING/HANDELLING: "+context.getResources().getString(R.string.rs)+ship_hand);
        holder.qty.setText("QTY :"+qty);


//        Log.d("adapters","  "+thumb+"  "+book_id+"   "+"   "+name+"   "+author+"    "+price);

        Uri uri= Uri.parse("https://s3.amazonaws.com/mypustak_new/uploads/books/"+thumb);

        Log.d("adapters22",""+book_id+"   "+uri+"          "+name);
//        icon.setImageURI(uri);

        Picasso.get().load(uri).fit().into(holder.book_image);

    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }



    protected class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title,mrp,shipping,qty;
        private ImageView book_image;
        private Button remove,wishlist;

        public ViewHolder(View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.cart_container_title);
            mrp=itemView.findViewById(R.id.cart_container_mrp);
            shipping=itemView.findViewById(R.id.cart_container_shipping);
            qty=itemView.findViewById(R.id.cart_containers_quantity);
            book_image=itemView.findViewById(R.id.cart_container_image);
            remove=itemView.findViewById(R.id.cart_container_remove);
            wishlist=itemView.findViewById(R.id.cart_container_wishlist);
        }
    }
}

