<%@ taglib uri="/WEB-INF/tag/fmt.tld" prefix="fmt"%>
<fmt:bundle basename="ApplicationResources" >
<script language="javascript">
<%
//This variable holds the input element that is the selected time element that the spinner will operate on
// (i.e. the hours input)
%>

var selectedInput = null;

<%
//The variable will hold the TimeOut object that will be used for the automatic scrolling while holding the mouse button down
%>
var timeout = null;

<%
//This variable is a counter so that the automatic scrolling starts out slow, then gets faster with iterations
%>
var stallCounter = 0;

<%
//formatTimeElements is going to be a two dimensional array where the 0th element of the second level will represent the
// separator, the 1st element will represent the time formatter itself, and the 2nd element will represent the user
// input value.  This all gets set in initTimeSpinner()
%>
var formatTimeElements = null;

<%
//This will be set in initTimeSpinner and will define the constaints of the hours based on the time format (ie, 12hour format
// versus 24hour format)
%>
var lowerHourConstraint = 1;
var upperHourConstraint = 12;

<%
//This variable is kept to determine if the individual times have been set to a valid time.  It will be updated whenever a user
// has selected a time input field, and then clicks anywhere else
%>
var validHours = true;
var validMinutes = true;
var validSeconds = true;

<%
//We need to create an array that will hold the id's of all the time spinners we might have on one page (since multiple may
// be present)
%>
var timeIdArray = new Array();


<%
/* This is the init function that will need to be called on the load of the page to include the time spinner.
 * It will need to pass in the name of the span that will hold the spinner itself.  This span should also include 
 * any initial time that should be loaded into the spinner.
 * Here's an example of what the code should look like to include the spinner;
 *
 * <SPAN ID="timeSpan">
 *   12:34:56 PM
 * </SPAN>
 * <SCRIPT>
 *  initTimeSpinner('timeSpan');
 * <\SCRIPT>
 *
 * Please also note that the order does make a difference as the SPAN must exist for the init method to run properly.
 * The user will then be able to get the final value through a hidden element by the name of userSpanName+"Time" (ie, 
 * from our example above, the hidden field name would be "timeSpanTime".  This hidden field is written out inside of the user's
 * span tag, so if the span tag is within a form, the form can be submitted and this field will contain the time value, otherwise
 * the user can get the value through the javascript call document.getElementById(userSpanName+"Time").value
 *
 * FORMATTING - The formatting of the dates is set from within the .properties file.  This can handle formatting using the following:
 *      h, hh, H, HH, k, K for the hour
 *      mm for the minutes
 *      ss for the seconds
 *      a for the am/pm marker
 *      a colon, comma, or space for the separator (or any combination of the three)
 *   there is logic put in place to not allow certain cases to work correctly (for instance, there shouldn't be a am/pm
 *   marker included for a format based on a 24hour scale, and therefore, the marker element won't spin when it would for a
 *   12 hour scale)
 */
