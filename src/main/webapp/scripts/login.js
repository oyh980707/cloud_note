/*
 * script/login.js 编码为utf-8
 * 
 * JavaScript里面的检验代码不是安全可靠的代码,只是提高用户体验,服务器的检验机制才是安全的
 * 应为可以在控制台中发请求用ajax,绕过JavaScript代码的检验机制,
 * 
 */
$(function (){
//	alert("Hello Word!");
	//登录功能
	//用户点击登录,触发事件
	$("#login").click(loginAction);
	//用户焦点离开用户名框,触发检查用户名格式是否正确
	$("#count").blur(checkName);
	//用户焦点离开用户名框,触发检查密码格式是否正确
	$("#password").blur(checkPassword);
	
	//注册功能
	$("#regist_button").click(registAction);
	$("#regist_username").blur(checkRegistName);
	$("#regist_password").blur(checkRegistPassword);
	$("#final_password").blur(checkRegistConfirm);
});
/*
 * 检查注册用户名
 */
function checkRegistName(){
	var name = $("#regist_username").val().trim();
	var rule = /^\w{3,16}$/;
	if(!rule.test(name)){
		$("#regist_username").next().show().find("span").html("3~16字符");
		return false;
	}
	$("#regist_username").next().hide();
	return true;
}
/*
 * 检验注册密码
 */
function checkRegistPassword(){
	var password = $("#regist_password").val().trim();
	var rule = /^\w{6,18}$/;
	if(!rule.test(password)){
		$("#regist_password").next().show().find("span").html("6~18字符");
		return false;
	}
	$("#regist_password").next().hide();
	return true;
}
/*
 * 检验注册再次确认密码
 */
function checkRegistConfirm(){
	var password1 = $("#regist_password").val();
	var password2 = $("#final_password").val();
	// 密码为空且和再次确认不相等,为空为false
	if(! password1 || password2!=password1){
		$("#final_password").next().show().find("span").html("密码不一致!");
		return false;
	}
	$("#final_password").next().hide();
	return true;
}
/*
 * 注册请求
 */
function registAction(){
//	alert("registAction");
	/* 检验界面参数
	 * 发起ajax请求
	 * 得到响应后,更新界面
	 */
	//获取界面表单数据
	var name = $("#regist_username").val().trim();
	var password = $("#regist_password").val().trim();
	var nick = $("#nickname").val().trim();
	var confirm = $("#final_password").val().trim();
	//检验界面参数
	var n = checkRegistConfirm()+checkRegistName()+checkRegistConfirm();
	if(n!=3){
		return;
	}
	
	//data 对象中的属性名要与服务器控制器的参数名要一致!
	var data = {"name":name,"password":password,"nick":nick,"confirm":confirm};
	// $.ajax的简化版
	$.post("user/regist.do",data,function (result){
//		console.log(result);
		if(result.state==0){
			//退回登录界面
			$("#back").click();//有参则绑定事件,无参则发起事件
			//帮用户填写用户名
			var name = result.data.name;
			$("#count").val(name);
			
			//focus() 无参是触发获取焦点事件,传入参数的话就是绑定获取焦点事件
			$("#password").focus();
			
			//清空上次留下的提示警告
			$("#count").next().empty();
			$("#password").next().empty();
			
			//清空注册表单
			$("#regist_username").val("");
			$("#regist_password").val("");
			$("#nickname").val("");
			$("#final_password").val("");
		}else if(result.state==4){//用户名错误
			$("#regist_username").next().show().find("span").html(result.message);
		}else if(result.state==3){
			$("#regist_password").next().show().find("span").html(result.message);
		}else{
			alert(result.message);
		}
	});
	//得到相应后,更新页面
	
}

/*
 * 检查用户名,客户端需要对用户名检查,服务端也要对用户名检查
 * 提高代码严谨性
 * 
 * 此处可更新,在检查用户名格式后添加一个ajax检查用户名是否存在,提示用户,用户名的状态
 * 
 */
function checkName(){
	var name = $("#count").val().trim();
	//检查用户名(正则表达式)
	var rule = /^\w{3,16}$/;
	if(!rule.test(name)){
		$("#count").next().html("3~16个字符");
		return false;
	}
	$("#count").next().empty();
	return true;
}
/*
 * 密码检查,检查用户输入的密码的格式是否正确
 */
function checkPassword(){
	var password = $("#password").val().trim();
	//检查用户名(正则表达式)
	var rule = /^\w{6,18}$/;
	if(!rule.test(password)){
		$("#password").next().html("6~18个字符");
		return false;
	}
	$("#password").next().empty();
	return true;
}

/*
 * 登录请求
 */
function loginAction(){
//	alert("Hello Word!");
	
	//获取用户输入的用户名和密码
	var name = $("#count").val().trim();
	var password = $("#password").val().trim();
	
	//检查用户名格式是否正确,防止用户直接点击登录没有失去焦点事件检查用户名
//	if(!checkName()){
//		return;
//	}
	//使用JavaScript的特性,防止用户不作任何输入直接登录提示信息不完全,所以此方式很巧妙的是两个方法都执行后判断结果,使提示信息显示完全
	var n = checkName() + checkPassword();
	if(n!=2){
		return ;
	}
	
	//data 对象中的属性名要与服务器控制器的参数名要一致! login(name,password)
	var data = {"name":name,"password":password};
	$.ajax({
		"url":"user/login.do",
		"data":data,
		"type":"post",
		"dataType":"json",
		"success": function(result){
//			console.log(result);
			if(result.state==0){
				//登录成功
				var user = result.data;
//				console.log(user);
				//登录成功后将userId保存在cookie里
				addCookie("userId", user.id);
				addCookie("name", user.name);
				//跳转到 edit.html
				location.href="edit.html";
			}else{
				var msg = result.message;
				if(result.state == 2){// 用户名不存在
					$("#count").next().html(msg);
				}else if(result.state == 3){// 密码错误
					$("#password").next().html(msg);
				}else{
					alert(msg);
				}
			}
		},
		"error": function(e){
			alert("通信失败!");
		}
	});
}

