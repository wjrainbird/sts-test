/* Copyright (c) 2015-2018 Smart Technology Solutions Limited. All Rights
 * Reserved.
 *
 * This software is the confidential and proprietary information of Smart
 * Technology Solutions Limited. ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into with Smart
 * Technology Solutions Limited.
 *
 * SMART TECHNOLOGY SOLUTIONS LIMITED MAKES NO REPRESENTATIONS OR WARRANTIES
 * ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. SMART TECHNOLOGY SOLUTIONS LIMITED
 * SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF
 * USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES. */

package sts.test.validator;

public class MyRun {

  public static void main(String[] args) {

    Validator validator = new MyValidator();

    // Should pass
    byte[] message =
        { 0x02, 0x10, 0x02, 0x0A, 0x10, 0x10, 0x07, 0x08, 0x03, 0x14 };
    System.out.println("Message is valid: " + validator.isValid(message));

    // Should fail, missing STX
    byte[] message2 =
            { 0x10, 0x02, 0x0A, 0x10, 0x10, 0x07, 0x08, 0x03, 0x14 };
    System.out.println("Message is valid: " + validator.isValid(message2));

    // Should fail, missing LRC
    byte[] message3 =
            { 0x02, 0x10, 0x02, 0x0A, 0x10, 0x10, 0x07, 0x08, 0x03 };
    System.out.println("Message is valid: " + validator.isValid(message3));

    // Should fail, missing ETX
    byte[] message4 =
            { 0x02, 0x10, 0x02, 0x0A, 0x10, 0x10, 0x07, 0x08, 0x14 };
    System.out.println("Message is valid: " + validator.isValid(message4));

    // Should fail, unescaped STX
    byte[] message5 =
            { 0x02, 0x02, 0x0A, 0x10, 0x10, 0x07, 0x08, 0x03, 0x14 };
    System.out.println("Message is valid: " + validator.isValid(message5));

    // Should fail, wrong LRC
    byte[] message6 =
            { 0x02, 0x10, 0x02, 0x0A, 0x10, 0x10, 0x07, 0x08, 0x03, 0x15 };
    System.out.println("Message is valid: " + validator.isValid(message6));
  }
}
