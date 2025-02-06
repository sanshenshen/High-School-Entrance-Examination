package com.huafu.school;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.widget.ListView;

import com.huafu.JsonInformation.SchoolAdapter;
import com.huafu.JsonInformation.SchoolInformation;
import com.huafu.JsonInformation.SchoolParser;

import java.util.List;

public class School extends Fragment {

    private String fraction;
    private ListView listView;
    private SchoolAdapter adapter;
    private List<SchoolInformation> schoolList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 启用菜单项，Fragment 中的返回按钮会被触发
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_school, container, false);

        // 设置 Toolbar 并启用返回按钮
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            activity.setSupportActionBar(toolbar);
            // 显示返回按钮
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            // 取消显示标题，如果你不需要标题
            // activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        // 获取分数
        Bundle bundle = getArguments();
        if (bundle != null) {
            fraction = bundle.getString("key");
        }
        Log.d("sorce", fraction);
        // 设置返回键图标颜色为白色
        Drawable drawable = toolbar.getNavigationIcon();
        if (drawable != null) {
            DrawableCompat.setTint(drawable, Color.WHITE);  // 设置图标颜色为白色
            toolbar.setNavigationIcon(drawable);  // 更新图标
        }
        // 设置标题颜色为白色
        toolbar.setTitleTextColor(Color.WHITE);
        listView = view.findViewById(R.id.listView);
        schoolList = SchoolParser.parseSchoolJson(getContext());
        adapter = new SchoolAdapter(getContext(), schoolList , 0);
        listView.setAdapter(adapter);
        if (fraction != null && !fraction.isEmpty()) {
            try {
                double score = Double.parseDouble(fraction);  // 将分数从 String 转为 int
                adapter.filter(score);  // 过滤学校数据
            } catch (NumberFormatException e) {
                Log.e("score", "Invalid score format: " + fraction);
            }
        }
        listView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                // 当视图布局完成后，移除监听器并执行动画
                listView.getViewTreeObserver().removeOnPreDrawListener(this);  // 防止多次执行
                setListViewAnimation();  // 开始动画
                return true;
            }
        });
        return view;
    }
    private void setListViewAnimation() {
        // 获取 ListView 中的每一项，并给它们设置动画
        for (int i = 0; i < listView.getChildCount(); i++) {
            final View child = listView.getChildAt(i);
            // 设置透明度动画
            AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1f);
            alphaAnimation.setDuration(1000);  // 动画时长 1 秒
            alphaAnimation.setStartOffset(i * 100);  // 每一项的动画延迟 100ms，逐渐展开
            child.startAnimation(alphaAnimation);  // 启动动画
        }
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
