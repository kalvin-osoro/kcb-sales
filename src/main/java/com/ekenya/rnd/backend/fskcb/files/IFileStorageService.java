package com.ekenya.rnd.backend.fskcb.files;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.List;

public interface IFileStorageService {
    List<String>uploadOneFile(MultipartFile file);

    List<String>multipleFiles(MultipartFile[] files);
    public Resource load(String filename);
    Resource loadFileAsResourceByName(String fileName);
//    List<String>multipleFilesWithDifferentParams(MultipartFile file1, MultipartFile file2, MultipartFile file3);
    String saveFileWithSpecificFileName(String fileName, MultipartFile file);
    List<String>saveMultipleFileWithSpecificFileName(String module, MultipartFile[] files);
    String saveFileWithSpecificFileNameV(String fileName, MultipartFile file,String folderName);
    List<String>saveMultipleFileWithSpecificFileNameV(String module, MultipartFile[] files,String folderName);
    public Resource loadFileAsResource(String fileName, String folderName);
}
