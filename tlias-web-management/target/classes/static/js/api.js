// API请求函数

// 基础配置
const API_BASE_URL = ''; // 后端API基础URL

// 请求方法
function request(url, method = 'GET', data = null, headers = {}) {
    const token = getToken();
    if (token) {
        headers['token'] = token;
    }
    
    const options = {
        method: method,
        headers: {
            'Content-Type': 'application/json',
            ...headers
        }
    };
    
    if (data && method !== 'GET') {
        options.body = JSON.stringify(data);
    }
    
    console.log('API请求:', method, url, data, headers);
    
    return fetch(`${API_BASE_URL}${url}`, options)
        .then(response => {
            console.log('API响应状态:', response.status, response.statusText);
            if (response.status === 401) {
                // 未授权，跳转到登录页面
                removeToken();
                goToLogin();
                throw new Error('未授权');
            }
            return response.json();
        })
        .then(result => {
            console.log('API响应数据:', result);
            if (result.code !== 1) {
                throw new Error(result.msg || '请求失败');
            }
            return result;
        })
        .catch(error => {
            console.error('API请求错误:', error);
            throw error;
        });
}

// 登录API
function login(username, password) {
    return request('/login', 'POST', { username, password });
}

// 部门API
function getDepts() {
    return request('/depts');
}

function getDeptById(id) {
    return request(`/depts/${id}`);
}

function addDept(dept) {
    return request('/depts', 'POST', dept);
}

function updateDept(dept) {
    return request('/depts', 'PUT', dept);
}

function deleteDept(id) {
    return request(`/depts?id=${id}`, 'DELETE');
}

// 员工API
function getEmps(params) {
    const queryString = new URLSearchParams(params).toString();
    return request(`/emps?${queryString}`);
}

function getEmpById(id) {
    return request(`/emps/${id}`);
}

function addEmp(emp) {
    return request('/emps', 'POST', emp);
}

function updateEmp(emp) {
    return request('/emps', 'PUT', emp);
}

function deleteEmp(ids) {
    const queryString = new URLSearchParams({ ids: ids.join(',') }).toString();
    return request(`/emps?${queryString}`, 'DELETE');
}

// 报表API
function getEmpJobData() {
    return request('/report/empJobData');
}

function getEmpGenderData() {
    return request('/report/empGenderData');
}

// 文件上传API
function uploadFile(file) {
    const formData = new FormData();
    formData.append('file', file);
    
    const token = getToken();
    const headers = {};
    if (token) {
        headers['token'] = token;
    }
    
    return fetch(`${API_BASE_URL}/upload`, {
        method: 'POST',
        headers: headers,
        body: formData
    })
    .then(response => {
        if (response.status === 401) {
            removeToken();
            goToLogin();
            throw new Error('未授权');
        }
        return response.json();
    })
    .then(result => {
        if (result.code !== 1) {
            throw new Error(result.msg || '上传失败');
        }
        return result;
    });
}