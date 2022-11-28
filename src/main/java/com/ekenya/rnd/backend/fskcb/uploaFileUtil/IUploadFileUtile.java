//package com.ekenya.rnd.backend.fskcb.uploaFileUtil;
//
//import com.ekenya.rnd.backend.utils.Utility;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//@Service
//public class IUploadFileUtile implements UploadfileUtilServe {
//    public static String uploadDirectory = System.getProperty("user.dir") + "/upload";
//
//
//    @Override
//    public String saveFileWithSpecificFileName(String fileName, MultipartFile file,String folderName) {
//       try {
//           File dir = new File(uploadDirectory);
//           if (!dir.exists()) {
//               dir.mkdirs();
//           }
//           File subDir = new File(uploadDirectory + "/" + folderName);
//           if (!subDir.exists()) {
//               subDir.mkdirs();
//           }
//           File serverFile = new File(subDir.getAbsolutePath() + "/" + fileName);
//              file.transferTo(serverFile);
//              return serverFile.getAbsolutePath();
//         }catch (Exception e){
//              e.printStackTrace();
//              return null;
//       }
//
//
//    }
//}
//
