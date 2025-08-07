/*********GENERADOR DE ESTRING > 16 PARA UserReference*********/
import java.util.Random;
import Labels.GeneratorUserReferenceId;

//Variable para almacer el string
def longitudGroovy = 20;

//Llamamos al método estático directamente usando el nombre del paquete y la clase
def codigoGenerado = Labels.GeneratorUserReferenceId.GeneratorChart(longitudGroovy)
context.UserRefId = codigoGenerado; //Contexto


/*****************GENERADOR DE STRINGS PARA Nm > 200*********/
//Variable para almacenar el string de Nm
def longitudNm = 351;

//Llamado del metodo
def generatorNm = Labels.GeneratorUserReferenceId.GeneratorChart(longitudNm);
context.chartNm = generatorNm;

/*****************GENERADOR DE CARACTERES ESPECIALES************/
//Llamar al metodo esttatico directamente usando el nombre del paquete y la clase
//Variable de longitud

def longitudCHs = 3;
def generatorCHs = Labels.GeneratorChartS.GeneratorCHs(longitudCHs);
context.chartSpecial = generatorCHs;
