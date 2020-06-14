package com.company;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException {
        // write your code here
        System.out.println("Welcome To Modelling and Simulation First Project");
        TimeUnit.SECONDS.sleep(2);
        while (true) {
            Scanner in = new Scanner(System.in);
            System.out.print("Enter 1 for Explicit or 2 for Probabilities : ");

            int option = in.nextInt();
            if (option == 1) {
                // for Explicit
                System.out.print("Enter 1 for Read from file or 2 for entering input or 3 to Exit : ");
                int fileOrInput = in.nextInt();
                if (fileOrInput == 1) {
                    // Read from file
                    System.out.print("Enter the path of the problem text : ");
                    String ll = in.next();
                    ReadingInput readingInput = new ReadingInput(ll);
                    String inp = readingInput.input;
                    Scanner scanner = new Scanner(inp);
                    int numberOfJobs = readingInput.numberOfJobs;


                    int[][] rows = new int[numberOfJobs][9];
                    /**
                     * row[i][0] = Jobs
                     * row[i][1] = Inter-Arrival
                     * row[i][2] = Arrived
                     * row[i][3] = Service Time
                     * row[i][4] = Beginning of Service
                     * row[i][5] = Waiting Time
                     * row[i][6] = End of Service Time
                     * row[i][7] = IDle Time
                     * row[i][8] = System Time
                     **/
                    for (int k = 0; scanner.hasNextLine(); k++) {

                        String line = scanner.nextLine();
                        String[] languages = line.split("\\s");
                        languages = line.trim().split("\\s+");
                        rows[k][0] = Integer.parseInt(languages[0]);
                        rows[k][1] = Integer.parseInt(languages[1]);
                        rows[k][3] = Integer.parseInt(languages[2]);
                    }
                    rows[0][2] = rows[0][1];
                    rows[0][4] = rows[0][2];
                    rows[0][5] = rows[0][4] - rows[0][2];
                    rows[0][6] = rows[0][4] + rows[0][3];
                    rows[0][7] = rows[0][4];
                    rows[0][8] = rows[0][3] + rows[0][5];
                    for (int i = 1; i < rows.length; i++) {
                        rows[i][2] = rows[i - 1][2] + rows[i][1];
                        rows[i][4] = Math.max(rows[i][2], rows[i - 1][6]);
                        rows[i][5] = rows[i][4] - rows[i][2];
                        rows[i][6] = rows[i][4] + rows[i][3];
                        rows[i][7] = rows[i][4] - rows[i - 1][6];
                        rows[i][8] = rows[i][3] + rows[i][5];
                    }

                    System.out.printf("%-15s |\t%-15s |\t%-15s |\t%-15s |\t%-15s |\t%-15s |\t%-15s |\t%-15s |\t%-15s |\n","Jobs","Inter-Arrival","Arrived","Service","Beginning","Waiting","Ending","Idle","SystemTime");

                   // System.out.println("Jobs\t\t|\t\tInter-Arrival\t|\t\tArrived\t\t\t|\t\tService\t\t\t|\t\tBeginning\t\t|\t\tWaiting\t\t\t|\t\tEnding\t\t\t|\t\t Idle\t\t\t|\t\tSystemTime");
                    System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");


                    for (int i = 0; i < rows.length; i++) {
                        for (int j = 0; j < rows[i].length; j++) {
                         //   System.out.print(rows[i][j] + "\t\t\t|\t\t\t");
                            System.out.printf("%-15s |\t",rows[i][j]);
                        }
                        System.out.println();
                        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

                    }


                    double sumOfWaiting = 0, sumOfSystemTime = 0, sumOfServiceTime = 0;
                    for (int i = 0; i < rows.length; i++) {
                        sumOfWaiting += rows[i][5];
                        sumOfSystemTime += rows[i][8];
                        sumOfServiceTime += rows[i][3];
                    }
                    System.out.println("Average Time in Queue = " + sumOfWaiting + " / " + numberOfJobs + " = " + (sumOfWaiting / numberOfJobs) + " mins");

                    System.out.println("Average Time in System = " + sumOfSystemTime + " / " + numberOfJobs + " = " + (sumOfSystemTime / numberOfJobs) + " mins");
                    DecimalFormat df = new DecimalFormat("#.###");
                    df.setRoundingMode(RoundingMode.HALF_UP);
                    System.out.println("Utilization = Summation of Service Time / Total System Time = " + sumOfServiceTime + " / " + rows[rows.length - 1][6] + " = " + df.format((sumOfServiceTime / rows[rows.length - 1][6])));


                } else if (fileOrInput == 2) {
                    // Enter Input
                } else {
                    //Exit
                }
            } else {
                // for probabilities
                System.out.print("Enter 1 for Read from file or 2 for entering input or 3 to Exit : ");
                int fileOrInput = in.nextInt();
                if (fileOrInput == 1) {
                    // Read from file
                    System.out.print("Enter the path of the Time Between Arrivals and it's Probabilities text : ");
                    String ll = in.next();
                    ReadingInput readingInput = new ReadingInput(ll);
                    String inp = readingInput.input;
                    Scanner scanner = new Scanner(inp);
                    int numberOfTimesRows = readingInput.numberOfJobs;
                    double[][] rowsFromPDFtoCDF = new double[numberOfTimesRows][3];

                    /**
                     * rowsFromPDFtoCDF[i][0] = Times
                     * rowsFromPDFtoCDF[i][1] = PDF
                     * rowsFromPDFtoCDF[i][2] = CDF
                     */
                    for (int k = 0; scanner.hasNextLine(); k++) {
                        String line = scanner.nextLine();
                        String[] languages = line.split("\\s");
                        languages = line.trim().split("\\s+");
                        rowsFromPDFtoCDF[k][0] = Integer.parseInt(languages[0]);
                        rowsFromPDFtoCDF[k][1] = Double.parseDouble(languages[1]);
                    }
                    rowsFromPDFtoCDF[0][2] = rowsFromPDFtoCDF[0][1];
                    for (int i = 1; i < rowsFromPDFtoCDF.length; i++) {
                        rowsFromPDFtoCDF[i][2] = rowsFromPDFtoCDF[i - 1][2] + rowsFromPDFtoCDF[i][1];
                    }
                    int startingRange, endingRange;
                    System.out.print("Enter The Number of Jobs : ");
                    String inq = in.next();
                    int numberOfJobs = 0;
                    //if (!inq.equals("*")) {
                        numberOfJobs = Integer.parseInt(inq);
                    //}

                    System.out.print("Enter the path of the jobs and its random values text or 2 for generating random values: ");
                    String ll2 = in.next();


                    double[][] rows = new double[numberOfJobs][11];
                    /**
                     * row[i][0] = Jobs
                     * row[i][1] = Random Values
                     * row[i][2] = Normalized Values
                     * row[i][3] = Inter-Arrival
                     * row[i][4] = Arrived
                     * row[i][5] = Service Time
                     * row[i][6] = Beginning of Service
                     * row[i][7] = Waiting Time
                     * row[i][8] = End of Service Time
                     * row[i][9] = IDle Time
                     * row[i][10] = System Time
                     **/

                    if (ll2.equals("2")) {

                        System.out.print("Enter the starting of the range : ");
                        startingRange = in.nextInt();
                        System.out.print("Enter the ending of the range : ");
                        endingRange = in.nextInt();

                        for (int k = 0; k < rows.length; k++) {
                            rows[k][0]= k;
                            rows[k][1] = ThreadLocalRandom.current().nextInt(startingRange, endingRange + 1);
                        }

                    } else {
                        ReadingInput readingInput2 = new ReadingInput(ll2);
                        String inp2 = readingInput2.input;
                        Scanner scanner2 = new Scanner(inp2);
                        numberOfJobs = readingInput2.numberOfJobs;



                        for (int k = 0; scanner2.hasNextLine(); k++) {
                            String line = scanner2.nextLine();
                            String[] languages = line.split("\\s");
                            languages = line.trim().split("\\s+");
                            rows[k][0] = Integer.parseInt(languages[0]);
                            rows[k][1] = Double.parseDouble(languages[1]);
                        }
                    }
                    System.out.print("Enter The Values of Service time : ");
                    for(int l =0;l<rows.length;l++){
                        rows[l][5]=in.nextInt();
                    }

                    for (int i = 0; i < rows.length; i++) {
                        rows[i][2] = rows[i][1] / 1000;
                    }

                    for (int i = 0; i < rows.length; i++) {
                        for (int j = 0; j < rowsFromPDFtoCDF.length; j++) {
                            if (rows[i][2] <= rowsFromPDFtoCDF[j][2]) {
                                rows[i][3] = rowsFromPDFtoCDF[j][0];
                                break;
                            }
                        }
                    }

                    rows[0][4] = rows[0][3];
                    rows[0][6] = rows[0][4];
                    rows[0][7] = rows[0][6] - rows[0][4];
                    rows[0][8] = rows[0][6] + rows[0][5];
                    rows[0][9] = rows[0][6];
                    rows[0][10] = rows[0][5] + rows[0][7];
                    for (int i = 1; i < rows.length; i++) {
                        rows[i][4] = rows[i - 1][4] + rows[i][3];
                        rows[i][6] = Math.max(rows[i][4], rows[i - 1][8]);
                        rows[i][7] = rows[i][6] - rows[i][4];
                        rows[i][8] = rows[i][6] + rows[i][5];
                        rows[i][9] = rows[i][6] - rows[i - 1][8];
                        rows[i][10] = rows[i][5] + rows[i][7];
                    }

                  //  System.out.println("Jobs\t\t\t|\t\t\tRandom\t\t\t\t|\t\t\tNormalize\t\t\t|\t\tInter-Arrival\t\t|\t\tArrived\t\t\t\t|\t\tService\t\t\t\t|\t\tBeginning\t\t\t|\t\tWaiting\t\t\t\t|\t\tEnding\t\t\t|\t\tIdle\t\t\t|\t\tSystemTime");
                    System.out.printf("%-15s |\t%-15s |\t%-15s |\t%-15s |\t%-15s |\t%-15s |\t%-15s |\t%-15s |\t%-15s |\t%-15s |\t%-15s |\n","Jobs","Random","Normalize","Inter-Arrival","Arrived","Service","Beginning","Waiting","Ending","Idle","SystemTime");

                    System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");


                    for (int i = 0; i < rows.length; i++) {
                        for (int j = 0; j < rows[i].length; j++) {
                             //   System.out.print(rows[i][j] + "\t|\t");
                            System.out.printf("%-15s |\t",rows[i][j]);

                        }
                        System.out.println();
                        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

                    }


                    double sumOfWaiting = 0, sumOfSystemTime = 0, sumOfServiceTime = 0;
                    for (int i = 0; i < rows.length; i++) {
                        sumOfWaiting += rows[i][7];
                        sumOfSystemTime += rows[i][10];
                        sumOfServiceTime += rows[i][5];
                    }
                    System.out.println("Average Time in Queue = " + sumOfWaiting + " / " + numberOfJobs + " = " + (sumOfWaiting / numberOfJobs) + " mins");

                    System.out.println("Average Time in System = " + sumOfSystemTime + " / " + numberOfJobs + " = " + (sumOfSystemTime / numberOfJobs) + " mins");
                    DecimalFormat df = new DecimalFormat("#.###");
                    df.setRoundingMode(RoundingMode.HALF_UP);
                    System.out.println("Utilization = Summation of Service Time / Total System Time = " + sumOfServiceTime + " / " + rows[rows.length - 1][8] + " = " + df.format((sumOfServiceTime / rows[rows.length - 1][8])));


                }

            }


        }
    }
}
