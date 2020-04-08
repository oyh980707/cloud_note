/*
 * script/login.js 编码为utf-8
 * 
 * JavaScript里面的检验代码不是安全可靠的代码,只是提高用户体验,服务器的检验机制才是安全的
 * 应为可以在控制台中发请求用ajax,绕过JavaScript代码的检验机制,
 * 
 */
$(function (){
	//显示用户名于右上角
	showUserName();
});

/**
 * 显示用户名于右上角
 * @returns
 */
function showUserName(){
	alert("1");
	let name = getCookie("name");
	$("#display_user_name").text(name);
}

















