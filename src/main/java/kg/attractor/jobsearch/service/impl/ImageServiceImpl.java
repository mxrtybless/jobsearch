package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.service.ImageService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {
    private static final String uploadDir = "data/images";

    @Override
    public ResponseEntity<?> download(String filename) throws IOException {
        try {
            Path filePath = Paths.get(uploadDir).resolve(filename);
            byte[] file = Files.readAllBytes(filePath);

            Resource resource = new ByteArrayResource(file);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .contentLength(resource.contentLength())
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);
        } catch (NoSuchFileException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image not found");
        }
    }

    @Override
    public String upload(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is required.");
        }

        String originalFilename = file.getOriginalFilename();

        if (originalFilename == null || originalFilename.isBlank()) {
            throw new IllegalArgumentException("File name is required.");
        }

        String uuid = UUID.randomUUID().toString();
        String resultFilename = uuid + "_" + originalFilename;

        try {
            Path pathDir = Paths.get(uploadDir);
            Files.createDirectories(pathDir);

            Path filePath = pathDir.resolve(resultFilename);

            try (OutputStream out = Files.newOutputStream(filePath)) {
                out.write(file.getBytes());
            }

            return resultFilename;
        } catch (IOException e) {
            throw new RuntimeException("Could not save image.", e);
        }
    }
}