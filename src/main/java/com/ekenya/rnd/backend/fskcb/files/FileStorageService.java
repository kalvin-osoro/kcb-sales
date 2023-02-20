package com.ekenya.rnd.backend.fskcb.files;

import com.ekenya.rnd.backend.utils.Utility;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class FileStorageService implements IFileStorageService {

    Logger log = Logger.getLogger(FileStorageService.class.getName());
    public static String pathString = "upload";
    public static String uploadDirectory = "upload";
    private final Path root = Paths.get(uploadDirectory);
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
            Path file = Paths.get(uploadDirectory)
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

    @Override
    public Resource loadFileAsResourceByName(String fileName) {
        try {
//            Path filePath = Paths.get(uploadDirectory +"/voomaOnboardingMerchant/").resolve(fileName).normalize();
            Path filePath = Paths.get(uploadDirectory + "/" + Utility.getSubFolder() + "/").resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("File not found " + fileName, ex);
        }
    }




    @Override
    public String saveFileWithSpecificFileName(String fileName, MultipartFile file) {
        try {
            fileName = new File(pathString + fileName).getName();
            file.transferTo(new File(fileName));
            log.info("Path is " + fileName);
            return fileName;
        } catch (Exception e) {
            log.info("Could not store the file. Error in saveFileWithSpecificFileName: "
                    + e.getMessage());
            return "";
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
            fileName = new File(subDirectory + "/" + fileName).getName();
//            file.transferTo(new File(fileName));
//            log.info("Path is " + fileName);
            Path targetLocation = subDirectory.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            log.info("Path is " + fileName);
            return fileName;

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    @Override
    public List<String> saveMultipleFileWithSpecificFileNameV(String module, MultipartFile[] files, String folderName) {
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
                    Path subDirectory = Paths.get(uploadDirectory + "/" + folderName);
                    if (!Files.exists(subDirectory)) {
                        Files.createDirectories(subDirectory);
                    }
                    path = Files.write(Paths.get(subDirectory + "/" + fileName), bytes);
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



    //save file
    public void save(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(),
                    this.root.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    @Override
    public Resource loadFileAsResource(String fileName, String folderName) {
       try {
           Path targetLocation =Paths.get(String.valueOf(Path.of(String.valueOf(uploadDirectory),"/" + folderName + "/")))
                   .resolve(fileName)
                   .normalize();
           Resource resource =new UrlResource(targetLocation.toUri());
           if (resource.exists()) {
               return resource;
           } else {
               throw new RuntimeException("File not found " + fileName);
           }
       } catch (MalformedURLException e) {
           throw new RuntimeException(e);
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
    }

}



