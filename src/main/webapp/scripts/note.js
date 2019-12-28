// scripts/note.js 编码一定是utf-8

var SUCCESS =0;
var ERROR = 1;

$(function(){
//	var userId = getCookie("userId");
//	console.log(userId);
	//在document对象中初始化分页页数,存储分页页数
	$(document).data("notebookPage",0);
	$(document).data("notePage",0);
	
	//网页加载以后,立即读取笔记本列表
	loadNotebooks();
//	loadPagedNotebooks();
	
	//on() 方法绑定时间可以区别事件源
	//click() 方法绑定事件,无法区分事件源
	//绑定笔记本列表区域的点击事件
	$("#notebook-list").on("click",".notebook",loadNotes);
//	$("#notebook-list").on("click",".notebook",loadPagedNotes);
	//加载更多笔记本
//	$("#notebook-list").on("click",".more",loadPagedNotebooks);
	//加载更多的笔记
//	$("#note-list").on("click",".more",loadMoreNotes);
	//删除笔记本
	$("#notebook-list").on("click","#delete_notebook",deleteNotebook);
	
	
	//添加笔记本列表的点击事件
	$("#notebook-list").on("click","#add_notebook",showAddNotebookDialog);
	$("#create-menu").on("click",".add-notebook",showAddNotebookDialog);
	//创建笔记本绑定事件
	$("#can").on("click",".create-notebook",addNotebook);
	
	//绑定笔记列表区域的点击事件
	$("#note-list").on("click",".note",loadNote);
	//添加笔记绑定事件
	$("#note-list").on("click","#add_note",showAddNoteDialog);
	$("#create-menu").on("click",".add-note",showAddNoteDialog);
	//创建笔记绑定事件
	$("#can").on("click",".create-note",addNote);
	//取消创建绑定事件
	$("#can").on("click",".close,.cancel",closeDialog);
	//点击保存按钮绑定事件
	$('#save_note').on('click', updateNote);
	
	//绑定笔记子菜单的触发时间
	$("#note-list").on("click",".btn-note-menu",showNoteMenu);
	//绑定document点击事件,用于点击其他位置收起笔记菜单
	$(document).click(hideNoteMenu);
	
	//点击菜单,然后弹出菜单
	$("#note-list").on("click",".btn_move",showMoveNoteDialog);
	//监听移动笔记对话框中的确定按钮
	$("#can").on("click",".move-note",moveNote);
	
	//监听笔记中的删除按钮点击弹框事件
	$("#note-list").on("click",".btn_delete",showDeleteNoteDialog);
	//监听笔记删除点击按钮确认事件
	$("#can").on("click",".delete-note",deleteNote);
	
	//监听回收站按钮
	$("#trash_button").click(showTrashBin);
	
	//恢复笔记到笔记本的监听事件
	$("#trash-bin").on("click",".btn_replay",showReplayDialog);
	//回收站点击选中笔记
	$("#trash-bin").on("click",".note",clickTrashBinNote);
	//恢复笔记的恢复按钮监听事件
	$("#can").on("click",".replay-note",replayNote);
	//回收站删除笔记或笔记本按钮监听事件
	$("#trash-bin").on("click",".btn_delete",showRemoveDialog);
	
	//回收站彻底删除按钮监听事件
	$("#can").on("click",".delete-trash-note",removeNote);
	$("#can").on("click",".delete-trash-notebook",removeNotebook);
	
	//心跳检测
	startHeartBeat();
	
	//浏览器调试过程调用方法
//	$("#add_notebook").click(demo);
});
/** 笔记本分页加载功能 */
function loadPagedNotebooks(){
	var page = $(document).data("notebookPage");
	var userId = getCookie("userId");
	//从服务器中获取数据
	var url = "notebook/page.do";
	var data = {"userId":userId,"page":page};
	$.getJSON(url,data,function(result){
		if(result.state==SUCCESS){
			var notebooks = result.data;
			showPagedNotebooks(notebooks,page);
			$(document).data("notebookPage",page+1);
		}else{
			alert(result.message);
		}
	});
}
/** 笔记本分页加载笔记本 */
function showPagedNotebooks(notebooks,page){
	var ul = $("#notebook-list ul");
	if(page==0){
		ul.empty();
	}else{
		//删除more
		ul.find(".more").remove();
	}
	
	for(var i=0;i<notebooks.length;i++){
		var notebook = notebooks[i];
		var li = notebookTemplate.replace("[name]",notebook.name);
		li = $(li);
		// 将 notebook.id 绑定到 li
		li.data("notebookId",notebook.id);
		
		ul.append(li);
	}
	//服务器分页查询的每页设置的为4,所以小于4则没有更多了
	if(notebooks.length<4){
		var li = $(moreNotebookTemplate.replace("[info]","没有更多了..."));
		li.find("i").remove();
		ul.append(li);
		return ;
	}
	ul.append(moreNotebookTemplate.replace("[info]","加载更多..."));
}

