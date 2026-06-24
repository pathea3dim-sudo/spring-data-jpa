package com.example.ecommerceiteapp.feature.file;

import com.example.ecommerceiteapp.feature.file.dto.FileUploadResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUploadService {

    FileUploadResponse upload(MultipartFile file);



}
