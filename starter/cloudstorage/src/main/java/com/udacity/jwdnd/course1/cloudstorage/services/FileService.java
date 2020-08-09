package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

@Service
public class FileService {

    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public boolean isFileNameAvailable(String fileName) {
        return this.fileMapper.getFileByName(fileName) == null;
    }

    public File getFile(Integer fileId) {
        return this.fileMapper.getFile(fileId);
    }

    public int addFile(File file) {
        return this.fileMapper.insert(file);
    }

    public List<File> getAllFiles(Integer userId) {
        var files = fileMapper.getAllFiles(userId);
        files.forEach(file -> file.setFileUrl("data" +
                ":" + file.getContentType() + ";base64," + Base64.getEncoder().encodeToString(file.getFileData())));
        return this.fileMapper.getAllFiles(userId);
    }

    public int deleteFile(Integer fileId) {
        return this.fileMapper.delete(fileId);
    }
}
