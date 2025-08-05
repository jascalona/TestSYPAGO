//SCRIPTS PARA PASAR A CONTEXTO GLOBAL LA DATA IMPORTADA CARGADA DESDE EXCEL APLICANDO EL METODO RANDOM

/*
*  Para invocar a las variables desde el msj JSON de forma statica haciendo se debe hacer referencia al numero del elemento deseado
*  
*  Ejm Statico de una etiqueta: "${=context.variableContextGlobal [0].nombre}"
*  Ejm Dinamico de una etiqueta "${=context.variableContextGlobal [${context.}]}"	
*/

/***********SCRIPT RECEPTOR IBP BANCARIBE******************/
import imports.Cellxlsx;
import imports.Index;
import java.util.Random;

def excelFilePath = "C:\\Import.xlsx"; //Ruta absoluta

def ReceptorBancaribe = Index.readExcelData(excelFilePath,0);

/****Instancia Random****/
def randomBancaribe = new Random().nextInt(ReceptorBancaribe.size()); //Definicion de las filas dentro de la hola
log.info("Indice de fila generado: ${randomBancaribe}" );

//Contextualizar Lista de variables globales
context.listRecepBancaribe = ReceptorBancaribe;
log.info(context.listRecepBancaribe); //.nombre[0] 

//Contextualizar valor generado por el metodo random
context.ListRandom = randomBancaribe;
//log.info(context.ListRandom)



