package com.example.wisienka.todomanager;

import android.view.View;

/**
 * Created by Wisienka on 2018-03-28.
 */

public interface RecyclerViewClickListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}