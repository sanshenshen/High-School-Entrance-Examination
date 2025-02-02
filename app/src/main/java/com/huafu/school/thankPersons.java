package com.huafu.school;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;



public class thankPersons extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_thank_persons, container, false);

        Toolbar toolbar = rootview.findViewById(R.id.thanksToolbar);
        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            activity.setSupportActionBar(toolbar);
            // 显示返回按钮
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            // 取消显示标题，如果你不需要标题
            // activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Drawable drawable = toolbar.getNavigationIcon();
        if (drawable != null) {
            DrawableCompat.setTint(drawable, Color.WHITE);  // 设置图标颜色为白色
            toolbar.setNavigationIcon(drawable);  // 更新图标
        }
        // 设置标题颜色为白色
        toolbar.setTitleTextColor(Color.WHITE);
        return rootview;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // 处理返回按钮点击事件，返回到上一个 Fragment
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            getActivity().onBackPressed();  // 调用 Activity 的 onBackPressed()
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}