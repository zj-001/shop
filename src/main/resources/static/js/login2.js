$(function() {
					// 发送验证码事件
					$('.getsms').click(function() {
						var email = $(this).parents('form').find('input.email');
						var error = $(this).parents('form').find('.error_msg');
						switch(email.validateemail()) {
							case 0:
								// 短信验证码的php请求
								error.html(msgtemp('验证码 <strong>已发送</strong>','alert-success'));
								$(this).rewire(60);
							break;
							case 1: error.html(msgtemp('<strong>邮箱为空</strong> 请输入邮箱号',    'alert-warning')); break;
							case 2: error.html(msgtemp('<strong>邮箱格式错误</strong> 请输入正确的邮箱','alert-warning')); break;
						}
					});
					// 以下确定按钮仅供参考
					$('.submit').click(function() {
						var form = $(this).parents('form')
						var phone = form.find('input.phone');
						var email = form.find('input.email');
						var pwd = form.find('input.password');
						var error = form.find('.error_msg');
						var success = form.siblings('.tabs_div');
						var options = {
							beforeSubmit: function () {
								console.log('喵喵喵')
							},
							success: function (data) {
								console.log(data)
							}
						}
						// 验证手机号参考这个
						switch(phone.validatemobile()) {
							case 1: error.html(msgtemp('<strong>用户名为空</strong> 请输入用户名',    'alert-warning')); return; break;
							case 2: error.html(msgtemp('<strong>用户名错误</strong> 用户名不能少于3位','alert-warning')); return; break;
							case 3: error.html(msgtemp('<strong>用户名错误</strong> 用户名不能大于6位',  'alert-warning')); return; break;
						}
						// 验证邮箱参考这个
						switch(email.validateemail()) {
							case 1: error.html(msgtemp('<strong>邮箱为空</strong> 请输入邮箱号',    'alert-warning')); return; break;
							case 2: error.html(msgtemp('<strong>邮箱错误</strong> 请输入正确的邮箱格式',		'alert-warning')); return; break;
						}
						// 验证密码复杂度参考这个
						switch(pwd.validatepwd()) {
							case 1: error.html(msgtemp('<strong>密码不能为空</strong> 请输入密码',    'alert-warning')); return; break;
							case 2: error.html(msgtemp('<strong>密码过短</strong> 请输入6位以上的密码','alert-warning')); return; break;
							case 3: error.html(msgtemp('<strong>密码过长</strong><br>请输入10位以下的密码',  'alert-warning')); return; break;
						}
//						form.ajaxForm(options);
						// 请求成功执行类似这样的事件
//						form.fadeOut(150,function() {
//							success.fadeIn(150);
//						 });
						
					})
				});
