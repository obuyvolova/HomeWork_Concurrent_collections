package com.company;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

public class Main {

    public static ArrayBlockingQueue<String> arrayBlockingQueueForA = new ArrayBlockingQueue<>(100);
    public static ArrayBlockingQueue<String> arrayBlockingQueueForB = new ArrayBlockingQueue<>(100);
    public static ArrayBlockingQueue<String> arrayBlockingQueueForC = new ArrayBlockingQueue<>(100);
    public static int TEXTS_LENGTH = 100_000;
    public static int TEXTS_NUMBERS = 10_000;


    public static void main(String[] args) {

        System.out.println("Please wait...");

        //Thread generate lines
        new Thread(() -> {
            for (int i = 0; i < TEXTS_NUMBERS; i++) {
                try {
                    String text = generateText("abc", TEXTS_LENGTH);
                    arrayBlockingQueueForA.put(text);
                    arrayBlockingQueueForB.put(text);
                    arrayBlockingQueueForC.put(text);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        //Thread max A
        new Thread(() -> {
            System.out.println(findMaxText(arrayBlockingQueueForA, 'a'));
            // System.out.println(findMaxSameChars(arrayBlockingQueueForA, 'a'));
        }).start();

        //Thread max B
        new Thread(() -> {
            System.out.println(findMaxText(arrayBlockingQueueForB, 'b'));
            // System.out.println(findMaxSameChars(arrayBlockingQueueForB, 'b'));
        }).start();

        //Thread max C
        new Thread(() -> {
            System.out.println(findMaxText(arrayBlockingQueueForC, 'c'));
            //  System.out.println(findMaxSameChars(arrayBlockingQueueForC, 'c'));
        }).start();

    }

    public static String findMaxSameChars(ArrayBlockingQueue<String> arrayBlockingQueue, char s) {
        String strMax = "";
        int max = 0;
        for (int i = 0; i < TEXTS_NUMBERS; i++) {
            String text = "";
            try {
                text = arrayBlockingQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int currentValue = maxSameChars(text, s);
            if (max < currentValue) {
                max = currentValue;
                strMax = text;
            }
        }
        return "Same chars " + max + " times" + " -> Line with max same chars '" + s + "' is " + strMax;
    }

    private static int maxSameChars(String text, char s) {
        int max = 0;
        for (int i = 0; i < text.length() - 1; i++) {
            int currentMax = 0;
            if (text.charAt(i) != s) {
                for (int j = i + 1; j < text.length(); j++) {
                    if (text.charAt(j) != s) {
                        break;
                    } else {
                        currentMax++;
                    }
                }
            }
            if (currentMax > max) {
                max = currentMax;
            }
        }
        return max;
    }

    public static String findMaxText(ArrayBlockingQueue<String> arrayBlockingQueue, char s) {
        String strMax = "";
        int max = 0;
        for (int i = 0; i < TEXTS_NUMBERS; i++) {
            String text = "";
            try {
                text = arrayBlockingQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int currentValue = maxChars(text, s);
            if (max < currentValue) {
                max = currentValue;
                strMax = text;
            }
        }
        return "" + max + " times" + " -> Line with max '" + s + "' is " + strMax;
    }

    public static int maxChars(String text, char s) {
        int maxChars = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == s) {
                maxChars++;
            }
        }
        return maxChars;
    }


    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

}
