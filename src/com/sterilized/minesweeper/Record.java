package com.sterilized.minesweeper;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/**
 * Best Scores
 */
public class Record {
    private int second;
    private String nickname;

    private static final String DIRECTORY = "record/mine_sweeper.dat";
    private static final Path PATH = Paths.get(DIRECTORY);

    public Record(int second, String nickname) {
        this.second = second;
        if (nickname.length() > 32)
            nickname.substring(0, 32);
        this.nickname = nickname;
    }

    public int getSecond() {
        return second;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public String toString() {
        return "" + second + ':' + nickname;
    }

    public static Record stringToRecord(String str) throws NoSuchElementException, NumberFormatException {
        String[] token = str.split(":");
        return new Record(Integer.parseInt(token[0]), token[1]);
    }

    private static void createRecordFile() {
        FileChannel fc;
        ByteBuffer buffer = (Charset.defaultCharset()).encode("999:Anonymous/999:Anonymous/999:Anonymous");
        buffer.flip();

        try {
            fc = FileChannel.open(PATH, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);
            fc.write(buffer);
            fc.close();
        } catch (IOException e) {}
    }

    public static void resetRecordFile() {
        FileChannel fc;
        ByteBuffer buffer = (Charset.defaultCharset()).encode("999:Anonymous/999:Anonymous/999:Anonymous");

        try {
            fc = FileChannel.open(PATH, StandardOpenOption.WRITE);
            fc.write(buffer);
            fc.truncate(buffer.position());
            fc.close();
        } catch (IOException e) {}
    }

    public static void saveRecord(Setting setting, int second, String nickname) {
        saveRecord(setting, new Record(second, nickname));
    }

    public static void saveRecord(Setting setting, Record record) {
        FileChannel fc;
        ByteBuffer buffer;

        if (!Files.exists(PATH))
            createRecordFile();

        Record[] records = loadRecords();

        if (setting == Setting.BEGINNER) {
            records[0] = record;
        } else if (setting == Setting.INTERMEDIATE) {
            records[1] = record;
        } else if (setting == Setting.ADVANCED) {
            records[2] = record;
        }

        String newRecords = "";
        for (int i = 0;; ++i) {
            newRecords += records[i].toString();
            if (i == 2)
                break;
            newRecords += '/';
        }
        buffer = (Charset.defaultCharset()).encode(newRecords);

        try {
            fc = FileChannel.open(PATH, StandardOpenOption.WRITE);
            fc.write(buffer);
            fc.truncate(buffer.position());
            fc.close();
        } catch (IOException e) {}
    }

    public static Record[] loadRecords() {
        if (!Files.exists(PATH))
            createRecordFile();

        Record[] records = new Record[3];
        FileChannel fc;
        ByteBuffer buffer = ByteBuffer.allocate(120);

        if (!Files.exists(PATH))
            createRecordFile();

        try {
            fc = FileChannel.open(PATH, StandardOpenOption.READ);
            fc.read(buffer);
            fc.close();
        } catch (IOException e) {}

        buffer.flip();
        StringTokenizer tokenizer = new StringTokenizer(Charset.defaultCharset().decode(buffer).toString(), "/");
        try {
            for (int i = 0; i < 3; ++i) {
                records[i] = stringToRecord(tokenizer.nextToken());
            }
        } catch (NoSuchElementException | NumberFormatException e) { // file is corrupted
            resetRecordFile();
            return loadRecords();
        }

        return records;
    }

    public static boolean isBest(Setting setting, int second) {
        if (!Files.exists(PATH))
            createRecordFile();

        Record[] records = loadRecords();
        if (setting == Setting.BEGINNER) {
            if (second < records[0].getSecond())
                return true;
        } else if (setting == Setting.INTERMEDIATE) {
            if (second < records[1].getSecond())
                return true;
        } else if (setting == Setting.ADVANCED) {
            if (second < records[2].getSecond())
                return true;
        }

        return false; // custom game
    }

}
