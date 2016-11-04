import java.util.ArrayList;
import java.util.List;

//Class HugeInteger contains methods to represent an integer that doesn't fit
//within the size constraints of the int data type and manipulates that value
//in various ways

public class HugeInteger {

    private static final int NUM_DIGITS = 40;
    private int digits[] = new int[NUM_DIGITS];
    private boolean positive;
    String zero = "0000000000000000000000000000000000000000"; //Check for if String val is 0

    //Takes in the string representation of the desired number and converts
    //and stores each character's int value as an int inside of an array. It also contains
    //code to represent if the number was positive or negative using a boolean.
    public HugeInteger(String num) {
        int len = num.length();
        int start = 0;
        if (num.charAt(0) == '-') {
            start = 1;
            positive = false;
        } else {
            positive = true;
        }
        for (int i = len - 1, j = 0; i >= start; i--, j++) {
            digits[NUM_DIGITS - 1 - j] = num.charAt(i) - (int) '0';
        }

    }

    // For calculation

    //Adds an input HugeInteger to the value of the HugeInteger
    //that this method is being called from
    public void add(HugeInteger hi) {

        int temp;
        if ((positive == true && hi.positive == true) || (positive == false && hi.positive == false)) {
            for (int i = digits.length - 1; i > 0; i--) {
                temp = digits[i] + hi.digits[i];
                if (temp < 10) {
                    digits[i] = temp;
                } else {
                    int onesPlace = temp - 10;
                    digits[i] = onesPlace;
                    digits[i - 1] += 1;
                }
            }
        }

        if (positive == true && hi.positive == false) {
            hi.positive = true;
            if (isEqualTo(hi)) {
                for (int i = 0; i < digits.length; i++) {
                    digits[i] = 0;
                }
                hi.positive = false;
            } else if (isGreaterThan(hi)) {

                for (int i = 0; i < digits.length; i++) {
                    digits[i] = digits[i] - hi.digits[i];
                }

                for (int i = digits.length - 1; i > 0; i--) {

                    if (digits[i] < 0) {
                        digits[i - 1] = digits[i - 1] - 1;
                        digits[i] = digits[i] + 10;
                    }
                }
                hi.positive = false;
            } else {
                int[] hiDigitReplace = hi.digits.clone();

                for (int i = 0; i < hiDigitReplace.length; i++) {
                    hiDigitReplace[i] = hiDigitReplace[i] - digits[i];
                }
                for (int i = hiDigitReplace.length - 1; i > 0; i--) {

                    if (hiDigitReplace[i] < 0) {
                        hiDigitReplace[i - 1] = hiDigitReplace[i - 1] - 1;
                        hiDigitReplace[i] = hiDigitReplace[i] + 10;
                    }
                }
                digits = hiDigitReplace.clone();
                positive = false;
                hi.positive = false;
            }
        }

        if (positive == false && hi.positive == true) {

            positive = true;

            if (isEqualTo(hi)) {
                for (int i = 0; i < digits.length; i++) {
                    digits[i] = 0;
                }

            } else if (isGreaterThan(hi)) {

                for (int i = 0; i < digits.length; i++) {
                    digits[i] = digits[i] - hi.digits[i];
                }

                for (int i = digits.length - 1; i > 0; i--) {

                    if (digits[i] < 0) {
                        digits[i - 1] = digits[i - 1] - 1;
                        digits[i] = digits[i] + 10;
                    }
                }

                positive = false;
                //leave this one as negative
            } else {
                int[] hiDigitReplace = hi.digits.clone();

                for (int i = 0; i < hiDigitReplace.length; i++) {
                    hiDigitReplace[i] = hiDigitReplace[i] - digits[i];
                }
                for (int i = hiDigitReplace.length - 1; i > 0; i--) {

                    if (hiDigitReplace[i] < 0) {
                        hiDigitReplace[i - 1] = hiDigitReplace[i - 1] - 1;
                        hiDigitReplace[i] = hiDigitReplace[i] + 10;
                    }
                }
                digits = hiDigitReplace.clone();
                positive = true;
            }

        }
    }

    //Subtracts an input HugeInteger to the value of the HugeInteger
    //that this method is being called from
    public void subtract(HugeInteger hi) {

        if (positive == true && hi.positive == true) {
            hi.positive = false;
            add(hi);
            hi.positive = true;
        } else if (positive == false && hi.positive == false) {
            hi.positive = true;
            add(hi);
            hi.positive = false;
        } else if (positive == true && hi.positive == false) {
            hi.positive = true;
            add(hi);
            hi.positive = false;
        } else {
            hi.positive = false;
            add(hi);
            hi.positive = true;
        }
    }

