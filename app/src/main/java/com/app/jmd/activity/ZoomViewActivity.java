package com.app.jmd.activity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.jmd.R;
import com.app.jmd.helper.TouchImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class ZoomViewActivity extends AppCompatActivity {

    TouchImageView touch_image_view;
    ImageView iv_back;
    TextView product_name;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_zoom_view);

        touch_image_view = findViewById(R.id.touch_image_view);
        iv_back=findViewById(R.id.iv_back);
        iv_back.setVisibility(View.VISIBLE);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        product_name=findViewById(R.id.tv_header);
        name = getIntent().getStringExtra("productname");
        String url = getIntent().getStringExtra("image_url");
        if (url != null) {
            Glide.with(this)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.NONE) // No disk caching
                    .skipMemoryCache(true) // Skip memory cache
                    .placeholder(R.drawable.dum_img) // Placeholder while loading
                    .error(R.drawable.dum_img) // Error image if load fails
                    .into(touch_image_view);
        } else {
            touch_image_view.setImageResource(R.drawable.dum_img);
        }
        product_name.setText(name);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


}