/** 心跳检测,防止session过期,检测用户是否在线 */
function startHeartBeat(){
	var url = "user/heart.do";
	setInterval(function(){
		$.getJSON(url,function(result){
			console.log(result.message);
		});
	}, 5000)
}

/** 彻底删除笔记 */
function removeNote(){
	var noteId = $(document).data("removeNoteId");
	var url = "note/remove.do";
	var data = {"noteId":noteId};
	$.post(url,data,function(result){
		if(result.state==SUCCESS){
			var li = $("#trash-bin .checked").parent();
			var lis = li.siblings();
			if(lis.size()>0){
				lis.eq(0).click();
			}
			li.slideUp(200, function(){$(this).remove()});
			closeDialog();
		}else{
			alert(result.message);
		}
	});
}

/** 彻底删除笔记本 */
function removeNotebook(){
	var notebookId = $(document).data("removeNotebookId");
//	console.log("notebookId:",notebookId);
	var url = "notebook/remove.do";
	var data = {"notebookId":notebookId};
	$.post(url,data,function(result){
		if(result.state==SUCCESS){
			var li = $("#trash-bin .checked").parent();
			var lis = li.siblings();
			if(lis.size()>0){
				lis.eq(0).click();
			}
			li.slideUp(200, function(){$(this).remove()});
			closeDialog();
		}else{
			alert(result.message);
		}
	});
}

/** 删除回收站的笔记或笔记本,即彻底删除 */
function showRemoveDialog(){
	var noteId = $(this).parent().parent().data("noteId");
//	console.log("noteId:",noteId);
	var notebookId = $(this).parent().parent().data("notebookId");
//	console.log("notebookId:",notebookId);
	if(noteId != null){
		$(document).data("removeNoteId",noteId);
		//选中点击的该元素
		$(this).parent().parent().parent().find("a").removeClass("checked");
		$(this).parent().addClass("checked");
		if(noteId){
			$("#can").load("alert/alert_delete_rollback.html",function(){
				$(".opacity_bg").show();
			});
			return;
		}
	}
	
	if(notebookId != null){
		$(document).data("removeNotebookId",notebookId);
		//选中点击的该元素
		$(this).parent().parent().parent().find("a").removeClass("checked");
		$(this).parent().addClass("checked");
		if(notebookId){
			$("#can").load("alert/alert_delete_notebook.html",function(){
				$(".opacity_bg").show();
			});
			return;
		}
	}
	alert("请选择笔记或笔记本!");
	return ;
}

/** 恢复删除的笔记 */
function replayNote(){
	var url = "note/replay.do";
	var noteId = $(document).data("repalyNoteId");
	var selectedId = $("#replaySelect").val();
	var data = {"noteId":noteId,"notebookId":selectedId};
	$.post(url,data,function(result){
		if(result.state==SUCCESS){
			var li = $("#trash-bin .checked").parent();
			var lis = li.siblings();
			if(lis.size()>0){
				lis.eq(0).click();
			}
			li.slideUp(200, function(){$(this).remove()});
			closeDialog();
		}else{
			alert("恢复失败!");
		}
	});
}