    //Multiplies an input HugeInteger to the value of the HugeInteger
    //that this method is being called from
    public void multiply(HugeInteger hi) {
        int[][] fin = new int[NUM_DIGITS][NUM_DIGITS];
        for (int i = 0; i < NUM_DIGITS; i++) {
            fin[i][i] = 0;
        }
        String string;
        int temp;
        int temp2;


        for (int i = hi.digits.length - 1, x = 0; i > 0 && x < 40; i--, x++) {
            temp2 = 0;
            for (int j = digits.length - 1; j > 0; j--) {

                temp = (hi.digits[i] * digits[j]) + temp2;
                if (j < x) {
                    break;
                }
                if (temp < 10) {
                    fin[x][j - x] = temp;
                    temp2 = 0;
                } else {
                    string = String.valueOf(temp);
                    fin[x][j - x] = Integer.valueOf(string.substring(1, 2));
                    temp2 = Integer.valueOf(string.substring(0, 1));
                }
            }
        }

        int temp3;
        int[] holder = new int[NUM_DIGITS];
        for (int i = 0; i < NUM_DIGITS; i++) {
            holder[i] = 0;
        }

        for (int i = 0; i < NUM_DIGITS; i++) {
            for (int j = NUM_DIGITS - 1; j > 0; j--) {
                temp3 = holder[j] + fin[i][j];
                if (temp3 < 10) {
                    holder[j] = temp3;
                } else {
                    int onesPlace = temp3 - 10;
                    holder[j] = onesPlace;
                    holder[j - 1] += 1;
                }
            }
        }

        digits = holder;

        if ((positive == true && hi.positive == true) || (positive == false && hi.positive == false)) {
            positive = true;
        } else {
            positive = false;
        }
    }

    //Changes the boolean flag of the HugeInteger object this method is being called from and changes
    //it to its opposite value
    public void negate() {

        if (positive = false) {
            positive = true;
        }

        if (positive = true) {
            positive = false;
        }
    }

    //Returns true if the HugeInteger object is equal to zero and false otherwise
    public boolean isZero() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < digits.length; i++) {
            builder.append(digits[i]);
        }

        if (builder.toString().equals(zero)) {
            return true;
        } else {
            return false;
        }

    }

    //This object takes the array representation of HugeInteger (digits) and converts into a string
    //It also appends a negative sign to the beginning index of the string if the boolean flag positive
    //is false
    public String toString() {

        List<String> list = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        StringBuilder builder2 = new StringBuilder();

        for (int i = 0; i < digits.length; i++) {
            builder2.append(digits[i]);
        }

        if (builder2.toString().equals(zero)) {
            return "0";
        }

        for (int x : digits) {
            list.add(String.valueOf(x));
        }

        for (int i = 0; i < list.size(); i++) {

            if (list.get(i).equals("0")) {
                list.set(i, "");
            } else {
                break;
            }
        }

        for (int i = 0; i < list.size(); i++) {
            builder.append(list.get(i));
        }

        if (!positive) {
            String negativeNumber = "-" + builder.toString();
            return negativeNumber;
        } else {
            return builder.toString();
        }
    }

    // For Comparison

   //The following methods take a HugeInteger object as input and compares it
    //to the HugeInteger object that the method is being called from

    //returns true if the HugeInteger objects are of equal value and false if not
    public boolean isEqualTo(HugeInteger hi) {
        for (int i = 0; i < NUM_DIGITS; i++) {
            if (hi.digits[i] != digits[i]) {
                return false;
            }
        }
        return true;
    }

    //Returns true if the HugeInteger object that the method is being called from is
    //greater than the HugeInteger object that is being used as input and false otherwise
    public boolean isGreaterThan(HugeInteger hi) {

        if (positive == true && hi.positive == false) {
            return true;
        }

        if (positive == true && hi.positive == true) {

            for (int i = 0; i < digits.length; i++) {
                if (digits[i] > hi.digits[i]) {
                    return true;
                }

                if (digits[i] < hi.digits[i]) {
                    return false;
                }
            }

        }

        if (positive == false && hi.positive == false) {

            for (int i = 0; i < digits.length; i++) {
                if (digits[i] < hi.digits[i]) {
                    return true;
                }

                if (digits[i] > hi.digits[i]) {
                    return false;
                }
            }

        }

        return false;
    }

    //Returns true if the HugeInteger object that the method is being called from is
    //is not equal to the HugeInteger object that is being used as input and false otherwise
    public boolean isNotEqualTo(HugeInteger hi) {

        if (isEqualTo(hi)) {
            return false;
        } else {
            return true;
        }
    }

    //Returns true if the HugeInteger object that the method is being called from is
    //less than the HugeInteger object that is being used as input and false otherwise
    public boolean isLessThan(HugeInteger hi) {
        if (toString().equals(hi.toString())) {
            return false;
        } else {
            boolean bool = isGreaterThan(hi);
            return !bool;
        }
    }

    //Returns true if the HugeInteger object that the method is being called from is
    //greater than or equal to the HugeInteger object that is being used as input and false otherwise
    public boolean isGreaterThanOrEqualTo(HugeInteger hi) {

        if (isGreaterThan(hi) == true || isEqualTo(hi) == true) {

            return true;
        } else {
            return false;
        }
    }

    //Returns true if the HugeInteger object that the method is being called from is
    //less than or equal to the HugeInteger object that is being used as input and false otherwise
    public boolean isLessThanOrEqualTo(HugeInteger hi) {

        if (isLessThan(hi) == true || isEqualTo(hi) == true) {
            return true;
        } else {
            return false;
        }
    }


}
