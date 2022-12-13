package com.ekenya.rnd.backend.fskcb.files;

import com.ekenya.rnd.backend.utils.Utility;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
@Service
public class FileStorageService implements IFileStorageService {

    Logger log = Logger.getLogger(FileStorageService.class.getName());
    //working directory folder called "uploads"

    public static String pathString ="upload";
    public static String uploadDirectory =  "upload";
    private final Path root = Paths.get(pathString);
    public static String uploadPath = "upload";


    @Override
    public List<String> uploadOneFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("Failed to store empty file");
        }
        try {
            String fileName = new File(uploadPath + file.getOriginalFilename()).getName();
            file.transferTo(new File(fileName));
        } catch (IOException e) {
            log.info("Error is {} " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<String> multipleFiles(MultipartFile[] files) {
        List<String> fileNames = new ArrayList<>();
        try {
            Arrays.asList(files).stream().forEach(file -> {
                save(file);
                fileNames.add(pathString + "" + file.getOriginalFilename());
            });
            return fileNames;
        } catch (Exception e) {
            log.info("Error in method " + e.getMessage());
            return fileNames;
        }
    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = Paths.get(uploadPath)
                    .resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }


//    @Override
//    public List<String> multipleFilesWithDifferentParams(MultipartFile file1, MultipartFile file2, MultipartFile file3) {
//        try {
//            saveFileWithSpecificFileName("file1.PNG", file1);
//            saveFileWithSpecificFileName("file2.PNG", file2);
//            saveFileWithSpecificFileName("file3.PNG", file3);
//        } catch (Exception e) {
//            log.info("Error in method " + e.getMessage());
//        }
//        return null;
//    }

    @Override
    public String saveFileWithSpecificFileName(String fileName, MultipartFile file) {
        try {
            Path subDirectory = Paths.get(uploadDirectory + "/subfolder");
            if (!Files.exists(subDirectory)) {
                Files.createDirectories(subDirectory);
            }
            //copy file to subdirectory
            Path copyLocation = Paths.get(subDirectory + File.separator + fileName);
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
            return copyLocation.toString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }



    }

    @Override
    public List<String> saveMultipleFileWithSpecificFileName(String module, MultipartFile[] files) {
        List<String> listFilePath = new ArrayList<>();
        try {
            Arrays.stream(files).forEach(file -> {
                String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
                String fileName = module.concat(Utility.generateUniqueNoByDate()).
                        concat(".").concat(fileExtension);
                byte[] bytes = new byte[0];
                Path path = null;
                try {
                    bytes = file.getBytes();
                    path = Files.write(Paths.get(uploadDirectory, fileName), bytes);
                } catch (IOException e) {
                    log.info("Error is ");
                }
                String filePath = path.toString();
                listFilePath.add(filePath);
//                log.info("Path is " + filePath);

            });
            return listFilePath;
        } catch (Exception e) {
            log.info("Could not store the file. Error in saveFileWithSpecificFileName: "
                    + e.getMessage());
            return listFilePath;

        }
    }

    @Override
    public String saveFileWithSpecificFileNameV(String fileName, MultipartFile file, String folderName) {
        try {
            Path subDirectory = Paths.get(uploadDirectory + "/" + folderName);
            if (!Files.exists(subDirectory)) {
                Files.createDirectories(subDirectory);
            }
            //copy file to subdirectory
            Path copyLocation = Paths.get(subDirectory + File.separator + fileName);
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
            return copyLocation.toString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    //save file
        public void save(MultipartFile file){
            try {
                Files.copy(file.getInputStream(),
                        this.root.resolve(file.getOriginalFilename()));
            } catch (Exception e) {
                throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
            }
        }

//    public FileMD store(MultipartFile file, String userName) throws Exception {
//        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
//        try {
//            if (file.isEmpty()) {
//                throw new Exception("Sorry ! file empty ");
//            }
//            if (fileName.contains("..")) {
//                throw new Exception("Sorry! Filename contains invalid path sequence " + fileName);
//            }
//
//            Path targetLocation = Paths.get(String.valueOf(Files.createDirectories(Path.of(String.valueOf(this.fileStorageLocation), "/" + userName + "/")))).toAbsolutePath().normalize().resolve(fileName);
//            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
//            String fileDownLoadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                    //.path("/app/uploads/" + userName + "/")
//                    .path("/uploads/" + userName + "/").path(fileName).toUriString();
//            logger.info("file download location ===>" + fileDownLoadUri);
//            FileMD fileDB = new FileMD();
//            fileDB.setName(fileName);
//            fileDB.setSize(file.getSize());
//            fileDB.setData(file.getBytes());
//            fileDB.setType(file.getContentType());
//            fileDB.setUri(fileDownLoadUri);
//            return fileRepository.save(fileDB);
//        } catch (Exception e) {
//            throw new Exception("Could not store file " + fileName + ". Please try again!", e);
//        }
//    }



}



