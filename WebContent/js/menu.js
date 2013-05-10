var autoMenuOffTimer;
var activeTab = -1;

function hideAllBars(){
    var i = 0;
	while (true)
	{
		bar=document.getElementById('bar'+i);
		if(bar){
			bar.style.display = 'none';
		}else{
			if(i>30){
				break;
			}
		}
		i++;
	}
}

function showBar(idx)
{
	var bar;

	hideAllBars()
	bar = document.getElementById('bar'+idx);
	if(bar)
	{
		bar.style.display = '';
	}
}


function do_tab_on(idx)
{
	window.clearTimeout(autoMenuOffTimer);
	//

	if(activeTab != -1) {
		do_tab_off(activeTab);
	}
	// showBar(activeTab);

	var o;
	
	o = document.getElementById('tab'+'_l_'+idx); 	
	o.src = 'images/menu/tab_l_on.gif';

	o = document.getElementById('tab'+'_r_'+idx);
	o.src = 'images/menu/tab_r_on.gif';

	o = document.getElementById('tab'+'_m_'+idx);
	o.className = 'menu_on';

	o = document.getElementById('tab'+'_a_'+idx);
	o.className = 'menu_on_a';

	activeTab = idx;

	showBar(idx);
}


function do_tab_off(idx)
{
	var o;

	if (clickTab != idx)
	{

	o = document.getElementById('tab'+'_l_'+idx);
	o.src = 'images/menu/tab_l_off.gif';

	o = document.getElementById('tab'+'_r_'+idx);
	o.src = 'images/menu/tab_r_off.gif';

	o = document.getElementById('tab'+'_m_'+idx);
	o.className = 'menu_off';

	o = document.getElementById('tab'+'_a_'+idx);
	o.className = 'menu_off_a';

	}

	hideAllBars();
}




function reset_menu(){
	autoMenuOffTimer = setTimeout('_restore_menu()',100);
}

function _restore_menu(){
	if(activeTab != -1) {
		do_tab_off(activeTab);
	}

	if (clickTab != -1){
		do_tab_on(clickTab);
	}
	
	if(clickBar != -1){
		document.getElementById('bar_a_'+clickTab+'_'+clickBar).className='subMenu_on_a';
	}
}
reset_menu();
