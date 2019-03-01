package com.example.simplechat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private List<Message> mMessages;

    private Context mContext;

    private String mUserId;



    public ChatAdapter(Context context, String userId, List<Message> messages) {

        mMessages = messages;

        this.mUserId = userId;

        mContext = context;

    }



    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.item_chat, parent, false);



        ViewHolder viewHolder = new ViewHolder(contactView);

        return viewHolder;

    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Message message = mMessages.get(position);

        final boolean isMe = message.getUserId().equals(mUserId);

        if (isMe) {
            holder.imageMe.setVisibility(View.VISIBLE);
            //holder.imageOther.setVisibility(View.GONE);
            Glide.with(mContext).load(getProfileUrl(message.getUserId())).into(holder.imageMe);
            Log.d("pic", "Binding image to me");

        }
        else if(!isMe) {
            holder.imageOther.setVisibility(View.VISIBLE);
            //holder.imageMe.setVisibility(View.GONE);
            Glide.with(mContext).load(getProfileUrl(message.getUserId())).into(holder.imageOther);
            Log.d("pic", "Binding image to other");
        }

        holder.body.setText(message.getBody());

    }



    // Create a gravatar image based on the hash value obtained from userId

    private static String getProfileUrl(final String userId) {

        String hex = "";

        try {
            final MessageDigest digest = MessageDigest.getInstance("MD5");
            final byte[] hash = digest.digest(userId.getBytes());
            final BigInteger bigInt = new BigInteger(hash);
            hex = bigInt.abs().toString(16);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("pic", "http://www.gravatar.com/avatar/" + hex + "?d=identicon");
        return "http://www.gravatar.com/avatar/" + hex + "?d=identicon";

    }



    @Override

    public int getItemCount() {

        return mMessages.size();

    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageOther;

        ImageView imageMe;

        TextView body;



        public ViewHolder(View itemView) {

            super(itemView);

            imageOther = (ImageView)itemView.findViewById(R.id.ivProfileOther);

            imageMe = (ImageView)itemView.findViewById(R.id.ivProfileOther);

            body = (TextView)itemView.findViewById(R.id.tvBody);

        }

    }

}