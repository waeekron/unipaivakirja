package unipaivakirja.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import unipaivakirja.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2021.08.03 10:35:11 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class TagNimiTest {



  // Generated by ComTest BEGIN
  /** testRekisteroi63 */
  @Test
  public void testRekisteroi63() {    // TagNimi: 63
    TagNimi tag = new TagNimi(); 
    assertEquals("From: TagNimi line: 65", 0, tag.getTagID()); 
    tag.rekisteroi(); 
    TagNimi tag2 = new TagNimi(); 
    tag2.rekisteroi(); 
    int n1 = tag.getTagID(); 
    int n2 = tag2.getTagID(); 
    assertEquals("From: TagNimi line: 72", n2 - 1, n1); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testParse148 */
  @Test
  public void testParse148() {    // TagNimi: 148
    TagNimi t = new TagNimi(); 
    t.parse("2|valveuni"); 
    assertEquals("From: TagNimi line: 151", 2, t.getTagID()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testToString166 */
  @Test
  public void testToString166() {    // TagNimi: 166
    TagNimi t = new TagNimi(); 
    t.parse("2|valveuni"); 
    assertEquals("From: TagNimi line: 169", true, t.toString().equals("2|valveuni")); 
  } // Generated by ComTest END
}