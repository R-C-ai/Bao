package com.hsiaoling.bao.utils

import android.content.Context
import android.graphics.Rect
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.hsiaoling.bao.R
import com.hsiaoling.bao.data.IEvent


class EventView : FrameLayout {


    lateinit var mEvent: IEvent

    protected var mEventClickListener: OnEventClickListener? = null

    lateinit var mEventHeader: RelativeLayout

    lateinit var mEventContent: LinearLayout

    lateinit var mEventHeaderText1: TextView

    lateinit var mEventHeaderText2: TextView

    lateinit var mEventName: TextView

    val headerHeight: Int
        get() = mEventHeader.measuredHeight

    val headerPadding: Int
        get() = mEventHeader.paddingBottom + mEventHeader.paddingTop

//    constructor(parcel: Parcel) : this {
//
//    }

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }

    protected fun init(attrs: AttributeSet?) {
        LayoutInflater.from(context).inflate(R.layout.view_event, this, true)

        mEventHeader = findViewById(R.id.item_event_header) as RelativeLayout
        mEventContent = findViewById(R.id.item_event_content) as LinearLayout
        mEventName = findViewById(R.id.item_event_name) as TextView
        mEventHeaderText1 = findViewById(R.id.item_event_header_text1) as TextView
        mEventHeaderText2 = findViewById(R.id.item_event_header_text2) as TextView

        super.setOnClickListener {
            if (mEventClickListener != null) {
                mEventClickListener!!.onEventClick(this@EventView, mEvent)
            }
        }

        val eventItemClickListener = OnClickListener { v ->
            if (mEventClickListener != null) {
                mEventClickListener!!.onEventViewClick(v, this@EventView, mEvent)
            }
        }

        //        mEventHeaderText1.setOnClickListener(eventItemClickListener);
        mEventHeaderText1.setOnClickListener { Log.d("Test", "clicked 1") }
        mEventHeaderText2.setOnClickListener { Log.d("Test", "clicked 2") }
        mEventContent.setOnClickListener { Log.d("Test", "clicked content") }
    }

    fun setOnEventClickListener(listener: OnEventClickListener) {
        this.mEventClickListener = listener
    }

    override fun setOnClickListener(l: View.OnClickListener?) {
        throw UnsupportedOperationException("you should use setOnEventClickListener()")
    }

    fun setEvent(event: IEvent) {
        this.mEvent = event
        mEventName.setText(event.name)
        mEventContent.setBackgroundColor(event.color)
    }

    fun setPosition(rect: Rect, topMargin: Int, bottomMargin: Int) {
        val params = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params.topMargin =
            rect.top - headerHeight - headerPadding + topMargin - resources.getDimensionPixelSize(R.dimen.cdv_extra_dimen)
        params.height = (rect.height()
                + headerHeight
                + headerPadding
                + bottomMargin
                + resources.getDimensionPixelSize(R.dimen.cdv_extra_dimen))
        params.leftMargin = rect.left
        layoutParams = params
    }

    interface OnEventClickListener {
        fun onEventClick(view: EventView, data: IEvent)
        fun onEventViewClick(view: View, eventView: EventView, data: IEvent)
    }

//    override fun writeToParcel(parcel: Parcel, flags: Int) {

//    }

//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<EventView> {
//        override fun createFromParcel(parcel: Parcel): EventView {
//            return EventView(parcel)
//        }
//
//        override fun newArray(size: Int): Array<EventView?> {
//            return arrayOfNulls(size)
//        }
//    }
}