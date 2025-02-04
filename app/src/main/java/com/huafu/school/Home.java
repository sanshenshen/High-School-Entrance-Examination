package com.huafu.school;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


public class Home extends Fragment {
    private EditText editText;
    private Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        editText = view.findViewById(R.id.editText);
        button = view.findViewById(R.id.button);
        button.setOnClickListener(v -> {
            String inputText = editText.getText().toString().trim(); // 去除前后空格
            // 使用正则表达式验证输入是否为有效的数字或者两位小数
            String regex = "^\\d+(\\.\\d{1,2})?$"; // 匹配整数或者最多两位小数
            if (inputText.matches(regex)) {
                // 转为浮动类型进行范围检查
                float inputNumber = Float.parseFloat(inputText);
                if (inputNumber >= 584 && inputNumber <= 750) {
                    GoToSchool(inputText , view);
                } else {
                    Toast.makeText(getContext(), "这位同学的分数不合法哦", Toast.LENGTH_SHORT).show();
                }
            } else {
                // 输入的内容不是数字或者浮动数
                Toast.makeText(getContext(), "请输入数字，且最多保留两位小数", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private void GoToSchool(String value , View view) {
        // 创建目标 School 的实例
        School schoolFragment = new School();
        Bundle bundle = new Bundle();
        bundle.putString("key", value);  // 传递数据

        // 将数据传递给目标 Fragment
        schoolFragment.setArguments(bundle);
        // 开始 FragmentTransaction
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(
                R.anim.enter_anim,    // 进入动画
                R.anim.exit_anim,     // 退出动画
                R.anim.pop_enter_anim, // 返回时的进入动画
                R.anim.pop_exit_anim  // 返回时的退出动画
        );
        ImageView imageView = view.findViewById(R.id.backgroundImage);
        // 替换当前 Fragment 为 FragmentB
        transaction.replace(R.id.content , schoolFragment);

        // 可以选择将当前 Fragment 添加到回退栈，这样按返回键时能回到 FragmentA
        transaction.addToBackStack(null);
        // 提交事务
        transaction.commit();


    }
}