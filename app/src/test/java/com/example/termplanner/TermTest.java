package com.example.termplanner;

import static junit.framework.TestCase.assertTrue;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import com.example.termplanner.Entities.Term;

public class TermTest {

    public static int termId = 0;
    public static String termTitle = "Term Test";
    public static String termTitleTooLong = "TermTitleTooManyCharacters";
    public static String validStart = "03/29/2022";
    public static String validEnd = "03/30/2022";
    public static String invalidStartFormat = "";
    public static String invalidEndFormat = "1/1/1";
    public static String invalidOrderStart = "04/01/2022";
    public static String invalidOrderEnd = "03/29/2022";


    @Test
    public void validUserInput(){

        Term term = new Term(termId, termTitle, validStart, validEnd);

        assertTrue(term.validTermInput(termTitle, validStart, validEnd));
        System.out.println("Test 1 - ValidUserInput() *** Inputs are valid *** TRUE");
    }

    @Test
    public void invalidTitleInput(){

        Term term = new Term(termId, termTitleTooLong, validStart, validEnd);

        assertFalse(term.validTermInput(termTitleTooLong, validStart, validEnd));
        System.out.println("Test 2 - invalidTitleInput() *** Inputs are valid *** FALSE");
    }

    @Test
    public void invalidDateFormatInput(){

        Term term = new Term(termId, termTitle, invalidStartFormat, invalidEndFormat);

        assertFalse(term.validTermInput(termTitle, invalidStartFormat, invalidEndFormat));
        System.out.println("Test 3 - invalidDateFormatInput() *** Inputs are valid *** FALSE");
    }

    @Test
    public void invalidDateOrderInput(){

        Term term = new Term(termId, termTitle, invalidOrderStart, invalidOrderEnd);

        assertFalse(term.validTermInput(termTitle, invalidOrderStart, invalidOrderEnd));
        System.out.println("Test 4 - invalidDateOrderInput() *** Inputs are valid *** FALSE");
    }

}
