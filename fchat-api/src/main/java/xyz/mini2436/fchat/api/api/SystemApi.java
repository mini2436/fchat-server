package xyz.mini2436.fchat.api.api;

import cn.hutool.core.util.IdUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import xyz.mini2436.fchat.api.model.vo.ApiVo;
import xyz.mini2436.fchat.api.model.vo.SeaweedFsVo;
import xyz.mini2436.fchat.api.utils.FileUtil;
import xyz.mini2436.fchat.model.vo.ResultVO;

import java.nio.file.Paths;

/**
 * 系统API
 *
 * @author mini2436
 * @date 2021-07-15 09:19
 **/
@RequestMapping("system")
@RestController
@RequiredArgsConstructor
public class SystemApi extends ApiVo {
    private final FileUtil fileUtil;

    @Value("${fchat.seaweedFs.windowsTempFilePath}")
    private String windowsTempFilePath;

    /**
     * 上传文件
     *
     * @return 返回文件服务器的fid与文件的访问URL
     */
    @PostMapping("uploadFile")
    Mono<ResultVO<SeaweedFsVo>> uploadFile(@RequestPart("file") Mono<FilePart> filePart) {
        String nameId = IdUtil.simpleUUID();
        filePart.flatMap(it -> it.transferTo(Paths.get(windowsTempFilePath + nameId + "-" + it.filename()))).subscribe();
        return filePart.flatMap(v -> fileUtil.uploadFile(windowsTempFilePath + nameId + "-" + v.filename()))
                .flatMap(this::success);
    }

    /**
     * 根据文件的Fid获取文件的访问连接
     *
     * @param fid 需要获取的文件唯一标识
     * @return 返回文件服务器查询到的文件数据
     */
    @GetMapping("fileByFid/{fid}")
    Mono<SeaweedFsVo> getFileByFid(@PathVariable("fid") Mono<String> fid) {
        return null;
    }

}
