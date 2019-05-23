package com.markoapps.feeds.utils

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewEmptySupport : RecyclerView {
    constructor(ctx: Context): super(ctx)
    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)
    constructor(ctx: Context, attrs: AttributeSet, defStyle: Int) : super(ctx, attrs, defStyle)

    private var emptyView : View? = null

    private val emptyObserver : AdapterDataObserver = object : AdapterDataObserver()  {

        override  fun onChanged() {

            if(adapter != null && emptyView != null) {
                if(adapter?.itemCount == 0) {
                    emptyView?.visibility = View.VISIBLE
                    this@RecyclerViewEmptySupport.setVisibility(View.GONE)
                }
                else {
                    emptyView?.visibility = View.GONE
                    this@RecyclerViewEmptySupport.setVisibility(View.VISIBLE)
                }
            }

        }
    };

    override fun setAdapter(adapter: Adapter<*>?) {
        super.setAdapter(adapter)

        adapter?.registerAdapterDataObserver(emptyObserver);

        emptyObserver.onChanged();
    }

    fun setEmptyView(view: View){
        emptyView = view
    }


}