/** 点击选中 */
function clickTrashBinNote(){
	var id = $(this).data("noteId");
	$(document).data("repalyNoteId",id);
	$(this).parent().find("a").removeClass("checked");
	$(this).children("a").addClass("checked");
}

/** 显示恢复笔记的对话框 */
function showReplayDialog(){
	var noteId = $(this).parent().parent().data("noteId");
//	console.log("noteId:",noteId);
	var notebookId = $(this).parent().parent().data("notebookId");
//	console.log("notebookId:",notebookId);
	if(noteId != null){
		$(document).data("repalyNoteId",noteId);
		//选中点击的该元素
		$(this).parent().parent().parent().find("a").removeClass("checked");
		$(this).parent().addClass("checked");
		if(noteId){
			$("#can").load("alert/alert_replay.html",loadReplayOpations);
			return;
		}
	}
	
	if(notebookId != null){
		$(document).data("repalyNotebookId",notebookId);
		//选中点击的该元素
		$(this).parent().parent().parent().find("a").removeClass("checked");
		$(this).parent().addClass("checked");
		if(notebookId){
			//恢复笔记本
			replayNotebook(notebookId);
			return ;
		}
	}
	alert("请选择笔记或笔记本!");
	return ;
}

/** 恢复笔记本 */
function replayNotebook(notebookId){
	var url = "notebook/replay.do";
	//不用考虑用户，因为每个笔记本自动绑定了用户，恢复不像笔记可以恢复到
	//不用的笔记本中，这里直接恢复就可以了，不用考虑恢复到哪个用户
	console.log(notebookId);
	var data = {"notebookId":notebookId};
	
	$.post(url,data,function(result){
		if(result.state==SUCCESS){
			var li = $("#trash-bin .checked").parent();
			var notebookId = li.data("notebookId");
			var name = li.data("name");
			
			var lis = li.siblings();
			if(lis.size()>0){
				lis.eq(0).click();
			}
			li.slideUp(200, function(){$(this).remove()});
			
			var newLi = notebookTemplate.replace("[name]",name);
			newLi = $(newLi);
			// 将 notebookId 绑定到 li
			newLi.data("notebookId",notebookId);
			
			$("#notebook_list").prepend(newLi);
			
			newLi.click();
			
		}else{
			alert("恢复失败!");
		}
	});
}

/** 加载撤销笔记对话框中的笔记本列表 */
function loadReplayOpations(){
	//让背景编程灰色
	$(".opacity_bg").show();
	
	var url = "notebook/list.do";
	var data = {"userId":getCookie("userId")};
	$.getJSON(url,data,function(result){
		if(result.state==SUCCESS){
			var notebooks = result.data;
			//清楚全部的笔记本下拉列表选项
			//添加新的笔记本列表选项
			$("#replaySelect").empty();
			for(var i=0;i<notebooks.length;i++){
				var notebook = notebooks[i];
				var opt = $("<option></option>").val(notebook.id).html(notebook.name);
				$("#replaySelect").append(opt);
			}
		}else{
			alert(result.message);
		}
	});
}

