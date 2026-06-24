package com.example.ecommerceiteapp.feature.file;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "files")
@Setter
@Getter
@NoArgsConstructor
public class FileUpload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(nullable = false, unique = true)
    private  String name;

    @Column(nullable = false)
    private String caption;
    @Column(nullable = false)
    private Long size;
    @Column(nullable = false)
    private String mediaType;



}
