package com.nearinfinity.examples.guava;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

public class HashingExample {
    public static void main(String[] args) {
        HashFunction hashFunction = Hashing.sha512();

        HashCode hash1 = hashFunction.newHasher()
                .putInt(42)
                .putString("Rod")
                .putString("Johnson")
                .hash();
        System.out.printf("hash1 bits : %d\n", hash1.bits());
        System.out.printf("hash1 value: %s\n", hash1.toString());

        HashCode hash2 = hashFunction.newHasher()
                .putInt(42)
                .putString("Rob")  // one letter difference...
                .putString("Johnson")
                .hash();
        System.out.printf("hash2 bits: %d\n", hash2.bits());
        System.out.printf("hash2 value: %s\n", hash2.toString());
    }
}
