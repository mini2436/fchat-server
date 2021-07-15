package xyz.mini2436.fchat.api.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import xyz.mini2436.fchat.api.model.bo.SeaweedFs;
import xyz.mini2436.fchat.api.model.bo.SeaweedFsUpload;
import xyz.mini2436.fchat.api.model.vo.SeaweedFsVo;

/**
 * 文件处理工具类
 *
 * @author mini2436
 * @date 2021-07-15 10:31
 **/
@Component
@RequiredArgsConstructor
@Slf4j
public class FileUtil {
    @Value("${fchat.seaweedFs.host}")
    private String seaweedFsHost;

    @Value("${fchat.seaweedFs.port}")
    private Integer seaweedFsPort;

    @Value("${fchat.seaweedFs.windowsTempFilePath}")
    private String windowsTempFilePath;

    @Value("${fchat.seaweedFs.linuxTempFilePath}")
    private String linuxTempFilePath;

    private WebClient webClient = WebClient.create();

    /**
     * 获取文件服务器的存放Fid
     *
     * @return 返回文件服务器的请求数据
     */
    public Mono<SeaweedFs> getFid() {
        return webClient.get()
                .uri("http://" + seaweedFsHost + ":" +seaweedFsPort + "/dir/assign")
                .retrieve()
                .bodyToMono(SeaweedFs.class);
    }

    /**
     * 文件上传至文件服务器
     *
     * @param filePath 上传的文件
     * @return 返回上后的Fid以及文件访问URL地址
     */
    public Mono<SeaweedFsVo> uploadFile(String filePath) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("file", new FileSystemResource(filePath));
        return getFid().flatMap(fid -> webClient.post().uri("http://"+fid.getPublicUrl()+"/"+fid.getFid()).contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .bodyToMono(SeaweedFsUpload.class)
                .map(seaweedFsUpload -> SeaweedFsVo.builder().fid(fid.getFid()).url("http://"+fid.getPublicUrl()+"/"+fid.getFid()).build()));
    }
}
