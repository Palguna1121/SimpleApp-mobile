package com.example.simpleapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.simpleapp.adapter.ImageCarouselAdapter;

import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    ViewPager2 viewPager;

    private Runnable runnable;
    private Handler handler = new Handler(Looper.getMainLooper());

    private int currentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.vpcarousel);

        List<String> images = Arrays.asList(
                "https://i.pinimg.com/736x/f6/b7/04/f6b704066c127e47d81e410d90e4fb48.jpg",
                "https://i.pinimg.com/736x/76/b1/75/76b175f09523097ae364b498d3233a8c.jpg",
                "https://i.pinimg.com/736x/07/4e/cd/074ecd78bd2986eb77632dd1c9bf8c56.jpg",
                "https://i.pinimg.com/736x/ec/5a/7c/ec5a7c4a605fb5ddd41855044068e2f8.jpg"
        );

        ImageCarouselAdapter adapter = new ImageCarouselAdapter(this, images);
        viewPager.setAdapter(adapter);

        viewPager.setPageTransformer(new DepthPageTransformer());

        runnable = new Runnable() {
            @Override
            public void run() {
                if (currentPage == images.size()) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
                handler.postDelayed(this, 3000);
            }
        };
        handler.postDelayed(runnable, 3000);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}