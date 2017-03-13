package com.bozo.spectrumview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * 音频频谱
 * <p>
 * Created by CJ on 2017/3/13 0013.
 *
 * @version 1.0
 */

public class SpectrumView extends View {

    private static final String TAG = SpectrumView.class.getSimpleName();

    private static final int DEFAULT_COLOR = Color.argb(255, 56, 144, 239);

    private static final int DEFAULT_NOTE_WIDTH = 6;

    private static final int DEFAULT_SPACE_WIDTH = 2;

    private static final int DEFAULT_LINE_SIZE = 2;

    /*
        音符列表
     */
    private List<Integer> notes = new ArrayList<>();

    /*
        音符画笔
     */
    private Paint paint;
    /*
        倒影画笔
     */
    private Paint reflectionPaint;
    /*
        音符颜色
     */
    private int color;
    /*
        倒影颜色
     */
    private int reflectionColor;
    /*
        音符宽度
     */
    private float noteWidth;
    /*
        空格宽度
     */
    private float space;
    /*
        中线高度
     */
    private float line;

    public SpectrumView(Context context) {
        super(context);
        init();
    }

    public SpectrumView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpectrumView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TintTypedArray typedArray = TintTypedArray.obtainStyledAttributes(context, attrs, R.styleable.SpectrumView, defStyleAttr, 0);
        color = typedArray.getColor(R.styleable.SpectrumView_sv_color, DEFAULT_COLOR);
        noteWidth = typedArray.getDimension(R.styleable.SpectrumView_sv_note, DEFAULT_NOTE_WIDTH);
        space = typedArray.getDimension(R.styleable.SpectrumView_sv_space, DEFAULT_SPACE_WIDTH);
        line = typedArray.getDimension(R.styleable.SpectrumView_sv_line, DEFAULT_LINE_SIZE);
        typedArray.recycle();

        reflectionColor = color - 0xC0000000;

        init();
    }

    private void init() {
        //初始化画笔
        paint = new Paint();
        reflectionPaint = new Paint();

        paint.setColor(color);
        reflectionPaint.setColor(reflectionColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        //画一条中线
        canvas.drawRect(0, height / 2 - line / 2, width, height / 2 + line / 2, paint);

        //循环向画布上画音频刻度
        for (int i = 0, size = notes.size(); i < size; i++){
            int noteHeight = notes.get(i);
            //音符
            float left = width - (noteWidth * (1 + i) + space * i);
            float top = height / 2 - noteHeight - space - line/2;
            float right = width - (noteWidth * i + space * i);
            float bottom = height / 2 - space - line/2;
            canvas.drawRect(left, top, right, bottom, paint);
            //倒影
            float refLeft = width - (noteWidth * (1 + i) + space * i);
            float refTop = height / 2 + space + line/2;
            float refRight = width - (noteWidth * i + space * i);
            float refBottom = height / 2 + noteHeight + space + line/2;
            canvas.drawRect(refLeft, refTop, refRight, refBottom, reflectionPaint);
        }
    }

    /**
     * 添加一个刻度
     * @param height 高度
     */
    public void addSpectrum(int height) {
        notes.add(0, height);
        invalidate();
    }
}