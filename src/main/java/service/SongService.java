package service;

import model.Song;
import utility.Database;
import utility.Utility;

import javax.rmi.CORBA.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class SongService {
    private static Connection connection = Database.connect();
    private static Statement stmt = null;
    private static PreparedStatement pstmt = null;
    private static String leftAlignFormat = "| %-3d | %-20s | %-6d | %-19s | %-11s | %-13s |%n";
    private static String SELECT_ALL_SQL = "SELECT * FROM songs as s INNER JOIN artists as a on s.artist_id = a.id ";
    private static String SELECT_ONE_SQL = "SELECT * FROM songs as s INNER JOIN artists as a on s.artist_id = a.id where s.id = ? ";
    private static String INSER__SQL = "INSERT INTO songs(title, release_year, artist_id) VALUES(?,?,?)";
    private static String SELECT_LAST_SQL = "SELECT * FROM songs as s INNER JOIN artists as a on s.artist_id = a.id ORDER BY s.id DESC LIMIT 1";
    private static String DELETE_SQL = "DELETE FROM songs as s WHERE s.id = ?";
    private static String UPDATE_SQL = "";
    public static void getAllSongs(){
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(SELECT_ALL_SQL);
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
        }catch (Exception e){
            e.printStackTrace(System.out);
        }
    }

    public static void getSongById(int songId){
        try {
            pstmt = connection.prepareStatement(SELECT_ONE_SQL);
            pstmt.setInt(1, songId);
            ResultSet rs = pstmt.executeQuery();
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
        }catch (Exception e){
            e.printStackTrace(System.out);
        }
    }

    public static void addSong(Song song){
        try {
            pstmt = connection.prepareStatement(INSER__SQL);
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

    public static  void updateSong(){

    }
}
