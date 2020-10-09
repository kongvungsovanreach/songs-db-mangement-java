package utility;

import service.SongService;

import java.util.Scanner;

public class Utility {
    public static void welcome(){
        System.out.println("============== WELCOME TO THE SYSTEM ==============");
        menu();
    }
    private static void menu(){
        boolean incorrectOption = false;
        do{
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
                    break;
                case 1:
                    getAllSongs();
                    break;
                case 2:
                    getSongById();
                    break;
                case 3:
                    addSong();
                    break;
                case 4:
                    deleteSong();
                    break;
                case 5:
                    updateSong();
                    break;
                default:
                    System.out.println("Incorrect option.");
                    incorrectOption = true;
            }
        }while (incorrectOption);
    }

    public static int validateOption(String o) {
        int option = 0;
        try{
            option =  Integer.parseInt(o);
        }catch (Exception e){
            System.out.println("Accept number only.");
        }
        return option;
    }

    public static void getAllSongs(){
        System.out.println("All songs are fetched");
    }

    public static void getSongById(){

    }

    public static void addSong(){

    }

    public static void deleteSong(){

    }

    public static  void updateSong(){

    }
}
