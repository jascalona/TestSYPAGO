/***********SCRIPT RECEPTOR******************/
import imports.Cellxlsx;
import imports.Index;
import java.util.Random;

def excelFilePath = "C:\\Import.xlsx" //Ruta absoluta

/*****Lectura del excel*******/
//Num Hoja
def ReceptorIBPs = Index.readExcelData(excelFilePath, 1);

/*********Instancia Random*************/
def randomIBPs = new Random().nextInt(ReceptorIBPs.size()); //Define las filas dentro de la hoja

//log.info ReceptorIBPs;

context.ListIBPs = ReceptorIBPs;
log.info (context.ListIBPs [0].id.substring(0,1)); 

//Variable para contextualizar Lista random
context.ListRandom = randomIBPs;
//log.info(context.ListRandom);