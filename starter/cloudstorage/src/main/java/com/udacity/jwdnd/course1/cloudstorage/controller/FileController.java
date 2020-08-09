package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping("/file")
public class FileController {

    private FileService fileService;
    private UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping
    public String addFile(@RequestParam("fileUpload") MultipartFile file, Authentication authentication, Model model) throws IOException {
        int userId = this.userService.getUser(authentication.getName()).getUserId();

        if (file.isEmpty()) {
            model.addAttribute("error", "Please select a file to upload.");
        }
        else if (!this.fileService.isFileNameAvailable(file.getOriginalFilename())) {
            model.addAttribute("error", "A file with the name already exists.");
        }
        else {
            File newFile = new File(null, file.getOriginalFilename(), file.getContentType(), Long.toString(file.getSize()), userId, file.getBytes());
            int result = this.fileService.addFile(newFile);
            model.addAttribute("saved", result > 0);
        }
        return "result";
    }

    @GetMapping("/delete/{fileId}")
    public String deleteFile(@PathVariable("fileId") Integer fileId, Model model) {
        int result = this.fileService.deleteFile(fileId);
        model.addAttribute("saved", result > 0);
        return "result";
    }

}
