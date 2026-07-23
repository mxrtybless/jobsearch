package kg.attractor.jobsearch.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    String upload(MultipartFile file);

    ResponseEntity<?> download(String filename) throws IOException;
}