package praise.animation.siqing.com.livepraiseanimation.view;

import android.view.animation.Interpolator;

import praise.animation.siqing.com.livepraiseanimation.path.TestPathEvaluator;
import praise.animation.siqing.com.livepraiseanimation.path.TestPathPoint;

/**
 * Created by siqing on 2018/2/9
 */
public class PriseModel {
    TestPathPoint pathPoint, startPathPoint, endPathPoint;
    TestPathEvaluator pathEvaluator = new TestPathEvaluator();
    private Interpolator interpolator;
    long startTime;
    long durtion = 1000;
    boolean isEnd = false;
    boolean isStarting = false;

    public PriseModel() {
        startPathPoint = new TestPathPoint();
        startPathPoint.x = 30;
        startPathPoint.y = 30;
        endPathPoint = new TestPathPoint();
        endPathPoint.x = 1000;
        endPathPoint.y = 500;
    }

    public void setInterpolator(Interpolator interpolator) {
        this.interpolator = interpolator;
    }

    public void setDurtion(long durtion) {
        this.durtion = durtion;
    }

    public void start() {
        startTime = System.currentTimeMillis();
        isStarting = true;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void calute() {
        pathPoint = pathEvaluator.evaluate(getT(), startPathPoint, endPathPoint);
    }

    private float getT() {
        long usedTime = System.currentTimeMillis() - startTime;
        float t = ((float) usedTime) / durtion;
        if (t > 1) {
            t = 1;
            isEnd = true;
            isStarting = false;
        }
        if (interpolator != null) {
            return interpolator.getInterpolation(t);
        }
        return t;
    }

    public TestPathPoint getPathPoint() {
        return pathPoint;
    }
}