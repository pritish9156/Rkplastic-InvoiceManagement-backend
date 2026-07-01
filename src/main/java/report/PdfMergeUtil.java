package report;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;

public class PdfMergeUtil {

    public static void merge(

            List<File> pdfFiles,

            String outputFile)

            throws Exception {

        Document document =
                new Document();

        PdfCopy copy =
                new PdfCopy(

                        document,

                        new FileOutputStream(
                                outputFile));

        document.open();

        for(File file : pdfFiles) {

            PdfReader reader =
                    new PdfReader(
                            file.getAbsolutePath());

            int pages =
                    reader.getNumberOfPages();

            for(int i=1;i<=pages;i++) {

                PdfImportedPage page =
                        copy.getImportedPage(
                                reader,
                                i);

                copy.addPage(page);

            }

            reader.close();

        }

        document.close();

    }

}