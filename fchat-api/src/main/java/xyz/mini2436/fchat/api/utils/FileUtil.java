package xyz.mini2436.fchat.api.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import xyz.mini2436.fchat.model.bo.SeaweedFs;
import xyz.mini2436.fchat.model.bo.SeaweedFsUpload;
import xyz.mini2436.fchat.model.vo.SeaweedFsVo;
import xyz.mini2436.fchat.api.system.FchatYmlConfig;

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
    private final FchatYmlConfig fchatYmlConfig;

    private WebClient webClient = WebClient.create();

    /**
     * 获取文件服务器的存放Fid
     *
     * @return 返回文件服务器的请求数据
     */
    public Mono<SeaweedFs> getFid() {
        return webClient.get()
                .uri(fchatYmlConfig.getSeaweedFs().getUrl()+ "/dir/assign")
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
                .map(seaweedFsUpload -> SeaweedFsVo.builder().fid(fid.getFid()).url(fchatYmlConfig.getSeaweedFs().getUrl()+"/"+fid.getFid()).build()));
    }

    /**
     * 根据fid删除文件服务器文件
     * @param fid 文件的fid标识
     * @return 返回删除状态
     */
    public Mono<Boolean> delFileByFid(String fid){
        return webClient
                .delete()
                .uri(fchatYmlConfig.getSeaweedFs().getUrl()+"/"+fid)
                .retrieve()
                .bodyToMono(SeaweedFsUpload.class)
                .defaultIfEmpty(SeaweedFsUpload.builder().build())
                .map(v -> Boolean.TRUE);
    }
}
