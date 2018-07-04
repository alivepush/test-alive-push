package com.testalivepush;


import android.os.Environment;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <h3>Fileπ§æﬂ¿‡</h3>
 * <p>÷˜“™∑‚◊∞¡À“ª–©∂‘Œƒº˛∂¡–¥µƒ≤Ÿ◊˜
 */
public final class FileUtil {

    private FileUtil() {
        throw new Error("£˛©n£˛");
    }

    /**
     * ∑÷∏Ù∑˚.
     */
    public final static String FILE_EXTENSION_SEPARATOR = ".";

    /**
     * "/"
     */
    public final static String SEP = File.separator;

    /**
     * SDø®∏˘ƒø¬º
     */
    public static final String SDPATH = Environment
            .getExternalStorageDirectory() + File.separator;

    /**
     * ≈–∂œSDø® «∑Òø…”√
     *
     * @return SDø®ø…”√∑µªÿtrue
     */
    public static boolean hasSdcard() {
        String status = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(status);
    }

    /**
     * ∂¡»°Œƒº˛µƒƒ⁄»›
     * <br>
     * ƒ¨»œutf-8±‡¬Î
     *
     * @param filePath Œƒº˛¬∑æ∂
     * @return ◊÷∑˚¥Æ
     * @throws IOException
     */
    public static String readFile(String filePath) throws IOException {
        return readFile(filePath, "utf-8");
    }

