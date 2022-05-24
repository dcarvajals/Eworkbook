/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;

import static java.nio.file.StandardCopyOption.*;
import java.util.Arrays;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;

/**
 * @author USUARIO
 */
public class FileAccess {

    public FileAccess() {
    }

    public boolean fileExits(String location) {
        try {
            File file = new File(location);
            return file.exists();
        } catch (Exception e) {
            return false;
        }
    }

    public String readFileText(String location, String flagformat) {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        String result = "";
        try {
            archivo = new File(location);
            if (archivo.exists()) {
                fr = new FileReader(archivo);
                br = new BufferedReader(fr);
                String linea;
                while ((linea = br.readLine()) != null) {
                    result += linea + (flagformat.equals("t") ? "\\n" : "");
                }
            } else {
                result = "{}";
                System.out.println("Archivo no existe");
            }
        } catch (Exception e) {
            result = "{}";
            System.out.println("Error in read File project");
        } finally {
            try {
                if (null != fr) {
                    fr.close();
                }
                if (null != br) {
                    br.close();
                }
            } catch (Exception e2) {
                System.out.println("Error in readFileText.fr.close()");
            }
        }
        return result;
    }

    public boolean writeFileText(String location, String structure) {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter(location);
            pw = new PrintWriter(fichero);
            pw.println(structure);
        } catch (Exception e) {
            System.out.println("Error in save File project");
        } finally {
            try {
                if (null != fichero) {
                    fichero.close();
                }
                if (null != pw) {
                    pw.close();
                }
            } catch (Exception e2) {
                System.out.println("Error in writeFileText.fichero.close()");
            }
        }
        return true;
    }

    public boolean SaveImg(String base64, String rutaImagen) {
        File file = new File(rutaImagen);
        return writeOutputStream(base64, file);
    }

    private boolean writeOutputStream(String value, File outputStream) {
        String[] partes = value.split(",");
        try {
            byte[] imgBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(partes[1]);
            BufferedImage bufImg = ImageIO.read(new ByteArrayInputStream(imgBytes));
            ImageIO.write(bufImg, "png", outputStream);
            return true;
        } catch (Exception e) {
            System.out.println("Error creating image: " + e.getMessage());
            return false;
        }
    }

    public boolean saveFile(String base64, String fileurl) {
        String[] parts = base64.split(",");
        try {
            byte[] dataBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(parts[1]);
            FileOutputStream out = new FileOutputStream(fileurl);
            out.write(dataBytes);
            out.close();
            return true;
        } catch (Exception e) {
            System.out.println("Error creating image: " + e.getMessage());
            return false;
        }
    }

    public String getFileNames(String ruta, String folder, String root) {
        String files = "[";
        try {
            File carpeta = new File(ruta);
//            System.out.println("r:"+ruta+"\n"+carpeta.listFiles().length);
            for (File archivo : carpeta.listFiles()) {
                String extension = archivo.getName().substring(archivo.getName().lastIndexOf(".") + 1, archivo.getName().length());
                files += String.format("{\"name\":\"%s\",\"extension\":\"%s\",\"path\":\"%s\"},",
                        archivo.getName(), extension,
                        root + folder + archivo.getName());//folderDataCenter
            }
            if (!files.equals("[")) {
                files = files.substring(0, files.length() - 1) + "]";
            } else {
                files += "]";
            }
//            System.out.println(files);
            return files;
        } catch (Exception e) {
            return "[]";
        }
    }

    public String getFileInfo(String ruta) {
        String files = "[";
        try {
            File carpeta = new File(ruta);
//            System.out.println("r:"+ruta+"\n"+carpeta.listFiles().length);
            for (File archivo : carpeta.listFiles()) {
                String extension = archivo.getName().substring(archivo.getName().lastIndexOf(".") + 1, archivo.getName().length());
                String documento = readFileText(ruta + "/" + archivo.getName(), "t");
                byte[] bytesEncoded = Base64.encodeBase64(documento.getBytes());
                documento = new String(bytesEncoded);
                files += String.format("{\"name\":\"%s\",\"extension\":\"%s\",\"data\":\"%s\"},",
                        archivo.getName(), extension, documento);
            }
            if (!files.equals("[")) {
                files = files.substring(0, files.length() - 1) + "]";
            } else {
                files += "]";
            }
//            System.out.println(files);
            return files;
        } catch (Exception e) {
            return "[]";
        }
    }

    public String getFileList(String ruta) {
        String files = "[";
        try {
            File carpeta = new File(ruta);
            for (File archivo : carpeta.listFiles()) {
                if (archivo.isFile()) {
                    String extension = getExtension(archivo.getName());
                    String dates[] = getTimeInfoFiles(archivo);
                    files += String.format("{\"name\":\"%s\",\"extension\":\"%s\",\"creation\":\"%s\",\"upgrade\":\"%s\"},",
                            archivo.getName(), extension, dates[0], dates[1]);
                }
            }
            if (!files.equals("[")) {
                files = files.substring(0, files.length() - 1) + "]";
            } else {
                files += "]";
            }
            return files;
        } catch (Exception e) {
            return "[]";
        }
    }

    public boolean deleteFile(String fileurl) {
        boolean resp = false;
        try {
            File carpeta = new File(fileurl);
            if (carpeta.exists()) {
                resp = carpeta.delete();
            }
        } catch (Exception e) {
            System.out.println("deleteFile:" + e.getMessage());
        }
        return resp;
    }

    public boolean deleteDirectoryOrFile(File fil) {
        if (fil.exists()) {
            if (fil.isDirectory()) {
                try {
                    FileUtils.deleteDirectory(fil);
                    return true;
                } catch (IOException ex) {
                    System.out.println("warning deleteing Directoru :c");
                }
            } else {
                return fil.delete();
            }
        }
        return false;
    }

    public boolean makeZipFromFolder(String source, String target) {
        try {
            new ZipFile(target).addFolder(new File(source));
            return true;
        } catch (ZipException ex) {
            System.out.println("[Error zip]: " + ex.getMessage());
        }
        return false;
    }

    public boolean makeZipFromOneFile(String source, String target) {
        try {
            new ZipFile(target).addFile(source);
            return true;
        } catch (ZipException ex) {
            System.out.println("[Error zip OneFile]: " + ex.getMessage());
        }
        return false;
    }

    public boolean makeZipFromManyFile(String[] source, String target) {
        try {
            List<File> files = new ArrayList<>();
            for (String path : source) {
                files.add(new File(path));
            }
            new ZipFile(target).addFiles(files);
            return true;
        } catch (ZipException ex) {
            System.out.println("[Error zip ManyFile]: " + ex.getMessage());
        }
        return false;
    }

    public String getExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1, filename.length());
    }

    public String cleanFileName(String fileName) {
        return fileName.toLowerCase().replaceAll("[^a-zA-Z0-9]+", "");
    }

    public boolean move(File f1, File f2) {
        try {
            Files.move(f1.toPath(), f2.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException ex) {
            System.out.println("error move file:" + ex.getMessage());
            return false;
        }
    }

    public String lastUpdateFile(File fill) {
        long lastModified = fill.lastModified();

        String pattern = "yyyy-MM-dd hh:mm aa";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        Date lastModifiedDate = new Date(lastModified);

        return simpleDateFormat.format(lastModifiedDate);
    }

    public String[] getTimeInfoFiles(File fil) {
        String[] response = {"0000-00-00 00:00 AM", "0000-00-00 00:00 AM"};
        try {
            Path path = Paths.get(fil.getPath());
            BasicFileAttributeView basicfile = Files.getFileAttributeView(path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
            BasicFileAttributes attr = basicfile.readAttributes();

            response[0] = milliSecondsToDate(attr.creationTime().toMillis());
            response[1] = milliSecondsToDate(attr.lastModifiedTime().toMillis());
        } catch (Exception e) {
            System.out.println("getTimeInfoFilesError: " + e.getMessage());
        }
        return response;
    }

    private String milliSecondsToDate(long date) {
        String pattern = "yyyy-MM-dd hh:mm aa";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date lastModifiedDate = new Date(date);
        return simpleDateFormat.format(lastModifiedDate);
    }

}
