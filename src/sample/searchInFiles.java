package sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class searchInFiles extends Thread{
    private File fileForSearch;
    private String strSearch;

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

            for (int i = 1;(str = inFile.readLine()) != null;i++) {
                mSearchStr = searchStr.matcher(str);
                if (mSearchStr.find()) {
                    System.out.println(fileForSearch.getPath() + ": " + mSearchStr.group() + "; line: "+ i);
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
