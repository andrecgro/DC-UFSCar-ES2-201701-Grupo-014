package net.sf.jabref.logic.importer.fileformat;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import net.sf.jabref.logic.util.FileExtensions;
import net.sf.jabref.model.entry.BibEntry;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import org.junit.runners.Parameterized;


public class CsvImporterTest {

    private CsvImporter testImporter;

    @Parameterized.Parameter
    public String filename;

    public Path txtFile;
    public String csvName;


    @Before
    public void setUp() throws Exception {
        testImporter = new CsvImporter();
        txtFile = Paths.get(CsvImporterTest.class.getResource("CsvImporterTest1.csv").toURI());
        System.out.println(txtFile);
        csvName = filename + ".csv";
    }

    @Test
    public final void testIsRecognizedFormat() throws Exception {
        Assert.assertTrue(testImporter.isRecognizedFormat(txtFile, StandardCharsets.UTF_8));
    }

    //Teste para importação de arquivos CSV com todos os campos obrigatórios preenchidos
    @Test
    public void testImportCSV() throws Exception {
        try (InputStream is = CsvImporter.class.getResourceAsStream(csvName)) {

            List<BibEntry> entries = testImporter.importDatabase(txtFile, StandardCharsets.UTF_8).getDatabase().getEntries();  

          
            BibEntry testEntry = entries.get(0);
            Assert.assertEquals(Optional.of("1936"), testEntry.getField("year"));
            Assert.assertEquals(Optional.of("John Maynard Keynes"), testEntry.getField("author"));
            Assert.assertEquals(Optional.of("The General Theory of Employment, Interest and Money"), testEntry.getField("title"));

            testEntry = entries.get(1);
            Assert.assertEquals(Optional.of("2003"), testEntry.getField("year"));
            Assert.assertEquals(Optional.of("Boldrin & Levine"), testEntry.getField("author"));
            Assert.assertEquals(Optional.of("Case Against Intellectual Monopoly"), testEntry.getField("title"));

            testEntry = entries.get(2);
            Assert.assertEquals(Optional.of("2004"), testEntry.getField("year"));
            Assert.assertEquals(Optional.of("ROBERT HUNT AND JAMES BESSEN"), testEntry.getField("author"));
            Assert.assertEquals(Optional.of("The Software Patent Experiment"), testEntry.getField("title"));
        
        }
    }


    @Test
    public void testGetName() {
        assertEquals("CSV file", testImporter.getName());
    }

    @Test
    public void testGetExtensions() {
        assertEquals(FileExtensions.CSV, testImporter.getExtensions());
    }
}