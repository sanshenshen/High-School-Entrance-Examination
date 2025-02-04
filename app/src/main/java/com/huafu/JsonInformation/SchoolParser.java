package com.huafu.JsonInformation;

import android.content.Context;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.io.InputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
public class SchoolParser {
    public static List<SchoolInformation> parseSchoolJson(Context context) {
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
            List<SchoolInformation> schools = schoolArray.toJavaList(SchoolInformation.class);
            return schools;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return java.util.Collections.emptyList();
    }
}