%>
function initTimeSpinner(spanName, fromCalendar)
{
<%
    //We start off by getting the time from the text box that is passed in, which should just be the value of the text box
    // and nothing more
%>
    var ctl1 = document.getElementById(spanName);
    var time = ctl1.innerHTML;

<%
    //Now we add the control id to our global array
%>
    timeIdArray[timeIdArray.length] = ctl1.id;
    
<%
    //Now, we want to remove any spaces from the end of the time string.  This is because through some HTML formatting,
    // some extranous spaces may be inserted at the end, so we just remove them so we can be consistant
%>
    var regExp = /\s*/;
    time = time.replace(regExp, "");
    
<%
    //We want to set this every time.  This is incase we have multiple time spinners on one page.  Because we only use
    // the formatting in other methods, it doesn't matter what the values are in it.  We do this so we only have to iterate
    // through the formatting logic once, saving us some time
%>
    formatTimeElements = new Array()
    
<%
    //Now we'll parse our time into the correct divisions
%>
    var dateFormat = "";
    
<%
    // if the time is null, we want to make sure we use a default time of 12:00:00 AM
%>
    if(time.length == 0)
    {
       dateFormat = '<fmt:message key="calendar_time_format" />';
       
<%
       // replace with 12:00:00 AM
%>
       time = dateFormat;
       time = time.replace("hh", "12");
       time = time.replace("HH", "00");
       time = time.replace("mm", "00");
       time = time.replace("ss", "00");
       time = time.replace("a", "AM");
    
    } else if(fromCalendar)
    {
        dateFormat='<fmt:message key="calendar_datetime_format"/>';
    }
    else
    {
        dateFormat='<fmt:message key="calendar_time_format" />';
    }

<%
    //We're going to start by picking out everything that's separated by a space
%>
    aformatChar = " "
    aFormat	= dateFormat.split(aformatChar);
    aUser	= time.split(aformatChar);
    for(i=0;i<aFormat.length;i++)
    {
<%
        //We're only going to grab elements that are less than three chars long from the time format.  If this
        // is the case, then we want to load our time array to parallel the time format array
%>
        if(aFormat[i].length < 3)
        {
            formatTimeElements[formatTimeElements.length] = new Array();
            formatTimeElements[formatTimeElements.length-1][0] = aformatChar;
            formatTimeElements[formatTimeElements.length-1][1] = aFormat[i];
            formatTimeElements[formatTimeElements.length-1][2] = aUser[i];
        }
<%
        //If we didn't put it in the array, that means it needs to be parsed
%>
        else
        {
<%
            //Now we pick out everything that's separated by a colon
%>
            bformatChar = ":";
            bFormat	= aFormat[i].split(bformatChar);
            bUser	= aUser[i].split(bformatChar);
            for(j=0;j<bFormat.length;j++)
            {
<%
                //We're only going to grab elements that are less than three chars long from the time format.  If this
                // is the case, then we want to load our time array to parallel the time format array
%>
                if(bFormat[j].length < 3)
                {
                    formatTimeElements[formatTimeElements.length] = new Array();
                    formatTimeElements[formatTimeElements.length-1][0] = bformatChar;
                    formatTimeElements[formatTimeElements.length-1][1] = bFormat[j];
                    formatTimeElements[formatTimeElements.length-1][2] = bUser[j];
                }
<%
                //If we didn't put it in the array, that means it needs to be parsed
%>
                else
                {
<%
                    //Now we pick out everything that's separated by a comma.  This is our last case
%>
                    cformatChar = ",";
                    cFormat	= bFormat[j].split(cformatChar);
                    cUser	= bUser[j].split(cformatChar);
                    for(k=0;k<cFormat.length;k++)
                    {
<%
                        //We're only going to grab elements that are less than three chars long from the time format.  If this
                        // is the case, then we want to load our time array to parallel the time format array
%>
                        if(cFormat[k].length < 3)
                        {
                            formatTimeElements[formatTimeElements.length] = new Array();
                            formatTimeElements[formatTimeElements.length-1][0] = cformatChar;
                            formatTimeElements[formatTimeElements.length-1][1] = cFormat[k];
                            formatTimeElements[formatTimeElements.length-1][2] = cUser[k];
                        }
                    }
                }
            }
        }
    }

<%
    //Now we build up the HTML that will will place back into the span that the user passed in.  We will separate all of the time
    // elements into their own span (and give them an id based on the parent span's id and what time element it is).  We will
    // also add the up and down arrows to spin the time elements
    //The timeOnly variable will collect the time only after going through the format.  We need this because it's possible
    // that the calendar will pass in a time and date.
%>
    var timeOnly = "";
    var newInnerHTML = "";
    newInnerHTML += "<TABLE CELLPADDING='0' CELLSPACING='0'>";
    newInnerHTML += "<TR>";
    newInnerHTML += "<TD nowrap>";
<%
    //Now we're going to make sure we write out the time in the correct format.  We want to make sure we put the separator
    // in the right place, so there is some logic around it: if it's the last element, put the separator first, else put it last,
    // unless it's the last one (which it'll be putting ahead of it) and unless it's the second to last (which will have the
    // separator bafter it due to the separator going before the last element
%>
    for	(i=0;i<formatTimeElements.length;i++)
    {
        if ((formatTimeElements[i][1]=="h") || (formatTimeElements[i][1]=="hh"))
        {
            if(i == formatTimeElements.length - 1)
            {
                newInnerHTML += "&nbsp;"+formatTimeElements[i][0]+"&nbsp;";
                timeOnly += formatTimeElements[i][0];
            }
            newInnerHTML += "<INPUT TYPE='text'  size='10' ID=\""+ctl1.id+"hours\" onBlur=\"javascript:stopSpinner('"+ctl1.id+"')\" onFocus=\"javascript:selectInput('"+ctl1.id+"hours')\" onBlur=\"javascript:validateHours()\" value='"+formatTimeElements[i][2]+"' maxlength='2' >";
            timeOnly += formatTimeElements[i][2];
            if(i != formatTimeElements.length - 1 && i != formatTimeElements.length - 2)
            {
                newInnerHTML += "&nbsp;"+formatTimeElements[i][0]+"&nbsp;";
                timeOnly += formatTimeElements[i][0];
            }
            lowerHourConstraint = 1;
            upperHourConstraint = 12;
        }
        else if	((formatTimeElements[i][1]=="H") || (formatTimeElements[i][1]=="HH"))
        {
            if(i == formatTimeElements.length - 1)
            {
                newInnerHTML += "&nbsp;"+formatTimeElements[i][0]+"&nbsp;";
                timeOnly += formatTimeElements[i][0];
            }
            newInnerHTML += "<INPUT TYPE='text'  size='10' ID=\""+ctl1.id+"hours\" onBlur=\"javascript:stopSpinner('"+ctl1.id+"')\" onFocus=\"javascript:selectInput('"+ctl1.id+"hours')\" onBlur=\"javascript:validateHours()\" value='"+formatTimeElements[i][2]+"' maxlength='2' >";
            timeOnly += formatTimeElements[i][2];
            if(i != formatTimeElements.length - 1 && i != formatTimeElements.length - 2)
            {
                newInnerHTML += "&nbsp;"+formatTimeElements[i][0]+"&nbsp;";
                timeOnly += formatTimeElements[i][0];
            }
            lowerHourConstraint = 0;
            upperHourConstraint = 23;
        }
        else if	((formatTimeElements[i][1]=="k"))
        {
            if(i == formatTimeElements.length - 1)
            {
                newInnerHTML += "&nbsp;"+formatTimeElements[i][0]+"&nbsp;";
                timeOnly += formatTimeElements[i][0];
            }
            newInnerHTML += "<INPUT TYPE='text'  size='10' ID=\""+ctl1.id+"hours\" onBlur=\"javascript:stopSpinner('"+ctl1.id+"')\" onFocus=\"javascript:selectInput('"+ctl1.id+"hours')\" onBlur=\"javascript:validateHours()\" value='"+formatTimeElements[i][2]+"' maxlength='2' >";
            timeOnly += formatTimeElements[i][2];
            if(i != formatTimeElements.length - 1 && i != formatTimeElements.length - 2)
            {
                newInnerHTML += "&nbsp;"+formatTimeElements[i][0]+"&nbsp;";
                timeOnly += formatTimeElements[i][0];
            }
            lowerHourConstraint = 1;
            upperHourConstraint = 24;
        }
        else if	((formatTimeElements[i][1]=="K"))
        {
            if(i == formatTimeElements.length - 1)
            {
                newInnerHTML += "&nbsp;"+formatTimeElements[i][0]+"&nbsp;";
                timeOnly += formatTimeElements[i][0];
            }
            newInnerHTML += "<INPUT TYPE='text'  size='10' ID=\""+ctl1.id+"hours\" onBlur=\"javascript:stopSpinner('"+ctl1.id+"')\" onFocus=\"javascript:selectInput('"+ctl1.id+"hours')\" onBlur=\"javascript:validateHours()\" value='"+formatTimeElements[i][2]+"' maxlength='2' >";
            timeOnly += formatTimeElements[i][2];
            if(i != formatTimeElements.length - 1 && i != formatTimeElements.length - 2)
            {
                newInnerHTML += "&nbsp;"+formatTimeElements[i][0]+"&nbsp;";
                timeOnly += formatTimeElements[i][0];
            }
            lowerHourConstraint = 0;
            upperHourConstraint = 11;
        }
        else if	((formatTimeElements[i][1]=="m") || (formatTimeElements[i][1]=="mm"))
        {
            if(i == formatTimeElements.length - 1)
            {
                newInnerHTML += "&nbsp;"+formatTimeElements[i][0]+"&nbsp;";
                timeOnly += formatTimeElements[i][0];
            }
            newInnerHTML += "<INPUT TYPE='text'  size='10' ID=\""+ctl1.id+"minutes\" onBlur=\"javascript:stopSpinner('"+ctl1.id+"')\" onFocus=\"javascript:selectInput('"+ctl1.id+"minutes')\" onBlur=\"javascript:validateMinutes('"+ctl1.id+"minutes')\" value='"+formatTimeElements[i][2]+"' maxlength='2' >";
            timeOnly += formatTimeElements[i][2];
            if(i != formatTimeElements.length - 1 && i != formatTimeElements.length - 2)
            {
                newInnerHTML += "&nbsp;"+formatTimeElements[i][0]+"&nbsp;";
                timeOnly += formatTimeElements[i][0];
            }
        }
        else if	((formatTimeElements[i][1]=="s") || (formatTimeElements[i][1]=="ss") )
        {
            if(i == formatTimeElements.length - 1)
            {
                newInnerHTML += "&nbsp;"+formatTimeElements[i][0]+"&nbsp;";
                timeOnly += formatTimeElements[i][0];
            }
            newInnerHTML += "<INPUT TYPE='text'  size='10' ID=\""+ctl1.id+"seconds\" onBlur=\"javascript:stopSpinner('"+ctl1.id+"')\" onFocus=\"javascript:selectInput('"+ctl1.id+"seconds')\" onBlur=\"javascript:validateSeconds('"+ctl1.id+"seconds')\" value='"+formatTimeElements[i][2]+"' maxlength='2' >";
            timeOnly += formatTimeElements[i][2];
            if(i != formatTimeElements.length - 1 && i != formatTimeElements.length - 2)
            {
                newInnerHTML += "&nbsp;"+formatTimeElements[i][0]+"&nbsp;";
                timeOnly += formatTimeElements[i][0];
            }
        }
        else if	(formatTimeElements[i][1]=="a")
        {
            if(i == formatTimeElements.length - 1)
            {
                newInnerHTML += "&nbsp;"+formatTimeElements[i][0]+"&nbsp;";
                timeOnly += formatTimeElements[i][0];
            }
            newInnerHTML += "<INPUT TYPE='text'  size='10' ID=\""+ctl1.id+"ampmMarker\" onFocus=\"javascript:selectInput('"+ctl1.id+"ampmMarker')\" value='"+formatTimeElements[i][2]+"' readonly >";
            timeOnly += formatTimeElements[i][2];
            if(i != formatTimeElements.length - 1 && i != formatTimeElements.length - 2)
            {
                newInnerHTML += "&nbsp;"+formatTimeElements[i][0]+"&nbsp;";
                timeOnly += formatTimeElements[i][0];
            }
        }
    }
    newInnerHTML += "</TD>";
    newInnerHTML += "<TD>";
    newInnerHTML += "<TABLE CELLPADDING='0' CELLSPACING='0'>";
    newInnerHTML += "<TR>";
    newInnerHTML += "<TD>";
    newInnerHTML += "<A href='javascript:timeSpinner()' ONMOUSEDOWN=\"javascript:startSpinner('"+ctl1.id+"', 'up')\" ONMOUSEUP=\"javascript:stopSpinner('"+ctl1.id+"')\" ONMOUSEOUT=\"javascript:stopSpinner('"+ctl1.id+"')\">"
    newInnerHTML += "<IMG NAME='timeSpinnerArrowUp' SRC='"+imgSrc_up+"' WIDTH='13' HEIGHT='8' ALT='"+"<fmt:message key='calendar_clickToSpin' />"+"' BORDER='0' HSPACE='4' VSPACE='4'>";
    newInnerHTML += "</A>";
    newInnerHTML += "</TD>";
    newInnerHTML += "</TR>";
    newInnerHTML += "<TR>";
    newInnerHTML += "<TD>";
    newInnerHTML += "<A HREF='javascript:timeSpinner()'  ONMOUSEDOWN=\"javascript:startSpinner('"+ctl1.id+"', 'down')\" ONMOUSEUP=\"javascript:stopSpinner('"+ctl1.id+"')\" ONMOUSEOUT=\"javascript:stopSpinner('"+ctl1.id+"')\">";
    newInnerHTML += "<IMG ID='timeSpinnerArrowDown' SRC='"+imgSrc_down+"'  WIDTH='13' HEIGHT='8' ALT='"+"<fmt:message key='calendar_clickToSpin' />"+"' BORDER='0' HSPACE='4' VSPACE='4'>";
    newInnerHTML += "</A>";
    newInnerHTML += "</TD>";
    newInnerHTML += "</TR>";
    newInnerHTML += "</TABLE>";
    newInnerHTML += "</TD>";
    newInnerHTML += "</TR>";
    newInnerHTML += "</TABLE>";
    newInnerHTML += "<INPUT TYPE='HIDDEN' NAME='"+ctl1.id+"Time' ID='"+ctl1.id+"Time' VALUE='"+timeOnly+"'>";
    ctl1.innerHTML = newInnerHTML;

<%
    //As our last step, we will set the selected input to be the hours input in case the user hits a button without selecting
    // a time element.  We choose hours for no particular reason other than it gets updates frequently
%>
    if(selectedInput == null)
    {
        selectedInput = ctl1.id + "hours";
        document.getElementById(selectedInput).className = "timeSpinnerInputSelected";
    }
}

