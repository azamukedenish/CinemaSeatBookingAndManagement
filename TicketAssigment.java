/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ticketassigment;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


/**
 *
 * @author Azamuke Denish
 */


class SeatSeller{
    int totalNumberOfTicketsSold = 0;
    float totalRevenue = 0;    
    Scanner fileInput;
    
    //function to print the column headers (1-20)
       void printColumnHeaders(){
           
        System.out.print("\t");
        for(int i=1; i<=20; i++){           
        System.out.printf("%d\t", i);
        }
        System.out.println();
        
       }
       
    //function to initialise the seat chart array with '*' 
       char[][] initialiseSeatChartArray(int numberOfSoldTickets, float totalSales){
           
        char seatChart[][] = new char[16][21]; //the extra column will hold the characters A-P
        char letter = 'A';
        numberOfSoldTickets = 0;
        totalSales = 0;
        
        
        for(int i=0; i<seatChart.length; i++){
            for(int j=0; j<seatChart[i].length; j++){
                if(j==0){
                    seatChart[i][0] = letter;
                    letter++;
                }
                else{
                    seatChart[i][j] = '*';
                }
            }
        }
        
        return seatChart;
    }
    
//  get the price for each row
    float getRowPrice(char row, int column, char seatChartArray[][], float prices[]){
        float price = 0;        
        
        if(row == 'A' || row == 'B'){
          if(column >= 5 && column <= 15){
              price = prices[0];
          }
          else{
              price = prices[1];
          }
        }
        else if(row >= 'C' && row <= 'F'){
            price = prices[1];
        }
        else if(row >= 'G' && row <= 'L'){
            price = prices[2];
        }
        else{
            price = prices[3];
        }
        
        return price;
    }
    
    void searchForSeat(char seatChart[][], int numberOfSoldTickets, float totalSales, float prices[]){        
        char seatMark;
        String message;
        Scanner input = new Scanner(System.in);        
        
        System.out.println("Enter seat row(A-P)");        
        char seatRow = input.next().charAt(0);
//      validate seat row        
        while(seatRow<'A' || seatRow>'P'){
            System.out.println("Invalid seat row. Please try again!");
            System.out.println("Enter seat row(A-P)");
            seatRow = input.next().charAt(0);
        }

        System.out.println("Enter seat number(1-20)");                   
            int seatNumber = input.nextInt();
        
//      validate seat number
        while(seatNumber<1 || seatNumber>=seatChart[1].length){
            System.out.println("Invalid seat number. Please try again!");
            System.out.println("Enter seat number(1-20)");
            seatNumber = input.nextInt();
        }
        
        System.out.println("Do you want to sell(S) or reserve(R) seat?(S/R)");
        char intention = input.next().charAt(0);
        switch (intention) {
            case 'S':        
                seatMark = '#';
                message = "sold";
                sellOrReserveSeat(seatChart, seatRow, seatNumber, numberOfSoldTickets, totalSales, seatMark, message, prices);
                break;
                
            case 'R':
                seatMark = 'o';
                message = "reserved";
                sellOrReserveSeat(seatChart, seatRow, seatNumber, numberOfSoldTickets, totalSales, seatMark, message, prices);
                break;
                
            default:
                System.out.println("Invalid Input");
                break;
        }
                   
    }
 
//     
    void sellOrReserveSeat(char seatChart[][], char seatRow, int seatNumber, int numberOfSoldTickets, float totalSales, char seatMark, String message, float prices[]){
        Boolean seatExists = false;
        Boolean isSeatAvailable = true;
        int numberOfSeatsAvailable;
        Scanner input = new Scanner(System.in);
        
            for(int i=0; i<seatChart.length; i++){
                for(int j=0; j<seatChart[i].length; j++){                
                    if(seatChart[i][j]==seatRow){
                        if(seatChart[i][seatNumber]=='#' || seatChart[i][seatNumber]=='o'){
                            isSeatAvailable = false;
                            System.out.println("Seat is not available. Would you love to sell or reserve another ticket?(Y/N)");
                            char reply = input.next().charAt(0);
                            switch (reply) {
                                case 'Y':
                                    searchForSeat(seatChart, numberOfSoldTickets, totalSales, prices);
                                    break;
                                case 'N':
                                    System.out.println("Thank You!!");
                                    break;
                                default:  
                                    System.out.println("Invalid Input");
                                    break;
                            }
                        }
                        else{

                            if(seatRow == 'A' || seatRow == 'B'){
                                if(seatNumber >= 5 && seatNumber <= 15){
                                  seatChart[i][seatNumber] = seatMark;
                                  seatChart[i][seatNumber+1] = seatMark;                                                                    
                                  numberOfSoldTickets += 2;
                                }
                                else{
                                  seatChart[i][seatNumber] = seatMark;
                                  numberOfSoldTickets += 1;
                                }
                            }
                            else{
                               seatChart[i][seatNumber] = seatMark;                            
                               numberOfSoldTickets += 1;
                            }

                            seatExists = true;                
                            break;                        
                        }                                        
                    }               
                }            
                if(seatExists || !isSeatAvailable){
                break;
                }
            }

            if(seatExists){                            
              if(seatMark!='o'){
              totalSales += getRowPrice(seatRow, seatNumber, seatChart, prices);
              }
              
              numberOfSeatsAvailable = 320-numberOfSoldTickets;
              displaySeatChart(seatChart, numberOfSeatsAvailable, totalSales);              
              System.out.println("Seat " + message + " successfully!!. Would you love to sell or reserve another ticket?(Y/N)");
              char reply = input.next().charAt(0);
                switch (reply) {
                    case 'Y':
                        searchForSeat(seatChart, numberOfSoldTickets, totalSales, prices);
                        break;
                    case 'N':
                        System.out.println("Thank You!!");
                        break;
                    default:
                        System.out.println("Invalid Input");
                        break;
                }
            }
            else{
              System.out.println("Seat Doesn't exist or is unavailable");
            }
    }
          
