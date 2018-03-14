package sample;

import java.io.*;
import java.net.MalformedURLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class searchInFiles extends Thread{
    private File fileForSearch;
    private String strSearch;
    private static BufferedWriter fileresult;

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

            fileresult = new BufferedWriter(new FileWriter("result.txt"));

            for (int i = 1;(str = inFile.readLine()) != null;i++) {
                mSearchStr = searchStr.matcher(str);
                if (mSearchStr.find()) {
                    write(fileForSearch.getPath() + ": " + mSearchStr.group() + "; line: "+ i);
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized void write(String str) throws IOException {
        fileresult.write(str+"\n");
    }

}
