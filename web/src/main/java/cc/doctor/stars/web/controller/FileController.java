package cc.doctor.stars.web.controller;

import cc.doctor.stars.biz.exception.BusinessException;
import cc.doctor.stars.web.dto.common.Response;
import cc.doctor.stars.web.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping("file")
@RestController
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("upload")
    public Response<Integer> upload(@RequestParam MultipartFile file) throws IOException {
        return fileService.upload(file);
    }

    @GetMapping("download/{fileId}")
    public void download(@PathVariable Integer fileId, HttpServletResponse response) throws IOException, BusinessException {
        fileService.download(fileId, response);
    }
}