    /**
     * ∂¡»°Œƒº˛µƒƒ⁄»›
     *
     * @param filePath    Œƒº˛ƒø¬º
     * @param charsetName ◊÷∑˚±‡¬Î
     * @return String◊÷∑˚¥Æ
     */
    public static String readFile(String filePath, String charsetName)
            throws IOException {
        if (TextUtils.isEmpty(filePath))
            return null;
        if (TextUtils.isEmpty(charsetName))
            charsetName = "utf-8";
        File file = new File(filePath);
        StringBuilder fileContent = new StringBuilder("");
        if (file == null || !file.isFile())
            return null;
        BufferedReader reader = null;
        try {
            InputStreamReader is = new InputStreamReader(new FileInputStream(
                    file), charsetName);
            reader = new BufferedReader(is);
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (!fileContent.toString().equals("")) {
                    fileContent.append("\r\n");
                }
                fileContent.append(line);
            }
            return fileContent.toString();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * ∂¡»°Œƒ±æŒƒº˛µΩList◊÷∑˚¥ÆºØ∫œ÷–(ƒ¨»œutf-8±‡¬Î)
     *
     * @param filePath Œƒº˛ƒø¬º
     * @return Œƒº˛≤ª¥Ê‘⁄∑µªÿnull£¨∑Ò‘Ú∑µªÿ◊÷∑˚¥ÆºØ∫œ
     * @throws IOException
     */
    public static List<String> readFileToList(String filePath)
            throws IOException {
        return readFileToList(filePath, "utf-8");
    }

    /**
     * ∂¡»°Œƒ±æŒƒº˛µΩList◊÷∑˚¥ÆºØ∫œ÷–
     *
     * @param filePath    Œƒº˛ƒø¬º
     * @param charsetName ◊÷∑˚±‡¬Î
     * @return Œƒº˛≤ª¥Ê‘⁄∑µªÿnull£¨∑Ò‘Ú∑µªÿ◊÷∑˚¥ÆºØ∫œ
     */
    public static List<String> readFileToList(String filePath,
                                              String charsetName) throws IOException {
        if (TextUtils.isEmpty(filePath))
            return null;
        if (TextUtils.isEmpty(charsetName))
            charsetName = "utf-8";
        File file = new File(filePath);
        List<String> fileContent = new ArrayList<String>();
        if (file == null || !file.isFile()) {
            return null;
        }
        BufferedReader reader = null;
        try {
            InputStreamReader is = new InputStreamReader(new FileInputStream(
                    file), charsetName);
            reader = new BufferedReader(is);
            String line = null;
            while ((line = reader.readLine()) != null) {
                fileContent.add(line);
            }
            return fileContent;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * œÚŒƒº˛÷––¥»Î ˝æ›
     *
     * @param filePath Œƒº˛ƒø¬º
     * @param content  “™–¥»Îµƒƒ⁄»›
     * @param append   »Áπ˚Œ™ true£¨‘ÚΩ´ ˝æ›–¥»ÎŒƒº˛ƒ©Œ≤¥¶£¨∂¯≤ª «–¥»ÎŒƒº˛ø™ º¥¶
     * @return –¥»Î≥…π¶∑µªÿtrue£¨ –¥»Î ß∞‹∑µªÿfalse
     * @throws IOException
     */
    public static boolean writeFile(String filePath, String content,
                                    boolean append) throws IOException {
        if (TextUtils.isEmpty(filePath))
            return false;
        if (TextUtils.isEmpty(content))
            return false;
        FileWriter fileWriter = null;
        try {
            createFile(filePath);
            fileWriter = new FileWriter(filePath, append);
            fileWriter.write(content);
            fileWriter.flush();
            return true;
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * œÚŒƒº˛÷––¥»Î ˝æ›<br>
     * ƒ¨»œ‘⁄Œƒº˛ø™ º¥¶÷ÿ–¬–¥»Î ˝æ›
     *
     * @param filePath Œƒº˛ƒø¬º
     * @param stream   ◊÷Ω⁄ ‰»Î¡˜
     * @return –¥»Î≥…π¶∑µªÿtrue£¨∑Ò‘Ú∑µªÿfalse
     * @throws IOException
     */
    public static boolean writeFile(String filePath, InputStream stream)
            throws IOException {
        return writeFile(filePath, stream, false);
    }

    /**
     * œÚŒƒº˛÷––¥»Î ˝æ›
     *
     * @param filePath Œƒº˛ƒø¬º
     * @param stream   ◊÷Ω⁄ ‰»Î¡˜
     * @param append   »Áπ˚Œ™ true£¨‘ÚΩ´ ˝æ›–¥»ÎŒƒº˛ƒ©Œ≤¥¶£ª
     *                 Œ™false ±£¨«Âø’‘≠¿¥µƒ ˝æ›£¨¥”Õ∑ø™ º–¥
     * @return –¥»Î≥…π¶∑µªÿtrue£¨∑Ò‘Ú∑µªÿfalse
     * @throws IOException
     */
    public static boolean writeFile(String filePath, InputStream stream,
                                    boolean append) throws IOException {
        if (TextUtils.isEmpty(filePath))
            throw new NullPointerException("filePath is Empty");
        if (stream == null)
            throw new NullPointerException("InputStream is null");
        return writeFile(new File(filePath), stream,
                append);
    }

    /**
     * œÚŒƒº˛÷––¥»Î ˝æ›
     * ƒ¨»œ‘⁄Œƒº˛ø™ º¥¶÷ÿ–¬–¥»Î ˝æ›
     *
     * @param file   ÷∏∂®Œƒº˛
     * @param stream ◊÷Ω⁄ ‰»Î¡˜
     * @return –¥»Î≥…π¶∑µªÿtrue£¨∑Ò‘Ú∑µªÿfalse
     * @throws IOException
     */
    public static boolean writeFile(File file, InputStream stream)
            throws IOException {
        return writeFile(file, stream, false);
    }

    /**
     * œÚŒƒº˛÷––¥»Î ˝æ›
     *
     * @param file   ÷∏∂®Œƒº˛
     * @param stream ◊÷Ω⁄ ‰»Î¡˜
     * @param append Œ™true ±£¨‘⁄Œƒº˛ø™ º¥¶÷ÿ–¬–¥»Î ˝æ›£ª
     *               Œ™false ±£¨«Âø’‘≠¿¥µƒ ˝æ›£¨¥”Õ∑ø™ º–¥
     * @return –¥»Î≥…π¶∑µªÿtrue£¨∑Ò‘Ú∑µªÿfalse
     * @throws IOException
     */
    public static boolean writeFile(File file, InputStream stream,
                                    boolean append) throws IOException {
        if (file == null)
            throw new NullPointerException("file = null");
        OutputStream out = null;
        try {
            createFile(file.getAbsolutePath());
            out = new FileOutputStream(file, append);
            byte data[] = new byte[1024];
            int length = -1;
            while ((length = stream.read(data)) != -1) {
                out.write(data, 0, length);
            }
            out.flush();
            return true;
        } finally {
            if (out != null) {
                try {
                    out.close();
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * ∏¥÷∆Œƒº˛
     *
     * @param sourceFilePath ‘¥Œƒº˛ƒø¬º£®“™∏¥÷∆µƒŒƒº˛ƒø¬º£©
     * @param destFilePath   ƒø±ÍŒƒº˛ƒø¬º£®∏¥÷∆∫ÛµƒŒƒº˛ƒø¬º£©
     * @return ∏¥÷∆Œƒº˛≥…π¶∑µªÿtrue£¨∑Ò‘Ú∑µªÿfalse
     * @throws IOException
     */
    public static boolean copyFile(String sourceFilePath, String destFilePath)
            throws IOException {
        InputStream inputStream = null;
        inputStream = new FileInputStream(sourceFilePath);
        return writeFile(destFilePath, inputStream);
    }


    /**
     * ªÒ»°ƒ≥∏ˆƒø¬ºœ¬µƒŒƒº˛√˚
     *
     * @param dirPath    ƒø¬º
     * @param fileFilter π˝¬À∆˜
     * @return ƒ≥∏ˆƒø¬ºœ¬µƒÀ˘”–Œƒº˛√˚
     */
    public static List<String> getFileNameList(String dirPath,
                                               FilenameFilter fileFilter) {
        if (fileFilter == null)
            return getFileNameList(dirPath);
        if (TextUtils.isEmpty(dirPath))
            return Collections.emptyList();
        File dir = new File(dirPath);

        File[] files = dir.listFiles(fileFilter);
        if (files == null)
            return Collections.emptyList();

        List<String> conList = new ArrayList<String>();
        for (File file : files) {
            if (file.isFile())
                conList.add(file.getName());
        }
        return conList;
    }

    /**
     * ªÒ»°ƒ≥∏ˆƒø¬ºœ¬µƒŒƒº˛√˚
     *
     * @param dirPath ƒø¬º
     * @return ƒ≥∏ˆƒø¬ºœ¬µƒÀ˘”–Œƒº˛√˚
     */
    public static List<String> getFileNameList(String dirPath) {
        if (TextUtils.isEmpty(dirPath))
            return Collections.emptyList();
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        if (files == null)
            return Collections.emptyList();
        List<String> conList = new ArrayList<String>();
        for (File file : files) {
            if (file.isFile())
                conList.add(file.getName());
        }
        return conList;
    }

    /**
     * ªÒ»°ƒ≥∏ˆƒø¬ºœ¬µƒ÷∏∂®¿©’π√˚µƒŒƒº˛√˚≥∆
     *
     * @param dirPath ƒø¬º
     * @return ƒ≥∏ˆƒø¬ºœ¬µƒÀ˘”–Œƒº˛√˚
     */
    public static List<String> getFileNameList(String dirPath,
                                               final String extension) {
        if (TextUtils.isEmpty(dirPath))
            return Collections.emptyList();
        File dir = new File(dirPath);
        File[] files = dir.listFiles(new FilenameFilter() {

            @Override
            public boolean accept(File dir, String filename) {
                if (filename.indexOf("." + extension) > 0)
                    return true;
                return false;
            }
        });
        if (files == null)
            return Collections.emptyList();
        List<String> conList = new ArrayList<String>();
        for (File file : files) {
            if (file.isFile())
                conList.add(file.getName());
        }
        return conList;
    }

    /**
     * ªÒµ√Œƒº˛µƒ¿©’π√˚
     *
     * @param filePath Œƒº˛¬∑æ∂
     * @return »Áπ˚√ª”–¿©’π√˚£¨∑µªÿ""
     */
    public static String getFileExtension(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }
        int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        int filePosi = filePath.lastIndexOf(File.separator);
        if (extenPosi == -1) {
            return "";
        }
        return (filePosi >= extenPosi) ? "" : filePath.substring(extenPosi + 1);
    }

    /**
     * ¥¥Ω®Œƒº˛
     *
     * @param path Œƒº˛µƒæ¯∂‘¬∑æ∂
     * @return
     */
    public static boolean createFile(String path) {
        if (TextUtils.isEmpty(path))
            return false;
        return createFile(new File(path));
    }

    /**
     * ¥¥Ω®Œƒº˛
     *
     * @param file
     * @return ¥¥Ω®≥…π¶∑µªÿtrue
     */
    public static boolean createFile(File file) {
        if (file == null || !makeDirs(getFolderName(file.getAbsolutePath())))
            return false;
        if (!file.exists())
            try {
                return file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        return false;
    }

    /**
     * ¥¥Ω®ƒø¬º£®ø…“‘ «∂‡∏ˆ£©
     *
     * @param filePath ƒø¬º¬∑æ∂
     * @return »Áπ˚¬∑æ∂Œ™ø’ ±£¨∑µªÿfalse£ª»Áπ˚ƒø¬º¥¥Ω®≥…π¶£¨‘Ú∑µªÿtrue£¨∑Ò‘Ú∑µªÿfalse
     */
    public static boolean makeDirs(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }
        File folder = new File(filePath);
        return (folder.exists() && folder.isDirectory()) ? true : folder
                .mkdirs();
    }

    /**
     * ¥¥Ω®ƒø¬º£®ø…“‘ «∂‡∏ˆ£©
     *
     * @param dir ƒø¬º
     * @return »Áπ˚ƒø¬º¥¥Ω®≥…π¶£¨‘Ú∑µªÿtrue£¨∑Ò‘Ú∑µªÿfalse
     */
    public static boolean makeDirs(File dir) {
        if (dir == null)
            return false;
        return (dir.exists() && dir.isDirectory()) ? true : dir.mkdirs();
    }

    /**
     * ≈–∂œŒƒº˛ «∑Ò¥Ê‘⁄
     *
     * @param filePath Œƒº˛¬∑æ∂
     * @return »Áπ˚¬∑æ∂Œ™ø’ªÚ’ﬂŒ™ø’∞◊◊÷∑˚¥Æ£¨æÕ∑µªÿfalse£ª»Áπ˚Œƒº˛¥Ê‘⁄£¨«“ «Œƒº˛£¨
     * æÕ∑µªÿtrue£ª»Áπ˚≤ª «Œƒº˛ªÚ’ﬂ≤ª¥Ê‘⁄£¨‘Ú∑µªÿfalse
     */
    public static boolean isFileExist(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }
        File file = new File(filePath);
        return (file.exists() && file.isFile());
    }

    /**
     * ªÒµ√≤ª¥¯¿©’π√˚µƒŒƒº˛√˚≥∆
     *
     * @param filePath Œƒº˛¬∑æ∂
     * @return
     */
    public static String getFileNameWithoutExtension(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }
        int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        int filePosi = filePath.lastIndexOf(File.separator);
        if (filePosi == -1) {
            return (extenPosi == -1 ? filePath : filePath.substring(0,
                    extenPosi));
        }
        if (extenPosi == -1) {
            return filePath.substring(filePosi + 1);
        }
        return (filePosi < extenPosi ? filePath.substring(filePosi + 1,
                extenPosi) : filePath.substring(filePosi + 1));
    }

    /**
     * ªÒµ√Œƒº˛√˚
     *
     * @param filePath Œƒº˛¬∑æ∂
     * @return »Áπ˚¬∑æ∂Œ™ø’ªÚø’¥Æ£¨∑µªÿ¬∑æ∂√˚£ª≤ªŒ™ø’ ±£¨∑µªÿŒƒº˛√˚
     */
    public static String getFileName(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }
        int filePosi = filePath.lastIndexOf(File.separator);
        return (filePosi == -1) ? filePath : filePath.substring(filePosi + 1);
    }

    /**
     * ªÒµ√À˘‘⁄ƒø¬º√˚≥∆
     *
     * @param filePath Œƒº˛µƒæ¯∂‘¬∑æ∂
     * @return »Áπ˚¬∑æ∂Œ™ø’ªÚø’¥Æ£¨∑µªÿ¬∑æ∂√˚£ª≤ªŒ™ø’ ±£¨»Áπ˚Œ™∏˘ƒø¬º£¨∑µªÿ"";
     * »Áπ˚≤ª «∏˘ƒø¬º£¨∑µªÿÀ˘‘⁄ƒø¬º√˚≥∆£¨∏Ò Ω»Á£∫C:/Windows/Boot
     */
    public static String getFolderName(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }
        int filePosi = filePath.lastIndexOf(File.separator);
        return (filePosi == -1) ? "" : filePath.substring(0, filePosi);
    }

    /**
     * ≈–∂œƒø¬º «∑Ò¥Ê‘⁄
     *
     * @param directoryPathƒø¬º¬∑æ∂
     * @return »Áπ˚¬∑æ∂Œ™ø’ªÚø’∞◊◊÷∑˚¥Æ£¨∑µªÿfalse£ª»Áπ˚ƒø¬º¥Ê‘⁄«“£¨»∑ µ «ƒø¬ºŒƒº˛º–£¨
     * ∑µªÿtrue£ª»Áπ˚≤ª «Œƒº˛º–ªÚ’ﬂ≤ª¥Ê‘⁄£¨‘Ú∑µªÿfalse
     */
    public static boolean isFolderExist(String directoryPath) {
        if (TextUtils.isEmpty(directoryPath)) {
            return false;
        }
        File dire = new File(directoryPath);
        return (dire.exists() && dire.isDirectory());
    }

    /**
     * …æ≥˝÷∏∂®Œƒº˛ªÚ÷∏∂®ƒø¬ºƒ⁄µƒÀ˘”–Œƒº˛
     *
     * @param path Œƒº˛ªÚƒø¬ºµƒæ¯∂‘¬∑æ∂
     * @return ¬∑æ∂Œ™ø’ªÚø’∞◊◊÷∑˚¥Æ£¨∑µªÿtrue£ªŒƒº˛≤ª¥Ê‘⁄£¨∑µªÿtrue£ªŒƒº˛…æ≥˝∑µªÿtrue£ª
     * Œƒº˛…æ≥˝“Ï≥£∑µªÿfalse
     */
    public static boolean deleteFile(String path) {
        if (TextUtils.isEmpty(path)) {
            return true;
        }
        return deleteFile(new File(path));
    }

    /**
     * …æ≥˝÷∏∂®Œƒº˛ªÚ÷∏∂®ƒø¬ºƒ⁄µƒÀ˘”–Œƒº˛
     *
     * @param file
     * @return ¬∑æ∂Œ™ø’ªÚø’∞◊◊÷∑˚¥Æ£¨∑µªÿtrue£ªŒƒº˛≤ª¥Ê‘⁄£¨∑µªÿtrue£ªŒƒº˛…æ≥˝∑µªÿtrue£ª
     * Œƒº˛…æ≥˝“Ï≥£∑µªÿfalse
     */
    public static boolean deleteFile(File file) {
        if (file == null)
            throw new NullPointerException("file is null");
        if (!file.exists()) {
            return true;
        }
        if (file.isFile()) {
            return file.delete();
        }
        if (!file.isDirectory()) {
            return false;
        }

        File[] files = file.listFiles();
        if (files == null)
            return true;
        for (File f : files) {
            if (f.isFile()) {
                f.delete();
            } else if (f.isDirectory()) {
                deleteFile(f.getAbsolutePath());
            }
        }
        return file.delete();
    }

    /**
     * …æ≥˝÷∏∂®ƒø¬º÷–Ãÿ∂®µƒŒƒº˛
     *
     * @param dir
     * @param filter
     */
    public static void delete(String dir, FilenameFilter filter) {
        if (TextUtils.isEmpty(dir))
            return;
        File file = new File(dir);
        if (!file.exists())
            return;
        if (file.isFile())
            file.delete();
        if (!file.isDirectory())
            return;

        File[] lists = null;
        if (filter != null)
            lists = file.listFiles(filter);
        else
            lists = file.listFiles();

        if (lists == null)
            return;
        for (File f : lists) {
            if (f.isFile()) {
                f.delete();
            }
        }
    }

    /**
     * ªÒµ√Œƒº˛ªÚŒƒº˛º–µƒ¥Û–°
     *
     * @param path Œƒº˛ªÚƒø¬ºµƒæ¯∂‘¬∑æ∂
     * @return ∑µªÿµ±«∞ƒø¬ºµƒ¥Û–° £¨◊¢£∫µ±Œƒº˛≤ª¥Ê‘⁄£¨Œ™ø’£¨ªÚ’ﬂŒ™ø’∞◊◊÷∑˚¥Æ£¨∑µªÿ -1
     */
    public static long getFileSize(String path) {
        if (TextUtils.isEmpty(path)) {
            return -1;
        }
        File file = new File(path);
        return (file.exists() && file.isFile() ? file.length() : -1);
    }


}
