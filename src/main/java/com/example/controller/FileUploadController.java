package com.example.controller;

import com.example.controller.dao.FileUploadDAO;
import com.example.controller.model.FileUploadEntity;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping("/uploadFile")
    public String uploadFile(MultipartHttpServletRequest request, Model model) {
        Iterator<String> itr = request.getFileNames();

        while (itr.hasNext()) {
            MultipartFile file = request.getFile(itr.next());

            if (file != null && !file.isEmpty()) {
                String extension = FilenameUtils.getExtension(file.getOriginalFilename());

                // ✅ Allow only PDF and Excel
                if (!("pdf".equalsIgnoreCase(extension)
                        || "xls".equalsIgnoreCase(extension)
                        || "xlsx".equalsIgnoreCase(extension))) {
                    model.addAttribute("message", "❌ Only PDF and Excel files are allowed!");
                    return "upload";
                }

                try {
                    // ✅ Save file to disk
                    File dest = new File(UPLOAD_DIR + file.getOriginalFilename());
                    file.transferTo(dest);

                    // ✅ Save metadata to database
                    FileUploadEntity entity = new FileUploadEntity(
                            file.getOriginalFilename(),
                            dest.getAbsolutePath(),
                            extension
                    );
                    FileUploadDAO dao = new FileUploadDAO();
                    dao.save(entity);

                    model.addAttribute("message", "✅ File uploaded and saved successfully: " + file.getOriginalFilename());
                } catch (IOException e) {
                    model.addAttribute("message", "❌ File upload failed: " + e.getMessage());
                } catch (Exception e) {
                    model.addAttribute("message", "❌ DB save failed: " + e.getMessage());
                }
            }
        }

        return "upload"; // JSP view
    }
}
