// 主页面逻辑

// 页面加载完成后执行
$(document).ready(function() {
    // 检查登录状态
    console.log('main.html加载，当前token:', getToken());
    checkLogin();
    
    // 初始化各模块
    initDeptManagement();
    initEmpManagement();
    initReportManagement();
    
    // 侧边栏导航点击事件
    $('.nav-link[data-page]').click(function(e) {
        e.preventDefault();
        
        // 移除所有导航的active类
        $('.nav-link[data-page]').removeClass('active');
        // 添加当前导航的active类
        $(this).addClass('active');
        
        // 获取要显示的页面
        const page = $(this).data('page');
        
        // 隐藏所有内容页面
        $('.content-page').hide();
        // 显示选中的内容页面
        $('#' + page + 'Page').show();
        
        // 更新页面标题
        const pageTitles = {
            dept: '部门管理',
            emp: '员工管理',
            report: '报表统计'
        };
        $('#pageTitle').text(pageTitles[page] || '');
    });
    
    // 退出登录按钮点击事件
    $('#logoutBtn').click(function() {
        showConfirm('确定要退出登录吗？', function() {
            removeToken();
            window.location.href = 'index.html';
        });
    });
});