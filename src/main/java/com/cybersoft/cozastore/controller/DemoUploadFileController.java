package com.cybersoft.cozastore.controller;

import com.cybersoft.cozastore.exception.CustomFileNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
@RequestMapping("/demo")
public class DemoUploadFileController {
    //Path : cứa toàn bộ hàm  hỗ trợ sẵn liên quan tới đường dẫn
    @Value("${path.root}")
    private String spath;

    @GetMapping("/{filename}")
    public ResponseEntity<?> loadFile(@PathVariable String filename){
        //duong dan folder root luu hinh

        try{
            Path rootPath=Paths.get(spath);
            Resource resource= new UrlResource(rootPath.resolve(filename).toUri());
            if (resource.exists()){
                //Neu ton` tai thi moi cho phep download
//                return new ResponseEntity<>(resource,HttpStatus.OK);

                //cho phép download
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);

            }else{
                //khi nem exception thi code se dung va` văng ra lỗi
                throw new CustomFileNotFoundException(200,"File not found");
            }

        }catch (Exception e){
            throw new CustomFileNotFoundException(200,"File not found");
        }



    }


    @PostMapping("/uploadfile")
    public ResponseEntity<?> uploadFile(@RequestParam MultipartFile file){

        //Dinh nghia duong dan
        Path rootPath = Paths.get(spath);
        System.out.println("ádasdasd:"+spath);
        try{
            if(!Files.exists(rootPath)){
                //Tao folder neu khong ton tai

                Files.createDirectories(rootPath);

            }

//            file.getOriginalFilename() : Lay ten file va dinh dang

            Files.copy(file.getInputStream(),rootPath.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);

        }catch (Exception e){
            throw new CustomFileNotFoundException(500,"Khong tim thay file");
//            System.out.println("Loi "+e.getLocalizedMessage());
        }



        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