/** 监听回收站按钮被点击 */
function showTrashBin(){
	var btn = $(this);
	//切换回收站笔记和未被删除的笔记
	$('#trash-bin').show();
	$('#note-list').hide();
	//删除被选定的笔记本的样式,找到相邻上一个元素里的a元素的checked将其删除
	btn.parent().prev().find("a").removeClass("checked");
	
	//删除编辑框中的内容
	$("#input_note_title").val("");
	um.setContent("");
	
	//ajax异步请求抓取相关信息
	loadTrashBin();
}
/** 加载回收站的笔记列表 */
function loadTrashBin(){
	var ul = $("#trash-bin ul")
	ul.empty();

	new Promise(function(resolve){
		showNotebooksInTrashBin(ul);
		resolve();
	}).then(function(){
		showNotesInTrashBin(ul);
	});
}
/** 展示回收站里的笔记本列表 */
function showNotebooksInTrashBin(ul){
	var url = "notebook/list.do";
	var data = {"userId":getCookie("userId"),"type":"2"};
	$.getJSON(url,data,function(result){
		if(result.state==SUCCESS){
			var notebooks = result.data;
			for(var i=0;i<notebooks.length;i++){
				var notebook = notebooks[i];
				var li = trashBinNotebookItem.replace("[name]",notebook.name);
				li = $(li);
				li.data("notebookId",notebook.id);
				li.data("name",notebook.name);
				ul.append(li);
			}
		}else{
			alert(result.message)
		}
	});

}
/** 展示回收站里的笔记列表 */
function showNotesInTrashBin(ul){
	var url = "note/trash.do";
	var data = {"userId":getCookie("userId"),"statusId":"0"};
	$.getJSON(url,data,function(result){
		if(result.state==SUCCESS){
			var notes = result.data
			for(var i=0;i<notes.length;i++){
				var note = notes[i];
				var li = trashBinNoteItem.replace("[title]",note.title);
				li = $(li);
				li.data("noteId",note.id);
				ul.append(li);
			}
		}else{
			alert(result.message)
		}
	});
}
/** 删除当前的笔记 */
function deleteNote(){
	var url = "note/delete.do";
	var noteId = $(document).data("note").id;
	if(!noteId){
		return ;
	}
	var data = {"noteId":noteId};
	$.post(url,data,function(result){
		if(result.state==SUCCESS){
			//删除成功, 在当前笔记列表中删除笔记
			//将笔记列表中的第一个设置为当前笔记, 否则清空边编辑区域
			var li = $("#note-list .checked").parent();
			var lis = li.siblings();
			if(lis.size()>0){
				lis.eq(0).click();
			}else{
				$("#intpu_note_title").val("");
				um.setContent("");
			}
			li.slideUp(200, function(){$(this).remove()});
			closeDialog();
		}else{
			alert("移动失败!");
		}
	});
}

/** 删除当前的笔记本 */
function deleteNotebook(e){
	var url = "notebook/delete.do";
	var notebookId = $($(e.target).parent()).data("notebookId");
	if(!notebookId){
		return false;
	}
	var data = {"notebookId":notebookId};
//	console.log(data);
	$.post(url,data,function(result){
		if(result.state==SUCCESS){
			//删除成功, 在当前笔记列表中删除笔记
			//将笔记列表中的第一个设置为当前笔记, 否则清空边编辑区域
			var li = $($(e.target).parent());
			var lis = li.siblings();
			if(lis.size()>0){
				lis.eq(0).click();
			}
			//没有笔记本时将笔记区刷新空
//			console.log("lis.size():",lis.size())
			if(lis.size()==0){
				$("#note-list ul").empty();
			}
			li.slideUp(200, function(){$(this).remove()});
		}else{
			alert("删除失败!");
		}
	});
}


/** 打开删除笔记对话框 */
function showDeleteNoteDialog(){
	//获得当前选定的元素的笔记的id
	var id = $(document).data("note").id;
	if(id){
		$("#can").load("alert/alert_delete_note.html");
		return;
	}
	alert("请选择笔记!");
	return ;
}

/** 移动笔记 */
function moveNote(){
	console.log(1111);
	var url = "note/move.do";
	var noteId = $(document).data("note").id;
	var notebookId = $(document).data("notebookId");
	var selectedId = $("#moveSelect").val();
	if(selectedId == notebookId){
		return ;
	}
	var data = {"noteId":noteId,"notebookId":selectedId};
	$.post(url,data,function(result){
		if(result.state==SUCCESS){
			//移动成功, 在当前笔记列表中删除移动的笔记
			//将笔记列表中的第一个设置为当前笔记, 否则清空边编辑区域
			var li = $("#note-list .checked").parent();
			var lis = li.siblings();
			if(lis.size()>0){
				lis.eq(0).click();
			}else{
				$("#input_note_title").val("");
				um.setContent("");
			}
			li.slideUp(200, function(){$(this).remove()});
			closeDialog();
		}else{
			alert("移动失败!");
		}
	});
}