<%
/* This is the function called on the mouseDown event of the user's click.  It will detect what the selected span is, and then
 * call the appropriate spinner based on that span.  If the selected span doesn't match that of which span correlates with the
 * button pressed, the function will slect the correct span's minutes span.
 *
 */
%>
function startSpinner(ctl1Id, direction)
{
<%
    //The first check is to verify that the arrow pressed corresponds with the span selected.  If not, we will choose the
    // correct span's hour element
%>
    var regExp = new RegExp("^"+ctl1Id+"hours$|^"+ctl1Id+"hours24$|^"+ctl1Id+"minutes$|^"+ctl1Id+"seconds$|^"+ctl1Id+"ampmMarker$");
    if(selectedInput.match(regExp) == null)
    {
        if(document.getElementById(selectedInput) != null)
        {
            document.getElementById(selectedInput).className = "timeSpinnerInputUnselected";
        }
        
        selectedInput = ctl1Id + "hours";
<%
        //We're going to go ahead and select it to take focus away from the last selected input
%>
        document.getElementById(selectedInput).select();
    }
    
<%
    //Now, we want to see which time element is selected, and call the correct spinner
%>
    if(selectedInput.match("hours") != null)
    {
<%
        //Make sure selectedInput is hilighted
%>
        document.getElementById(selectedInput).className = "timeSpinnerInputSelected";
        spinHours(direction);
    }
    else if(selectedInput.match("minutes") != null)
    {
<%
        //Make sure selectedInput is hilighted
%>
        document.getElementById(selectedInput).className = "timeSpinnerInputSelected";
        spinMinutes(direction);
    }
    else if(selectedInput.match("seconds") != null)
    {
<%
        //Make sure selectedInput is hilighted
%>
        document.getElementById(selectedInput).className = "timeSpinnerInputSelected";
        spinSeconds(direction);
    }
    else if(selectedInput.match("ampmMarker") != null)
    {
<%
        //Make sure selectedInput is hilighted
%>
        document.getElementById(selectedInput).className = "timeSpinnerInputSelected";
        spinMarker();
    }
    
<%
    //Now, we want to recursively call our function in case the user holds the mouse down.  We also want to add
    // a stall, so if the user holds the mouse down for the set amount of iterations, the recursive calls become faster
%>
    if(stallCounter < 3)
    {
        timeout = window.setTimeout("startSpinner(\""+ctl1Id+"\", \""+direction+"\")", 500);
    }
    else if(stallCounter < 13)
    {
        timeout = window.setTimeout("startSpinner(\""+ctl1Id+"\", \""+direction+"\")", 100);
    }
    else if(stallCounter < 60)
    {
        timeout = window.setTimeout("startSpinner(\""+ctl1Id+"\", \""+direction+"\")", 50);
    }
    else
    {
        timeout = window.setTimeout("startSpinner(\""+ctl1Id+"\", \""+direction+"\")", 1);
    }
    stallCounter++;
}

