/**
 * %HEADER%
 */
package be.abeel.bioinformatics.dnaproperties;

import java.lang.reflect.Field;
import java.util.Vector;

/**
 * A interface that represents a property of the DNA that can be calculated from
 * the sequence. Typically these properties are stored as a simple conversation
 * tables which allow to convert each nucleotide, di-, tri- and so on
 * n-nucleotide to a numberical value. Althoug more complex models are also
 * possible
 * 
 * @author Thomas Abeel
 * 
 */
public abstract class DNAProperty {

    public static final DNAProperty ATG = new ConversionMapDNAProperty("ATG-density");

    public static final DNAProperty G = new ConversionMapDNAProperty("G-content");

    public static final DNAProperty C = new ConversionMapDNAProperty("C-content");

    public static final DNAProperty T = new ConversionMapDNAProperty("T-content");

    public static final DNAProperty A = new ConversionMapDNAProperty("A-content");

    public static final DNAProperty AT = new ConversionMapDNAProperty("AT-content");

    public static final DNAProperty GC = new ConversionMapDNAProperty("GC-content");

    public static final DNAProperty Pyrimidine = new ConversionMapDNAProperty("Pyrimidine-content");

    public static final DNAProperty Purine = new ConversionMapDNAProperty("Purine-content");

    public static final DNAProperty Bendability = new ConversionMapDNAProperty("Bendability");

    public static final DNAProperty zDNA = new ConversionMapDNAProperty("zDNA");

    public static final DNAProperty DuplexStabilityFreeEnergy = new ConversionMapDNAProperty(
            "DuplexStabilityFreeEnergy");

    public static final DNAProperty DuplexStabilityDisruptEnergy = new ConversionMapDNAProperty(
            "DuplexStabilityDisruptEnergy");

    public static final DNAProperty DNADenaturation = new ConversionMapDNAProperty("DNADenaturation");

    public static final DNAProperty PropellorTwist = new ConversionMapDNAProperty("PropellorTwist");

    public static final DNAProperty BaseStacking = new ConversionMapDNAProperty("BaseStacking");

    public static final DNAProperty ProteinDeformation = new ConversionMapDNAProperty("ProteinDeformation");

    public static final DNAProperty BendingStiffness = new ConversionMapDNAProperty("BendingStiffness");

    public static final DNAProperty ProteinDNATwist = new ConversionMapDNAProperty("ProteinDNATwist");

    public static final DNAProperty bDNATwist = new ConversionMapDNAProperty("bDNATwist");

    public static final DNAProperty APhilicity = new ConversionMapDNAProperty("APhilicity");

    public static final DNAProperty NucleosomePosition = new ConversionMapDNAProperty("NucleosomePosition");

    public static final DNAProperty DimerRadicalCleavageIntensity = new RadicalCleavageIntensity(2);

    public static final DNAProperty TrimerRadicalCleavageIntensity = new RadicalCleavageIntensity(3);

    public static final DNAProperty TetramerRadicalCleavageIntensity = new RadicalCleavageIntensity(4);

    public static final DNAProperty PentamerRadicalCleavageIntensity = new RadicalCleavageIntensity(5);

    public static final DNAProperty Twist = new ConversionMapDNAProperty("Twist");

    public static final DNAProperty Tilt = new ConversionMapDNAProperty("Tilt");

    public static final DNAProperty Roll = new ConversionMapDNAProperty("Roll");

    public static final DNAProperty Shift = new ConversionMapDNAProperty("Shift");

    public static final DNAProperty Slide = new ConversionMapDNAProperty("Slide");

    public static final DNAProperty Rise = new ConversionMapDNAProperty("Rise");

    protected DNAProperty(String name) {
        this.name = name;
    }

    private String name = null;

    /**
     * This method will return the average value of the structural feature over
     * the sequence given as parameter.
     * 
     * @param sequence
     *            the sequence for which to calculate the average.
     * @return the average structural value
     */
    public abstract double value(String seq);

    /**
     * This method will return the average value of the normalized structural
     * feature over the sequence given as parameter.
     * 
     * @param sequence
     *            the sequence for which to calculate the average.
     * @return the average structural value
     */
    public abstract double normalizedValue(String seq);

    /**
     * Return the profile for the sequence given as a parameter.
     * 
     * @param sequence
     *            the sequence for which to calculate the structural profile.
     * @return the profile of the sequence
     */
    public abstract double[] profile(String seq);

    /**
     * Return the normalized profile for the sequence given as a parameter.
     * 
     * @param sequence
     *            the sequence for which to calculate the structural profile.
     * @return the profile of the sequence
     */
    public abstract double[] normalizedProfile(String seq);

    public abstract int length();

    @Override
    public String toString() {
        return name;
    }

    public static DNAProperty[] values() {
        Field[] arr = DNAProperty.class.getFields();
        Vector<DNAProperty> props = new Vector<DNAProperty>();
        for (Field f : arr) {
            if (f.getType().equals(DNAProperty.class)) {
                // System.out.println(f.getName());
                try {
                    DNAProperty pp = (DNAProperty) f.get(null);
                    props.add(pp);
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        DNAProperty[] out = new DNAProperty[props.size()];
        props.toArray(out);
        return out;
    }

    public static DNAProperty create(String name) {
        Field[] arr = DNAProperty.class.getFields();
        for (Field f : arr) {
            if (f.getType().equals(DNAProperty.class)) {
                // System.out.println(f.getName());
                try {
                    DNAProperty pp = (DNAProperty) f.get(null);
                    if (pp.toString().equalsIgnoreCase(name) || f.getName().equalsIgnoreCase(name))
                        return pp;
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
