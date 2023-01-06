package com.example.myapplication

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat


class CircularSectionIndicatorView : View {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    enum class State {
        NORMAL {
            override fun color():Int {
                return R.color.black
            }
        },
        VISITED{
            override fun color():Int {
                return R.color.purple_700
            }
        }, ACTIVE{
            override fun color():Int {
               return R.color.teal_200
            }
        }, SKIPPED{
            override fun color():Int {
                return R.color.purple_200
            }
        };
        abstract fun color():Int
    }

    private val spacing = 5F
    private var mRadius:Int = 0
    private var mSteps:Int = 0
    private var mStrokeWidth:Int = 0

    private val mStateMap = mutableMapOf<Int,State>()
    private val mPaintsStateMap = mutableMapOf<State,Paint>()

    fun setup(radius:Int, width:Int, stateMap:Map<Int,State>?=null){
        setup(radius,width)
        mSteps = stateMap?.size?:1
        mStrokeWidth = width
        stateMap?.let {
            mStateMap.putAll(stateMap)
        }
    }

    fun setup(radius:Int, width:Int, steps:Int){
        setup(radius,width)
        mSteps = steps
        mStrokeWidth = width
        for(step in 1..steps){
            mStateMap[step] = State.NORMAL
        }
    }

    private fun setup(radius:Int, width:Int){
        mRadius = radius
        State.values().forEach {
            mPaintsStateMap[it] = preparePaint(width,it)
        }
    }

    fun updateState(position:Int,state:State){
        mStateMap[position] = state
        invalidate()
    }

    private fun preparePaint(width: Int,state:State):Paint{
        return Paint(Paint.ANTI_ALIAS_FLAG).apply {
            strokeCap = Paint.Cap.ROUND
            style = Paint.Style.STROKE
            isAntiAlias = true
            isDither = true
            strokeWidth = width.toFloat()
            color = ContextCompat.getColor(this@CircularSectionIndicatorView.context,state.color())
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawD(canvas)
    }

    private fun drawD(canvas: Canvas?){
        canvas?:return
        val oval = RectF(mStrokeWidth.toFloat(), mStrokeWidth.toFloat(),  mStrokeWidth+(mRadius*2).toFloat(), mStrokeWidth+(mRadius*2).toFloat())
        arcs(mSteps,oval,canvas)
    }

    private fun arcs(steps:Int, oval:RectF,canvas: Canvas){
        val sweepAngle =  360/steps
        var startAngle = 0
        for(position in 1 .. steps){
            val state = if(mStateMap.containsKey(position)) mStateMap[position] else State.NORMAL
            val paint = mPaintsStateMap[state]?:preparePaint(mStrokeWidth,State.NORMAL)
            val startA = startAngle.toFloat()+spacing-90
            val sweepA = sweepAngle.toFloat()-(spacing*2)
            Log.d(this.javaClass.simpleName,"Starting Angle - $startAngle with sweep angle - $sweepAngle and state - $state" )
            Log.d(this.javaClass.simpleName,"Actual Angle - $startA with sweep angle - $sweepA" )
            canvas.drawArc(oval,  startA, sweepA, false, paint)
            startAngle += sweepAngle
        }
    }
}