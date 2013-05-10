var xmlHttpRequest;
 
    
function doAjax(url,handleStateChangeFunc) {
	if (window.ActiveXObject) {
	    xmlHttpRequest = new ActiveXObject("Microsoft.XMLHTTP");
	} 
	else if (window.XMLHttpRequest) {
	    xmlHttpRequest = new XMLHttpRequest();
	}
    xmlHttpRequest.onreadystatechange = handleStateChangeFunc;
    xmlHttpRequest.open("GET", url+ "&" + Math.random, true);
    xmlHttpRequest.setRequestHeader("If-Modified-Since","0");
    xmlHttpRequest.send(null);
}