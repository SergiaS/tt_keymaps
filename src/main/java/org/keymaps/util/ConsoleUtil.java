package org.keymaps.util;

public class ConsoleUtil {

    /**
     * Simple animation method with 3 dots "...", that will appears one after another.
     *
     * @param operation     Part of sentence, it will shows like "YOUR_OPERATION" + " IN PROCESS ".
     */
    public static void showAnimationBar(String operation) {
        StringBuilder sb = new StringBuilder(operation + " IN PROCESS ");
        int length = sb.length();
        int count = 0;
        while (count != 7) {
            sb.append(".");
            System.out.print(sb+"\r");

            if (sb.length() > length + 3) {
                sb.setLength(length);
                System.out.print(sb+"\r");
            }

            try {
                Thread.sleep(220);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            count++;
        }
        System.out.println("\rSUCCESS");
    }

}
