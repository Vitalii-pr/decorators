
package com.example;

public class TimedDocument extends DocumentDecorator {
    public TimedDocument(Document document) {
        super(document);
    }
    @Override
    public String parse(String path){
        long startTime = System.nanoTime();
        String res = super.parse(path);
        double duration = (System.nanoTime() - startTime) / 1_000_000.0;
        System.out.println("Duration in seconds, "+ duration);
        return res;
    }
}
