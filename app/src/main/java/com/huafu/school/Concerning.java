package com.huafu.school;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class Concerning extends Fragment {

    public static thankPersons thankPersons;

    private String[] items = {"感谢人员以及说明", "学校官网", "软件版本：1.5.0"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_concerning, container, false);
        // 获取 ListView 控件
        ListView listView = rootview.findViewById(R.id.Concerning);
        // 创建适配器，绑定数据到 ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext() , android.R.layout.simple_list_item_1 , items);
        // 设置适配器
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            if (position == 2) {
                return ;  // 第 3 项不可点击
            }
            //嵌套fragment的底部导航栏切换实在太累！不想写了
            if(position == 0) {
                thankPersons = new thankPersons();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setCustomAnimations(
                        R.anim.pop_enter_anim,    // 进入动画
                        R.anim.pop_exit_anim,     // 退出动画
                        R.anim.enter_anim, // 返回时的进入动画
                        R.anim.exit_anim  // 返回时的退出动画
                );
                transaction.add(R.id.content, thankPersons, thankPersons.class.getName());
                transaction.replace(R.id.content ,thankPersons);
                // 可以选择将当前 Fragment 添加到回退栈，这样按返回键时能回到 FragmentA
                 transaction.addToBackStack(null);
                // 提交事务
                transaction.commit();
            } else {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.huafu2019.com/"));
                startActivity(intent);
            }
        });
        return rootview;
    }
}