    void displaySeatChart(char seatChart[][], int numberOfSeatsAvailable, float totalSales){
        printColumnHeaders();
        for(int i=0; i<seatChart.length; i++ ){
            for(int j=0; j<seatChart[i].length; j++){
                System.out.printf("%c\t", seatChart[i][j]);               
            }
            System.out.println();
        }
        
        System.out.printf("Number of seats available: %d\n", numberOfSeatsAvailable);
        System.out.printf("Total sales: shs. %.2f\n", totalSales);
    }
    
    
//this function will list the available seats basing on a certain category desired by the user
    void listAvailableSeats(char seatChartArray[][], int numberOfSoldTickets, float totalSales, float prices[]){
        Scanner input = new Scanner(System.in);
        int numberOfRequiredSeats;
        String desiredSeatCategory;
        int countSeats = 0;
        char rowLetter;
        
        System.out.print("\nEnter number of seats required: ");
        numberOfRequiredSeats = input.nextInt();
        System.out.print("\nEnter desired seat category(Twin, VVIP, VIP or Economy): ");
        desiredSeatCategory = input.next();
        
        System.out.println("\nAvailable seats:");
        
        switch (desiredSeatCategory) {
            case "Twin":
                for(int i = 0; countSeats < numberOfRequiredSeats && i <= 1; i++){
                    rowLetter = seatChartArray[i][0];
                    for(int j=5; countSeats < numberOfRequiredSeats && j<16; j++){
                       if(seatChartArray[i][j] == '*'){
                           countSeats++;
                           System.out.println();
                           System.out.print(rowLetter);
                           System.out.println(j + ", " + prices[0]);                           
                       }                           
                    }                   
                }
                
                if(countSeats == 0){
                    tryAgain(seatChartArray, numberOfSoldTickets, totalSales, prices);
                }
                
                searchForSeat(seatChartArray, numberOfSoldTickets, totalSales, prices);
                break;
                
            case "VVIP":
                for(int i = 0; countSeats < numberOfRequiredSeats && i <= 5; i++){
                    rowLetter = seatChartArray[i][0];
                    for(int j=1; countSeats < numberOfRequiredSeats && j<21; j++){
                       if((i==0 || i==1) && (j>=5 && j<=15)){
                           continue;
                       } 
                       if(seatChartArray[i][j] == '*'){
                           countSeats++;
                           System.out.println();
                           System.out.print(rowLetter);
                           System.out.println(j + ", " + prices[1]);
                           
                       }                           
                    }                    
                }
                
                if(countSeats == 0){
                    tryAgain(seatChartArray, numberOfSoldTickets, totalSales, prices);       
                }
                
                searchForSeat(seatChartArray, numberOfSoldTickets, totalSales, prices);
                break;
                
            case "VIP":                
                printAvailableSeats(seatChartArray, 6, 11, prices[2], numberOfRequiredSeats);
               
                if(countSeats == 0){
                    tryAgain(seatChartArray, numberOfSoldTickets, totalSales, prices);            
                }
                
                searchForSeat(seatChartArray, numberOfSoldTickets, totalSales, prices);
                break;
                
            case "Economy":                
                printAvailableSeats(seatChartArray, 12, 15, prices[3], numberOfRequiredSeats);
                
                if(countSeats == 0){
                    tryAgain(seatChartArray, numberOfSoldTickets, totalSales, prices);                 
                }
                
                searchForSeat(seatChartArray, numberOfSoldTickets, totalSales, prices);
                break;
                
            default:
                System.out.println("Invalid Input");
                break;
        }
        
    }

//    
    void tryAgain( char seatChartArray[][], int numberOfSoldTickets, float totalSales, float prices[]){
        System.out.println("Available seats: None");
        System.out.println("Please try again!");
        listAvailableSeats(seatChartArray, numberOfSoldTickets, totalSales, prices);     
    }

    
    void printAvailableSeats(char seatChartArray[][], int startRow, int endRow, float price, int requiredSeats){
        char rowLetter;
        int countSeats = 0;
        for(int i = startRow; countSeats < requiredSeats && i <= endRow; i++){
                    rowLetter = seatChartArray[i][0];
                    for(int j=1; countSeats < requiredSeats && j<21; j++){
                       if(seatChartArray[i][j] == '*'){
                           System.out.println();
                           System.out.print(rowLetter);
                           System.out.println(j + ", " + price);
                           countSeats++;
                       }                           
                    }
        }           
    }
    
