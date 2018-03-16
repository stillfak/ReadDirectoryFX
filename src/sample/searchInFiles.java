package sample;

import java.io.*;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class searchInFiles extends Thread {
    private File fileForSearch;
    private String strSearch;

    private static ArrayList<searchInFiles> searchInFiles;
    private static volatile BufferedWriter fileResult;
    private static int i = 0;

    public searchInFiles(File fileForSearch, String strSearch) {
        this.fileForSearch = fileForSearch;
        this.strSearch = strSearch;
    }

    @Override
    public void run() {
        String str;
        Pattern searchStr = Pattern.compile(strSearch);


        Matcher mSearchStr;
        try (BufferedReader inFile = new BufferedReader(new FileReader(fileForSearch))) {


            for (int i = 1; (str = inFile.readLine()) != null; i++) {
                mSearchStr = searchStr.matcher(str);
                if (mSearchStr.find()) {
                    write(fileForSearch.getPath() + ": " + mSearchStr.group() + "; line: " + i);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void starter(File pathDirectory, String searchMsg, String type) throws IOException, InterruptedException {
        fileResult = new BufferedWriter(new FileWriter(new File("result.txt")));
        searchInFiles = new ArrayList<>();

        searchDirectory(pathDirectory, searchMsg, type);

        aliveAndJoin(searchInFiles);
        fileResult.close();
    }

    private synchronized void write(String str) throws IOException {
        fileResult.write(str + "\n");
    }

    public static synchronized void  searchDirectory(File pathDirectory, String searchMsg, String type) throws NullPointerException, IOException, IndexOutOfBoundsException{
        File[] files = pathDirectory.listFiles();
        assert files != null;

        for (int j = 0;j < files.length; j++) {

            if (files[j].isFile() && files[j].getName().matches(".*\\." + type + "$")) {
                searchInFiles.add(new searchInFiles(files[j], searchMsg));

                searchInFiles.get(i).start();
                i++;
            } else if (files[j].isDirectory()) {

                searchDirectory(files[j], searchMsg,type);
            }
        }

    }

    private static void aliveAndJoin(ArrayList<searchInFiles> searchInFiles) throws InterruptedException {
        for (int i = 0; i < searchInFiles.size(); i++) {
            if (searchInFiles.get(i).isAlive()) {
                searchInFiles.get(i).join();
            }
        }
    }

}
