package com.fbvid.download.controller;

import com.fbvid.download.dto.TelegramUpdate;
import com.fbvid.download.processors.Downloader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;
import java.io.IOException;
import java.util.Set;

@RestController
public class DownloadController {
    @Value("${app.telegramSendUrl}")
    private String telegramVideoSendUrl;

    @Value("${app.chatid}")
    private String chatid;

    @Autowired
    Set<String> urlSet;

    @GetMapping("/data")
    public String getData(){
        return "sample";
    }

    @PostMapping("/downloadVideo")
    public String process(@RequestBody TelegramUpdate telegramUpdate) throws IOException, InterruptedException {
        if(urlSet.contains(telegramUpdate.getMessage().getText())) {
            return "video already present in group";
        } else{
            Downloader downloader=new Downloader(telegramUpdate, chatid, telegramVideoSendUrl);
            downloader.start();
            return "success";
        }

    }


}
