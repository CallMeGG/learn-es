package com.example;

import com.example.demo.ChineseName;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;
import java.util.*;

@Slf4j
public class ElasticSearchAddDocument {

    public static void main(String[] args) throws IOException {

        ElasticSearchAddDocument searchAddDocument = new ElasticSearchAddDocument();
        searchAddDocument.addDoc();
        System.out.println("完成");

    }

    public void addDoc() throws IOException {

        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost(
                "101.200.87.141", 9200, "http"
        )));

        IndexRequest request = new IndexRequest("gyp_test");

        List<Map<String, Object>> list = new ArrayList<>(1000000);

        for (int i = 1; i <= 1000000; i++) {
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("age", new Random().nextInt(4) + 18);
            jsonMap.put("gender", (new Random().nextInt(2) + 1) == 1 ? "男" : "女");
            jsonMap.put("user_name", ChineseName.getNewName());
            jsonMap.put("address", "四川省成都市犀浦镇百草路" + new Random().nextInt(100) + "号");
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
