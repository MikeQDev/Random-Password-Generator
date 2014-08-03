import java.util.ArrayList;
import java.util.Random;

public class PWGen {

	private final char[] LOWERCASEALPHA = { 'a', 'b', 'c', 'd', 'e', 'f', 'g',
			'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
			'u', 'v', 'w', 'x', 'y', 'z' };
	private final char[] UPPERCASEALPHA = { 'A', 'B', 'C', 'D', 'E', 'F', 'G',
			'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
			'U', 'V', 'W', 'X', 'Y', 'Z' };
	private final char[] NUMBERS = { '0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9' };
	private final char[] SPECIAL_CHARS = { '!', '@', '#', '$', '%', '^', '&',
			'*', '(', ')', '-', '=', '+', '_', '~', '/', '\\', '[', ']', '{',
			'}', ':', ';', '>', '<', ',', '.' };

	private int pwLength;
	private ArrayList<char[]> passCriteria = new ArrayList<>();
	private Random rand = new Random();
	private String password;

	/**
	 * Specify password requirements
	 * 
	 * @param length
	 *            Length of the password
	 * @param uppercase
	 *            Should password contain uppercase characters?
	 * @param lowercase
	 *            Should password contain lowercase characters?
	 * @param numbers
	 *            Should password contain numeric characters?
	 * @param specialChars
	 *            Should password contain special characters?
	 * @throws NotEnoughInfoProvided
	 *             If the user did not provide enough information
	 */
	public void setFields(int length, boolean uppercase, boolean lowercase,
			boolean numbers, boolean specialChars) throws NotEnoughInfoProvided {
		// Make sure requested password is valid before doing anything
		if (length < 1
				|| (!uppercase && !lowercase && !numbers && !specialChars)) {
			throw new NotEnoughInfoProvided();
		}

		pwLength = length;
		passCriteria.clear();

		if (uppercase)
			passCriteria.add(UPPERCASEALPHA);
		if (lowercase)
			passCriteria.add(LOWERCASEALPHA);
		if (numbers)
			passCriteria.add(NUMBERS);
		if (specialChars)
			passCriteria.add(SPECIAL_CHARS);
	}

	/**
	 * Builds the random password
	 */
	public void generatePassword() {
		int criteriaLen = passCriteria.size();
		int curLength = 0;
		int randSetPicker = 0;
		int randCharPicker = 0;

		StringBuilder currentPassword = new StringBuilder();

		if (passCriteria.size() > 1)
			do {
				randSetPicker = rand.nextInt(criteriaLen);

				char[] curArray = passCriteria.get(randSetPicker);

				randCharPicker = rand.nextInt(curArray.length);

				currentPassword.append(curArray[randCharPicker]);

				curLength++;
			} while (curLength < pwLength);
		else if (passCriteria.size() == 1) {
			char[] curArray = passCriteria.get(0);
			do {
				randCharPicker = rand.nextInt(curArray.length);

				currentPassword.append(curArray[randCharPicker]);

				curLength++;
			} while (curLength < pwLength);

		}
		password = currentPassword.toString();
	}

	/**
	 * Returns the randomly generated password
	 * 
	 * @return
	 */
	public String getPassword() {
		return password;
	}
}

/**
 * Exception that is thrown when user does not provide sufficient criteria
 * 
 * @author Mike
 *
 */
class NotEnoughInfoProvided extends Exception {
	@Override
	public String getMessage() {
		return "Not enough criterias provided";
	}
}
