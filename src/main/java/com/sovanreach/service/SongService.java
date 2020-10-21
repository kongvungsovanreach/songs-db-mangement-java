package com.sovanreach.service;

import com.sovanreach.model.Song;
import com.sovanreach.utility.Database;
import com.sovanreach.utility.Utility;

import java.sql.*;

public class SongService {
    //Constants declaration
    private static Connection connection = Database.connect();
    private static Statement stmt = null;
    private static PreparedStatement pstmt = null;
    private static String leftAlignFormat = "| %-3d | %-40s | %-6d | %-19s | %-11s | %-23s |%n";
    //SQL stored variables
    private static String SELECT_ALL_SQL = "SELECT * FROM songs INNER JOIN artists on songs.artist_id = artists.id ";
    private static String SELECT_ONE_SQL = "SELECT * FROM songs  INNER JOIN artists on songs.artist_id = artists.id where songs.id = ? ";
    private static String INSERT__SQL = "INSERT INTO songs(title, release_year, artist_id) VALUES(?,?,?)";
    private static String SELECT_LAST_SQL = "SELECT * FROM songs INNER JOIN artists on songs.artist_id = artists.id ORDER BY songs.id DESC LIMIT 1";
    private static String DELETE_SQL = "DELETE FROM songs  WHERE songs.id = ?";
    private static String UPDATE_SQL = "UPDATE songs SET title = ?, release_year = ?, artist_id = ? where songs.id = ? ";

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
                System.out.println("\n+++++++++++++++++++++++");
                System.out.println("+    NO DATA FOUND    +");
                System.out.println("+++++++++++++++++++++++\n");
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
                System.out.println("\n+++++++++++++++++++++++");
                System.out.println("+    NO DATA FOUND    +");
                System.out.println("+++++++++++++++++++++++\n");
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
            System.out.println("\n++++++++++++++++++++++++++++++++++++");
            System.out.println("+    Song inserted successfully    +");
            System.out.println("++++++++++++++++++++++++++++++++++++\n");
        }catch (Exception e){
            System.out.println("Error inserting song.");
            e.printStackTrace(System.out);
        }
    }

    //Delete a specific song by given id
    public static void deleteSong(int songId){
        try {
            if(getSongObject(songId) != null){
                pstmt = connection.prepareStatement(DELETE_SQL);
                pstmt.setInt(1, songId);
                pstmt.executeUpdate();
                System.out.println("\n+++++++++++++++++++++++++++++++++++");
                System.out.println("+    Song deleted successfully    +");
                System.out.println("+++++++++++++++++++++++++++++++++++\n");
            }else {
                System.out.println("\n+++++++++++++++++++++++++++++++++++");
                System.out.println("+   Not Found Song for deletion.  +");
                System.out.println("+++++++++++++++++++++++++++++++++++\n");
            }
        }catch (Exception e){
            System.out.println("Error inserting song.");
            e.printStackTrace(System.out);
        }
    }

    //Update a specific song by given id
    public static  void updateSong(Song newSong){
        System.out.println(getSongObject(newSong.getId()));
        try {
            if(getSongObject(newSong.getId()) != null){
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
                pstmt = connection.prepareStatement(UPDATE_SQL);
                pstmt.setString(1, newSong.getTitle());
                pstmt.setInt(2, newSong.getReleaseYear());
                pstmt.setInt(3, newSong.getArtistId());
                pstmt.setInt(4, newSong.getId());
                pstmt.executeUpdate();
                getSongById(newSong.getId());
                System.out.println("\n+++++++++++++++++++++++++++++++++++");
                System.out.println("+    Song updated successfully    +");
                System.out.println("+++++++++++++++++++++++++++++++++++\n");
            }else {
                System.out.println("\n+++++++++++++++++++++++++++++++++++");
                System.out.println("+    NOT Found Song for update    +");
                System.out.println("+++++++++++++++++++++++++++++++++++\n");
            }
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
                int id  = rs.getInt("songs.id");
                String title = rs.getString("title");
                int releaseYear = rs.getInt("release_year");
                int artistID = rs.getInt("artists.id");
                song = new Song(id, title, releaseYear, artistID);
            }
            rs.close();
        }catch (Exception e){
            e.printStackTrace(System.out);
        }
        return song;
    }

    //Count songs by song type using SQL function call
    public static void countArtistByType(String artistType){
        try{
            CallableStatement cstmt = connection.prepareCall("{? = CALL countArtistBySongType(?)}");
            cstmt.registerOutParameter(1, Types.INTEGER);
            cstmt.setString(2, artistType);
            cstmt.executeUpdate();
            int artistCount= cstmt.getInt(1);
            System.out.println("\nCount of artists type "+artistType+" is "+artistCount+" artist.\n");
        }catch (Exception e){
            System.out.println("Error counting artish!");
        }
    }
}


