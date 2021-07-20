package xyz.mini2436.fchat.api.api;

import cn.hutool.core.util.IdUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import xyz.mini2436.fchat.api.model.vo.ApiVo;
import xyz.mini2436.fchat.model.vo.SeaweedFsVo;
import xyz.mini2436.fchat.api.system.FchatYmlConfig;
import xyz.mini2436.fchat.api.utils.FileUtil;
import xyz.mini2436.fchat.model.vo.ResultVo;

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
    private final FchatYmlConfig fchatYmlConfig;

    /**
     * 上传文件
     *
     * @return 返回文件服务器的fid与文件的访问URL
     */
    @PostMapping("uploadFile")
    Mono<ResultVo<SeaweedFsVo>> uploadFile(@RequestPart("file") Mono<FilePart> filePart) {
        String nameId = IdUtil.simpleUUID();
        // 判定系统版本进行文件上传
        String oSType = "WIN";
        String osName = "os.name";
        if (System.getProperty(osName).toUpperCase().startsWith(oSType)){
            filePart.flatMap(it -> it.transferTo(Paths.get(fchatYmlConfig.getSeaweedFs().getWindowsTempFilePath() + nameId + "-" + it.filename()))).subscribe();
            return filePart.flatMap(v -> fileUtil.uploadFile(fchatYmlConfig.getSeaweedFs().getWindowsTempFilePath() + nameId + "-" + v.filename()))
                    .flatMap(this::success);
        }else {
            filePart.flatMap(it -> it.transferTo(Paths.get(fchatYmlConfig.getSeaweedFs().getLinuxTempFilePath() + nameId + "-" + it.filename()))).subscribe();
            return filePart.flatMap(v -> fileUtil.uploadFile(fchatYmlConfig.getSeaweedFs().getLinuxTempFilePath() + nameId + "-" + v.filename()))
                    .flatMap(this::success);
        }
    }

    /**
     * 根据文件的Fid获取文件的访问连接
     *
     * @param fid 需要获取的文件唯一标识
     * @return 返回文件服务器查询到的文件数据
     */
    @GetMapping("fileByFid/{fid}")
    Mono<ResultVo<SeaweedFsVo>> getFileByFid(@PathVariable("fid") String fid) {
        return Mono.just(fid).flatMap(fidStr -> this.success(SeaweedFsVo.builder().fid(fidStr)
                .url(fchatYmlConfig.getSeaweedFs().getUrl()+"/"+fidStr).build()));
    }

    /**
     * 根据文件的Fid删除文件在文件服务器上面的存储
     * @param fid 需要删除的文件唯一标识
     * @return 返回删除成功的信息
     */
    @DeleteMapping("fileByFid/{fid}")
    Mono<ResultVo<Boolean>> delFileByFid(@PathVariable("fid")String fid){
        return Mono.just(fid).flatMap(fileUtil::delFileByFid).flatMap(this::success);
    }
}