package com.wr.java.ai;


import dev.langchain4j.model.openai.OpenAiChatModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LLMTest {

    @Test
    public void testLLM() {
        //初始化模型
        OpenAiChatModel model = OpenAiChatModel.builder()
            .baseUrl("http://langchain4j.dev/demo/openai/v1")
                .apiKey("demo") //设置模型apiKey
                .modelName("gpt-4o-mini")
                .build();
        String anser = model.chat("你是谁啊？");
        System.out.println(anser);

    }

    @Autowired
    private OpenAiChatModel model;
    @Test
    public void testLLM2() {
        String chat = model.chat("今晚吃什么？");
        System.out.println(chat);

    }




}
