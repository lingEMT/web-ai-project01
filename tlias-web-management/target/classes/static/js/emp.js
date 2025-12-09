// 员工管理页面逻辑

let currentEmpId = null;
let empModal = null;
let currentPage = 1;
let currentParams = {};

// 初始化员工管理
function initEmpManagement() {
    // 初始化模态框
    empModal = new bootstrap.Modal(document.getElementById('empModal'));
    
    // 加载部门列表到下拉框
    loadDeptOptions();
    
    // 查询表单提交事件
    $('#empQueryForm').submit(function(e) {
        e.preventDefault();
        currentPage = 1;
        loadEmps();
    });
    
    // 重置查询表单
    $('#resetEmpQueryBtn').click(function() {
        $('#empQueryForm')[0].reset();
        currentPage = 1;
        loadEmps();
    });
    
    // 新增员工按钮点击事件
    $('#addEmpBtn').click(function() {
        currentEmpId = null;
        $('#empModalLabel').text('新增员工');
        $('#empForm')[0].reset();
        empModal.show();
    });
    
    // 全选/取消全选
    $('#selectAllEmp').click(function() {
        const isChecked = $(this).prop('checked');
        $('.emp-checkbox').prop('checked', isChecked);
    });
    
    // 员工表单提交事件
    $('#empForm').submit(function(e) {
        e.preventDefault();
        
        // 检查必填字段
        const requiredFields = ['username', 'password', 'name', 'gender', 'phone', 'job', 'salary', 'entryDate', 'deptId'];
        let isValid = true;
        
        requiredFields.forEach(field => {
            if (!$('#emp' + field.charAt(0).toUpperCase() + field.slice(1)).val()) {
                isValid = false;
            }
        });
        
        if (!isValid) {
            showMessage('请填写所有必填字段', 'warning');
            return;
        }
        
        const emp = {
            username: $('#empUsername').val(),
            password: $('#empPassword').val(),
            name: $('#empRealName').val(),
            gender: parseInt($('#empGenderSelect').val()),
            phone: $('#empPhone').val(),
            job: parseInt($('#empJob').val()),
            salary: parseInt($('#empSalary').val()),
            entryDate: $('#empEntryDate').val(),
            deptId: parseInt($('#empDeptId').val())
        };
        
        // 处理头像上传
        const imageFile = $('#empImage')[0].files[0];
        
        if (imageFile) {
            // 上传头像
            uploadFile(imageFile)
                .then(result => {
                    emp.image = result.data;
                    saveEmp(emp);
                })
                .catch(error => {
                    showMessage(error.message, 'danger');
                });
        } else {
            // 没有头像文件
            saveEmp(emp);
        }
    });
    
    // 初始加载员工列表
    loadEmps();
}

// 保存员工信息
function saveEmp(emp) {
    if (currentEmpId) {
        // 更新员工
        emp.id = currentEmpId;
        updateEmp(emp)
            .then(() => {
                showMessage('员工更新成功');
                empModal.hide();
                loadEmps();
            })
            .catch(error => {
                showMessage(error.message, 'danger');
            });
    } else {
        // 新增员工
        addEmp(emp)
            .then(() => {
                showMessage('员工新增成功');
                empModal.hide();
                loadEmps();
            })
            .catch(error => {
                showMessage(error.message, 'danger');
            });
    }
}

// 加载部门列表到下拉框
function loadDeptOptions() {
    getDepts()
        .then(result => {
            const depts = result.data;
            const select = $('#empDeptId');
            select.empty();
            
            depts.forEach(dept => {
                select.append(`<option value="${dept.id}">${dept.name}</option>`);
            });
        })
        .catch(error => {
            showMessage(error.message, 'danger');
        });
}

// 加载员工列表
function loadEmps() {
    const params = {
        page: currentPage,
        pageSize: 10,
        name: $('#empName').val(),
        gender: $('#empGender').val(),
        begin: $('#empBegin').val(),
        end: $('#empEnd').val()
    };
    
    currentParams = params;
    
    getEmps(params)
        .then(result => {
            const pageResult = result.data;
            renderEmpTable(pageResult);
            renderPagination(pageResult);
        })
        .catch(error => {
            showMessage(error.message, 'danger');
        });
}

