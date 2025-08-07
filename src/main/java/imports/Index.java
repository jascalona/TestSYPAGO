package imports;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author DELL
 */
public class Index {

    public static List<Cellxlsx> readExcelData(String filePath, int Sheet ) {
        List<Cellxlsx> datos = new ArrayList<>();
        try (FileInputStream excelFile = new FileInputStream(filePath); Workbook workbook = new XSSFWorkbook(excelFile)) {

            Sheet sheet = workbook.getSheetAt(Sheet); // Obtener la primera hoja (en caso de ser seleccionar otra hoja modifique el valor de la misma)
            Iterator<Row> rowIterator = sheet.iterator();

            // Saltar la fila de encabezado
            if (rowIterator.hasNext()) {
                rowIterator.next();
            }

            while (rowIterator.hasNext()) {
                Row currentRow = rowIterator.next();
                // mapeo basado en el índice de la columna para mayor robustez
                // Orden fijo de columnas: ID, Nombre, CodBanco, Tlf, Cuenta
                String id = getCellValue(currentRow.getCell(0));
                String nombre = getCellValue(currentRow.getCell(1));
                String codBanco = getCellValue(currentRow.getCell(2)); 
                String tlf = getCellValue(currentRow.getCell(3));
                String cuenta = getCellValue(currentRow.getCell(4));
                String CMCN = getCellValue(currentRow.getCell(5));
                String email = getCellValue(currentRow.getCell(6));
                String celeOTP = getCellValue(currentRow.getCell(7));
                datos.add(new Cellxlsx(id, nombre, codBanco, tlf, cuenta, CMCN, email, celeOTP));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return datos;
    }

    private static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }

        // Declaracion de variable para almacenar el valor de la celdas
        String cellValue = "";

        switch (cell.getCellType()) {
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            case NUMERIC:
                // Se verifica si el valor numérico es un entero para evitar decimales innecesarios
                if (DateUtil.isCellDateFormatted(cell)) {
                    cellValue = cell.getDateCellValue().toString(); // Manejo de fechas si es necesario
                } else {
                    cellValue = String.valueOf((long) cell.getNumericCellValue());
                }
                break;
            case BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case FORMULA:
                // Para obtener el resultado de la fórmula, necesitas un FormulaEvaluator
                // Es un poco más complejo, por ahora devolverá la fórmula como String
                cellValue = cell.getCellFormula();
                break;
            case BLANK:
                cellValue = "";
                break;
            default:
                cellValue = "";
                break;
        }
        return cellValue;
    }

    public static void main(String[] args) {
        String excelFilePath = "C:\\Import.xlsx"; //Ruta absoluta
        List<Cellxlsx> datos_row = readExcelData(excelFilePath, 2);

        if (datos_row.isEmpty()) {
            System.out.println("No se encontraron datos en el archivo Excel o el archivo está vacío.");
        } else {
            for (Cellxlsx p : datos_row) {
                System.out.println(p); // Esto llamará al método toString() de Cellxlsx
            }
        }
    }
}
