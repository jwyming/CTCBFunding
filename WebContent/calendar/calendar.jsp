<%@ taglib uri="/WEB-INF/tag/fmt.tld" prefix="fmt"%>
<%@ include file="/calendar/timeSpinner.jsp"%>

<fmt:bundle basename="ApplicationResources" >




<script language="javascript">
	//---------------------------------------- System Parameter --------------------------------------------

	var showToday = 1;            /** 0 - don't show; 1 - show */
	var startAt = 0;              /** 0 - sunday ; 1 - monday */
	//---------------------------------------- Current Date --------------------------------------------
	var	today =	new	Date();
	var	dateNow	 = today.getDate();
	var	monthNow = today.getMonth();
	var	yearNow	 = today.getFullYear();
	
	var dateTimeFormat = "<fmt:message key='calendar_datetime_format'/>";
	var dateFormat='<fmt:message key="calendar_date_format"/>';	
    var timeFormat = "<fmt:message key='calendar_time_format'/>";
	//---------------------------------------- Month Name --------------------------------------------
	var	monthName =	new	Array(
	"<fmt:message key="calendar_month1"/>",
	"<fmt:message key="calendar_month2"/>",
	"<fmt:message key="calendar_month3"/>",
	"<fmt:message key="calendar_month4"/>",
	"<fmt:message key="calendar_month5"/>",
	"<fmt:message key="calendar_month6"/>",
	"<fmt:message key="calendar_month7"/>",
	"<fmt:message key="calendar_month8"/>",
	"<fmt:message key="calendar_month9"/>",
	"<fmt:message key="calendar_month10"/>",
	"<fmt:message key="calendar_month11"/>",
	"<fmt:message key="calendar_month12"/>"
	);
	//---------------------------------------- Day Name --------------------------------------------
	if (startAt==0){
		dayName = new Array	("<fmt:message key="calendar_sun"/>","<fmt:message key="calendar_mon"/>","<fmt:message key="calendar_tue"/>","<fmt:message key="calendar_wed"/>","<fmt:message key="calendar_thu"/>","<fmt:message key="calendar_fri"/>","<fmt:message key="calendar_sat"/>");
	}else{
		dayName = new Array	("<fmt:message key="calendar_mon"/>","<fmt:message key="calendar_tue"/>","<fmt:message key="calendar_wed"/>","<fmt:message key="calendar_thu"/>","<fmt:message key="calendar_fri"/>","<fmt:message key="calendar_sat"/>","<fmt:message key="calendar_sun"/>");
	}
	//------------------------------------------ Image Source ------------------------------------------
	
	var imgSrc_calendar="images/calendar/calendar.gif"
	var imgSrc_ok="images/calendar/ok.gif"
	var imgSrc_clean="images/calendar/clean.gif"
	var imgSrc_close="images/calendar/close.gif"
	var imgSrc_divider="images/calendar/divider.gif"
	var imgSrc_down="images/calendar/down.gif"
	var imgSrc_up="images/calendar/up.gif"
	var imgSrc_drop1="images/calendar/drop1.gif"
	var imgSrc_drop2="images/calendar/drop2.gif"
	var imgSrc_left1="images/calendar/left1.gif"
	var imgSrc_left2="images/calendar/left2.gif"
	var imgSrc_right1="images/calendar/right1.gif"
	var imgSrc_right2="images/calendar/right2.gif"
	//------------------------------------------- System Variable -----------------------------------------
	
	var	crossobj, crossMonthObj, crossYearObj, monthSelected, yearSelected, dateSelected, omonthSelected, oyearSelected, odateSelected, monthConstructed, yearConstructed, intervalID1, intervalID2, timeoutID1, timeoutID2, ctlToPlaceValue, dateFormat, nStartingYear;

	
	//-------------------------------------------- changeImg() ----------------------------------------
	function changeImg(imgId, imgSrc)
	{
		document.getElementById(imgId).setAttribute("src",imgSrc);
	}

	//-------------------------------------------- hideCalendar() ----------------------------------------
	function hideCalendar()	
	{
		crossobj.visibility="hidden";
		if (crossMonthObj != null){crossMonthObj.visibility="hidden"}
		if (crossYearObj !=	null){crossYearObj.visibility="hidden"}

<%
        // Removed to fix CR171952
        // var timeoutText = "toScroll(" + document.body.scrollTop + "," + document.body.scrollLeft + ");";
        // setTimeout(timeoutText, 20);
%>
	}
	
	
	//-------------------------------------------- closeCalendar() ----------------------------------------
	function closeCalendar() 
	{
		var	sTmp;

        var isTimeValid = true;
        if(document.getElementById(ctlToPlaceValue.id+"TimeSpinner") != null)
        {
            isTimeValid = validTime();
        }
		
        if(isTimeValid)
        {
            hideCalendar();

            if(ctlToPlaceValue.type == "text")
            {
                if(document.getElementById(ctlToPlaceValue.id+"TimeSpinner") != null)
                {
                    ctlToPlaceValue.value =	constructDateTime(dateSelected,monthSelected,yearSelected);
                }
                else
                {
                    ctlToPlaceValue.value =	constructDate(dateSelected,monthSelected,yearSelected);
                }
            }
            else
            {
                if(document.getElementById(ctlToPlaceValue.id+"TimeSpinner") != null)
                {
                    ctlToPlaceValue.innerHTML =	constructDateTime(dateSelected,monthSelected,yearSelected);
                }
                else
                {
                    ctlToPlaceValue.innerHTML =	constructDate(dateSelected,monthSelected,yearSelected);
                }
            }
        }
        else
        {
            alert("<fmt:message key='calendar_invalid_time'/>");
        }
	}
	//-------------------------------------------- constructDate() constructDateTime()----------------------------------------
	function padZero(num) 
	{
		return (num	< 10)? '0' + num : num ;
	}

	function constructDate(d,m,y)
	{
		sTmp = dateFormat;
        sTmp = sTmp.replace	("dd",padZero(d));
		sTmp = sTmp.replace	("d",d);
		//sTmp = sTmp.replace	("yyyy",y);
		//sTmp = sTmp.replace	("yy",y.toString().substring(2));    
		sTmp = sTmp.replace	("yyyy",(y-1911).toString());////////////////
		sTmp = sTmp.replace	("yy",(y-11).toString());/////////////
		sTmp = sTmp.replace	("MMMM",monthName[m]);
		sTmp = sTmp.replace	("MMM",monthName[m].substring(0, 3));
		sTmp = sTmp.replace	("MM",padZero(m+1));
		sTmp = sTmp.replace	("M",m+1);
		return sTmp;
	}
	
    function constructDateTime(d,m,y)
    {
		var date = constructDate(d,m,y);
        var datetime = dateTimeFormat.replace(dateFormat, date);
        datetime = datetime.replace(timeFormat, document.getElementById(ctlToPlaceValue.id+"TimeSpinnerTime").value);
        return datetime;
    }
	
	//------------------------------------------------------------------------------------

	var sHTML1="";
	sHTML1+="<span id='spanLeft'	style='border-style:solid;border-width:1px;border-color:#3366FF;cursor:pointer' 	onmouseover='changeImg(\"changeLeft\",imgSrc_left2);	this.style.borderColor=\"#88AAFF\";window.status=\""+scrollLeftMessage+"\"' 	onmouseout='clearInterval(intervalID1);changeImg(\"changeLeft\",imgSrc_left1);this.style.borderColor=\"#3366FF\";window.status=\"\"'  onclick='javascript:decMonth()'  onmousedown='clearTimeout(timeoutID1);timeoutID1=setTimeout(\"StartDecMonth()\",500)'	onmouseup='clearTimeout(timeoutID1);clearInterval(intervalID1)'>&nbsp<IMG id='changeLeft' SRC='"+imgSrc_left1+"' width=10 height=11 BORDER=0>&nbsp</span>&nbsp;";
	sHTML1+="<span id='spanRight' 	style='border-style:solid;border-width:1px;border-color:#3366FF;cursor:pointer'	onmouseover='changeImg(\"changeRight\",imgSrc_right2);	this.style.borderColor=\"#88AAFF\";window.status=\""+scrollRightMessage+"\"' 	onmouseout='clearInterval(intervalID1);changeImg(\"changeRight\",imgSrc_right1);this.style.borderColor=\"#3366FF\";window.status=\"\"' onclick='incMonth()' onmousedown='clearTimeout(timeoutID1);timeoutID1=setTimeout(\"StartIncMonth()\",500)'	onmouseup='clearTimeout(timeoutID1);clearInterval(intervalID1)'>&nbsp<IMG id='changeRight' SRC='"+imgSrc_right1+"' width=10 height=11 BORDER=0>&nbsp</span>&nbsp";
	sHTML1+="<span id='spanMonth' 	style='border-style:solid;border-width:1px;border-color:#3366FF;cursor:pointer'	onmouseover='changeImg(\"changeMonth\",imgSrc_drop2);	this.style.borderColor=\"#88AAFF\";window.status=\""+selectMonthMessage+"\"' 	onmouseout='changeImg(\"changeMonth\",imgSrc_drop1);this.style.borderColor=\"#3366FF\";window.status=\"\"' onclick='popUpMonth()'></span>&nbsp;";
	sHTML1+="<span id='spanYear' 	style='border-style:solid;border-width:1px;border-color:#3366FF;cursor:pointer' 	onmouseover='changeImg(\"changeYear\",imgSrc_drop2);	this.style.borderColor=\"#88AAFF\";window.status=\""+selectYearMessage+"\"'		onmouseout='changeImg(\"changeYear\",imgSrc_drop1);this.style.borderColor=\"#3366FF\";window.status=\"\"'	onclick='popUpYear()'></span>&nbsp;";













