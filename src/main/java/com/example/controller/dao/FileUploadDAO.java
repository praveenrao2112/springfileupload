package com.example.controller.dao;



import com.example.controller.model.FileUploadEntity;
import com.example.controller.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class FileUploadDAO {

    public void save(FileUploadEntity file) throws Exception {
        String sql = "INSERT INTO uploaded_files (file_name, file_path, file_type) VALUES (?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, file.getFileName());
            ps.setString(2, file.getFilePath());
            ps.setString(3, file.getFileType());
            ps.executeUpdate();
        }
    }
}
