// 登录页面逻辑
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

$(document).ready(function() {
    // 检查是否已登录
    console.log('login.html加载，当前token:', getToken());
    if (isLoggedIn()) {
        console.log('已登录，跳转到main.html');
        window.location.href = 'main.html';
        return;
    }

    // 登录表单提交
    $('#loginForm').submit(function(e) {
        e.preventDefault();
        
        const username = $('#username').val();
        const password = $('#password').val();
        
        console.log('登录请求，用户名:', username, '密码:', password);
        
        if (!username || !password) {
            showMessage('请输入用户名和密码', 'warning');
            return;
        }
        
        login(username, password)
            .then(result => {
                // 登录成功，保存token
                console.log('登录成功，完整返回数据:', result);
                console.log('result.data:', result.data);
                
                // 检查result.data是否存在且包含token
                if (result.data && result.data.token) {
                    console.log('准备保存的token:', result.data.token);
                    setToken(result.data.token);
                    console.log('保存后的token:', getToken());
                    // 跳转到主页面
                    setTimeout(() => {
                        console.log('跳转到main.html');
                        window.location.href = 'main.html';
                    }, 500);
                } else {
                    console.error('登录成功但没有token:', result);
                    showMessage('登录成功但没有token', 'danger');
                }
            })
            .catch(error => {
                console.error('登录失败:', error);
                console.error('错误详情:', error.stack);
                showMessage(error.message, 'danger');
            });
    });
});