    int rowIndexOfElement(char seatChartArray[][], char element){
        Boolean isElementFound = false;
        int index = -1;
        for(int i=0; i<seatChartArray.length; i++){
            for(int j=0; j<seatChartArray[i].length; j++){
                if(element == seatChartArray[i][j]){
                    index = i;
                    isElementFound = true;
                    break;
                }    
            }
            if(isElementFound){
                break;
            }
        }
        return index;
    }
    
    void reservedSeatMenu(char seatChartArray[][], int numberOfSoldTickets, int numberOfSeatsAvailable, float totalSales, float prices[]){
        Scanner input = new Scanner(System.in);
        char reply;
        System.out.println("Do you want to pay for(P) or make reserved seat available(M)? (P/M)");
        reply = input.next().charAt(0);
        
        switch (reply) {
            case 'P':
                payOrMakeReservedSeatAvailable(seatChartArray, reply, numberOfSoldTickets, totalSales, prices);
                displaySeatChart(seatChartArray, numberOfSeatsAvailable, totalSales);
                break;
            case 'M':
                payOrMakeReservedSeatAvailable(seatChartArray, reply, numberOfSoldTickets, totalSales, prices);
                displaySeatChart(seatChartArray, numberOfSeatsAvailable, totalSales);
                break;
            default:
                System.out.println("Invalid Input");
                break;
        }
        
    }
    
    void payOrMakeReservedSeatAvailable(char seatChartArray[][], char reply, int numberOfSoldTickets, float totalSales, float prices[]){
        Scanner input = new Scanner(System.in);
        char seatRow;
        int seatNumber;
        int rowIndex;
        
        System.out.println("Enter seat row(A-P)");
        seatRow = input.next().charAt(0);
        rowIndex = rowIndexOfElement(seatChartArray, seatRow);
                
        System.out.println("Enter seat number(1-20)");
        seatNumber = input.nextInt();
        
        if(reply == 'M'){
            seatChartArray[rowIndex][seatNumber] = '*';
            System.out.println("Seat is now available");
        }
        else{
            if(seatRow == 'A' || seatRow == 'B'){
                if(seatNumber >= 5 && seatNumber <= 15){
                  seatChartArray[rowIndex][seatNumber] = '#';
                  seatChartArray[rowIndex][seatNumber+1] = '#';                                                                    
                  numberOfSoldTickets += 2;
                }
                else{
                  seatChartArray[rowIndex][seatNumber] = '#';
                  numberOfSoldTickets += 1;
                }
            }
            else{
               seatChartArray[rowIndex][seatNumber] = '#';                            
               numberOfSoldTickets += 1;
            }
            totalSales += getRowPrice(seatRow, seatNumber, seatChartArray, prices);
            System.out.println("Seat has been paid for successfully!");
        }
    }        
    
