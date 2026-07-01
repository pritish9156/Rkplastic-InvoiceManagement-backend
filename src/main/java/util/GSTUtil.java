package util;

public class GSTUtil {

    private static final String
            COMPANY_STATE_CODE = "27";

    public static boolean isInterState(
            String gstin) {

        if(gstin == null
                || gstin.length() < 2) {

            return false;
        }

        String customerStateCode =
                gstin.substring(0, 2);

        return !COMPANY_STATE_CODE
                .equals(customerStateCode);
    }
}