package kg.attractor.jobsearch.controller.api;

import kg.attractor.jobsearch.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping("download")
    public ResponseEntity<?> download(
            @RequestParam(name = "filename")
            String filename
    ) throws IOException {
        return imageService.download(filename);
    }

    @PostMapping(
            value = "upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public String upload(
            @RequestParam("file")
            MultipartFile file
    ) {
        return imageService.upload(file);
    }
}