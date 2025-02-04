package com.huafu.JsonInformation;

import android.content.Context;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huafu.school.School;
import java.io.InputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
public class SchoolParser {
    public static void parseSchoolJson(Context context) {
        try {
            // 读取 assets 文件夹中的 JSON 文件
            InputStream is = context.getAssets().open("schools.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            // 将文件内容转换为字符串
            String jsonString = new String(buffer, StandardCharsets.UTF_8);

            // 解析 JSON 字符串
            JSONObject jsonObject = JSON.parseObject(jsonString);
            JSONArray schoolArray = jsonObject.getJSONArray("School");

            // 将解析的数组转为 List
            List<School> schools = schoolArray.toJavaList(School.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

