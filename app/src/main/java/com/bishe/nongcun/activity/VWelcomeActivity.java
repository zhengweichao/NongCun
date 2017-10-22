package com.bishe.nongcun.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.bishe.nongcun.R;
import com.stephentuso.welcome.BasicPage;
import com.stephentuso.welcome.TitlePage;
import com.stephentuso.welcome.WelcomeActivity;
import com.stephentuso.welcome.WelcomeConfiguration;

/**
 * 欢迎页面
 */
public class VWelcomeActivity extends WelcomeActivity {

    @Override
    protected WelcomeConfiguration configuration() {
        return new WelcomeConfiguration.Builder(this)
                .defaultBackgroundColor(R.color.colorAccent)
                .page(new TitlePage(R.mipmap.girl,
                        "城农通")
                )
                .page(new BasicPage(R.mipmap.girl,
                        "城农通",
                        "方便快捷")
                        .background(R.color.base_progress_bar_color)
                )
                .page(new BasicPage(R.drawable.base_empty,
                        "安全",
                        "快速找到你想要的")
                )
                .swipeToDismiss(true)
                .build();
    }
}
