package praise.animation.siqing.com.livepraiseanimation.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by siqing on 2018/2/9
 */
public class PaintView extends View {
    public String TAG = "PaintView";

    private List<PriseModel> prewList = new ArrayList<>();
    private List<PriseModel> drawingList = new ArrayList<>();

    private Paint mPaint = new Paint();

    private CaluteThread caluteThread = null;
    private Object lock = new Object();


    public PaintView(Context context) {
        super(context);
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        makeSureThread();
    }

    public PaintView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        makeSureThread();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        synchronized (drawingList) {
            for (PriseModel priseModel : drawingList) {
                if (!priseModel.isEnd() && priseModel.isStarting && priseModel.getPathPoint() != null) {
                    Log.i(TAG, priseModel.getPathPoint().x + "=====" + priseModel.getPathPoint().y);
                    canvas.drawCircle(priseModel.getPathPoint().x, priseModel.getPathPoint().y, 30, mPaint);
                }
            }
        }
    }

    /**
     * 点赞调用
     *
     * @param prise
     */
    public void addPrise(int prise) {
        makeSureThread();
        PriseModel priseModel = new PriseModel();
        Random random = new Random();
        int ran = random.nextInt(4);
        priseModel.setDurtion(5000);
        priseModel.setInterpolator(new AccelerateDecelerateInterpolator(getContext(), null));
        synchronized (prewList) {
            prewList.add(priseModel);
        }
        notifyDrawing();
    }

    private void notifyDrawing() {
        if (caluteThread != null) {
            caluteThread.drawing();
        }
    }

    private void makeSureThread() {
        if (caluteThread == null) {
            caluteThread = new CaluteThread();
            caluteThread.start();
        }
    }


    /**
     * 计算线程(负责计算动画运动轨迹)
     */
    class CaluteThread extends Thread {

        @Override
        public void run() {
            while (true) {

                synchronized (lock) {
                    if (prewList.isEmpty() && drawingList.isEmpty()) {
                        try {
                            Log.e(TAG, "等待状态");
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                long start = System.currentTimeMillis();

                synchronized (prewList) {
                    if (!prewList.isEmpty()) {
                        PriseModel priseModel = prewList.remove(0);
                        priseModel.start();
                        synchronized (drawingList) {
                            drawingList.add(priseModel);
                        }
                    }
                }

                synchronized (drawingList) {
                    int length = drawingList.size();
                    for (int i = length - 1; i >= 0; i--) {
                        PriseModel priseModel = drawingList.get(i);
                        if (priseModel.isEnd()) {
                            drawingList.remove(i);
                            continue;
                        }
                        priseModel.calute();
                    }
                }

                long end = System.currentTimeMillis();

//                Log.e(TAG, "calute time " + (end - start));

                postInvalidate();

                try {
                    Thread.sleep(1000 / 60);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void drawing() {
            synchronized (lock) {
                lock.notify();
            }
        }
    }


}