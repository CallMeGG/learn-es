package com.example;

import org.apache.http.HttpHost;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ElasticSearchAddDocument {

    public static void main(String[] args) throws IOException {
        ElasticSearchAddDocument searchAddDocument = new ElasticSearchAddDocument();
        searchAddDocument.addDoc();
        System.out.println("完成");

    }

    public void addDoc() throws IOException {


        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost(
                "101.200.87.141:9200/", 9200, "http"
        )));

        //赋值
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("phone", "1513893");
        jsonMap.put("qq", "2413");
        jsonMap.put("weixin", "1513893");
        IndexRequest request = new IndexRequest("sse_test").id("1").source(jsonMap);
        //相应
        //同步执行的写法(当以下列方式执行IndexRequest时，客户端在继续执行代码之前，会等待返回索引响应:)
        // 同步调用可能会在高级REST客户端中解析REST响应失败、请求超时或类似服务器没有响应的情况下抛出IOException
        //  在服务器返回4xx或5xx错误代码的情况下，高级客户端会尝试解析响应主体错误详细信息，然后抛出一个通用的弹性响应异常，并将原始响应异常作为抑制异常添加到其中。
        //IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);

        //异步执行(我们也可以用异步方式执行IndexRequest，以便客户端可以直接返回。用户需要通过向异步索引方法传递请求和侦听器来指定如何处理响应或潜在故障:)
        ActionListener<IndexResponse> listener = new ActionListener<IndexResponse>() {
            @Override
            public void onResponse(IndexResponse indexResponse) {
            }

            @Override
            public void onFailure(Exception e) {
            }
        };
        client.indexAsync(request, RequestOptions.DEFAULT, listener);// listener是执行完成时要使用的侦听器

        //String index = indexResponse.getIndex();
        //String id = indexResponse.getId();
        //System.out.println("index: "+index+"  "+"id:  "+id);

    }
}