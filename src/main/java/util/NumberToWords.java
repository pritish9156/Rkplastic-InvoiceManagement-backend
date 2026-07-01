package util;

public class NumberToWords {

    private static final String[] units = {
            "", "One", "Two", "Three",
            "Four", "Five", "Six",
            "Seven", "Eight", "Nine",
            "Ten", "Eleven", "Twelve",
            "Thirteen", "Fourteen",
            "Fifteen", "Sixteen",
            "Seventeen", "Eighteen",
            "Nineteen"
    };

    private static final String[] tens = {
            "", "", "Twenty", "Thirty",
            "Forty", "Fifty", "Sixty",
            "Seventy", "Eighty",
            "Ninety"
    };

    public static String convert(
            long number) {

        if(number == 0)
            return "Zero";

        return convertNumber(number)
                + " only";
    }

    private static String convertNumber(
            long n) {

        if(n < 20)
            return units[(int)n];

        if(n < 100)
            return tens[(int)n / 10]
                    + " "
                    + convertNumber(n % 10);

        if(n < 1000)
            return convertNumber(n / 100)
                    + " Hundred "
                    + convertNumber(n % 100);

        if(n < 100000)
            return convertNumber(n / 1000)
                    + " Thousand "
                    + convertNumber(n % 1000);

        if(n < 10000000)
            return convertNumber(n / 100000)
                    + " Lakh "
                    + convertNumber(n % 100000);

        return convertNumber(n / 10000000)
                + " Crore "
                + convertNumber(n % 10000000);
    }
}