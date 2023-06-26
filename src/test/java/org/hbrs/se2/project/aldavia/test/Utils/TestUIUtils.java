package org.hbrs.se2.project.aldavia.test.Utils;

import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import org.hbrs.se2.project.aldavia.util.UIUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TestUIUtils {

    public static final String HALLO = "Hallo";

    @Test
    public void testSplitOnSpaces(){
        String[] split = UIUtils.splitOnSpaces("Hallo Welt");
        assertEquals(HALLO, split[0]);
        assertEquals("Welt", split[1]);
        String[] split2 = UIUtils.splitOnSpaces("Hallo,Welt,Hallo,Welt");
        assertEquals("Hallo,Welt,Hallo,Welt", split2[0]);
    }

    @Test
    public void testCheckIfTextFieldIsEmpty(){
        assertTrue(UIUtils.checkIfTextFieldIsEmpty(new TextField()));
        TextField textField = new TextField();
        textField.setValue(HALLO);
        assertFalse(UIUtils.checkIfTextFieldIsEmpty(textField));
    }

    @Test
    public void testCheckIfTextAreaIsEmpty(){
        assertTrue(UIUtils.checkIfTextAreaIsEmpty(null));
        assertTrue(UIUtils.checkIfTextAreaIsEmpty(new TextArea()));
        TextArea textField = new TextArea();
        textField.setValue(HALLO);
        assertFalse(UIUtils.checkIfTextAreaIsEmpty(textField));
    }
}
