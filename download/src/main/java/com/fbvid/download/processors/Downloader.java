package com.fbvid.download.processors;

import com.fbvid.download.dto.TelegramUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Downloader extends Thread {

    private TelegramUpdate telegramUpdate;
    private String chatid;
    private String sendVideoUrl;

    Logger log= LoggerFactory.getLogger(Downloader.class);


    public Downloader(TelegramUpdate telegramUpdate, String chatid, String sendVideoUrl){
        this.telegramUpdate=telegramUpdate;
        this.chatid=chatid;
        this.sendVideoUrl=sendVideoUrl;

    }
    public void run(){
        try{
            String data=telegramUpdate.getMessage().getText();
            long filename=System.currentTimeMillis();
            ProcessBuilder processBuilder = new ProcessBuilder("yt-dlp", "-f", "b[filesize<50M] / w","-o", "/downloaddata/"+filename+".mp4", data);
            log.debug(processBuilder.command().stream().reduce("", (seed, str)->{return seed+" "+str;}));
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    log.debug(line);
                }
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new RuntimeException("yt-dlp failed with exit code " + exitCode);
            }

            RestTemplate restTemplate = new RestTemplate();
            String url = "https://api.telegram.org/bot6954628939:AAFd9qHvhHsJAZF8NLsxkHyNmgwjF3dWRn8/sendVideo"; // Replace with your URL

            File videoFile = new File("/downloaddata/"+filename+".mp4");
            String chatId = "-1002203590566";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("video", new FileSystemResource(videoFile));
            body.add("chat_id", chatId);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

            log.debug("Response: "+ response.getBody());

        }
        catch(IOException | InterruptedException e){

        }

    }
}
