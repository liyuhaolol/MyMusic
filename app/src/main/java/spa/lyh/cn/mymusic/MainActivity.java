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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.EmptySignature;

import spa.lyh.cn.mymusic.base.BaseActivity;

public class MainActivity extends BaseActivity implements AppBarLayout.OnOffsetChangedListener {
    CoordinatorLayout c;
    AppBarLayout appBarLayout;

    TextView head,down;

    View statusBar;

    RelativeLayout headArea;

    int length;

    ImageView img;

    private static final int UNSET = -1;
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
        head.setVisibility(View.INVISIBLE);

        img = findViewById(R.id.img);
        RequestOptions options = new RequestOptions()
                .sizeMultiplier(1f)
                .useUnlimitedSourceGeneratorsPool(false)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .priority(Priority.NORMAL)
                .error(null)
                .placeholder(null)
                .skipMemoryCache(false)
                .override(UNSET,UNSET)
                .signature(EmptySignature.obtain())
                .fallback(null)
                .theme(null)
                //.transform()
                .onlyRetrieveFromCache(false);
        //.format(DecodeFormat.PREFER_ARGB_8888);

        Glide.with(this)
                .asDrawable()
                .apply(options)
                .load("http://gss0.baidu.com/9fo3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/241f95cad1c8a78603c996956009c93d71cf504e.jpg")
                .into(img);


        statusBar = findViewById(R.id.status_bar);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(this));
        statusBar.setLayoutParams(layoutParams);


        headArea = findViewById(R.id.headArea);

        length = dip2px(this,90) + getStatusBarHeight(this);
        headArea.setMinimumHeight(length);

        appBarLayout = findViewById(R.id.appbar);


    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        //之所以要这么写是为了避免这个弱引用被回收
        float offset = -i;
        float height = appBarLayout.getHeight()-length;
        float b = offset / height;
        //applayout内部尽量都为固定高度，否则容易出现像素抖动问题
        if (b > 0.95){
            head.setVisibility(View.VISIBLE);
            down.setVisibility(View.INVISIBLE);
        }else {
            head.setVisibility(View.INVISIBLE);
            down.setVisibility(View.VISIBLE);
        }
    }


    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale);
    }

    @Override
    protected void onResume() {
        super.onResume();
        appBarLayout.addOnOffsetChangedListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        appBarLayout.removeOnOffsetChangedListener(this);
    }
}
