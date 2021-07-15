package xyz.mini2436.fchat.api.utils;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import xyz.mini2436.fchat.api.model.bo.SeaweedFs;
import xyz.mini2436.fchat.api.model.bo.SeaweedFsUpload;
import xyz.mini2436.fchat.api.model.vo.SeaweedFsVo;

import java.io.File;

/**
 * 文件处理工具类
 *
 * @author mini2436
 * @date 2021-07-15 10:31
 **/
@Component
@RequiredArgsConstructor
public class FileUtil {
    @Value("${fchat.seaweedFs.host}")
    private String seaweedFsHost;

    @Value("${fchat.seaweedFs.port}")
    private Integer seaweedFsPort;

    @Value("${fchat.seaweedFs.windowsTempFilePath}")
    private String windowsTempFilePath;

    @Value("${fchat.seaweedFs.linuxTempFilePath}")
    private String linuxTempFilePath;

    /**
     * 获取文件服务器的存放Fid
     *
     * @return 返回文件服务器的请求数据
     */
    public Mono<SeaweedFs> getFid() {
        WebClient webClient = WebClient.create();
        return webClient.get()
                .uri("http://" + seaweedFsHost + ":" +seaweedFsPort + "/dir/assign")
                .retrieve()
                .bodyToMono(SeaweedFs.class);
    }

    /**
     * 文件上传至文件服务器
     *
     * @param filePart 上传的文件
     * @return 返回上后的Fid以及文件访问URL地址
     */
    public Mono<SeaweedFsVo> uploadFile(Mono<FilePart> filePart) {
        // 保存到临时文件
        return filePart.map(file -> {
            File localUploadFile = new File(System.getProperty("os.name").toUpperCase().startsWith("WIN")
                    ? windowsTempFilePath + IdUtil.simpleUUID() + file.filename()
                    : linuxTempFilePath + IdUtil.simpleUUID() + file.filename());
            file.transferTo(localUploadFile);
            return localUploadFile;
        }).flatMap(localUploadFile -> {
            FileSystemResource resource = new FileSystemResource(localUploadFile);
            // 封装请求参数
            MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
            param.add("myFile", resource);
            // 将临时文件上传到文件服务器
            Mono<SeaweedFs> fid = getFid().defaultIfEmpty(SeaweedFs.builder().build());
            WebClient webClient = WebClient.create();
            return fid.flatMap(seaweedFs ->
                    webClient.post()
                    .uri("http://" + seaweedFs.getPublicUrl() + "/" + seaweedFs.getFid())
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromMultipartData(param))
                    .retrieve()
                    .bodyToMono(SeaweedFsUpload.class))
                    .defaultIfEmpty(SeaweedFsUpload.builder().build())
                    .map(seaweedFsUpload -> StrUtil.isBlank(seaweedFsUpload.getETag())
                            ? SeaweedFsVo.builder().build()
                            : SeaweedFsVo.builder().fid(fid.block().getFid()).url(fid.block().getPublicUrl()+"/"+fid.block().getFid()).build()
                    );
        });
    }
}
