package com.huafu.JsonInformation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.huafu.school.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SchoolAdapter extends ArrayAdapter<SchoolInformation> {

    private Context context;
    private List<SchoolInformation> schoolList;
    private List<SchoolInformation> filteredList;  // 用于存储过滤后的数据
    private double Score;

    public SchoolAdapter(Context context, List<SchoolInformation> schoolList , double score) {
        super(context, 0, schoolList);
        this.context = context;
        this.Score = score;
        this.schoolList = schoolList;
        this.filteredList = new ArrayList<>(schoolList);  // 初始化为原始数据
    }

    // 过滤方法
    public void filter(double score) {
        this.Score = score;
        filteredList.clear();  // 清空过滤后的列表
        for (SchoolInformation school : schoolList) {
            if (school.getMinScore() <= score) {
                filteredList.add(school);  // 仅添加最低分数小于等于输入分数的项
            }
        }

        // 按照分数从高到低排序
        Collections.sort(filteredList, new Comparator<SchoolInformation>() {
            @Override
            public int compare(SchoolInformation school1, SchoolInformation school2) {
                return Integer.compare(school2.getMinScore(), school1.getMinScore());  // 反转顺序实现从高到低
            }
        });

        notifyDataSetChanged();  // 更新 ListView
    }

    @Override
    public int getCount() {
        return filteredList.size();  // 返回过滤后的项数
    }

    @Override
    public SchoolInformation getItem(int position) {
        return filteredList.get(position);  // 获取过滤后的每一项
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 获取当前项的学校信息
        SchoolInformation school = filteredList.get(position);

        // 如果当前项的视图为空，创建新的视图
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        }

        // 获取每个 TextView 控件
        TextView schoolNameTextView = convertView.findViewById(R.id.school_name);
        TextView schoolOrderTextView = convertView.findViewById(R.id.school_order);
        TextView schoolRankTextView = convertView.findViewById(R.id.school_rank);

        // 设置每个 TextView 的值
        schoolNameTextView.setText(school.getName());
        schoolOrderTextView.setText("最低次序: " + String.valueOf(school.getMinOrder()));
        schoolRankTextView.setText("最低分数: " + String.valueOf(school.getMinScore()));

        // 为每一项设置点击事件
        convertView.setOnClickListener(v -> {
            // 处理点击事件
            Toast.makeText(context, "点击了: " + school.getName(), Toast.LENGTH_SHORT).show();
        });

        return convertView;
    }
}
