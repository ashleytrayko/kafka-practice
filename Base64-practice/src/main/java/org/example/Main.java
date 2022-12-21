package org.example;

import java.util.Base64;

public class Main {
    public static void main(String[] args) {
        base64();
    }

    public static void base64(){
        String text = "It's good for us";
        byte[] targetBytes = text.getBytes();

        // base64 Encoding
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encode(targetBytes);

        // base64 Decoding
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decodedBytes = decoder.decode(encodedBytes);

        System.out.println("before encode : " + text);
        System.out.println("after encode : " + new String(encodedBytes));
        System.out.println("after decode : " + new String(decodedBytes));


    };
}