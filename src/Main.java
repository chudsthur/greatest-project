import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Bitte Anzahl Spieler angeben 2-4:");
        System.out.println(" ");

        int numPlayers = sc.nextInt();

        if (numPlayers <= 1) {
            System.out.println("Zu wenig Spieler, Bitte such dir Freunde.");
        } else if (numPlayers == 2) {


        }

    }
}