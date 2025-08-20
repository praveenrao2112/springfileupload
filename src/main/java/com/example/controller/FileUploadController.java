package com.example.controller;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

@Controller
public class FileUploadController {

    private static final String UPLOAD_DIR = "C:/uploads/";

    @GetMapping("/")
    public String home(Model model) {
        return "upload";  // maps to /WEB-INF/views/upload.jsp
    }

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public String uploadFile(MultipartHttpServletRequest request, Model model) {
        Iterator<String> itr = request.getFileNames();

        while (itr.hasNext()) {
            MultipartFile file = request.getFile(itr.next());

            if (file != null && !file.isEmpty()) {
                // ✅ get extension
                String extension = FilenameUtils.getExtension(file.getOriginalFilename());

                // ✅ allow only PDF and Excel
                if (!("pdf".equalsIgnoreCase(extension)
                        || "xls".equalsIgnoreCase(extension)
                        || "xlsx".equalsIgnoreCase(extension))) {
                    model.addAttribute("message", "❌ Only PDF and Excel files are allowed!");
                    return "upload"; // return back to JSP with error
                }

                try {
                    File dest = new File(UPLOAD_DIR + file.getOriginalFilename());
                    file.transferTo(dest);
                    model.addAttribute("message", "✅ File uploaded successfully: " + file.getOriginalFilename());
                } catch (IOException e) {
                    model.addAttribute("message", "❌ Upload failed: " + e.getMessage());
                }
            }
        }

        return "upload"; // JSP page
    }
}
