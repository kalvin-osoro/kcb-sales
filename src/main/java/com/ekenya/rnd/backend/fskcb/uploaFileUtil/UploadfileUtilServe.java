package com.ekenya.rnd.backend.fskcb.uploaFileUtil;

import org.springframework.web.multipart.MultipartFile;

public interface UploadfileUtilServe{
    String saveFileWithSpecificFileName1(String fileName, MultipartFile file,String folderName);


}