<%
/*
 * Once the mouse button is released (mouseup event), we want to stop our recursive call and reset our stall counter
 */
%>
function stopSpinner(ctl1Id)
{
  window.clearTimeout(timeout);
  stallCounter = 0;
  updateHiddenValue(ctl1Id);
}

function updateHiddenValue(ctl1Id)
{
    var hours = "";
    var minutes = "";
    var seconds = "";
    var ampmMarker = "";
    if(document.getElementById(ctl1Id+"hours") != null)
    {
        hours = document.getElementById(ctl1Id+"hours").value;
    }
    if(document.getElementById(ctl1Id+"hours24") != null)
    {
        hours = document.getElementById(ctl1Id+"hours24").value;
    }
    if(document.getElementById(ctl1Id+"minutes") != null)
    {
        minutes = document.getElementById(ctl1Id+"minutes").value;
    }
    if(document.getElementById(ctl1Id+"seconds") != null)
    {
        seconds = document.getElementById(ctl1Id+"seconds").value;
    }
    if(document.getElementById(ctl1Id+"ampmMarker") != null)
    {
        ampmMarker = document.getElementById(ctl1Id+"ampmMarker").value;
    }
<%
    //This is just formatting to make sure all our digits are 2 places
%>
    if(hours < 10 && hours.length == 1)
    {
        hours = "0" + hours;
    }
    if(minutes < 10 && minutes.length == 1)
    {
        minutes = "0" + minutes;
    }
    if(seconds < 10 && seconds.length == 1)
    {
        seconds = "0" + seconds;
    }


    var newTime = "";
<%
    //Now we're going to make sure we write out the time in the correct format
%>
    for	(i=0;i<formatTimeElements.length;i++)
    {
        if ((formatTimeElements[i][1]=="h") || (formatTimeElements[i][1]=="hh") || (formatTimeElements[i][1]=="H") || (formatTimeElements[i][1]=="HH") || (formatTimeElements[i][1]=="k") || (formatTimeElements[i][1]=="K"))
        {
            if(i == formatTimeElements.length - 1)
            {
                newTime += formatTimeElements[i][0];
            }
            newTime += hours;
            if(i != formatTimeElements.length - 1 && i != formatTimeElements.length - 2)
            {
                newTime += formatTimeElements[i][0];
            }
        }
        else if	((formatTimeElements[i][1]=="m") || (formatTimeElements[i][1]=="mm"))
        {
            if(i == formatTimeElements.length - 1)
            {
                newTime += formatTimeElements[i][0];
            }
            newTime += minutes;
            if(i != formatTimeElements.length - 1 && i != formatTimeElements.length - 2)
            {
                newTime += formatTimeElements[i][0];
            }
        }
        else if	((formatTimeElements[i][1]=="s") || (formatTimeElements[i][1]=="ss") )
        {
            if(i == formatTimeElements.length - 1)
            {
                newTime += formatTimeElements[i][0];
            }
            newTime += seconds;
            if(i != formatTimeElements.length - 1 && i != formatTimeElements.length - 2)
            {
                newTime += formatTimeElements[i][0];
            }
        }
        else if	(formatTimeElements[i][1]=="a")
        {
            if(i == formatTimeElements.length - 1)
            {
                newTime += formatTimeElements[i][0];
            }
            newTime += ampmMarker;
            if(i != formatTimeElements.length - 1 && i != formatTimeElements.length - 2)
            {
                newTime += formatTimeElements[i][0];
            }
        }
    }

    document.getElementById(ctl1Id+"Time").value = newTime;
}

