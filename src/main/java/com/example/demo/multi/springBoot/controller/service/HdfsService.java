package com.example.demo.multi.springBoot.controller.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface HdfsService {


    List<String> ls(String dir) throws IOException;

    String upload(File file, String path) throws IOException;

    String upload(File file, String path, boolean overwritten) throws IOException;

    void upload(InputStream is, String path, boolean overwritten) throws IOException;
}
