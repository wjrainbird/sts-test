package sts.test.validator;

public class MyValidator implements Validator {
    private final byte STX = 0x02;
    private final byte ETX = 0x03;
    private final byte DLE = 0x10;

    /**
     * Indicate if the given message is valid.
     *
     * @param message The message to check
     * @return {@code true} if the message starts with STX, ends with ETX and the
     *         correct LRC, and has correctly-escaped data; {@code false}
     *         otherwise.
     */
    @Override
    public boolean isValid(byte[] message) {
        // Check if STX and ETX are present in the correct position
        if (message[0] != STX || message[message.length - 2] != ETX) {
            System.err.println("STX or ETX not present");
            return false;
        }

        byte lrc = 0x00;

        // Iterate through message body
        for (int i = 1; i < message.length - 2; i++) {
            // Check for unescaped STX or ETX
            if (message[i] == STX || message[i] == ETX) {
                System.err.println("Unescaped STX or ETX");
                return false;
            }

            // Proceed to next character if DLE found
            if (message[i] == DLE) {
                i++;
                // Check that the character was one that should have been escaped
                if (i >= message.length || (message[i] != STX && message[i] != ETX && message[i] != DLE)) {
                    System.err.println("DLE used incorrectly");
                    return false;
                }
            }

            // XOR byte into LRC
            lrc ^= message[i];
        }

        // XOR ETX into LRC (not part of message body)
        lrc ^= ETX;

        // Check if calculated LRC is equal to the one in the message
        return message[message.length - 1] == lrc;
    }
}
