package activities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Activity2 {

    @Test
    public void notEnoughFunds(){
        // Create an object for BankAccount class
        BankAccount acct = new BankAccount(10);

        // Assertion for exception
        Assertions.assertThrows(NotEnoughFundsException.class,()-> acct.withdraw(20),"Balance less than withdraw amount");
    }

    @Test
    public void enoughFunds(){
        // Create an object for BankAccount class
        BankAccount acct = new BankAccount(100);

        // Assertion for exception
        Assertions.assertDoesNotThrow(()-> acct.withdraw(100),"Balance sufficient to withdraw amount");
    }
}
