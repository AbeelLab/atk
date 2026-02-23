/**
 * %HEADER%
 */
package be.abeel.bioinformatics.dnaproperties;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;

/**
 * A conversion map can be used to convert n-nucleotides into numerical values.
 * These conversion tables can be constructed from files in a very simple
 * format. On each line there should be a n-nucleotide with its corresponding
 * values seperated by a tab character.
 * 
 * @author Thomas Abeel
 * 
 */
final class ConversionMap extends HashMap<Integer, Double> {

    private static final long serialVersionUID = 6218177437128101213L;

    private double defaultValue = 0;

    private int length = 0;

    private ConversionMap() {
    };

    static byte[] lowercase(byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
            switch (bytes[i]) {
            case (byte) 'A':
                bytes[i] = (byte) 'a';
                break;
            case (byte) 'T':
                bytes[i] = (byte) 't';
                break;
            case (byte) 'C':
                bytes[i] = (byte) 'c';
                break;
            case (byte) 'G':
                bytes[i] = (byte) 'g';
                break;
            }
        }
        return bytes;
    }

    static int translate(byte... seq) {
        int out = 0;
        for (byte b : seq) {

            out *= 255;
            out += b;
        }
        return out;
    }

    static ConversionMap create(String file) {
        ConversionMap out = new ConversionMap();
        URL url = ConversionMap.class.getResource(file);
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String line = in.readLine();
            while (line != null) {
                if (line.length() > 0) {
                    String[] arr = line.split("\t");
                    try {
                        double value = Double.parseDouble(arr[1]);
                        out.put(translate(lowercase(arr[0].getBytes())), value);
                        out.defaultValue += value;
                        out.length = arr[0].length();
                    } catch (NumberFormatException e) {
                        throw new RuntimeException("Something is wrong with in conversion file: " + e);
                    }
                }
                line = in.readLine();
            }
            in.close();
            out.defaultValue /= Math.pow(4, out.length);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Their was an error while reading the conversion map: " + e);
        }
        return out;
    }

    @Override
    public Double get(Object key) {
        Double value = super.get((Integer) key);
        if (value != null)
            return value;
        else
            return defaultValue;
    }

    /**
     * Gives the length of n-nucleotide that can be converted with this
     * conversion map. For example, if there are values available for
     * dinucleotides, this method would return 2.
     * 
     * @return the length of n-nucleotides that can be converted
     */
    int length() {
        return length;
    }

    void normalize() {
        // System.out.println("Normalizing map...");
        double upperBound = 1;
        double lowerBound = -1;
        double max = Double.MIN_VALUE;
        double min = Double.MAX_VALUE;
        for (Double d : super.values()) {
            if (d > max)
                max = d;
            if (d < min)
                min = d;
        }

        for (Integer s : super.keySet()) {
            Double d = super.get(s);
            Double tmp = (d - min) / (max - min) * (upperBound - lowerBound) + lowerBound;
            // System.out.println("\t"+s+"\t"+d+"\t"+tmp);
            super.put(s, tmp);
        }
        defaultValue = (defaultValue - min) / (max - min) * (upperBound - lowerBound) + lowerBound;
    }
}
