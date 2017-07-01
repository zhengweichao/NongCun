package com.bishe.nongcun.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.bishe.nongcun.R;
import com.stephentuso.welcome.BasicPage;
import com.stephentuso.welcome.TitlePage;
import com.stephentuso.welcome.WelcomeActivity;
import com.stephentuso.welcome.WelcomeConfiguration;

public class VWelcomeActivity extends WelcomeActivity {

    @Override
    protected WelcomeConfiguration configuration() {
        return new WelcomeConfiguration.Builder(this)
                .defaultBackgroundColor(R.color.colorAccent)
                .page(new TitlePage(R.mipmap.girl,
                        "Title")
                )
                .page(new BasicPage(R.mipmap.girl,
                        "Header",
                        "More text.")
                        .background(R.color.base_progress_bar_color)
                )
                .page(new BasicPage(R.drawable.base_empty,
                        "Lorem ipsum",
                        "dolor sit amet.")
                )
                .swipeToDismiss(true)
                .build();
    }
}
