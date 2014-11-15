/**
 * Created by yaroslav on 11/15/14.
 */
var webSocket;
var autoScroll = true;
var tapHold = false;
var pageNumber = 1;

$(window).on("touchstart", function(ev) {
    tapHold = true;
});

$(window).on("touchend", function(ev) {
    tapHold = false;
});

changeAuto = function(){
    autoScroll = !autoScroll;
};

setPageNumber = function(number){
    pageNumber = number;
};

openSocket = function(authToken){
    //ensure only one connection open at a time
    if(webSocket !== undefined){
        if(webSocket.readyState !== WebSocket.CLOSED){
            return;
        }
    }
    //document.cookie = 'auth_token='+authToken;
    if(authToken === undefined){
        return;
    }
    webSocket = new WebSocket("ws://tevatron.ddns.ukrtel.net/lingvo-movie-core/update/"+authToken);
    webSocket.onmessage = function(event){
        if(autoScroll && ! tapHold){
            var data = JSON.parse(event.data);
            scrollToTimeFrame(data.previousTimeFrame, data.timeFrame, data.nextTimeFrame);
        }
    }
};

var tempElement;
var tempNextElement;
scrollToTimeFrame = function(previousTimeFrame, timeFrame, nextTimeFrame){
    var selectorPart = "li>span[title=time-"+pageNumber+"-";
    var previousElement = document.querySelector(selectorPart+previousTimeFrame+"]").parentNode;
    var element = document.querySelector(selectorPart+timeFrame+"]").parentNode;
    var nextElement = document.querySelector(selectorPart+nextTimeFrame+"]").parentNode;
    //var yPosition = 0;


    var yPositionPrevious = $(previousElement).offset().top;
    //var yPositionCurrent = $(element).offset().top;
    //var yPosition = yPositionPrevious - (yPositionCurrent - yPositionPrevious)*2;
    var yPosition = yPositionPrevious - ($( window ).height()/2);

    if(tempElement !== undefined){
        $(tempElement).removeClass("highlighted");
    }
    if(tempNextElement !== undefined){
        $(tempNextElement).removeClass("border-highlighted");
    }

    tempElement = element;
    tempNextElement = nextElement;

    $(element).addClass("highlighted");
    $(nextElement).addClass("border-highlighted");

    $("html, body").animate({ scrollTop: yPosition });
}