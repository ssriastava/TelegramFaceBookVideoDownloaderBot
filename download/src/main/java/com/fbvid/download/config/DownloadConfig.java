package com.fbvid.download.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class DownloadConfig {

    @Bean
    public Set<String> getsynchronizedSet(){
        Set<String> hs = new HashSet<String>();
        return Collections.synchronizedSet(hs);
    }

}
