package com.example.ecommerceiteapp.feature.file;

import com.example.ecommerceiteapp.feature.file.dto.FileUploadResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileUploadService {

    FileUploadResponse upload(MultipartFile file);

    List<FileUploadResponse> uploadMultiple(MultipartFile[] files);

    void deleteByName(String name);


}
