package praise.animation.siqing.com.livepraiseanimation;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;

import praise.animation.siqing.com.livepraiseanimation.view.PaintView;


/**
 * Created by siqing on 2018/2/9
 */

public class TestActivity extends Activity {
    public String TAG = "TestActivity";
    private FrameLayout paintContainer;
    private PaintView paintView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        paintContainer = (FrameLayout) findViewById(R.id.paint_container);
        paintView = new PaintView(this);
        paintView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        paintContainer.addView(paintView);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void onPriseClick(View view) {
        paintView.addPrise(0);
    }


}
