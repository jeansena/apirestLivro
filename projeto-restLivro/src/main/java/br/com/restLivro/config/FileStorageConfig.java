package br.com.restLivro.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

//classe de configuração #upload e dawnload

 //#upload e dawnload
//file vem la da application.yml
@Configuration
@ConfigurationProperties(prefix = "file")
public class FileStorageConfig {

    //uploadDir e usado no application.yml
    private  String uploadDir;

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}
