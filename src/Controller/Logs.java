package Controller;

import Model.Log;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Logs {

    private ArrayList<Log> logs;
    private Server server;

    public Logs(Server server){
        this.server = server;
        logs = new ArrayList<>();
    }

    public void add(String logMsg){
        Log log = new Log(logMsg);
        logs.add(log);
        save();
    }

    private void save() {
        try(FileOutputStream fos = new FileOutputStream("logs/amount_of_logs.dat");
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            ObjectOutputStream oos = new ObjectOutputStream(bos)){
            oos.writeInt(logs.size());
            oos.flush();
        }catch(IOException e){
            e.printStackTrace();
        }

        try(FileOutputStream fos = new FileOutputStream("logs/logs.dat");
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            for (int i = 0; i < logs.size(); i++) {
                oos.writeObject(logs.get(i));
                oos.flush();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void load(){
        int size = 0;
        File file = new File("logs/amount_of_logs.dat");
        if(file.isFile()){
            try(FileInputStream fis = new FileInputStream(file);
                BufferedInputStream bis = new BufferedInputStream(fis);
                ObjectInputStream ois = new ObjectInputStream(bis)) {
                size = ois.readInt();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        file = new File("logs/logs.dat");
        if(file.isFile()){
            try(FileInputStream fis = new FileInputStream(file);
                BufferedInputStream bis = new BufferedInputStream(fis);
                ObjectInputStream ois = new ObjectInputStream(bis)) {
                for(int i = 0; i < size; i++){
                    Object temp = ois.readObject();
                    if(temp instanceof Log log){
                        logs.add(log);
                    }
                }
            } catch(IOException | ClassNotFoundException e){
                e.printStackTrace();
            }
        }
    }

    public String getTheseLogs(LocalDateTime from, LocalDateTime until) {
        StringBuilder builder = new StringBuilder();
        for(Log log : logs){
            if(log.getTime().isAfter(from) && log.getTime().isBefore(until)){
                builder.append(log.getTimeAsString() + ": " + log.getText()).append("\n");
            }
        }
        return builder.toString();
    }
}
