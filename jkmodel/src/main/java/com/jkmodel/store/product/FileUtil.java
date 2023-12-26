package com.jkmodel.store.product;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class FileUtil {
    public static byte[] convertMultipartFileToBytes(MultipartFile file) throws IOException {
        return file.getBytes();
    }
}
