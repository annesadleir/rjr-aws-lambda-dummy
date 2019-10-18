package uk.co.littlestickyleaves;

import com.fasterxml.jackson.jr.ob.JSON;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

import static org.junit.Assert.*;

public class StringRepeaterTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private final JSON json = JSON.std;

    private StringRepeater stringRepeater;

    @Before
    public void setUp() throws Exception {
        stringRepeater = new StringRepeater();
    }

    @Test
    public void emptyInputString() {
        String input = "";
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Unable to deserialize StringRepeatInstruction from input: ''");

        // act
        stringRepeater.handleRaw(input);
    }

    @Test
    public void malformedInputString() throws IOException {
        // arrange
        String input = "thisIsNotJson";
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Unable to deserialize StringRepeatInstruction from input: 'thisIsNotJson'");

        // act
        stringRepeater.handleRaw(input);
    }

    @Test
    public void fineInputString() throws IOException {
        // arrange
        String str = "beep";
        int times = 8;
        String input = instructionJson(str, times);

        // act
        String result = stringRepeater.handleRaw(input);

        // assert
        StringRepetitionResult bean = json.beanFrom(StringRepetitionResult.class, result);
        assertEquals(str, bean.getInput());
        assertEquals(times, bean.getRepeat());
        assertEquals("beepbeepbeepbeepbeepbeepbeepbeep", bean.getResult());
    }

    @Test
    public void repeatEmpty() throws IOException {
        // arrange
        String str = "";
        int times = 8;
        String input = instructionJson(str, times);

        // act
        String result = stringRepeater.handleRaw(input);

        // assert
        StringRepetitionResult bean = json.beanFrom(StringRepetitionResult.class, result);
        assertEquals(str, bean.getInput());
        assertEquals(times, bean.getRepeat());
        assertEquals("", bean.getResult());
    }

    @Test
    public void repeatMinusTimes() throws IOException {
        // arrange
        String str = "tapir";
        int times = -3;
        String input = instructionJson(str, times);

        // act
        String result = stringRepeater.handleRaw(input);

        // assert
        StringRepetitionResult bean = json.beanFrom(StringRepetitionResult.class, result);
        assertEquals(str, bean.getInput());
        assertEquals(times, bean.getRepeat());
        assertEquals("", bean.getResult());
    }

    @Test
    public void repeatZeroTimes() throws IOException {
        // arrange
        String str = "kestrel";
        int times = 0;
        String input = instructionJson(str, times);

        // act
        String result = stringRepeater.handleRaw(input);

        // assert
        StringRepetitionResult bean = json.beanFrom(StringRepetitionResult.class, result);
        assertEquals(str, bean.getInput());
        assertEquals(times, bean.getRepeat());
        assertEquals("", bean.getResult());
    }

    private String instructionJson(String str, int times) throws IOException {
        StringRepetitionInstruction instruction = new StringRepetitionInstruction();
        instruction.setInput(str);
        instruction.setRepeat(times);
        return json.asString(instruction);
    }
}