package org.example.service.report;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfXrefTable;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.VerticalAlignment;
import lombok.RequiredArgsConstructor;
import org.example.dto.ReportDto;
import org.example.dto.ReportTypeDto;
import org.example.utils.Reports;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.itextpdf.kernel.pdf.PdfName.BaseFont;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final Map<String, ReportBuilder> reportBuilders;

    public List<ReportTypeDto> getReports() {
        return Arrays.stream(Reports.values())
                .map(r -> new ReportTypeDto(r.getName(), r.getRequestValue()))
                .toList();
    }

    public ReportDto buildReport(String type,
                                 LocalDate dateFrom,
                                 LocalDate dateTo,
                                 String interval,
                                 Map<String, String> params,
                                 String username) {
        ReportBuilder reportBuilder = reportBuilders.get(type);

        return reportBuilder.buildReport(type, dateFrom, dateTo, interval, params, username);
    }

    public byte[] buildDocumentFromReport(ReportDto reportDto) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outputStream));
            Document document = new Document(pdfDocument);
            Table table = new Table(2);
            table.setMarginTop(5);
            fillTableColumnNames(table, reportDto);

            reportDto.getResult().forEach((key, value) -> {
                table.addCell(key);
                table.addCell(value);
            });

            document.add(table);
            document.close();

            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void fillTableColumnNames(Table table, ReportDto reportDto) throws Exception{
        if (reportDto.getName().equals("mileage")) {
            String fontPath = "/System/Library/Fonts/Supplemental/Arial.ttf";  // путь на macOS
            PdfFont baseFont = PdfFontFactory.createFont(fontPath, PdfEncodings.IDENTITY_H);
            Paragraph para = new Paragraph("Интевал").setFont(baseFont);
            para.setFixedLeading(0);
            para.setMultipliedLeading(1);

            Paragraph para2 = new Paragraph("Дистанция").setFont(baseFont);
            para2.setFixedLeading(0);
            para2.setMultipliedLeading(1);

            Cell cell = new Cell();
            cell.setMinHeight(50);
            cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
            cell.add(para);
            table.addCell(cell);

            Cell cell2 = new Cell();
            cell2.setMinHeight(50);
            cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
            cell2.add(para2);
            table.addCell(cell2);
        }
    }
}
