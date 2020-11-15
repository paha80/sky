package kohdeluettelo;
// Generated by ComTest BEGIN

import java.io.File;
import java.util.*;
import static org.junit.Assert.*;
import org.junit.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2015.07.29 17:19:44 // Generated by ComTest
 *
 */
public class KohteetTest {



  // Generated by ComTest BEGIN
  /** 
   * testLisaa44 
   * @throws SailoException when error
   */
  @Test
  public void testLisaa44() throws SailoException {    // Kohteet: 44
    Kohteet kohteet = new Kohteet(); 
    Kohde venus = new Kohde(); 
    assertEquals("From: Kohteet line: 48", 0, kohteet.getLkm()); 
    kohteet.lisaa(venus); 
    assertEquals("From: Kohteet line: 50", 1, kohteet.getLkm()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testPoista65 
   * @throws SailoException when error
   */
  @Test
  public void testPoista65() throws SailoException {    // Kohteet: 65
    Kohteet Kohteet = new Kohteet(1); 
    Kohde mars = new Kohde(); 
    mars.rekisteroi(); 
    int id = mars.getKohdeNro(); 
    assertEquals("From: Kohteet line: 71", 0, Kohteet.poista(id)); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testKorvaaTaiLisaa90 
   * @throws SailoException when error
   * @throws CloneNotSupportedException when error
   */
  @Test
  public void testKorvaaTaiLisaa90() throws SailoException,CloneNotSupportedException {    // Kohteet: 90
    Kohteet Kohteet = new Kohteet(); 
    Kohde mars1 = new Kohde(); 
    Kohde mars2 = new Kohde(); 
    mars1.rekisteroi(); mars2.rekisteroi(); 
    assertEquals("From: Kohteet line: 97", 0, Kohteet.getLkm()); 
    Kohteet.korvaaTaiLisaa(mars1); assertEquals("From: Kohteet line: 98", 1, Kohteet.getLkm()); 
    Kohteet.korvaaTaiLisaa(mars2); assertEquals("From: Kohteet line: 99", 2, Kohteet.getLkm()); 
    Kohde mars3 = mars1.clone(); 
    mars3.aseta(3,"kkk"); 
    Iterator<Kohde> it = Kohteet.iterator(); 
    assertEquals("From: Kohteet line: 103", true, it.next() == mars1); 
    Kohteet.korvaaTaiLisaa(mars3); assertEquals("From: Kohteet line: 104", 2, Kohteet.getLkm()); 
    it = Kohteet.iterator(); 
    Kohde j0 = it.next(); 
    assertEquals("From: Kohteet line: 107", mars3, j0); 
    assertEquals("From: Kohteet line: 108", true, j0 == mars3); 
    assertEquals("From: Kohteet line: 109", false, j0 == mars1); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testLueTiedostosta144 
   * @throws SailoException when error
   */
  @Test
  public void testLueTiedostosta144() throws SailoException {    // Kohteet: 144
    Kohteet Kohteet = new Kohteet(); 
    Kohde mars1 = new Kohde(); 
    Kohde mars2 = new Kohde(); 
    mars1.vastaaVenus(); 
    mars2.vastaaVenus(); 
    String hakemisto = "testikohteet"; 
    String tiedNimi = hakemisto+"/nimet"; 
    File ftied = new File(tiedNimi+".dat"); 
    File dir = new File(hakemisto); 
    dir.mkdir(); 
    ftied.delete(); 
    try {
    Kohteet.lueTiedostosta(tiedNimi); 
    fail("Kohteet: 159 Did not throw SailoException");
    } catch(SailoException _e_){ _e_.getMessage(); }
    Kohteet.lisaa(mars1); 
    Kohteet.lisaa(mars2); 
    Kohteet.talleta(); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testKohteetIterator276 
   * @throws SailoException when error
   */
  @Test
  public void testKohteetIterator276() throws SailoException {    // Kohteet: 276
    Kohteet Kohteet = new Kohteet(); 
    Kohde mars1 = new Kohde(); 
    Kohde mars2 = new Kohde(); 
    mars1.rekisteroi(); 
    mars2.rekisteroi(); 
    Kohteet.lisaa(mars1); 
    Kohteet.lisaa(mars2); 
    Kohteet.lisaa(mars1); 
    StringBuffer ids = new StringBuffer(30); 
    for (Kohde kohde:Kohteet) // Kokeillaan for-silmukan toimintaa
    ids.append(" "+kohde.getKohdeNro()); 
    String tulos = " " + mars1.getKohdeNro() + " " + mars2.getKohdeNro() + " " + mars1.getKohdeNro(); 
    assertEquals("From: Kohteet line: 297", tulos, ids.toString()); 
    ids = new StringBuffer(30); 
    for (Iterator<Kohde>  i=Kohteet.iterator(); i.hasNext(); ) {
    Kohde kohde = i.next(); 
    ids.append(" "+kohde.getKohdeNro()); 
    }
    assertEquals("From: Kohteet line: 305", tulos, ids.toString()); 
    Iterator<Kohde>  i=Kohteet.iterator(); 
    assertEquals("From: Kohteet line: 308", true, i.next() == mars1); 
    assertEquals("From: Kohteet line: 309", true, i.next() == mars2); 
    assertEquals("From: Kohteet line: 310", true, i.next() == mars1); 
    try {
    i.next(); 
    fail("Kohteet: 312 Did not throw NoSuchElementException");
    } catch(NoSuchElementException _e_){ _e_.getMessage(); }
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testEtsi372 
   * @throws SailoException when error
   */
  @Test
  public void testEtsi372() throws SailoException {    // Kohteet: 372
    Kohteet Kohteet = new Kohteet(); 
    Kohde kohde1 = new Kohde(); kohde1.parse("1|Merkurius|ei tiedossa|esihistoriallinen|57 909 227 km|87 d 23,3 h|47,87 km/s|0|-|4 879,4 km|74,797x10^6 km2|330,104x10^21 kg (0,055 Maan massaa)|5,427 g/cm3|58 d 15,6 h|alin: 100 K (-173 C), keski: 440 K (167 C), ylin: 700 K (427 C)|Aurinkokunnan pienin planeetta."); 
    Kohde kohde2 = new Kohde(); kohde2.parse("2|Venus|esihistoriallinen|ei tiedossa|108 208 926 km|224,70096 d|35,020|0|-|12 103,7 km|4,60x10^8 km2|4,86885x10^24 kg (0,815 Maan massaa)|5,204 g/cm3|243,0185 d|alin: 228 K (-45 C), keski: 735 K (462 C), ylin: 773 K (500 C)|Venus on kooltaan lähimpänä omaa planeettaamme."); 
    Kohteet.lisaa(kohde1); Kohteet.lisaa(kohde2); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testAnnaId400 
   * @throws SailoException when error
   */
  @Test
  public void testAnnaId400() throws SailoException {    // Kohteet: 400
    Kohteet Kohteet = new Kohteet(5); 
    Kohde mars1 = new Kohde(); 
    Kohde mars2 = new Kohde(); 
    Kohde mars3 = new Kohde(); 
    mars1.rekisteroi(); mars2.rekisteroi(); mars3.rekisteroi(); 
    int id1 = mars1.getKohdeNro(); 
    Kohteet.lisaa(mars1); Kohteet.lisaa(mars2); Kohteet.lisaa(mars3); 
    assertEquals("From: Kohteet line: 409", true, Kohteet.annaId(id1  ) == mars1); 
    assertEquals("From: Kohteet line: 410", true, Kohteet.annaId(id1+1) == mars2); 
    assertEquals("From: Kohteet line: 411", true, Kohteet.annaId(id1+2) == mars3); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testEtsiId426 
   * @throws SailoException when error
   */
  @Test
  public void testEtsiId426() throws SailoException {    // Kohteet: 426
    Kohteet Kohteet = new Kohteet(5); 
    Kohde mars1 = new Kohde(); 
    Kohde mars2 = new Kohde(); 
    Kohde mars3 = new Kohde(); 
    mars1.rekisteroi(); mars2.rekisteroi(); mars3.rekisteroi(); 
    int id1 = mars1.getKohdeNro(); 
    Kohteet.lisaa(mars1); Kohteet.lisaa(mars2); Kohteet.lisaa(mars3); 
    assertEquals("From: Kohteet line: 435", 1, Kohteet.etsiId(id1+1)); 
    assertEquals("From: Kohteet line: 436", 2, Kohteet.etsiId(id1+2)); 
  } // Generated by ComTest END
}