///////////////////////////////////////////////////////






	var fixedX = -1;              /** x position (-1 if to appear below control) */
	var fixedY = -1;              /** y position (-1 if to appear below control) */
	
	var showWeekNumber = 0;       /** 0 - don't show; 1 - show */
	



	var gotoString = "<fmt:message key="calendar_goToMonth"/>";
	var todayString = "<fmt:message key="calendar_todayIs"/>";
	var weekString = "<fmt:message key="calendar_wk"/>";
	var scrollLeftMessage  = "<fmt:message key="calendar_prev_month"/>";
	var scrollRightMessage = "<fmt:message key="calendar_next_month"/>";
	var selectMonthMessage = "<fmt:message key="calendar_select_month"/>";
	var selectYearMessage  = "<fmt:message key="calendar_select_year"/>";
	var selectDateMessage  = "[date]";            // do not replace [date], it will be replaced by date.


	var	imgsrc = new Array(imgSrc_drop1,imgSrc_drop2,imgSrc_left1,imgSrc_left2,imgSrc_right1,imgSrc_right2);
	var	img	= new Array();

	var bShow = false;
    


	

	for	(i=0;i<imgsrc.length;i++)
	{
		img[i] = new Image;
		img[i].src = imgsrc[i];
	}


<%
	//We set the zIndex over 5001 because of the 'popup' dialog framework which is at a zIndex of 5001 (and we need the
    // calendar to sit over it)
