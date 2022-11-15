package com.example.teamproject;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewDecoration extends RecyclerView.ItemDecoration {
    private final int divHeight;

    public RecyclerViewDecoration(int divHeight){
        this.divHeight =divHeight;
    }

    public void getItemOffsets(Rect outRect, View view,RecyclerView parent, RecyclerView.State state){
        super.getItemOffsets(outRect,view,parent,state);
        if(parent.getChildAdapterPosition(view)!=parent.getAdapter().getItemCount()-1)
            outRect.bottom=divHeight;
    }

    public void addItemDecoration(RecyclerViewDecoration recyclerViewDecoration) {
    }
}

