package com.hsiaoling.bao.utils

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.annotation.NonNull
import com.hsiaoling.bao.R
import com.hsiaoling.bao.data.IEvent
import com.hsiaoling.bao.data.IPopup
import com.hsiaoling.bao.data.ITimeDuration
import java.util.*


//class CalendarDayView : FrameLayout {
//
//    private var mDayHeight = 0
//
//    private var mEventMarginLeft = 0
//
//    private var mHourWidth = 120
//
//    private var mTimeHeight = 120
//
//    private var mSeparateHourHeight = 0
//
//    private var mStartHour = 0
//
//    private var mEndHour = 24
//
//    private var mLayoutDayView: LinearLayout? = null
//
//    private var mLayoutEvent: FrameLayout? = null
//
//    private var mLayoutPopup: FrameLayout? = null
//
////    var decoration: CdvDecoration? = null
////        private set
//
//    private var mEvents: List<IEvent>? = null
//
//    private var mPopups: List<IPopup>? = null
//
//    constructor(context: Context) : super(context) {
//        init(null)
//    }
//
//    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
//        init(attrs)
//    }
//
//    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
//        context,
//        attrs,
//        defStyleAttr
//    ) {
//        init(attrs)
//    }
//
//    private fun init(attrs: AttributeSet?) {
//        LayoutInflater.from(context).inflate(R.layout.fragment_add, this, true)
//
//        mLayoutDayView = findViewById(R.id.dayview_container) as LinearLayout
//        mLayoutEvent = findViewById(R.id.event_container) as FrameLayout
//        mLayoutPopup = findViewById(R.id.popup_container) as FrameLayout
//
//        mDayHeight = resources.getDimensionPixelSize(R.dimen.dayHeight)
//
//        if (attrs != null) {
//            val a = context.obtainStyledAttributes(attrs, R.styleable.CalendarDayView)
//            try {
//                mEventMarginLeft = a.getDimensionPixelSize(
//                    R.styleable.CalendarDayView_eventMarginLeft,
//                    mEventMarginLeft
//                )
//                mDayHeight =
//                    a.getDimensionPixelSize(R.styleable.CalendarDayView_dayHeight, mDayHeight)
//                mStartHour = a.getInt(R.styleable.CalendarDayView_startHour, mStartHour)
//                mEndHour = a.getInt(R.styleable.CalendarDayView_endHour, mEndHour)
//            } finally {
//                a.recycle()
//            }
//        }
//
//        mEvents = ArrayList< IEvent>()
//        mPopups = ArrayList< IPopup>()
////        decoration = CdvDecorationDefault(context)
//
//        refresh()
//    }
//
//    fun refresh() {
//        drawDayViews()
//
//        drawEvents()
//
//        drawPopups()
//    }
//
//    private fun drawDayViews() {
//        mLayoutDayView!!.removeAllViews()
//        var dayView: DayView? = null
//        for (i in mStartHour..mEndHour) {
//            dayView = decoration!!.getDayView(i)
//            mLayoutDayView!!.addView(dayView)
//        }
//        mHourWidth = dayView!!.getHourTextWidth()
//        mTimeHeight = dayView!!.getHourTextHeight()
//        mSeparateHourHeight = dayView!!.getSeparateHeight()
//    }
//
//    private fun drawEvents() {
//        mLayoutEvent!!.removeAllViews()
//
//        for (event in mEvents!!) {
//            val rect = getTimeBound(event)
//
//            // add event view
//            val eventView = decoration!!.getEventView(event, rect, mTimeHeight, mSeparateHourHeight)
//            if (eventView != null) {
//                mLayoutEvent!!.addView(eventView, eventView!!.getLayoutParams())
//            }
//        }
//    }
//
//    private fun drawPopups() {
//        mLayoutPopup!!.removeAllViews()
//
//        for (popup in mPopups!!) {
//            val rect = getTimeBound(popup!!)
//
//            // add popup views
//            val view = decoration!!.getPopupView(popup, rect, mTimeHeight, mSeparateHourHeight)
//            if (popup != null) {
//                mLayoutPopup!!.addView(view, view.getLayoutParams())
//            }
//        }
//    }
//
//    private fun getTimeBound(event: ITimeDuration): Rect {
//        val rect = Rect()
//        rect.top = getPositionOfTime(event.getStartTime()) + mTimeHeight / 2 + mSeparateHourHeight
//        rect.bottom = getPositionOfTime(event.getEndTime()) + mTimeHeight / 2 + mSeparateHourHeight
//        rect.left = mHourWidth + mEventMarginLeft
//        rect.right = width
//        return rect
//    }
//
//    private fun getPositionOfTime(calendar: Calendar): Int {
//        val hour = calendar.get(Calendar.HOUR_OF_DAY) - mStartHour
//        val minute = calendar.get(Calendar.MINUTE)
//        return hour * mDayHeight + minute * mDayHeight / 60
//    }
//
//    fun setEvents(events: List<IEvent>) {
//        this.mEvents = events
//        refresh()
//    }
//
//    fun setPopups(popups: List<IPopup>) {
//        this.mPopups = popups
//        refresh()
//    }
//
//    fun setLimitTime(startHour: Int, endHour: Int) {
//        require(startHour < endHour) { "start hour must before end hour" }
//        mStartHour = startHour
//        mEndHour = endHour
//        refresh()
//    }
//
//    /**
//     * @param decorator decoration for draw event, popup, time
//     */
//    fun setDecorator(@NonNull decorator: CdvDecoration) {
//        this.decoration = decorator
//        refresh()
//    }
//}