%>
    document.write("<div onclick='bShow=true' id='__calendar__' style='z-index:+5999;position:absolute;visibility:hidden;'>");
    document.write("<table width="+((showWeekNumber==1)?380:350)+" style='font-family:arial;font-size:11px;border-width:1px;border-style:solid;border-color:#a0a0a0;font-family:arial; font-size:11px}' bgcolor='#ffffff'>");
    document.write("<tr bgcolor='#0000aa'><td>");
    document.write("  <table width='"+((showWeekNumber==1)?278:348)+"'><tr><td style='padding:2px;font-family:arial; font-size:11px;'>");
    document.write("     <font color='#ffffff'><B><span id='caption'>" + sHTML1 + "</span></B></font>");
    document.write("  </td><td align=right valign='middle'>");
    document.write("     <a href='javascript:clean()'><IMG SRC='"+imgSrc_clean+"' BORDER='0' ALT='Clean'></a>");
    document.write("&nbsp;&nbsp;");
    document.write("     <a href='javascript:hideCalendar()'><IMG SRC='"+imgSrc_close+"' WIDTH='15' HEIGHT='13' BORDER='0' ALT='Close the Calendar'></a>");
    document.write("  </td></tr></table>");
    document.write("</td></tr>");
    document.write("<tr><td style='padding:5px' bgcolor=#ffffff>");
    document.write("<span id='calendarcontent'></span></td></tr>");
		
	if (showToday==1)
	{
		document.write ("<tr bgcolor=#f0f0f0><td style='padding:5px' align=center><span id='lblToday'>" + todayString + " <a onmousemove='window.status=\""+gotoString+"\"' onmouseout='window.status=\"\"' title='"+gotoString+"' style='"+styleAnchor+"' href='javascript:monthSelected=monthNow;yearSelected=yearNow;constructCalendar();'>"+dayName[(today.getDay()-startAt==-1)?6:(today.getDay()-startAt)]+", " + dateNow + " " + monthName[monthNow].substring(0,3)	+ "	" +	yearNow	+ "</a>" + "</span></td></tr>");
	}
		
	document.write ("</table></div><div id='selectMonth' style='z-index:+5999;position:absolute;visibility:hidden;'></div><div id='selectYear' style='z-index:+5999;position:absolute;visibility:hidden;'></div>");

	var	styleAnchor="text-decoration:none;color:black;";
	var	styleLightBorder="border-style:solid;border-width:1px;border-color:#a0a0a0;";

	function init()	
	{

		crossobj=document.getElementById("__calendar__").style;

		crossMonthObj=document.getElementById("selectMonth").style;
		crossYearObj=document.getElementById("selectYear").style;

		monthConstructed=false;
		yearConstructed=false;

	}








    
	function clean() 
	{
        hideCalendar();
        ctlToPlaceValue.value =	'';
    }





	/*** Month Pulldown	***/

	function StartDecMonth()
	{
		intervalID1=setInterval("decMonth()",80);
	}

	function StartIncMonth()
	{
		intervalID1=setInterval("incMonth()",80);
	}

	function incMonth () 
	{
		monthSelected++;
		if (monthSelected>11) 
		{
			monthSelected=0;
			yearSelected++;
		}
		constructCalendar();
	}

	function decMonth () 
	{
		monthSelected--;
		if (monthSelected<0) {
			monthSelected=11;
			yearSelected--;
		}
		constructCalendar();
	}

	function constructMonth() 
	{
		popDownYear();
		if (!monthConstructed) {
			sHTML =	""
			for	(i=0; i<12;	i++) {
				sName =	monthName[i];
				if (i==monthSelected){
					sName =	"<B>" +	sName +	"</B>"
				}
				sHTML += "<tr><td id='m" + i + "' onmouseover='this.style.backgroundColor=\"#FFCC99\"' onmouseout='this.style.backgroundColor=\"\"' style='cursor:pointer' onclick='monthConstructed=false;monthSelected=" + i + ";constructCalendar();popDownMonth();event.cancelBubble=true'>&nbsp;" + sName + "&nbsp;</td></tr>"
			}

			document.getElementById("selectMonth").innerHTML = "<table width=70	style='font-family:arial; font-size:11px; border-width:1px; border-style:solid; border-color:#a0a0a0;' bgcolor='#FFFFDD' cellspacing=0 onmouseover='clearTimeout(timeoutID1)'	onmouseout='clearTimeout(timeoutID1);timeoutID1=setTimeout(\"popDownMonth()\",100);event.cancelBubble=true'>" +	sHTML +	"</table>"

			monthConstructed=true
		}
	}

	function popUpMonth() 
	{
		constructMonth();
		crossMonthObj.visibility = "visible";
		crossMonthObj.left = parseInt(crossobj.left) + 50 + "px";
		crossMonthObj.top =	parseInt(crossobj.top) + 26 + "px";
	}

	function popDownMonth()	
	{
		crossMonthObj.visibility= "hidden";
	}

	/*** Year Pulldown ***/

	function incYear() 
	{
		for	(i=0; i<7; i++){
			newYear	= (i+nStartingYear)+1
			if (newYear==yearSelected)
			{ txtYear =	"&nbsp;<B>"	+ newYear +	"</B>&nbsp;" }
			else
			{ txtYear =	"&nbsp;" + newYear + "&nbsp;" }
			document.getElementById("y"+i).innerHTML = txtYear;
		}
		nStartingYear ++;
		bShow=true
	}

	function decYear() 
	{
<%
		//We only want the calendar to go down to the year 1000 AD.  This is for two resons;One being that we wanted to follow the
        // model that exists within the IDE, the second being that when Phil parses expressions based on date, he can assume the dates 
        // are AD (and there should be no reason would want to enter in a BC date)
%>
        if(nStartingYear > 1000)
        {
            for	(i=0; i<7; i++){
                newYear	= (i+nStartingYear)-1
                if (newYear==yearSelected)
                { txtYear =	"&nbsp;<B>"	+ newYear +	"</B>&nbsp;" }
                else
                { txtYear =	"&nbsp;" + newYear + "&nbsp;" }
                document.getElementById("y"+i).innerHTML = txtYear;
            }
            nStartingYear --;
        }
        bShow=true
	}

	function selectYear(nYear)
	{
		yearSelected=parseInt(nYear+nStartingYear);
		yearConstructed=false;
		constructCalendar();
		popDownYear();
	}

	function constructYear() 
	{
		popDownMonth();
		sHTML =	""
		if (!yearConstructed) {

            sHTML =	"<tr><td align='center'	onmouseover='this.style.backgroundColor=\"#FFCC99\"' onmouseout='clearInterval(intervalID1);this.style.backgroundColor=\"\"' style='cursor:pointer'	onmousedown='clearInterval(intervalID1);intervalID1=setInterval(\"decYear()\",30)' onmouseup='clearInterval(intervalID1)'>-</td></tr>"

			j =	0
			nStartingYear =	yearSelected-3
			for	(i=(yearSelected-3); i<=(yearSelected+3); i++) {
				sName =	i;
				if (i==yearSelected){
					sName =	"<B>" +	sName +	"</B>"
				}

				sHTML += "<tr><td id='y" + j + "' onmouseover='this.style.backgroundColor=\"#FFCC99\"' onmouseout='this.style.backgroundColor=\"\"' style='cursor:pointer' onclick='selectYear("+j+");event.cancelBubble=true'>&nbsp;" + sName + "&nbsp;</td></tr>"
				j ++;
			}

			sHTML += "<tr><td align='center' onmouseover='this.style.backgroundColor=\"#FFCC99\"' onmouseout='clearInterval(intervalID2);this.style.backgroundColor=\"\"' style='cursor:pointer' onmousedown='clearInterval(intervalID2);intervalID2=setInterval(\"incYear()\",30)'	onmouseup='clearInterval(intervalID2)'>+</td></tr>";

			document.getElementById("selectYear").innerHTML	= "<table width=44 style='font-family:arial; font-size:11px; border-width:1px; border-style:solid; border-color:#a0a0a0;'	bgcolor='#FFFFDD' onmouseover='clearTimeout(timeoutID2)' onmouseout='clearTimeout(timeoutID2);timeoutID2=setTimeout(\"popDownYear()\",100)' cellspacing=0>"	+ sHTML	+ "</table>";

			yearConstructed	= true
		}
	}

	function popDownYear() 
	{
		clearInterval(intervalID1)
		clearTimeout(timeoutID1)
		clearInterval(intervalID2)
		clearTimeout(timeoutID2)
		crossYearObj.visibility= "hidden"
	}

	function popUpYear() 
	{
		var	leftOffset

		constructYear()
		crossYearObj.visibility	= "visible";
		leftOffset = parseInt(crossobj.left) + document.getElementById("spanYear").offsetLeft  + 6;
		crossYearObj.left =	leftOffset + "px"
		crossYearObj.top = parseInt(crossobj.top) +	26 + "px"
	}

	/*** calendar ***/
   function WeekNbr(n) 
   {
<%
      // Algorithm used:
      // From Klaus Tondering's Calendar document (The Authority/Guru)
      // hhtp://www.tondering.dk/claus/calendar.html
      // a = (14-month) / 12
      // y = year + 4800 - a
      // m = month + 12a - 3
      // J = day + (153m + 2) / 5 + 365y + y / 4 - y / 100 + y / 400 - 32045
      // d4 = (J + 31741 - (J mod 7)) mod 146097 mod 36524 mod 1461
      // L = d4 / 1460
      // d1 = ((d4 - L) mod 365) + L
      // WeekNumber = d1 / 7 + 1
%>

      year = n.getFullYear();
      month = n.getMonth() + 1;
      if (startAt == 0) {
         day = n.getDate() + 1;
      }
      else {
         day = n.getDate();
      }
 
      a = Math.floor((14-month) / 12);
      y = year + 4800 - a;
      m = month + 12 * a - 3;
      b = Math.floor(y/4) - Math.floor(y/100) + Math.floor(y/400);
      J = day + Math.floor((153 * m + 2) / 5) + 365 * y + b - 32045;
      d4 = (((J + 31741 - (J % 7)) % 146097) % 36524) % 1461;
      L = Math.floor(d4 / 1460);
      d1 = ((d4 - L) % 365) + L;
      week = Math.floor(d1/7) + 1;
 
      return week;
   }

	function constructCalendar () 
	{
		var aNumDays = Array (31,0,31,30,31,30,31,31,30,31,30,31)

		var dateMessage
		var	startDate =	new	Date (yearSelected,monthSelected,1)
		var endDate

		if (monthSelected==1)
		{
			endDate	= new Date (yearSelected,monthSelected+1,1);
			endDate	= new Date (endDate	- (24*60*60*1000));
			numDaysInMonth = endDate.getDate()
		}
		else
		{
			numDaysInMonth = aNumDays[monthSelected];
		}

		datePointer	= 0
		dayPointer = startDate.getDay() - startAt
		
		if (dayPointer<0)
		{
			dayPointer = 6
		}

		sHTML =	"<table	 border=0 style='font-family:arial;font-size:11px;'><tr>"

		if (showWeekNumber==1)
		{
			sHTML += "<td width=27><b>" + weekString + "</b></td><td width=1 rowspan=7 bgcolor='#d0d0d0' style='padding:0px'><img SRC='"+imgSrc_divider+"' width=1></td>"
		}

		for	(i=0; i<7; i++)	{
			sHTML += "<td width='50' align='right'><B>"+ dayName[i]+"</B></td>"
		}
		sHTML +="</tr><tr>"
		
		if (showWeekNumber==1)
		{
			sHTML += "<td align=right>" + WeekNbr(startDate) + "&nbsp;</td>"
		}

		for	( var i=1; i<=dayPointer;i++ )
		{
			sHTML += "<td>&nbsp;</td>"
		}
	
		for	( datePointer=1; datePointer<=numDaysInMonth; datePointer++ )
		{
			dayPointer++;
			sHTML += "<td align=right>"
			sStyle=styleAnchor
			if ((datePointer==odateSelected) &&	(monthSelected==omonthSelected)	&& (yearSelected==oyearSelected))
			{ sStyle+=styleLightBorder }

			
			sHint = ""
			
			dateMessage = "onmousemove='window.status=\""+selectDateMessage.replace("[date]",constructDate(datePointer,monthSelected,yearSelected))+"\"' onmouseout='window.status=\"\"' "

			if ((datePointer==dateNow)&&(monthSelected==monthNow)&&(yearSelected==yearNow))
			{ sHTML += "<b><a "+dateMessage+" title=\"" + sHint + "\" style='"+sStyle+"' href='javascript:dateSelected="+datePointer+";closeCalendar();'><font color=#ff0000>&nbsp;" + datePointer + "</font>&nbsp;</a></b>"}
			else if	(dayPointer % 7 == (startAt * -1)+1)
			{ sHTML += "<a "+dateMessage+" title=\"" + sHint + "\" style='"+sStyle+"' href='javascript:dateSelected="+datePointer + ";closeCalendar();'>&nbsp;<font color=#909090>" + datePointer + "</font>&nbsp;</a>" }
			else
			{ sHTML += "<a "+dateMessage+" title=\"" + sHint + "\" style='"+sStyle+"' href='javascript:dateSelected="+datePointer + ";closeCalendar();'>&nbsp;" + datePointer + "&nbsp;</a>" }

			sHTML += ""
			if ((dayPointer+startAt) % 7 == startAt) { 
				sHTML += "</tr><tr>" 
				if ((showWeekNumber==1)&&(datePointer<numDaysInMonth))
				{
					sHTML += "<td align=right>" + (WeekNbr(new Date(yearSelected,monthSelected,datePointer+1))) + "&nbsp;</td>"
				}
			}
		}

		document.getElementById("calendarcontent").innerHTML   = sHTML
		document.getElementById("spanMonth").innerHTML = "&nbsp;" +	monthName[monthSelected] + "&nbsp;<IMG id='changeMonth' SRC='"+imgSrc_drop1+"' WIDTH='12' HEIGHT='10' BORDER=0>"
		document.getElementById("spanYear").innerHTML =	"&nbsp;" + yearSelected	+ "&nbsp;<IMG id='changeYear' SRC='"+imgSrc_drop1+"' WIDTH='12' HEIGHT='10' BORDER=0>"
	}

	function popUpCalendar(ctl,	ctl2, showTimeSpinner) 
	{
		var	leftpos=0;
		var	toppos=0;
		
		var sTop;
		var sLeft;
        if (document.documentElement.scrollTop)
        {
	  		sTop = document.documentElement.scrollTop;
		    sLeft = document.documentElement.scrollLeft;
        } else
        {
	  		sTop = document.body.scrollTop;
		    sLeft = document.body.scrollLeft;
        }

		if ( crossobj.visibility ==	"hidden" ) 
        {
			ctlToPlaceValue	= ctl2
			

			formatChar = " "
			aFormat	= dateFormat.split(formatChar)
			if (aFormat.length<3)
			{
				formatChar = "/"
				aFormat	= dateFormat.split(formatChar)
				if (aFormat.length<3)
				{
					formatChar = "."
					aFormat	= dateFormat.split(formatChar)
					if (aFormat.length<3)
					{
						formatChar = "-"
						aFormat	= dateFormat.split(formatChar)
						if (aFormat.length<3)
						{
<%
							// invalid date	format
%>
							formatChar=""
						}
					}
				}
			}

            var userTime = "";
<%
            // use user's date
%>
            if(ctl2.type == "text")
            {
                userTime =	ctl2.value;
            }
            else
            {
                userTime =	ctl2.innerHTML;
            }
            tokensChanged =	0
			if ( formatChar	!= "" )
			{

                aData = userTime.split(formatChar);

				for	(i=0;i<3;i++)
				{
					if ((aFormat[i]=="d") || (aFormat[i]=="dd"))
					{
						dateSelected = parseInt(aData[i], 10)
						tokensChanged ++
					}
					else if	((aFormat[i]=="MM") || (aFormat[i]=="MMM"))
					{
						monthSelected =	parseInt(aData[i], 10) - 1
						tokensChanged ++
					}
					else if	(aFormat[i]=="yyyy")
					{
						yearSelected = parseInt(aData[i], 10)
						yearSelected = yearSelected+1911;/////////////////////////////////////////
						tokensChanged ++
					}
					else if	(aFormat[i]=="MMMM")
					{
						for	(j=0; j<12;	j++)
						{
							if (aData[i]==monthName[j])
							{
								monthSelected=j
								tokensChanged ++
							}
						}
					}
				}
			}

			if ((tokensChanged!=3)||isNaN(dateSelected)||isNaN(monthSelected)||isNaN(yearSelected))
			{
				dateSelected = dateNow
				monthSelected =	monthNow
				yearSelected = yearNow
			}

			odateSelected=dateSelected
			omonthSelected=monthSelected
			oyearSelected=yearSelected

			aTag = ctl
			do {
				aTag = aTag.offsetParent;
				leftpos	+= aTag.offsetLeft;
				toppos += aTag.offsetTop;
			} while(aTag.tagName!="BODY");

			crossobj.left =	fixedX==-1 ? ctl.offsetLeft	+ leftpos + "px" :	fixedX + "px";
			crossobj.top = fixedY==-1 ?	ctl.offsetTop +	toppos + ctl.offsetHeight +	2 + "px" :	fixedY + "px";
			constructCalendar (1, monthSelected, yearSelected);
			crossobj.visibility="visible";

			bShow = true;

            if (showToday==1)
            {
                if(showTimeSpinner == true)
                {
                    newInnerHTML = "";
                    newInnerHTML    += "<TABLE>";
                    newInnerHTML    += "<TR>";
                    newInnerHTML    += "<TD nowrap>";
                    newInnerHTML    += "<SPAN ID='"+ctl2.id+"TimeSpinner' style='white-space: nowrap;'>";
                    //newInnerHTML    += userTime;
                    newInnerHTML    += "</SPAN>";
                    newInnerHTML    += "</TD>";
                    newInnerHTML    += "<TD>";
                    newInnerHTML    += "<A HREF='javascript:closeCalendar()'>";
                    newInnerHTML    += "<IMG NAME='calendarDone' SRC='"+imgSrc_ok+"'  ALT='"+"<fmt:message key='calendar_done'/>"+"' BORDER='0' HSPACE='4' VSPACE='4'>";
                    newInnerHTML    += "</A>";
                    newInnerHTML    += "</TD>";
                    newInnerHTML    += "</TR>";
                    newInnerHTML    += "</TABLE>";

                    document.getElementById("lblToday").innerHTML = newInnerHTML;
                    initTimeSpinner(ctl2.id+"TimeSpinner", true);
                }
                else
                {
                    document.getElementById("lblToday").innerHTML =	todayString + " <a onmousemove='window.status=\""+gotoString+"\"' onmouseout='window.status=\"\"' title='"+gotoString+"' style='"+styleAnchor+"' href='javascript:monthSelected=monthNow;yearSelected=yearNow;constructCalendar();'>"+dayName[(today.getDay()-startAt==-1)?6:(today.getDay()-startAt)]+", " + dateNow + " " + monthName[monthNow].substring(0,3)	+ "	" +	yearNow	+ "</a>"
                }
            }
            var timeoutText = "toScroll(" + sTop + "," + sLeft + ");";
            setTimeout(timeoutText, 24);
        }
	}

	document.onclick = function hidecal2 (event)
	{
		if (!bShow)
		{
			hideCalendar()
		}
		bShow = false
	}

	function toScroll(top, left)
	{
	   window.scrollTo(left, top);
	}

    init();
</script>
</fmt:bundle>
