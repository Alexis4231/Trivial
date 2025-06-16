package org.proyect.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Client {
    private static Scanner scString = new Scanner(System.in);
    private static Scanner scInt = new Scanner(System.in);
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 48120);
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.print(br.readLine());
            pw.println(scString.next());
            System.out.print(br.readLine());
            pw.println(scString.next());
            String continueCode = br.readLine();
            if (continueCode.equals("Cod:10")) {
                for (int i = 0; i < 6; i++) {
                    System.out.println(br.readLine());
                    System.out.println(br.readLine());
                    System.out.println(br.readLine());
                    System.out.println(br.readLine());
                    System.out.println(br.readLine());
                    System.out.print(br.readLine());
                    int number = 0;
                    while(true) {
                        try {
                            number = scInt.nextInt();
                            if(number <= 0 || number > 4){
                                System.out.print("Introduce una opción valida: ");
                            }else {
                                pw.println(number);
                                break;
                            }
                        } catch (InputMismatchException e) {
                            System.out.print("Introduce un valor númerico: ");
                            scInt.next();
                        }
                    }
                    System.out.println(br.readLine());
                    System.out.println();
                }
                System.out.println(br.readLine());
            } else {
                System.out.println("Usuario o contraseña incorrectos");
            }
            socket.close();
        } catch (ConnectException e) {
            System.err.println("El servidor está apagado");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