/** 点击显示移动对话框 */
function showMoveNoteDialog(){
	//获得当前选定的元素的笔记的id
	var id = $(document).data("note").id;
	if(id){
		$("#can").load("alert/alert_move.html",loadNotebookOptions);
		return;
	}
	alert("请选择笔记!");
	return ;
}
/** 加载移动笔记对话框中的笔记本列表 */
function loadNotebookOptions(){
	//让背景编程灰色
	$(".opacity_bg").show();
	
	var url = "notebook/list.do";
	var data = {"userId":getCookie("userId")};
	$.getJSON(url,data,function(result){
		if(result.state==SUCCESS){
			var notebooks = result.data;
//			//在showNotebooks方法中将全部的笔记本数据notebooks系那是到notebook-list区域
//			showNotebooks(notebooks);
//			console.log(notebooks);
			//清楚全部的笔记本下拉列表选项
			//添加新的笔记本列表选项
			$("#moveSelect").empty();
			var id = $(document).data("notebookId");
			for(var i=0;i<notebooks.length;i++){
				var notebook = notebooks[i];
				var opt = $("<option></option>").val(notebook.id).html(notebook.name);
				if(notebook.id == id){
					opt.attr("selected","selected");
				}
				$("#moveSelect").append(opt);
			}
		}else{
			alert(result.message);
		}
	});
}

/** 点击任何地方收起菜单 */
function hideNoteMenu(){
	$(".note_menu").hide();
//	$(".note_menu").not(".checked+.note_menu").hide();
}

/** 显示笔记菜单 */
function showNoteMenu(){
	//找到菜单对象,调用show()方法
//	$(this).parent().parent().parent().find("a").removeClass("checked");
//	$(this).parent().addClass("checked");
	var btn = $(this);
	//直接点击显示笔记菜单,加载一次笔记,以保证数据的存储的完整
	loadNote.call(btn.parent().parent());
//	$(".note_menu").not(btn).hide(function(){
	//如果当前是被选定的笔记项目,就弹出子菜单
		btn.parent(".checked").next().toggle();
//	btn.parent().next().toggle();
//	loadNote.call(btn.parent().parent());
//	});
	return false;//阻止事件的继续传播
}

/** 保存笔记实现 */
function updateNote(){
	
	var url = "note/update.do";
	var note = $(document).data("note");
	var modified = false;
	var data = {"noteId":note.id};
	var title = $("#input_note_title").val();
	var body = um.getContent();
	
	if(title && title!=note.title){
		data.title = title;
		modified = true;
	}
	if(body && body!=note.body){
		data.body = body;
		modified = true;
	}
	if(modified){
		$.post(url, data, function(result){

            if(result.state == SUCCESS){
                //console.log("Success!");
                //内存中的 note 改成新的数据
                note.title = title;
                note.body = body;
                var l = $('#note-list .checked').parent();
                $('#note-list .checked').remove()
                var li = noteTemplate.replace( '[title]', title);
                var a = $(li).find('a');
                a.addClass('checked');
                l.prepend(a);
            }else{
                alert(result.mesage);
            }
        });
	}
}

