package com.example.ecommerceiteapp.feature.file;

import com.example.ecommerceiteapp.feature.file.dto.FileUploadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService{

@Value("${file.storage-location}")
private String storageLocation;
@Value("${file.base-uri}")
private String baseUri;

    @Override
    public FileUploadResponse upload(MultipartFile file) {
        //prepare file fromation
        //file name
        String name= UUID.randomUUID().toString();

        //mypro.file.png
        String ext=file.getOriginalFilename()
                .substring(file.getOriginalFilename().lastIndexOf(".")+1);

        name+="."+ext;//new inique-filename.exg

        //create sbsolute part
//        Path path= Paths.get(storageLocation+name);
        Path path=Paths.get(storageLocation+name);


        try{
            Files.copy(file.getInputStream(),path);

        }catch (IOException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "File has been failed to upload");
        }



        return FileUploadResponse.builder()
                .name(name)
                .size(file.getSize())
                .mediaType(file.getContentType())
                .uri(baseUri + name)
                .build();
    }



}
