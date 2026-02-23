/**
 * %HEADER%
 */
package be.abeel.bioinformatics.dnaproperties;

public class ConversionMapDNAProperty extends DNAProperty {

    /**
     * Creates a new DNAProperty object with the given name from the conversion
     * values that are found in the supplied file.
     * 
     * The file should have on each line a n-nucleotide an its value, separated
     * by a tab-character. All possible n-nucleotide should be included in the
     * file.
     * 
     * @param name
     * @param conversionTable
     */
    protected ConversionMapDNAProperty(String name) {
        super(name);
        String file = name.toLowerCase() + ".tsv";
        this.map = ConversionMap.create(file);
        this.normalizedMap = ConversionMap.create(file);
        this.normalizedMap.normalize();

    }

    /**
     * Returns the minimum required length to convert
     * 
     * @return minimum length
     */
    public int length() {
        return map.length();
    }

    /**
     * This method will return the average value of the structural feature over
     * the sequence given as parameter.
     * 
     * @param sequence
     *            the sequence for which to calculate the average.
     * @return the average structural value
     */
    public double value(String sequence) {
        return value(ConversionMap.lowercase(sequence.getBytes()), map);
    }

    /**
     * This method will return the average value of the normalized structural
     * feature over the sequence given as parameter.
     * 
     * @param sequence
     *            the sequence for which to calculate the average.
     * @return the average structural value
     */
    public double normalizedValue(String sequence) {
        return value(ConversionMap.lowercase(sequence.getBytes()), normalizedMap);
    }

    /**
     * This method will return the average value of the structural feature over
     * the sequence given as parameter.
     * 
     * @param sequence
     *            the sequence for which to calculate the average.
     * @return the average structural value
     */
    private double value(byte[] seq, ConversionMap localMap) {
        int length = localMap.length();
        int numberOfValues = seq.length - length + 1;
        if (seq.length < length) {
            System.err.println("Offending sequence: \"" + new String(seq) + "\"");
            throw new RuntimeException("The sequence is too short to be converted");
        }

        double output = 0;

        for (int i = 0; i < numberOfValues; i++) {

            if (length == 2)
                output += localMap.get(ConversionMap.translate(seq[i], seq[i + 1]));
            else if (length == 3)
                output += localMap.get(ConversionMap.translate(seq[i], seq[i + 1], seq[i + 2]));
            else {
                byte[] tmp = new byte[length];
                System.arraycopy(seq, i, tmp, 0, length);
                output += localMap.get(ConversionMap.translate(tmp));
            }

        }
        return output / numberOfValues;

    }

    /**
     * Return the profile for the sequence given as a parameter.
     * 
     * @param sequence
     *            the sequence for which to calculate the structural profile.
     * @return the profile of the sequence
     */
    public double[] profile(String sequence) {
        return profile(ConversionMap.lowercase(sequence.getBytes()), map);
    }

    /**
     * Return the normalized profile for the sequence given as a parameter.
     * 
     * @param sequence
     *            the sequence for which to calculate the structural profile.
     * @return the profile of the sequence
     */
    public double[] normalizedProfile(String sequence) {
        return profile(ConversionMap.lowercase(sequence.getBytes()), normalizedMap);
    }

    /**
     * Return the profile for the sequence given as a parameter.
     * 
     * @param sequence
     *            the sequence for which to calculate the structural profile.
     * @return the profile of the sequence
     */
    private double[] profile(byte[] seq, ConversionMap localMap) {
        int length = localMap.length();
        if (seq.length < length) {
            System.err.println("Offending sequence: \"" + new String(seq) + "\"");
            throw new RuntimeException("The sequence is too short to be converted");
        }

        double[] output = new double[seq.length - length + 1];
        // byte[] tmp = new byte[length];
        for (int i = 0; i < seq.length - length + 1; i++) {
            // System.arraycopy(seq, i, tmp, 0, length);
            // output[i] = localMap.get(ConversionMap.translate(tmp));
            if (length == 2)
                output[i] = localMap.get(ConversionMap.translate(seq[i], seq[i + 1]));
            else if (length == 3)
                output[i] = localMap.get(ConversionMap.translate(seq[i], seq[i + 1], seq[i + 2]));
            else {
                byte[] tmp = new byte[length];
                System.arraycopy(seq, i, tmp, 0, length);
                output[i] = localMap.get(ConversionMap.translate(tmp));
            }

        }
        return output;

    }

    private ConversionMap map = null;

    private ConversionMap normalizedMap = null;
}
