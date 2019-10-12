package spa.lyh.cn.mymusic;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import spa.lyh.cn.mymusic.base.BaseActivity;

public class MainActivity extends BaseActivity {
    CoordinatorLayout c;
    AppBarLayout appBarLayout;

    TextView head,down;
    String content;

    View statusBar;

    RelativeLayout headArea;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTranslucent();
        //隐藏掉导航栏
        setSystemUiVisibility(getWindow().getDecorView(),View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        c = findViewById(R.id.main_content);
        head = findViewById(R.id.head);
        down = findViewById(R.id.down);
        content = down.getText().toString();
        //head.setText(content);


        statusBar = findViewById(R.id.status_bar);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(this));
        statusBar.setLayoutParams(layoutParams);


        headArea = findViewById(R.id.headArea);

        //headArea.setMinimumHeight(dip2px(this,50) + getStatusBarHeight(this));

        appBarLayout = findViewById(R.id.appbar);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                float offset = -i;
                float height = appBarLayout.getHeight()-dip2px(MainActivity.this,100);
                float b = offset / height;
                Log.e("liyuhao",b+"");

                if (b > 0.95){
                    new Handler(getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            head.setText(content);
                            down.setText("");
                        }
                    });

                }else {
                    new Handler(getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            head.setText("");
                            down.setText(content);
                        }
                    });
                }
            }
        });



    }


    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale);
    }

}
