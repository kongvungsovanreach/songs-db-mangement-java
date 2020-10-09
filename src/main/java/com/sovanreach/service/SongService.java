package com.sovanreach.service;

import com.sovanreach.model.Song;
import com.sovanreach.utility.Database;
import com.sovanreach.utility.Utility;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class SongService {
    //Constants declaration
    private static Connection connection = Database.connect();
    private static Statement stmt = null;
    private static PreparedStatement pstmt = null;
    private static String leftAlignFormat = "| %-3d | %-20s | %-6d | %-19s | %-11s | %-13s |%n";
    //SQL stored variables
    private static String SELECT_ALL_SQL = "SELECT * FROM songs as s INNER JOIN artists as a on s.artist_id = a.id ";
    private static String SELECT_ONE_SQL = "SELECT * FROM songs as s INNER JOIN artists as a on s.artist_id = a.id where s.id = ? ";
    private static String INSERT__SQL = "INSERT INTO songs(title, release_year, artist_id) VALUES(?,?,?)";
    private static String SELECT_LAST_SQL = "SELECT * FROM songs as s INNER JOIN artists as a on s.artist_id = a.id ORDER BY s.id DESC LIMIT 1";
    private static String DELETE_SQL = "DELETE FROM songs as s WHERE s.id = ?";
    private static String UPDATE_SQL = "UPDATE songs as s SET title = ?, release_year = ?, artist_id = ? where s.id = ? ";

    //Get all song from database
    public static void getAllSongs(){
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(SELECT_ALL_SQL);
            if (rs.isBeforeFirst()){
                Utility.printHeader();
                while(rs.next()){
                    int id  = rs.getInt("id");
                    String title = rs.getString("title");
                    int releaseYear = rs.getInt("release_year");
                    String name = rs.getString("name");
                    String type = rs.getString("type");
                    String nationality = rs.getString("nationality");
                    System.out.format(leftAlignFormat, id , title, releaseYear, name, type, nationality);
                }
                Utility.printFooter();
            }else {
                System.out.println("+++++++++++++++++++++++");
                System.out.println("+    NO DATA FOUND    +");
                System.out.println("+++++++++++++++++++++++");
                return;
            }
            rs.close();
        }catch (Exception e){
            e.printStackTrace(System.out);
        }
    }

    //Get a specific song by given id
    public static void getSongById(int songId){
        try {
            pstmt = connection.prepareStatement(SELECT_ONE_SQL);
            pstmt.setInt(1, songId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.isBeforeFirst()){
                Utility.printHeader();
                while(rs.next()){
                    int id  = rs.getInt("id");
                    String title = rs.getString("title");
                    int releaseYear = rs.getInt("release_year");
                    String name = rs.getString("name");
                    String type = rs.getString("type");
                    String nationality = rs.getString("nationality");
                    System.out.format(leftAlignFormat, id , title, releaseYear, name, type, nationality);
                }
                Utility.printFooter();
            }else {
                System.out.println("+++++++++++++++++++++++");
                System.out.println("+    NO DATA FOUND    +");
                System.out.println("+++++++++++++++++++++++");
                return;
            }
            rs.close();
        }catch (Exception e){
            e.printStackTrace(System.out);
        }
    }

    //Add a new song record
    public static void addSong(Song song){
        try {
            pstmt = connection.prepareStatement(INSERT__SQL);
            pstmt.setString(1, song.getTitle());
            pstmt.setInt(2, song.getReleaseYear());
            pstmt.setInt(3, song.getArtistId());
            pstmt.executeUpdate();
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(SELECT_LAST_SQL);
            Utility.printHeader();
            while(rs.next()){
                int id  = rs.getInt("id");
                String title = rs.getString("title");
                int releaseYear = rs.getInt("release_year");
                String name = rs.getString("name");
                String type = rs.getString("type");
                String nationality = rs.getString("nationality");
                System.out.format(leftAlignFormat, id , title, releaseYear, name, type, nationality);
            }
            Utility.printFooter();
            rs.close();
            System.out.println("Song inserted successfully.");
        }catch (Exception e){
            System.out.println("Error inserting song.");
            e.printStackTrace(System.out);
        }
    }

    //Delete a specific song by given id
    public static void deleteSong(int songId){
        try {
            pstmt = connection.prepareStatement(DELETE_SQL);
            pstmt.setInt(1, songId);
            pstmt.executeUpdate();
            getSongById(songId);
            System.out.println("Song deleted successfully.");
        }catch (Exception e){
            System.out.println("Error inserting song.");
            e.printStackTrace(System.out);
        }
    }

    //Update a specific song by given id
    public static  void updateSong(Song newSong){
        Song oldSong = getSongObject(newSong.getId());
        if(oldSong.getTitle() == ""){
            newSong.setTitle(oldSong.getTitle());
        }
        if(oldSong.getReleaseYear() == 0){
            newSong.setReleaseYear(oldSong.getReleaseYear());
        }
        if (oldSong.getArtistId() == 0){
            newSong.setArtistId(oldSong.getArtistId());
        }

        try {
            pstmt = connection.prepareStatement(UPDATE_SQL);
            pstmt.setString(1, newSong.getTitle());
            pstmt.setInt(2, newSong.getReleaseYear());
            pstmt.setInt(3, newSong.getArtistId());
            pstmt.setInt(4, newSong.getId());
            pstmt.executeUpdate();
            System.out.println("Song updated successfully.");
            getSongById(newSong.getId());
        }catch (Exception e){
            System.out.println("Error inserting song.");
            e.printStackTrace(System.out);
        }

    }

    //Get song object to update
    public static Song getSongObject(int songId){
        Song song = null;
        try {
            pstmt = connection.prepareStatement(SELECT_ONE_SQL);
            pstmt.setInt(1, songId);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                int id  = rs.getInt("id");
                String title = rs.getString("title");
                int releaseYear = rs.getInt("release_year");
                int artistID = rs.getInt("a.id");
                song = new Song(id, title, releaseYear, artistID);
            }
            rs.close();
        }catch (Exception e){
            e.printStackTrace(System.out);
        }
        return song;
    }
}


