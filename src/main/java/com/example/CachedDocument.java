/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example;

import java.sql.*;

public class CachedDocument extends DocumentDecorator {
    private static final String DB_URL = "jdbc:sqlite:db";
    public CachedDocument(Document document) {
        super(document);
    }
    @Override
    public String parse(String path) {
        String query = "SELECT parsed_string FROM cache WHERE path = ?";
        String insertSQL = "INSERT OR REPLACE INTO cache (path, parsed_string) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement selectStmt = conn.prepareStatement(query);
             PreparedStatement insertStmt = conn.prepareStatement(insertSQL)) {
            selectStmt.setString(1, path);
            ResultSet rs = selectStmt.executeQuery();
            if (rs.next()) {
                System.out.println("Already cached!!!");
                return rs.getString("parsed_string");
            }
            String parsedContent = super.parse(path);
            insertStmt.setString(1, path);
            insertStmt.setString(2, parsedContent);
            insertStmt.executeUpdate();
            return parsedContent;
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return super.parse(path);
    }
}