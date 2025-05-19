package storage;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import model.user.Admin;
import model.user.Company;
import model.user.Individual;
import model.user.User;

public class CSVManager implements StorageManager {
    private static int loadCount;
    private static int saveCount;
    private String data;

    public CSVManager(){
        loadCount = 0;
        saveCount = 0;
    }

    private String readSpecificLine(String filePath, int lineNumber){
        try {
            FileInputStream fs = new FileInputStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(fs));
            for(int i = 0; i < lineNumber; ++i){
                br.readLine();
            }
            return br.readLine();
        } catch (FileNotFoundException e) {
            System.err.println(e);
            return null;
        } 
        catch (IOException e) {
            System.err.println(e);
            return null;
        }
    }
    
    public User createUserFromCSV(String filePath){
        String data = readSpecificLine(filePath, loadCount);
        String[] parts = data.split(",");
        String[] kv = parts[0].split(":");
        switch(kv[1]){
            case "Admin": return new Admin();
            case "Individual": return new Individual(); 
            case "Company": return new Company();
            default: throw new NullPointerException();
        }
    }

    // IMPLEMENTING INTERFACE METHODS
    @Override
    public void load(Storable s, String filePath){
        String data = readSpecificLine(filePath, loadCount++);
        if(data == null)
            throw new RuntimeException();
        s.unmarshal(data);
        this.data = data;
    }


    @Override // @param append true: προσθέτει στο τέλος, false: αντικαθιστα αρχειο
    public void save(Storable s, String filePath, boolean append){
        
    }

}
