/*
 * script/login.js 编码为utf-8
 * 
 * JavaScript里面的检验代码不是安全可靠的代码,只是提高用户体验,服务器的检验机制才是安全的
 * 应为可以在控制台中发请求用ajax,绕过JavaScript代码的检验机制,
 * 
 */
/**
 * 面板颜色数组
 */
let color = ["panel-primary","panel-default","panel-success","panel-info","panel-warning","panel-danger"];

$(function (){
	//显示用户名于右上角
	showUserName();
	//加载活动
	loadActivities();
	
	//绑定点击事件
	$("#action_part").on("click",".activity",openDetailActivity)
});

/** 点击打开详细活动界面 */
function openDetailActivity(){
	let id = $(this).data("activityId");
	location.href = "activity_detail.html?id="+id;
}


/**
 * 加载所有活动
 * @returns
 */
function loadActivities(){
	$.get("activity/list.do",{},function(result){
		let activities = result.data;
		showActivities(activities);
	});
}

/**
 * 展示所有的笔记到界面上
 * @param activities
 * @returns
 */
function showActivities(activities){
	$("#col_0").empty();
	$("#col_1").empty();
	$("#col_2").empty();
	for(let i=0;i<activities.length;i++){
		template = activityTemplate.replace("[color]",color[Math.floor(Math.random()*color.length)]);
		template = template.replace("[title]",activities[i].title);
		template = template.replace("[content]",activities[i].body);
		template = template.replace("[time]",getDate(activities[i].deadline));
		let temp = $(template);
		//绑定数据
		temp.data("activityId",activities[i].id);
		
		let div = "#col_"+i%3;
		$(div).append(temp);
	}
}

/**
 * 将时间戳变成时间格式
 * @param str
 * @returns
 */
function getDate(str){  
   var oDate = new Date(str),  
   oYear = oDate.getFullYear(),  
   oMonth = oDate.getMonth()+1,  
   oDay = oDate.getDate(),  
   oHour = oDate.getHours(),  
   oMin = oDate.getMinutes(),  
   oSen = oDate.getSeconds(),  
   //oTime = oYear +'-'+ getzf(oMonth) +'-'+ getzf(oDay) +' '+ getzf(oHour) +':'+ getzf(oMin) +':'+getzf(oSen);//最后拼接时间  
   oTime = oYear +'-'+ getzf(oMonth) +'-'+ getzf(oDay);
   return oTime;  
}; 
//补0操作
function getzf(num){  
	 if(parseInt(num) < 10){  
	     num = '0'+num;  
	 }  
	 return num;  
}


/**
 * 显示用户名于右上角
 * @returns
 */
function showUserName(){
	let name = getCookie("name");
	$("#display_user_name").text(name);
}


//+++++++++++++++++++++++++++++html模板+++++++++++++++++++++++

let activityTemplate = '<div class="panel [color] activity">'
	  						+'<div class="panel-heading">[title]</div>'
	  						+'<div class="panel-body">'
	  							+'[content]'
	  						+'</div>'
	  						+'deadline:&nbsp;&nbsp;<span class="deadline" style="color: red">[time]</span>'
	  					+'</div>';