    void openFile(){
        try{
        fileInput = new Scanner(new File("prices.txt"));
        }
        catch(FileNotFoundException fileNotFoundException){
            System.err.println("Error in opening file");
            System.out.println(fileNotFoundException.getMessage());
            System.exit(1);    
        }    
    }
    
    void closeFile(){
        if(fileInput != null){
            fileInput.close();
        }
    }
    
    //function to read prices from prices.txt into an array
    void readFileContentsIntoArray(float prices[]){
        int i = 0;
        while(fileInput.hasNext()){
            fileInput.next();
            prices[i] = fileInput.nextFloat();
            i++;
        }
    } 
}


public class TicketAssigment {
         
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        float prices[] = new float[4];  //prices array
        char seatChartArray[][]; //
        
        int numberOfSoldTickets=0;
        int numberOfSeatsAvailable = 320;
        float totalSales = 0;
        
        SeatSeller seatSeller = new SeatSeller();
        
        seatSeller.openFile();
        seatSeller.readFileContentsIntoArray(prices);
        seatSeller.closeFile();  
         
        seatChartArray = seatSeller.initialiseSeatChartArray(numberOfSoldTickets, totalSales);  //intitialise the array with '*',      
        
        displaySeatMenu(seatChartArray, numberOfSeatsAvailable, numberOfSoldTickets, totalSales, prices);
    }

//this function will display the menu choices    
    static void displaySeatMenu(char seatChartArray[][], int numberOfSeatsAvailable, int numberOfSoldTickets, float totalSales, float prices[]){
        SeatSeller seatSeller = new SeatSeller();
        Scanner input = new Scanner(System.in);
        int option;
        System.out.println("Choose an option(1-4)");
        System.out.println("1. Seat assignment");
        System.out.println("2. Payments (for reserved seat)");
        System.out.println("3. View available seats");
        System.out.println("4. View current seat chart"); 
        System.out.println("5. Reset Seating Plan"); 
        System.out.println("6. Exit"); 
        
        option = input.nextInt();        
        switch(option){
            case 1:                
                seatSeller.displaySeatChart(seatChartArray, numberOfSeatsAvailable, totalSales);
                seatSeller.searchForSeat(seatChartArray, numberOfSoldTickets, totalSales, prices);
                System.out.println();
                displaySeatMenu(seatChartArray, numberOfSeatsAvailable, numberOfSoldTickets, totalSales, prices);
                break;
            
            case 2:
                seatSeller.reservedSeatMenu(seatChartArray, numberOfSoldTickets, numberOfSeatsAvailable, totalSales, prices);
                System.out.println();
                displaySeatMenu(seatChartArray, numberOfSeatsAvailable, numberOfSoldTickets, totalSales, prices);
                break;
              
            case 3:
                seatSeller.listAvailableSeats(seatChartArray, numberOfSoldTickets, totalSales, prices);
                System.out.println();
                displaySeatMenu(seatChartArray, numberOfSeatsAvailable, numberOfSoldTickets, totalSales, prices);
                break;
                
            case 4:
                seatSeller.displaySeatChart(seatChartArray, numberOfSeatsAvailable, totalSales);
                break;
            
            case 5:
                seatChartArray = seatSeller.initialiseSeatChartArray(numberOfSoldTickets, totalSales);
                numberOfSeatsAvailable = 320 - numberOfSoldTickets;
                seatSeller.displaySeatChart(seatChartArray, numberOfSeatsAvailable, totalSales);
                System.out.println();
                displaySeatMenu(seatChartArray, numberOfSeatsAvailable, numberOfSoldTickets, totalSales, prices);
                break;
            
            case 6:
                System.exit(0);
                break;
             
            default:
                System.out.println("Invalid Input");
                System.out.println();
                displaySeatMenu(seatChartArray, numberOfSeatsAvailable, numberOfSoldTickets, totalSales, prices);
        }        
        
    }
    
}
