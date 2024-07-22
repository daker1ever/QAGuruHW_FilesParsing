import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileParsingTests {
    private ClassLoader cl = FileParsingTests.class.getClassLoader();

    @Test
    void zipFileParsingTest() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(cl.getResourceAsStream("exampleZIP.zip"))) {
            ZipEntry ze;
            while ((ze = zis.getNextEntry()) != null) {
                String fileName = ze.getName();
                String fileResolution = fileName.substring(fileName.length() - 3);
                switch (fileResolution) {
                    case "pdf" -> {
                        PDF pdf = new PDF(zis);
                        Assertions.assertEquals(
                                "Сведения о трудовой деятельности, предоставляемые из информационных ресурсов Фонда пенсионного и социального страхования Российской Федерации",
                                pdf.title);
                    }
                    case "csv" -> {
                        CSVReader csvReader = new CSVReader(new InputStreamReader(zis));
                        List<String[]> strings = csvReader.readAll();
                        Assertions.assertArrayEquals(new String[]{"Name","Job Title","Address","State","City"}, strings.get(0));
                    }
                    case "xls" -> {
                        XLS xls = new XLS(zis);
                        Assertions.assertEquals(1.478, xls.excel.getSheetAt(0).getRow(46).getCell(7).getNumericCellValue() );
                    }

                }
            }
        }
    }
}
