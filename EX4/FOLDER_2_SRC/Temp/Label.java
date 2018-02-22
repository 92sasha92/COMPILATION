package Temp;
import java.util.HashMap;

// import Symbol.Symbol;

/**
 * A Label represents an address in assembly language.
 */

public class Label {
	public String name;
	private static int count;
    private static HashMap<String, Label> allLabels = new HashMap<String,Label>();

	/**
	 * a printable representation of the label, for use in assembly language
	 * output.
	 */
	public String toString() {
		return name;
	}

	/**
	 * Makes a new label that prints as "name". Warning: avoid repeated calls to
	 * <tt>new Label(s)</tt> with the same name <tt>s</tt>.
	 */

    public Label(String n, boolean shouldEnumerate) {
        name = n;
        if (shouldEnumerate) {
            name += (count++);
        }
    }
    public Label(String n) {
        this(n, true);
    }

	/**
	 * Makes a new label with an arbitrary name.
	 */
	public Label() {
		this("L");
	}
	


    public static Label getLabel() {
        Label newLabel = new Label();
		allLabels.put(newLabel.name,newLabel);
		return newLabel;
	}

    public static Label getLabel(String labelName) {
        if (allLabels.get(labelName) != null) {
            return allLabels.get(labelName);
        }
        Label newLabel = new Label(labelName, true);
        allLabels.put(newLabel.name,newLabel);
        return newLabel;
    }
    public static Label getLabel(String labelName, boolean shouldEnumerate) {
        if (allLabels.get(labelName) != null) {
            return allLabels.get(labelName);
        }
        Label newLabel = new Label(labelName, shouldEnumerate);
        allLabels.put(newLabel.name,newLabel);
        return newLabel;
    }


	/**
	 * Makes a new label whose name is the same as a symbol.
	 */
	// public Label(Symbol s) {
	// 	this(s.toString());
	// }
}
