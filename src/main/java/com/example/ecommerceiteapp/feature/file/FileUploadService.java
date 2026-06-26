package com.example.ecommerceiteapp.feature.file;

import com.example.ecommerceiteapp.feature.file.dto.FileUploadResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileUploadService {

//    FileUploadResponse upload(MultipartFile file);
//
//    List<FileUploadResponse> uploadMultiple(MultipartFile[] files);
//
//
////    List<FileUploadResponse> deleteByName(String name) ;
//    void deleteByName(String name) ;
//
////    List<FileUploadResponse> uploadMultiplePath(List<MultipartFile> files);
//    List<FileUploadResponse> uploadMultiple(List<MultipartFile> files);
//
//    FileUploadResponse findByName(String name);
//
//    Page<FileUploadResponse> findAll(int pageNumber, int pagSize);


FileUploadResponse findByName(String name);

    Page<FileUploadResponse> findAll(int pageNumber, int pageSize);

    List<FileUploadResponse> uploadMultiple(List<MultipartFile> files);

    FileUploadResponse upload(MultipartFile file);

    void deleteByName(String name);

}