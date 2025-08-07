import com.eviware.soapui.support.XmlHolder
import net.sf.*
import net.sf.json.*
import net.sf.json.groovy.*

//testRunner.testCase.testSteps["auth"].testRequest.response.toString();

def response = testRunner.testCase.testSteps["auth"].testRequest.response.contentAsString

def jsonSlurper = new JsonSlurper().parseText(response)
def codigo = jsonSlurper.access_token.toString()

testRunner.testCase.testSuite.project.setPropertyValue('Authorization', 'Barer '+codigo)