<%
/*
 * This is were we spin the hours.  We first make sure that we grab the right span id because this function can be
 * called two different ways.  One, it could be called when the hours element is selected.  The second is if the minutes
 * roll over (ie from 59 to 0, we need to increment the hour counter).  So, if the hours element isn't selected, we
 * need to grab the correct hours element
 */
%>
function spinHours(direction)
{
<%
    //Grab the correct hours element
%>
    var hoursSpanId = selectedInput;
    var regExp = new RegExp("hours$");
    if(selectedInput.match(regExp) == null)
    {
        regExp = new RegExp("minutes$|seconds$|ampmMarker$");
        var timeElement = selectedInput.match(regExp);
        var spanId = selectedInput.substring(0, selectedInput.indexOf(timeElement));
        hoursSpanId = spanId + "hours";
    }
    
<%
    //Do a quick chech to make sure the number is valid, and if not, set it to 00
%>
    if(!validateNumber(document.getElementById(hoursSpanId).value))
    {
        document.getElementById(hoursSpanId).value = "00";
        validHours = true;
    }
    
<%
    //Now we're going to either add or subtract, based on our direction.  Also, if we roll over, we want to change
    // any necessary time elements.  We also want to check if this span even exists.  Because there are mulitple hour spinners
%>
    var hours = document.getElementById(hoursSpanId).value;
    if(direction == "up")
    {
        hours = Math.abs(hours) + 1;
    }
    else
    {
        hours = Math.abs(hours) - 1;
    }

<%
    //We want to cycle based on our upper and lower hour constraints
%>
    if(hours > upperHourConstraint)
    {
        hours = lowerHourConstraint;
    }
    else if(hours < lowerHourConstraint)
    {
        hours = upperHourConstraint;
    }

<%
    //This is just formatting to make sure all our digits are 2 places
%>
    if(hours < 10)
    {
        hours = "0" + hours;
    }

<%
    //Finally, write out our new hour
%>
    document.getElementById(hoursSpanId).value = hours;

}


