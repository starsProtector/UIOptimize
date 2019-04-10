package com.sn.overdraw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * 自定义控件过渡绘制案例
 */

public class MyView extends FrameLayout {
    public MyView(@NonNull Context context) {
        super(context);
        init();
    }

    public MyView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //图片资源
    private final int[] ids = new int[]{R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4, R.drawable.img5, R.drawable.img6};
    private final Bitmap[] imgs = new Bitmap[ids.length];

    private Paint paint;
    //初始化,在构造方法里调用
    private void init() {
        //初始化数据,把图片数据通过BitmapFactory,转换为BitmapFactory,装入Bitmap数组中
        for (int i = 0; i < ids.length; i++) {
            imgs[i] = BitmapFactory.decodeResource(getResources(), ids[i]);
        }
        //创建画笔对象
        paint = new Paint();
        //打开抗锯齿
        paint.setAntiAlias(true);
    }

    /**
     * onDraw绘制方法,不要再此方法里创建对象,消耗内存,因为这个方法会被调用多次,导致对象多次创建
     * 扩展:不要在频繁调用的方法去创建对象
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //A.相邻两张牌错开20像素,这种写法没有优化,过度绘制很严重,可以打开工具看一下
/*        for (int i = 0; i < 6; i++) {
            //参数 1.图片bitmap  2.每次绘制比上一次水平间隔的距离,int型  3.固定0,每次绘制比上一次垂直间隔的距离,int型   4.画笔对象
            canvas.drawBitmap(imgs[i],i*100,0,paint);
        }*/

        // Canvas clipRect,仅绘制一张牌的一部分案例
        //B.裁剪出一块矩形区域，对于用户来说，大王这张牌仅能看到这块指定绘制区域
/*        canvas.clipRect(0,0,100,imgs[0].getHeight());//参数左,上,右,下
        canvas.drawBitmap(imgs[0],0,0,paint);*/

        //C.优化了过度绘制,只绘制显示的部分,不显示被遮挡的部分不看
        //提示:要让裁剪循环有效,必不可少的两个方法canvas.save();canvas.restore();两个方法配对出现
        for (int i = 0; i < imgs.length; i++) {
            //裁剪前调用此方法
            canvas.save();
            //前5张牌都是被遮挡住的
            if(i<imgs.length-1) {
                canvas.clipRect(i * 100, 0, (i + 1) * 100, imgs[i].getHeight());
            }
            //最后一张牌是完整画下来的
            else if(i==5){
                canvas.clipRect(i * 100, 0, i * 100+imgs[i].getWidth(), imgs[i].getHeight());
            }
            canvas.drawBitmap(imgs[i],i*100,0,paint);
            //裁剪后调用此方法
            canvas.restore();
        }
    }
}
