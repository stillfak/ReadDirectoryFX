package sample;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * В классе реализованно: переход по ветки файловой системы,
 * Ищет файл определенного типа(type введенного пользователем),
 * если файл найден создается экземпляр этого класса,
 * для поиска запроса пользователя в этом файле
 *
 * @author Gavrikov V 15it18
 */

public class SearchInFiles extends Thread {
    private File fileForSearch;
    private String strSearch;

    private static ArrayList<SearchInFiles> searchInFiles;
    private static volatile BufferedWriter fileResult;

    private SearchInFiles(File fileForSearch, String strSearch) {
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
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Промежуточный метод
     * открытие и закрытие файла для записи в результирующий файл,
     * вызов метода поиска файлов типа type,
     * вызов метода, который жждет выполнение потоков
     * и закрытие результирующего файла.
     *
     * @param pathDirectory стартовая директория
     * @param searchMsg запрос пользователя
     * @param type тип искомых фалов
     * @throws IOException input output
     * @throws InterruptedException
     */
    static void starter(File pathDirectory, String searchMsg, String type) throws IOException, InterruptedException, NullPointerException {

    try {
        fileResult = new BufferedWriter(new FileWriter(new File("result.txt")));
        searchInFiles = new ArrayList<>();

        searchFiles(pathDirectory, searchMsg, type);

        aliveAndJoin(searchInFiles);

        fileResult.close();
    }catch (NullPointerException e) {}
    }

    /**
     * Метод записывает в файл строки
     *
     * @param str - строка для записи
     * @throws IOException input output
     */
    private synchronized void write(String str) throws IOException {
        fileResult.write(str + "\n");

    }

    /**
     * Метод ищет файл определеного типа в определенной
     * ветке файловой системы и вызывает создает
     * экземпляр класса(наследник thread) и запускает его
     *
     * @param pathDirectory дирректория для поиска
     * @param searchMsg запрос пользователя
     * @param type тип файлов в которых нужно искать(расширение файла без точки)
     */
    private static void searchFiles(File pathDirectory, String searchMsg, String type)  {
        File[] files = pathDirectory.listFiles();
        assert files != null;
        try {
            for (File file : files) {
//                assert file != null;
                if (file.isFile() && file.getName().matches(".*\\." + type + "$")) {
                    searchInFiles.add(new SearchInFiles(file, searchMsg));
//                    assert searchInFiles.get(searchInFiles.size()-1) != null;
                    searchInFiles.get(searchInFiles.size()-1).start();
                } else if (file.isDirectory()) {

                    searchFiles(file, searchMsg, type);
                }
            }
        }catch (NullPointerException | IndexOutOfBoundsException e){
//            e.printStackTrace();
        }

    }


    /**
     * Метод проверяет выполняеться ли группа потоков из array list, если в результате проверки
     * поток еще выполняется то вызвается метод join для этого потока.
     *
     * @param searchInFiles array list с эземплярами класса SearchInFiles
     * @throws InterruptedException исключение заврешение потока
     */
    private static void aliveAndJoin(ArrayList<SearchInFiles> searchInFiles) throws InterruptedException {
        for (SearchInFiles searchInFile : searchInFiles) {
            if (searchInFile.isAlive()) {
                searchInFile.join();
            }
        }
    }

}