<%
/*
 * This is were we spin the minutes.  We first make sure that we grab the right span id because this function can be
 * called two different ways.  One, it could be called when the minutes element is selected.  The second is if the seconds
 * roll over (ie from 59 to 0, we need to increment the hour counter).  So, if the minutes element isn't selected, we
 * need to grab the correct minutes element
 */
%>
function spinMinutes(direction)
{
<%
    //Grab the correct minutes element
%>
    var minutesSpanId = selectedInput;
    var regExp = new RegExp("minutes$");
    if(selectedInput.match(regExp) == null)
    {
        regExp = new RegExp("hours$|seconds$|ampmMarker$");
        var timeElement = selectedInput.match(regExp);
        var spanId = selectedInput.substring(0, selectedInput.indexOf(timeElement));
        minutesSpanId = spanId + "minutes";
    }
    
<%
    //Do a quick chech to make sure the number is valid, and if not, set it to 00
%>
    if(!validateNumber(document.getElementById(minutesSpanId).value))
    {
        document.getElementById(minutesSpanId).value = "00";
        validMinutes = true;
    }
    
<%
    //Now we're going to either add or subtract, based on our direction.  Also, if we roll over, we want to change
    // any necessary time elements
%>
    var minutes = document.getElementById(minutesSpanId).value;
    if(direction == "up")
    {
        minutes = Math.abs(minutes) + 1;
    }
    else
    {
        minutes = Math.abs(minutes) - 1;
    }

<%
    //Because we only have 60 minutes, we want to cycle if we hit 59, or -1
%>
    if(minutes > 59)
    {
        minutes = 0;
    }
    else if(minutes < 0)
    {
        minutes = 59;
    }

<%
    //This is just formatting to make sure all our digits are 2 places
%>
    if(minutes < 10)
    {
        minutes = "0" + minutes;
    }
    
<%
    //Finally, write out our new minute
%>
    document.getElementById(minutesSpanId).value = minutes;

}

