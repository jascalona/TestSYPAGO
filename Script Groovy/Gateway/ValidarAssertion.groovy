//import org.bson.Document
import groovy.json.JsonSlurper;
import Utiliti.SYPAGOBancaribe;

sleep(4000) //Aplica un retardo de 2sg;

def ClienteBancaribe =  new SYPAGOBancaribe();

    def requestContent = new String(messageExchange.rawRequestData)
    def jsonStartIndex = requestContent.indexOf("{")
    def jsonContent = requestContent.substring(jsonStartIndex)
    log.info  jsonContent
    def json = new groovy.json.JsonSlurper().parseText(jsonContent)

    log.info json.TransactionId
    def a = ClienteBancaribe.geyStsusReports("sycom_gateway_user","3b17-0e38206Pae-88_@fb8476dff92f271A7C","http://10.0.62.20:8086/sypago/gateway/api/v1/solest",json.TransactionId);
    log.info(a);

    def jsonR = new groovy.json.JsonSlurper().parseText(a)
    
    log.info jsonR.TrnStRp.TxSts
    
    def TxSts = jsonR.TrnStRp.TxSts; 
	assert  TxSts== "RJCT"

	def Rsn = jsonR.TrnStRp.Rsn; 
	log.info Rsn
	assert  Rsn== "VE01" //Assert condicion que verifica que el valor sea igual a lo definido
