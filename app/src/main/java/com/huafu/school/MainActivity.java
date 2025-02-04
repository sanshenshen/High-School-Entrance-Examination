package com.huafu.school;

import android.os.Bundle;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    private Home homeFragment;
    private Concerning concerningFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化控件
        bottomNavigationView = findViewById(R.id.buttonPanel);
        ImageView imageView = findViewById(R.id.backgroundImage);
        imageView.setAlpha(0.5f);
        // 默认首页选中
        selectedFragment(0);

        // bottomNavigationView点击事件
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home) {
                    selectedFragment(0);
                } else {
                    selectedFragment(1);
                }
                return true;
            }
        });
    }

    private void selectedFragment(int position) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        // 设置动画
        if (position == 0) {
            fragmentTransaction.setCustomAnimations(
                    R.anim.enter_anim,    // 新 Fragment 进入时的动画（从右滑入）
                    R.anim.exit_anim      // 当前 Fragment 退出时的动画（从左滑出）
            );
        } else {
            fragmentTransaction.setCustomAnimations(
                    R.anim.pop_enter_anim,    // 返回时进入的动画（从左滑入）
                    R.anim.pop_exit_anim      // 当前 Fragment 退出时的动画（从右滑出）
            );
        }

        // 隐藏当前显示的 Fragment
        hideFragment(fragmentTransaction);

        // 根据选中位置切换对应的 Fragment
        if (position == 0) {
            if (homeFragment == null) {
                homeFragment = new Home();
                fragmentTransaction.add(R.id.content, homeFragment, Home.class.getName());
            } else {
                fragmentTransaction.show(homeFragment);
            }
        } else {
            if (concerningFragment == null) {
                concerningFragment = new Concerning();
                fragmentTransaction.add(R.id.content, concerningFragment, Concerning.class.getName());
            } else {
                fragmentTransaction.show(concerningFragment);
            }
        }

        // 提交事务
        fragmentTransaction.commit();
    }

    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if (homeFragment != null) {
            fragmentTransaction.hide(homeFragment);
        }
        if (concerningFragment != null) {
            if(Concerning.thankPersons != null) {
                fragmentTransaction.hide(Concerning.thankPersons);
            }
            fragmentTransaction.hide(concerningFragment);
        }
    }
}
