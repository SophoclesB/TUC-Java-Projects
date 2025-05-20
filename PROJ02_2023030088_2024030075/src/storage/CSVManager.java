package storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;

import model.user.Admin;
import model.user.Company;
import model.user.Individual;
import model.user.User;

public class CSVManager implements StorageManager {
    public static final CSVManager INSTANCE = new CSVManager();
    private static int loadCount;
    private static int saveCount;
    private String data;

    public CSVManager(){
    }

    // IMPLEMENTING INTERFACE METHODS
    @Override
    public void load(Storable s, String filePath){
        File file = new File(filePath);
        try {
            if(!file.exists()) {
                System.out.println("File" + file.getAbsolutePath() + " does not exist.");
                return;
            }

            if (file.isDirectory()) {
                System.out.println("Cannot load directory into system: " + file.getAbsolutePath());
                return;
            }

            String data = new String(Files.readAllBytes(file.toPath()));
            s.unmarshal(data);
        } catch (IOException e) {
            System.out.println("IOException occured during the loading process of the file: " + file.getPath());
        }
    }

    @Override 
    public void save(Storable s, String filePath, boolean append){
        try {
            File fl = new File(filePath);

            if(fl.isDirectory()){
                System.out.println(filePath + " is an invalid filepath because it is a directory.");
                return;
            }

            if(!fl.exists()){
                fl.createNewFile();
            }

            FileWriter fw = new FileWriter(filePath);
            if(append){
                fw.append(s.marshal());
            } else {
                fw.write(s.marshal());
            }

            fw.flush();
            fw.close();
        } catch (IOException e) {
            System.out.println("IOException occured during the saving process of the file: " + filePath);
        }
    }
}