<%
/*
 * This is were we spin the seconds.
 */
%>
function spinSeconds(direction)
{
    var secondsSpanId = selectedInput;
    
<%
    //Do a quick chech to make sure the number is valid, and if not, set it to 00
%>
    if(!validateNumber(document.getElementById(secondsSpanId).value))
    {
        document.getElementById(secondsSpanId).value = "00";
        validSeconds = true;
    }
    
<%
    //Now we're going to either add or subtract, based on our direction.  Also, if we roll over, we want to change
     // any necessary time elements
%>
    var seconds = document.getElementById(secondsSpanId).value;
    if(direction == "up")
    {
        seconds = Math.abs(seconds) + 1;
    }
    else
    {
        seconds = Math.abs(seconds) - 1;
    }

<%
    //Because we only have 60 seconds, we want to cycle if we hit 59, or -1
%>
    if(seconds > 59)
    {
        seconds = 0;
    }
    else if(seconds < 0)
    {
        seconds = 59;
    }

<%
    //This is just formatting to make sure all our digits are 2 places
%>
    if(seconds < 10)
    {
        seconds = "0" + seconds;
    }
    
<%
    //Finally, write out our new second
%>
   document.getElementById(secondsSpanId).value = seconds;

}

<%
/*
 * This is were we spin the AM PM Marker.  We first make sure that we grab the right span id because this function can be
 * called two different ways.  One, it could be called when the marker element is selected.  The second is if the hour
 * rolls over (ie from 11AM to 12PM).  So, if the marker element isn't selected, we need to grab the correct marker element
 */
%>
function spinMarker()
{
<%
    //Grab the correct hours element
%>
    var ampmMarkerSpanId = selectedInput;
    var regExp = new RegExp("ampmMarker$");
    if(selectedInput.match(regExp) == null)
    {
        regExp = new RegExp("hours$|minutes$|seconds$");
        var timeElement = selectedInput.match(regExp);
        var spanId = selectedInput.substring(0, selectedInput.indexOf(timeElement));
        ampmMarkerSpanId = spanId + "ampmMarker";
    }
    
<%
    //Now we're going to flip between AM and PM.  Basically, if the marker element is current set to AM, we set
    // to PM, and vice versa
%>
    var marker = document.getElementById(ampmMarkerSpanId).value;
    if(marker == "AM")
    {
        marker = "PM"
    }
    else
    {
        marker = "AM"
    }
    
<%
    //Finally, write out our new marker
%>
    document.getElementById(ampmMarkerSpanId).value = marker;

}

<%
/*
 * This function is called when the individual time element is selected.  It will de-highlight the currently selected
 * element, highlight the newly selected element, and then set the global variable to represent the newly selected element
 */
