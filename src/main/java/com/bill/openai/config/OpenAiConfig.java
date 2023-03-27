package com.bill.openai.config;

import com.unfbx.chatgpt.OpenAiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenAiConfig {

    @Bean
    public OpenAiClient openAiApi(@Value("${openai.api.key}") String apikey) {
        OpenAiClient client = new OpenAiClient(apikey);
        return client;
    }


}
