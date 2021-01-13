package com.example.bookself_main_deneme.recylerview;

import android.view.animation.AnimationUtils;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookself_main_deneme.R;

import static android.os.Build.VERSION_CODES.R;

public class CustomItemAnimator extends DefaultItemAnimator {

    @Override
    public boolean animateRemove(RecyclerView.ViewHolder holder) {
        return super.animateRemove(holder);
    }

    @Override
    public boolean animateAdd(RecyclerView.ViewHolder holder) {


        holder.itemView.setAnimation(AnimationUtils.loadAnimation(
                holder.itemView.getContext(),
                com.example.bookself_main_deneme.R.anim.viewholder_add_anim
        ));

        return super.animateAdd(holder);
    }
}