%>
function selectInput(inputName)
{
    if(document.getElementById(selectedInput) == null)
    {
        selectedInput = inputName;
    }
    
    document.getElementById(selectedInput).className = "timeSpinnerInputUnselected";
    document.getElementById(inputName).className = "timeSpinnerInputSelected";
    var regExp = new RegExp("ampmMarker$");
    if(inputName.match(regExp) == null)
    {
        document.getElementById(inputName).select();
    }

    selectedInput = inputName;
}


<%
/*
 * This function is called when the hours entry field loses focus and will check to see that the value that was entered
 * is valid
 */
%>
function validateHours()
{
    for(i=0;i<timeIdArray.length;i++)
    {
        var ctl1Id = timeIdArray[i];
        if(document.getElementById(ctl1Id+"hours") != null)
        {
            var hours = document.getElementById(ctl1Id+"hours").value;
<%
            //First check is to make sure he entry field is a valid number
%>
            var validHoursTemp = validateNumber(hours);

            if(validHoursTemp)
            {
                if(hours > upperHourConstraint)
                {
                    validHoursTemp = false;
                }
                else if(hours < lowerHourConstraint)
                {
                    validHoursTemp = false;
                }
            }

<%
            //We need make sure that if any of the inputs are false, that our global is set to false and not later overwritten
%>
            if((i==0) || (i > 0 && validHours))
            {
                validHours = validHoursTemp;

                if(!validHours)
                {
                    alert("<fmt:message key='calendar_invalidHours' />");    
                }
            }
        }
    }
}

<%
/*
 * This function is called when the hours entry field loses focus and will check to see that the value that was entered
 * is valid
 */
%>
function validateMinutes(ctl1Id)
{
    for(i=0;i<timeIdArray.length;i++)
    {
        var ctl1Id = timeIdArray[i];
        if(document.getElementById(ctl1Id+"minutes") != null)
        {
            var minutes = document.getElementById(ctl1Id+"minutes").value;
<%
            //First check is to make sure he entry field is a valid number
%>
            var validMinutesTemp = validateNumber(minutes);
            
            if(validMinutesTemp)
            {
                if(minutes > 59)
                {
                    validMinutesTemp = false;
                }
                else if(minutes < 0)
                {
                    validMinutesTemp = false;
                }
            }
            
<%
            //We need make sure that if any of the inputs are false, that our global is set to false and not later overwritten
%>
            if((i==0) || (i > 0 && validMinutes))
            {
                validMinutes = validMinutesTemp;
                if(!validMinutes)
                {
                    alert("<fmt:message key='calendar_invalidMinutes' />");    
                }
            }
        }
    }
}

<%
/*
 * This function is called when the hours entry field loses focus and will check to see that the value that was entered
 * is valid
 */
%>
function validateSeconds(ctl1Id)
{
    for(i=0;i<timeIdArray.length;i++)
    {
        var ctl1Id = timeIdArray[i];
        if(document.getElementById(ctl1Id+"seconds") != null)
        {
            var seconds = document.getElementById(ctl1Id+"seconds").value;
<%
            //First check is to make sure he entry field is a valid number
%>
            var validSecondsTemp = validateNumber(seconds);
            
            if(validSecondsTemp)
            {
                if(seconds > 59)
                {
                    validSecondsTemp = false;
                }
                else if(seconds < 0)
                {
                    validSecondsTemp = false;
                }
            }
            
<%
            //We need make sure that if any of the inputs are false, that our global is set to false and not later overwritten
%>
            if((i==0) || (i > 0 && validSeconds))
            {
                validSeconds = validSecondsTemp;
                if(!validSeconds)
                {
                    alert("<fmt:message key='calendar_invalidSeconds' />");    
                }
            }
        }
    }
}

<%
/*
 * This function should be called from the outside to make sure that the time entered is a valid time
 */
%>
function validTime()
{
    if(validHours && validMinutes && validSeconds)
    {
        return true;
    }
    else
    {
        return false;
    }
}
    
<%
/*
 * This is a validation function used to check if the passed in value is a number or not
 */
%>
function validateNumber(value)
{
    invalid = false;
    var booleanRegExp = new RegExp("^[0-9]*$");
    matchArray = value.match(booleanRegExp);
    if(matchArray == null)
    {
        invalid = true;
    }

    return !invalid;
}

<%
/*
 * This is just a bogus function for our spinner arrows to call in their HREF.  It was added just to make it look a bit
 * cleaner (rather than calling "#" in their HREF
 */
%>
function timeSpinner()
{
}
</script>
</fmt:bundle>