/** 关闭对话框 */
function closeDialog(){
	$(".opacity_bg").hide();
	$("#can").empty();
}
/** 创建笔记 */
function addNote(){
	var title = $("#can #input_note").val();
	var url = "note/add.do";
	var data = {
			"userId":getCookie("userId"),
			"notebookId": $("#add-to-notebook").val(),
			"title":title
	};
//	console.log(data);
	$.post(url,data,function(result){
		if(result.state==SUCCESS){
			var note = result.data;
			
			var notebookLis = $("#notebook_list").children();
			for(let i=0;i<notebookLis.length;i++){
				var li = $(notebookLis[i]);
//				console.log(li);
				if(li.data("notebookId") == note.notebookId){
					//这里执行顺序有问题，点击后不能立马把元素添加上来，所以
					//这里获得元素必须延迟一点。
					setTimeout(() => {
						console.log($("#note-list ul").children());
				    	$("#note-list ul").children().eq(0).click();
					},300);
					console.log(li);
					li.click();
				}
			}
			
//			//显示当前创建的note
//			showNote(note);
//			var ul = $("#note-list ul");
//			var li = noteTemplate.replace('[title]',note.title);
//			li = $(li);
//			// 将 note.id 绑定到 li
//			li.data("noteId",note.id);
//			
//			//在被点击的笔记本li增加选定效果
//			ul.find("a").removeClass("checked");
//			li.find("a").addClass("checked");
//			
//			ul.prepend(li);
			
			//关闭对话框
			closeDialog();
		}else{
			alert(result.message);
		}
	});
}
/** 添加笔记对话框 */
function showAddNoteDialog(){
	var id = $(document).data("notebookId");
	$("#can").load("alert/alert_note.html",function(){
		//让背景编程灰色
		$(".opacity_bg").show();
		//触发获得焦点事件
		$("#input_note").focus();
		
	});
	
	var url = "notebook/list.do";
	var data = {"userId":getCookie("userId"),"type":"5"};
	$.getJSON(url,data,function(result){
		if(result.state==SUCCESS){
			var notebooks = result.data;
			//清楚全部的笔记本下拉列表选项
			//添加新的笔记本列表选项
			$("#add-to-notebook").empty();
			if(notebooks.length < 1){
				var opt = $("<option></option>").val(notebook.id).html("您没有笔记本，请添加了再来！！！");
				$("#add-to-notebook").append(opt);
				return;
			}
			//遍历所有添加标签并找到选中的notebookId放到第一个，如果没有则默认第一个选中；
			for(var i=0;i<notebooks.length;i++){
				var notebook = notebooks[i];
				console.log(notebook.id == id);
				var opt = $("<option></option>").val(notebook.id).html(notebook.name);
				$("#add-to-notebook").append(opt);
				if(notebook.id == id){
					opt.attr("selected","selected");
				}
			}
		}else{
			alert(result.message);
		}
	});
}

/** 添加笔记本对话框 */
function showAddNotebookDialog(){
	$("#can").load("alert/alert_notebook.html",function(){
		//让背景编程灰色
		$(".opacity_bg").show();
		//触发获得焦点事件
		$("#input_notebook").focus();
	});
	return ;
}

/** 删除笔记本对话框 */
function showDeleteNotebookDialog(e){
//	console.log($(e.target).parent())
	$("#can").load("alert/alert_delete_notebook.html",function(){
		//让背景编程灰色
		$(".opacity_bg").show();
	});
	return false;
}

/** 创建笔记本 */
function addNotebook(){
	var name = $("#can #input_notebook").val();
	var url = "notebook/add.do";
	var data = {
			"userId":getCookie("userId"),
			"name":name
	};
//	console.log(data);
	$.post(url,data,function(result){
		if(result.state==SUCCESS){
			var notebook = result.data;
//			console.log(notebook);
			var ul = $("#notebook-list ul");
			var li = notebookTemplate.replace('[name]',notebook.name);
			li = $(li);
			// 将 notebook.id 绑定到 li
			li.data("notebookId",notebook.id);
			
			ul.prepend(li);
			
			//在被点击的笔记本li增加选定效果
			ul.find("a").removeClass("checked");
			li.find("a").addClass("checked");
			
			//关闭对话框
			closeDialog();
			//触发点击
			li.trigger('click');
		}else{
			alert(result.message);
		}
	});
}

/** 笔记项目点击事件处理方法,加载全部笔记的内容 */
function loadNote(){
//	console.log("hello");
	var li = $(this);
	//在被点击的笔记li增加选定效果
	li.parent().find("a").removeClass("checked");
	li.find("a").addClass("checked");
	
	var url = "note/load.do";
	var data = {noteId:li.data("noteId")};
	$.getJSON(url,data,function(result){
		if(result.state==SUCCESS){
			var note = result.data;
			showNote(note);
		}else{
			alert(result.message);
		}
	});
}
/** 渲染笔记的全部内容 */
function showNote(note){
	//显示笔记标题
    $('#input_note_title').val(note.title);
    //显示笔记内容
    um.setContent(note.body);
    
    //存笔记,用于保存操作
    $(document).data("note",note);
}

