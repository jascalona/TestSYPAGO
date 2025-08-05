//SCRIPTS PARA PASAR A CONTEXTO GLOBAL LA DATA IMPORTADA CARGADA DESDE EXCEL APLICANDO EL METODO RANDOM

/***********SCRIPT RECEPTOR******************/
import imports.Cellxlsx;
import imports.Index;
import java.util.Random;

def excelFilePath = "C:\\Import.xlsx" //Ruta absoluta

/*****Lectura del excel*******/
//Definicion de el numero de hoja donde realiza extraera los datos
def ReceptorIBPs = Index.readExcelData(excelFilePath, 1);

/*********Instancia Random*************/
// Corregido: Usar nextInt(bound) para generar un número aleatorio dentro del rango.
def randomIBPs = new Random().nextInt(ReceptorIBPs.size()); //Define las filas dentro de la hoja
log.info("Indice de fila generado: ${randomIBPs}")

//Variable contextualizada global
context.ListIBPs = ReceptorIBPs;
log.info(context.ListIBPs); //.nombre[0]

//Variable para contextualizar Lista random
context.ListRandom = randomIBPs;
log.info(context.ListRandom);