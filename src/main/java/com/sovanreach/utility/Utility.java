package com.sovanreach.utility;

import com.sovanreach.model.Song;
import com.sovanreach.service.SongService;
import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;

import java.util.Scanner;

public class Utility {

    //Start point and welcome
    public static void start(){
        System.out.println("================================== WELCOME TO THE SYSTEM ==================================");
        //Calling to show menu
        menu();
    }

    //Show multiple available menus
    private static void menu(){
        boolean showMenu = true;
        do{
            if(showMenu) printMenu();
            System.out.print("Please choose the option: ");
            Scanner scanner = new Scanner(System.in);
            int option = 0;
            if (scanner.hasNextInt()){
                option = scanner.nextInt();
            }else {
                option = validateOption(scanner.next());
            }
            switch (option) {
                case 0:
                    showMenu = false;
                    break;
                case 1:
                    getAllSongs();
                    showMenu = true;
                    break;
                case 2:
                    getSongById();
                    showMenu = true;
                    break;
                case 3:
                    addSong();
                    showMenu = true;
                    break;
                case 4:
                    deleteSong();
                    showMenu = true;
                    break;
                case 5:
                    updateSong();
                    showMenu = true;
                    break;
                case 6:
                    countArtistByType();
                default:
                    showMenu = false;
                    System.out.println("Incorrect option.");
            }
        }while (true);
    }

    //Print menu
    private static void printMenu(){
        System.out.println("1. Show all songs");
        System.out.println("2. Find a song");
        System.out.println("3. Add a song");
        System.out.println("4. Delete a song");
        System.out.println("5. Update a song");
        System.out.println("6. Count songs by type");
    }

    //Validate the valid given option
    public static int validateOption(String o) {
        int option = 0;
        try{
            option =  Integer.parseInt(o);
        }catch (Exception e){
            System.out.println("Accept number only.");
        }
        return option;
    }

    //Get all songs from database
    public static void getAllSongs(){
        System.out.println("===> Show All Songs ");
        SongService.getAllSongs();
    }

    //Get a specific song by given id
    public static void getSongById(){
        int songId = 0;
        System.out.println("===> Find a song ");
        do {
            System.out.print("Enter song ID: ");
            Scanner scanner = new Scanner(System.in);
            if (scanner.hasNextInt()){
                songId = scanner.nextInt();
            }else {
                songId = validateOption(scanner.next());
            }
        }while (songId == 0);
        SongService.getSongById(songId);
    }

    //Add a new song record into database
    public static void addSong(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Title => ");
        String title = scanner.nextLine();
        System.out.print("Year => ");
        int releaseYear = scanner.nextInt();
        System.out.print("Artist ID => ");
        int artistId = scanner.nextInt();
        Song song  = new Song(0, title, releaseYear, artistId);
        SongService.addSong(song);
    }

    //Delete a specific song by given id
    public static void deleteSong(){
        int songId = 0;
        System.out.println("===> Delete a song ");
        do {
            System.out.print("Enter song ID: ");
            Scanner scanner = new Scanner(System.in);
            if (scanner.hasNextInt()){
                songId = scanner.nextInt();
            }else {
                songId = validateOption(scanner.next());
            }
        }while (songId == 0);
        SongService.deleteSong(songId);
    }

    //Update a specific song by given id
    public static  void updateSong(){
        int songId = 0;
        System.out.println("===> Update a song ");
        do {
            System.out.print("Enter song ID: ");
            Scanner scanner = new Scanner(System.in);
            if (scanner.hasNextInt()){
                songId = scanner.nextInt();
            }else {
                songId = validateOption(scanner.next());
            }
        }while (songId == 0);
        Scanner scanner = new Scanner(System.in);
        System.out.print("Title => ");
        String title = scanner.nextLine();
        System.out.print("Year => ");
        int releaseYear = scanner.nextInt();
        System.out.print("Artist ID => ");
        int artistId = scanner.nextInt();
        Song song  = new Song(songId, title, releaseYear, artistId);
        SongService.updateSong(song);

    }

    public static void countArtistByType(){
        boolean isError = true;
        String artistType;
        do{
            System.out.println("===>  Count songs by type");
            Scanner scanner = new Scanner(System.in);
            System.out.print("Song Type (Modern, Classic) => ");
            artistType = scanner.nextLine();
            if(!artistType.toLowerCase().equals("modern") || !artistType.toLowerCase().equals("classic")){
                isError = true;
            }else {
                isError = false;
            }
        } while (isError);
        SongService.countArtistByType(artistType);

    }

    //Print table header
    public static void printHeader(){
        System.out.format("+-----+------------------------------------------+--------+---------------------+-------------+-------------------------+%n");
        System.out.format("| ID  |                  TITLE                   |   YEAR |     ARTIST NAME     | ARTIST TYPE |       ARTIST NAT.       |%n");
        System.out.format("+-----+------------------------------------------+--------+---------------------+-------------+-------------------------+%n");
    }

    //Print table footer
    public static void printFooter(){
        System.out.format("+-----+------------------------------------------+--------+---------------------+-------------+-------------------------+%n");
    }
}
