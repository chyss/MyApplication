package com.chyss.myapplication.view;

import android.content.Context;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.chyss.myapplication.R;

/**
 * @Description ClickableSpan
 */
public abstract class SpannableClickable extends ClickableSpan implements View.OnClickListener {

    private int textColor;

    public SpannableClickable(Context context) {
        this.textColor = context.getResources().getColor(R.color.red);
    }

    public SpannableClickable(int textColor) {
        this.textColor = textColor;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setColor(textColor);
        ds.setUnderlineText(false);
        ds.clearShadowLayer();
    }
}