// 渲染员工表格
function renderEmpTable(pageResult) {
    const tbody = $('#empTableBody');
    tbody.empty();
    
    const emps = pageResult.list || [];
    
    emps.forEach(emp => {
        const tr = $('<tr></tr>');
        tr.html(`
            <td><input type="checkbox" class="emp-checkbox" value="${emp.id}"></td>
            <td>${emp.id}</td>
            <td>${emp.username}</td>
            <td>${emp.name}</td>
            <td>${getGenderText(emp.gender)}</td>
            <td>${emp.phone}</td>
            <td>${getJobText(emp.job)}</td>
            <td>${emp.salary}</td>
            <td>${formatShortDate(emp.entryDate)}</td>
            <td>${emp.deptName || '未分配'}</td>
            <td>
                <button class="btn btn-sm btn-primary edit-emp" data-id="${emp.id}">
                    <i class="fa fa-edit"></i> 编辑
                </button>
                <button class="btn btn-sm btn-danger delete-emp" data-id="${emp.id}">
                    <i class="fa fa-trash"></i> 删除
                </button>
            </td>
        `);
        tbody.append(tr);
    });
    
    // 绑定编辑按钮事件
    $('.edit-emp').click(function() {
        const id = $(this).data('id');
        editEmp(id);
    });
    
    // 绑定删除按钮事件
    $('.delete-emp').click(function() {
        const id = $(this).data('id');
        deleteEmpConfirm([id]);
    });
}

// 渲染分页控件
function renderPagination(pageResult) {
    const pagination = $('#empPagination');
    pagination.empty();
    
    const totalPages = pageResult.totalPages;
    
    if (totalPages <= 1) {
        return;
    }
    
    const ul = $('<ul></ul>').addClass('pagination');
    
    // 上一页按钮
    const prevLi = $('<li></li>').addClass('page-item');
    const prevA = $('<a></a>').addClass('page-link').text('上一页');
    if (currentPage === 1) {
        prevLi.addClass('disabled');
    } else {
        prevA.click(function() {
            currentPage--;
            loadEmps();
        });
    }
    prevLi.append(prevA);
    ul.append(prevLi);
    
    // 页码按钮
    for (let i = 1; i <= totalPages; i++) {
        const li = $('<li></li>').addClass('page-item');
        const a = $('<a></a>').addClass('page-link').text(i);
        
        if (i === currentPage) {
            li.addClass('active');
        } else {
            a.click(function() {
                currentPage = i;
                loadEmps();
            });
        }
        
        li.append(a);
        ul.append(li);
    }
    
    // 下一页按钮
    const nextLi = $('<li></li>').addClass('page-item');
    const nextA = $('<a></a>').addClass('page-link').text('下一页');
    if (currentPage === totalPages) {
        nextLi.addClass('disabled');
    } else {
        nextA.click(function() {
            currentPage++;
            loadEmps();
        });
    }
    nextLi.append(nextA);
    ul.append(nextLi);
    
    pagination.append(ul);
}

// 编辑员工
function editEmp(id) {
    getEmpById(id)
        .then(result => {
            const emp = result.data;
            currentEmpId = emp.id;
            $('#empModalLabel').text('编辑员工');
            $('#empUsername').val(emp.username);
            $('#empPassword').val(emp.password);
            $('#empRealName').val(emp.name);
            $('#empGenderSelect').val(emp.gender);
            $('#empPhone').val(emp.phone);
            $('#empJob').val(emp.job);
            $('#empSalary').val(emp.salary);
            $('#empEntryDate').val(emp.entryDate);
            $('#empDeptId').val(emp.deptId);
            empModal.show();
        })
        .catch(error => {
            showMessage(error.message, 'danger');
        });
}

// 删除员工确认
function deleteEmpConfirm(ids) {
    showConfirm('确定要删除选中的员工吗？', function() {
        deleteEmp(ids)
            .then(() => {
                showMessage('员工删除成功');
                loadEmps();
            })
            .catch(error => {
                showMessage(error.message, 'danger');
            });
    });
}