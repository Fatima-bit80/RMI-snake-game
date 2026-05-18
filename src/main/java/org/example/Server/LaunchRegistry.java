package org.example.Server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;

import static org.example.Statics.Config.PORT;


public class LaunchRegistry {


    public static void main(String[] args) {

        try {
            LocateRegistry.createRegistry(PORT);

            Scanner sc = new Scanner(System.in);
            System.out.println("enter to stop the registry");
            sc.nextLine();

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }


    }
}
