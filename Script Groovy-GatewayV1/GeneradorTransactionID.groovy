/************GENERADOR DE TRANSACTIONID CARACTERES ESPECIALES*****************/
import java.util.Random;

//Instanciar random
Random idRandom = new Random();
 //Rango de volumen 6
 long min = 111111;
 long max = 999999;
 
//Calculo de random
long resultadoRandom = idRandom.nextLong(max - min) + 1

def caracterHead = "#^"
def caracterFooter = "zY#"
def transactionId = caracterHead + resultadoRandom + caracterFooter
//log.info transactionId
//CARGAR EL VALOR AL CONTEXTO GLOBAL
context.transIdChars = transactionId;

//APROVECHAMOS EL RANDOM Y GENERAMOS UN N# DE CNTRATO ALETORIO
def charH = "C";
def randomCMCN = charH + resultadoRandom;
context.randomCmcn = randomCMCN;
//log.info randomCMCN

/********************GENERADOR DE TRANSACTION ID RANGO > 11******************************/
//Instanciar Random
Random idMayorRandom = new Random();

//Rango de volumen 14
long minI = 11111111111111;
long maxI = 99999999999999;

//Calculo del random
long resultadoMayorRandom = idMayorRandom.nextLong(maxI - minI) + 1

def complementoI = "zTwF"
def complementoII = "Yz"
def transactioIdMayor = complementoI + resultadoMayorRandom + complementoII;
//log.info("TransactionId: ${transactioIdMayor}")\

//CARGAR EL VALOR AL CONTEXTO GLOBAL
context.transIdMayor = transactioIdMayor;

