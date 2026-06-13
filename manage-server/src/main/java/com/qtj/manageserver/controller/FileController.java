package com.qtj.manageserver.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.qtj.manageserver.common.CustomException;
import com.qtj.manageserver.common.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.UUID;

@RestController
public class FileController {

    // 上传文件夹
    @Value("${files.upload.local-path}")
    private String localDir;

    @Value("${files.upload.virtual-path}")
    private String virtualDir;

    @Value("${files.download.path}")
    private String downloadDir;

    @PostMapping("/upload")
    public Result<String> uploadFile(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws IOException {
        String extName = FileUtil.extName(file.getOriginalFilename());
        // 每天一个文件夹
        String dirName = DateUtil.format(new Date(), "yyyyMMdd");
        File dir = Path.of(localDir, dirName).toFile();
        if(!dir.exists()) {
            dir.mkdirs();
        }
        // 随机文件名
        String uuid = UUID.randomUUID().toString();
        String fileName = uuid + "." + extName;
        File desFile = Path.of(localDir, dirName, fileName).toFile();
        // 保存文件
        file.transferTo(desFile);
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String url = baseUrl + virtualDir + dirName + "/" + fileName;
        return Result.success(url);
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) throws MalformedURLException, UnsupportedEncodingException {
        Path path = Paths.get(downloadDir).resolve(filename).normalize();
        Resource resource = new UrlResource(path.toUri());
        if(resource.exists() && resource.isReadable()) {
            // Content-Type 数据流
            // Content-Disposition 附件
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + URLEncoder.encode(resource.getFilename(), StandardCharsets.UTF_8) + "\"")
                    .body(resource);
        } else {
            throw new CustomException("文件找不到");
        }
    }
}
