package com.example.mymoneyapp.common.view

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.mymoneyapp.R


class CardVisibility : FrameLayout {
    private lateinit var card: CardView
    private lateinit var constraint: ConstraintLayout
    private lateinit var imgVisibility: ImageView
    private lateinit var textView: TextView
    private var visible: Boolean = true
    private var text: String? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setup(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs,
        defStyleAttr
    ) {
        setup(context, attrs)
    }

    private fun setup(context: Context, attrs: AttributeSet?) {
        val inflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.card_visibility, this)

        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.CardVisibility, 0, 0)
        text = typeArray.getString(R.styleable.CardVisibility_text)

        card = getChildAt(0) as CardView
        constraint = card.getChildAt(0) as ConstraintLayout
        textView = constraint.getChildAt(0) as TextView
        imgVisibility = constraint.getChildAt(1) as ImageView

        imgVisibility.setOnClickListener {
            showVisibility(visible)
        }

        typeArray.recycle()
    }

    fun setText(text: String) {
        this.text = text
        invalidate()
        requestLayout()
    }
    fun showVisibility(v: Boolean) {
        if (v) {
            textView.text = text
            imgVisibility.setImageResource(com.example.mymoneyapp.R.drawable.ic_visibility)
            visible = false
        } else {
            textView.text = "*****"
            imgVisibility.setImageResource(com.example.mymoneyapp.R.drawable.ic_visibility_gone)
            visible = true

        }
    }



}