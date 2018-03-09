package praise.animation.siqing.com.livepraiseanimation.path;


import android.animation.TypeEvaluator;
import android.util.Log;

import java.util.Random;

/**
 * Created by siqing on 2018/2/9
 * 估值器类,实现坐标点的计算
 */

public class TestPathEvaluator implements TypeEvaluator<TestPathPoint> {
    private String TAG = "TestPathEvaluator";
    private TestPathPoint currentPathPoint = new TestPathPoint();
    private TestPathPoint midPathPoint = null;

    /**
     * @param t          :执行的百分比
     * @param startValue : 起点
     * @param endValue   : 终点
     * @return
     */
    @Override
    public TestPathPoint evaluate(float t, TestPathPoint startValue, TestPathPoint endValue) {

        if (midPathPoint == null) {
            midPathPoint = new TestPathPoint();
            Random r = new Random();
            float f = r.nextFloat();
//            midPathPoint.x = startValue.x + (endValue.x - startValue.x) * f;
//            midPathPoint.y = startValue.y + (endValue.y - startValue.y) * f;

            midPathPoint.x = 500;
            midPathPoint.y = 0;

        }

//        currentPathPoint.x = t * (endValue.x - startValue.x);
//        currentPathPoint.y = t * (endValue.y - startValue.y);

        currentPathPoint.x = (1 - t) * (1 - t) * startValue.x + 2 * t * (1 - t) * midPathPoint.x + t * t * endValue.x;
        currentPathPoint.y = (1 - t) * (1 - t) * startValue.y + 2 * t * (1 - t) * midPathPoint.y + t * t * endValue.y;


        Log.i(TAG, "t = " + t + ",x == " + currentPathPoint.x + ", y == " + currentPathPoint.y);

        return currentPathPoint;
    }
}
