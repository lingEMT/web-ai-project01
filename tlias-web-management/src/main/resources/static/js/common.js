// 公共工具函数

// 获取token
function getToken() {
    return localStorage.getItem('token');
}

// 设置token
function setToken(token) {
    localStorage.setItem('token', token);
}

// 移除token
function removeToken() {
    localStorage.removeItem('token');
}

// 检查是否登录
function isLoggedIn() {
    return !!getToken();
}

// 跳转到登录页面
function goToLogin() {
    window.location.href = 'index.html';
}

// 验证登录状态
function checkLogin() {
    if (!isLoggedIn()) {
        goToLogin();
    }
}

// 格式化日期
function formatDate(dateString) {
    if (!dateString) return '';
    const date = new Date(dateString);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    const seconds = String(date.getSeconds()).padStart(2, '0');
    return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
}

// 格式化短日期
function formatShortDate(dateString) {
    if (!dateString) return '';
    const date = new Date(dateString);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
}

// 性别显示转换
function getGenderText(gender) {
    return gender === 1 ? '男' : '女';
}

// 岗位显示转换
function getJobText(job) {
    const jobMap = {
        1: '经理',
        2: '开发',
        3: '测试',
        4: '运维',
        5: '产品'
    };
    return jobMap[job] || '未知';
}

// 显示消息提示
function showMessage(message, type = 'success') {
    // 使用Bootstrap的toast组件
    const toastEl = document.createElement('div');
    toastEl.className = `toast align-items-center text-white bg-${type} border-0 position-fixed bottom-0 end-0 m-3`;
    toastEl.setAttribute('role', 'alert');
    toastEl.setAttribute('aria-live', 'assertive');
    toastEl.setAttribute('aria-atomic', 'true');
    
    toastEl.innerHTML = `
        <div class="d-flex">
            <div class="toast-body">
                ${message}
            </div>
            <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
        </div>
    `;
    
    document.body.appendChild(toastEl);
    
    const toast = new bootstrap.Toast(toastEl);
    toast.show();
    
    // 3秒后自动移除
    setTimeout(() => {
        toastEl.remove();
    }, 3000);
}

// 显示确认对话框
function showConfirm(message, callback) {
    if (confirm(message)) {
        callback();
    }
}