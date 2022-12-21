package org.example;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.Base64;

public class Base64FileEncoding {
    public static void main(String [] args) throws IOException {
//        byte[] data = getFileBinary("C:\\Users\\User\\Downloads\\test-text.txt");
//        String encoded = Base64.getEncoder().encodeToString(data);
//        System.out.println(new String(encoded));
//        byte[] decoded = Base64.getDecoder().decode(encoded);
//        System.out.println(new String(decoded));
//        File file = new File("test.txt");
//        FileWriter fw = new FileWriter(file, true);
//        fw.write(new String(decoded));
//        fw.close();
        //byte[] data = getFileBinary("C:\\Users\\User\\Downloads\\batman-5k.jpg");
        File file = new File("C:\\Users\\User\\Downloads\\batman-5k.jpg");
        FileInputStream fis = new FileInputStream(file);
        long LIMITSIZE = (1024*1024);
        long fileSize = file.length();
        String filename = "batman";
        if(fileSize <= LIMITSIZE){
            System.out.println("바로 전송");
        }else{
            System.out.println("파일 분할");
            long numberOfNewFiles = fileSize / LIMITSIZE;
            System.out.println(numberOfNewFiles);
            for(int i = 0; i < numberOfNewFiles; i++){
                FileOutputStream fos = new FileOutputStream("batman_" + i);
                long endpoint = 0;
                byte[] splited = new byte[(int)LIMITSIZE];

//                while((endpoint = fis.read(splited, 0, (int)LIMITSIZE))){
//
//                }

            }
        }
    }

    public static byte[] getFileBinary(String filepath){
        File file = new File(filepath);
        byte[] data = new byte[(int) file.length()];
        try(FileInputStream fio = new FileInputStream(file)){
            data = FileUtils.readFileToByteArray(file);
//            fio.read(data, 0, data.length);
        } catch (Throwable e){
            e.printStackTrace();
        }
        return data;
    }
}