/** 笔记本项目点击事件处理方法,加载分页笔记 */
function loadPagedNotes(){
	
	var li = $(this);
	var page = $(document).data("notePage");
	//在被点击的笔记本li增加选定效果
	li.parent().find("a").removeClass("checked");
	li.find("a").addClass("checked");
	//关闭回收站 打开笔记本列表
	$("#trash-bin").hide();
	$("#note-list").show();
	//清除标题和内容
	$("#input_note_title").val("");
	um.setContent("");
	
	var url = "note/pageNote.do";
	var data = {"notebookId":li.data("notebookId"),"page":page};
	//绑定到document上,在点击笔记本时候用于验证是否选中笔记本
	$(document).data('notebookId', li.data('notebookId'));
	$.getJSON(url,data,function(result){
		if(result.state==SUCCESS){
			var notes = result.data;
			showPagedNotes(notes,page);
			$(document).data("notePage",page+1);
		}else{
			alert(result.message)
		}
	});
}
/** 笔记区域点击跟多事件处理方法,加载分页笔记 */
function loadMoreNotes(){
	var page = $(document).data("notePage");
	var url = "note/pageNote.do";
	var data = {"notebookId":$(document).data("notebookId"),"page":page};
	$.getJSON(url,data,function(result){
		if(result.state==SUCCESS){
			var notes = result.data;
			showPagedNotes(notes,page);
			$(document).data("notePage",page+1);
		}else{
			alert(result.message)
		}
	});
}
/** 分页加载笔记 */
function showPagedNotes(notes,page){
	//将每个笔记对象显示到屏幕的ul区域
	var ul = $("#note-list ul");
	if(page==0){
		ul.empty();
	}else{
		//删除more
		ul.find(".more").remove();
	}
	for(var i=0;i<notes.length;i++){
		var note = notes[i];
		var li = noteTemplate.replace('[title]',note.title);
		li = $(li);
		// 将 note.id 绑定到 li
		li.data("noteId",note.id);
		
		ul.append(li);
	}
	//服务器分页查询的每页设置的为4,所以小于4则没有更多了
	if(notes.length<4){
		var li = $(moreNoteTemplate.replace("[info]","没有更多了..."));
		li.find("i").remove();
		ul.append(li);
		return ;
	}
	ul.append(moreNoteTemplate.replace("[info]","加载更多..."));
}
/** 笔记本项目点击事件处理方法,加载全部笔记 */
function loadNotes(){
	//关闭回收站 打开笔记本列表
	$("#trash-bin").hide();
	$("#note-list").show();
	//清除标题和内容
	$("#input_note_title").val("");
	um.setContent("");
	
	var li = $(this);//当前被点击的对象li
	
	//在被点击的笔记本li增加选定效果
	li.parent().find("a").removeClass("checked");
	li.find("a").addClass("checked");
	
	var url = "note/list.do";
	var data = {notebookId:li.data("notebookId")};
	//绑定到document上,在点击笔记本时候用于验证是否选中笔记本
	$(document).data('notebookId', li.data('notebookId'));
//	console.log(data);
	$.getJSON(url,data,function(result){
		if(result.state==SUCCESS){
			var notes = result.data;
//			console.log(notes);
			//判断是否有笔记
			showNotes(notes);
		}else{
			alert(result.message)
		}
	});
}
/** 将笔记列表信息显示到屏幕上 */
function showNotes(notes){
//	console.log(notes);
	//将每个笔记对象显示到屏幕的ul区域
	var ul = $("#note-list ul");
	ul.empty();
	for(var i=0;i<notes.length;i++){
		var note = notes[i];
		var li = noteTemplate.replace('[title]',note.title);
		li = $(li);
		// 将 note.id 绑定到 li
		li.data("noteId",note.id);
		
		ul.append(li);
	}
}


