package com.example;

import com.example.demo.ChineseName;
import com.example.demo.ChinesePinyinUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;
import java.util.*;

@Slf4j
public class ElasticSearchAddDocumentTest {

    private static String citys[] = {"北京", "广东", "山东", "江苏", "河南", "上海", "河北", "浙江", "香港", "山西", "陕西", "湖南", "重庆", "福建", "天津", "云南", "四川", "广西", "安徽", "海南", "江西", "湖北", "山西", "辽宁", "内蒙古"};

    private static String jobs[] = {"student", "teacher", "driver", "waiter", "programmer", "loser"};


    public static void main(String[] args) throws IOException {

        ElasticSearchAddDocumentTest searchAddDocument = new ElasticSearchAddDocumentTest();
        searchAddDocument.addDoc();
        System.out.println("完成");

    }

    public void addDoc() throws IOException {

        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost(
                "101.200.87.141", 9200, "http"
        )));

        IndexRequest request = new IndexRequest("gyp_test1");

        List<Map<String, Object>> list = new ArrayList<>(100000);

        for (int i = 1; i <= 100000; i++) {
            Map<String, Object> jsonMap = new HashMap<>();
            Random r = new Random();

            String name = ChineseName.getName();
            String address = citys[r.nextInt(citys.length - 1)];
            String firstName = ChineseName.getFirstName();
            String fullName = firstName + name;
            String sex = (new Random().nextInt(2) + 1) == 1 ? "男" : "女";
            long age = r.nextInt(4) + 18;
            String job = jobs[r.nextInt(jobs.length - 1)];

            jsonMap.put("age", age);
            jsonMap.put("gender", sex);
            jsonMap.put("user_first_name", firstName);
            jsonMap.put("user_name", fullName);
            jsonMap.put("address", address);
            jsonMap.put("job", job);

            jsonMap.put("content", "My name is " + ChinesePinyinUtils.toPinyin(fullName) + "，sex " + (sex.equals("男") ? "man" : "woman") + "，I'm " + age + " years old，I come from " + ChinesePinyinUtils.toPinyin(address) + ",My job is " + job);
            list.add(jsonMap);
        }

        list.parallelStream().forEach(e -> {
            request.source(e);
            try {
                client.index(request, RequestOptions.DEFAULT);
            } catch (IOException ioException) {
                log.error(e + "出错");
            }
        });
    }


}
