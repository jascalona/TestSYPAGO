//Open Windows
//Transaction_paylink_CELE: hace referencia al nombre del paso de prueba
//pay_link: hace referencia al link que genera el paso de prueba
def url = context.expand('${Transaction_paylink_CELE#Response#$.pay_link}')
java.awt.Desktop.desktop.browse(new URI(url))