/** 加载笔记本列表 */
function loadNotebooks(){
	//利用ajax从服务器获取(get)数据
	var url = "notebook/list.do";
	var data = {"userId":getCookie("userId"),"type":"5"};
	$.getJSON(url,data,function(result){
//		console.log(result);
		if(result.state==SUCCESS){
			var notebooks = result.data;
			//在showNotebooks方法中将全部的笔记本数据notebooks系那是到notebook-list区域
			showNotebooks(notebooks);
		}else{
			alert(result.message);
		}
	});
}
/*
 * 在notebook-list区域中显示笔记本列表
 */
function showNotebooks(notebooks){
	//找到显示笔记本列表的区域
	//遍历notebooks数组,将每个对象创建一个li元素,添加到ul元素中
	var ul = $("#notebook-list ul");
	ul.empty();
	for(var i=0;i<notebooks.length;i++){
		var notebook = notebooks[i];
		var li = notebookTemplate.replace("[name]",notebook.name);
		li = $(li);
		// 将 notebook.id 绑定到 li
		li.data("notebookId",notebook.id);
		
		ul.append(li);
	}
}


var notebookTemplate = '<li class="online notebook">'+
							'<a><i class="fa fa-book" title="online" rel="tooltip-bottom"></i>[name]</a>'+
							'<button id="delete_notebook" class="btn btn-xs">x</button>'+
						'</li>'

var noteTemplate = '<li class="online note">'+
						'<a>'+
							'<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i> [title]<button type="button" class="btn btn-default btn-xs btn_position btn_slide_down btn-note-menu"><i class="fa fa-chevron-down"></i></button>'+
						'</a>'+
						'<div class="note_menu" tabindex="-1">'+
							'<dl>'+
								'<dt><button type="button" class="btn btn-default btn-xs btn_move" title="移动至..."><i class="fa fa-random"></i></button></dt>'+
								'<dt><button type="button" class="btn btn-default btn-xs btn_share" title="分享"><i class="fa fa-sitemap"></i></button></dt>'+
								'<dt><button type="button" class="btn btn-default btn-xs btn_delete" title="删除"><i class="fa fa-times"></i></button></dt>'+
							'</dl>'+
						'</div>'+
					'</li>';

var trashBinNoteItem = '<li class="disable note"><a ><i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i> [title]<button type="button" class="btn btn-default btn-xs btn_position btn_delete"><i class="fa fa-times"></i></button><button type="button" class="btn btn-default btn-xs btn_position_2 btn_replay"><i class="fa fa-reply"></i></button></a></li>';

var trashBinNotebookItem = '<li class="disable notebook"><a ><i class="fa fa-book" title="online" rel="tooltip-bottom"></i> [name]<button type="button" class="btn btn-default btn-xs btn_position btn_delete"><i class="fa fa-times"></i></button><button type="button" class="btn btn-default btn-xs btn_position_2 btn_replay"><i class="fa fa-reply"></i></button></a></li>';

var moreNotebookTemplate = '<li class="online more">'+
							'<a><i class="fa fa-angle-down" title="online" rel="tooltip-bottom"></i>[info]</a>'+
						'</li>';

var moreNoteTemplate = '<li class="online more">'+
							'<a>'+
								'<i class="fa fa-angle-down" title="online" rel="tooltip-bottom"></i> [info]'+
							'</a>'+
						'</li>';

/**
 * 浏览器调试所用方法
 */
/*function demo(){
	var arr = [1,4,2,3,6,5,7];
	sort(arr);
	console.log(arr);
}
function sort(arr){
	for(var i=0;i<arr.length-1;i++){
		for(var j=0;j<arr.length-i-1;j++){
			if(arr[j]>arr[j+1]){
				var t = arr[j];
				arr[j] = arr[j+1];
				arr[j+1] = t;
			}
		}
	}
}
*/







