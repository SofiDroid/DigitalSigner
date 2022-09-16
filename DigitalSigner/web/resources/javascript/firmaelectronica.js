function probarDisponibilidadServlets(functionOK, functionERROR, URL_SERVLET_TRIFASE)
{
    try
    {
        // Si falla voy por sockets, sino uso el trifase
        // URL llamada: urlmapplet + 'trifase/SignatureService'
        // Devuelve: ERR-1: No se ha indicado la operacion a realizar
        toLog("Probando disponibilidad servlet: " + URL_SERVLET_TRIFASE);

        var self = this;

        // Mozilla/Safari
        if (window.XMLHttpRequest)
        {
            toLog("NAVEGADOR: " + "Mozilla/Safari/Edge");
            self.xmlHttpReq = new XMLHttpRequest();
        }
        // IE
        else if (window.ActiveXObject)
        {
            toLog("NAVEGADOR: " + "IE");
            self.xmlHttpReq = new ActiveXObject("Microsoft.XMLHTTP");
        }

        // SERVLET PARA TRIFASICO
        self.xmlHttpReq.open('GET', URL_SERVLET_TRIFASE, true);
        self.xmlHttpReq.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        self.xmlHttpReq.onreadystatechange = function ()
        {
            if (self.xmlHttpReq.readyState === 4)
            {
                switch (self.xmlHttpReq.status)
                {
                    case 200:
                        disponibleOK(functionOK, functionERROR);
                        break;
                    case 404:
                        disponibleERROR(functionOK, functionERROR);
                        break;
                    default:
                        toLog("ERROR readyState: " + self.xmlHttpReq.readyState + " || status: " + self.xmlHttpReq.status);
                        toLog("ERROR asumimos sockets (¿edge cross domain?)");
                        disponibleERROR(functionOK, functionERROR);
                }
            }
        };
        self.xmlHttpReq.timeout = 2000; // TIMEOUT de 2 segundos para que responda el servlet trifase.
        self.xmlHttpReq.ontimeout = function ()
        {
            toLog('timeout');
            disponibleERROR(functionOK, functionERROR);
        };
        self.xmlHttpReq.send("op=ver");
    }
    catch (e)
    {
        throw e;
    }
}