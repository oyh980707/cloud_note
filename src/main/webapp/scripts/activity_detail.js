/*
 * script/login.js 编码为utf-8
 * 
 * JavaScript里面的检验代码不是安全可靠的代码,只是提高用户体验,服务器的检验机制才是安全的
 * 应为可以在控制台中发请求用ajax,绕过JavaScript代码的检验机制,
 * 
 */
/** 是否赞/踩 */
let isCheck = false;

$(function (){
	//显示用户名于右上角
	showUserName();
	
	//加载改活动的所有笔记
	loadActivityNotes();
	
	//绑定笔记列表区域的点击事件
	$("#action_note_list").on("click",".activity_note",loadNote);
	
	//绑定笔记赞点击事件
	$("#action_note_list").on("click",".btn_up",up);
	//绑定笔记踩点击事件
	$("#action_note_list").on("click",".btn_down",down);
	//绑定笔记踩点击事件
	$("#action_note_list").on("click",".btn_like",collect);
	
	//关闭弹窗绑定事件
	$("#can").on("click",".close,.cancel",closeDialog);
});

/** 收藏笔记 */
function collect(){
	let btn = $(this);
	let id = $(document).data("activityId");
	let noteId = btn.parent().parent().data("noteId");
	let userId = getCookie("userId");
	$.post("activity/collect.do",{activityId:id,noteId:noteId,userId:userId},function(result){
		if(result.state==0){
			tip("收藏成功!");
		}else{
			alert(result.message);
		}
	});
}

/** 笔记赞事件处理 */
function up(){
	//如果赞/踩过，则不进行操作
	if(isCheck) return ;
	let btn = $(this);
	let noteId = btn.parent().parent().data("noteId");
	let activityId = $(document).data("activityId");
	$.post("activity/noteUp.do",{noteId:noteId,activityId:activityId},function(result){
		if(result.state==0){
			let note = result.data;
			btn.html('<i class="fa fa-thumbs-o-up"></i>'+note.up);
			tip("赞成功!");
			
			isCheck = true;
		}else{
			alert(result.message);
		}
	})
}

/** 笔记踩事件处理 */
function down(){
	//如果赞/踩过，则不进行操作
	if(isCheck) return ;
	let btn = $(this);
	let noteId = btn.parent().parent().data("noteId");
	let activityId = $(document).data("activityId");
	$.post("activity/noteDown.do",{noteId:noteId,activityId:activityId},function(result){
		if(result.state==0){
			let note = result.data;
			btn.html('<i class="fa fa-thumbs-o-down"></i>'+note.down);
			tip("踩成功!");
			
			isCheck = true;
		}else{
			alert(result.message);
		}
	})
}

/** 加载笔记 */
function loadNote(){
	var li = $(this);
	//在被点击的笔记li增加选定效果
	li.parent().find("a").removeClass("checked");
	li.find("a").addClass("checked");
	
	var url = "note/load.do";
	var data = {noteId:li.data("noteId")};
	$.getJSON(url,data,function(result){
		if(result.state==0){
			var note = result.data;
			showNote(note);
		}else{
			alert(result.message);
		}
	});
}

/** 渲染笔记的全部内容 */
function showNote(note){
	//清空body内的内容
	$('#content_body').empty();
	//显示笔记标题和内容
    $('#content_body').append("<h4><strong>笔记标题: </strong>"+note.title+"</h4>"+note.body);
}

/** 加载活动下的所有笔记 */
function loadActivityNotes(){
	let activityId = getUrlParam("id");
	//将id保存到document上
	$(document).data("activityId",activityId);
	
	$.get("activity/detail.do",{activityId:activityId},function(result){
		let notes = result.data;
		showNotes("#action_note_list",notes);
	});
}

/** 将笔记列表信息显示到区域上 */
function showNotes(location, notes){
	let id = location+" .contacts-list";
	var ul = $(id);
	ul.empty();
	for(let i=0;i<notes.length;i++){
		let note = notes[i];
		let template = activityNoteTemplate.replace(new RegExp("\\[title\\]","g"),note.title);
		template = template.replace("[up]",note.up);
		template = template.replace("[down]",note.down);
		
		let li = $(template);
		li.data("noteId",note.noteId);
		ul.append(li);
	}
}


//获取url中的参数  
function getUrlParam(name) {   
     var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象  
     var r = window.location.search.substr(1).match(reg);  //匹配目标参数   
     if (r != null) return unescape(r[2]); return null; //返回参数值  
} 

/**
 * 显示用户名于右上角
 * @returns
 */
function showUserName(){
	let name = getCookie("name");
	$("#display_user_name").text(name);
}

/** 关闭对话框 */
function closeDialog(){
	$(".opacity_bg").hide();
	$("#can").empty();
}

/**
 * 弹窗提示
 * @param message 提示信息
 * @param style 样式
 * @param time 时间
 * @returns
 */
function tip(message, style, time)
{
    style = (style === undefined) ? 'alert-success' : style;
    time = (time === undefined) ? 500 : time;
    $('<div>')
        .appendTo('body')
        .addClass('alert ' + style)
        .html(message)
        .show()
        .delay(time)
        .fadeOut();
};


//+++++++++++++++++++++++++++++html模板+++++++++++++++++++++++

let activityNoteTemplate = '<li class="activity_note">'
								+'<a >'
									+'<i class="fa fa-file-text-o" title="[title]" rel="tooltip-bottom"></i>[title]'
									+'<button type="button" class="btn btn-default btn-xs btn_position_3 btn_up">'
										+'<i class="fa fa-thumbs-o-up"></i>[up]'
									+'</button>'
									+'<button type="button" class="btn btn-default btn-xs btn_position_2 btn_down">'
										+'<i class="fa fa-thumbs-o-down"></i>[down]'
									+'</button>'
									+'<button type="button" class="btn btn-default btn-xs btn_position btn_like">'
										+'<i class="fa fa-star-o"></i>'
									+'</button>'
								+'</a>'
							+'</li>';












