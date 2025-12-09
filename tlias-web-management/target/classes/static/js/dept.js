// 部门管理页面逻辑

let currentDeptId = null;
let deptModal = null;

// 初始化部门管理
function initDeptManagement() {
    // 获取部门列表
    loadDepts();
    
    // 初始化模态框
    deptModal = new bootstrap.Modal(document.getElementById('deptModal'));
    
    // 新增部门按钮点击事件
    $('#addDeptBtn').click(function() {
        currentDeptId = null;
        $('#deptModalLabel').text('新增部门');
        $('#deptForm')[0].reset();
        deptModal.show();
    });
    
    // 部门表单提交事件
    $('#deptForm').submit(function(e) {
        e.preventDefault();
        
        const deptName = $('#deptName').val();
        
        if (!deptName) {
            showMessage('请输入部门名称', 'warning');
            return;
        }
        
        const dept = {
            name: deptName
        };
        
        if (currentDeptId) {
            // 更新部门
            dept.id = currentDeptId;
            updateDept(dept)
                .then(() => {
                    showMessage('部门更新成功');
                    deptModal.hide();
                    loadDepts();
                })
                .catch(error => {
                    showMessage(error.message, 'danger');
                });
        } else {
            // 新增部门
            addDept(dept)
                .then(() => {
                    showMessage('部门新增成功');
                    deptModal.hide();
                    loadDepts();
                })
                .catch(error => {
                    showMessage(error.message, 'danger');
                });
        }
    });
}

// 加载部门列表
function loadDepts() {
    getDepts()
        .then(result => {
            const depts = result.data;
            renderDeptTable(depts);
        })
        .catch(error => {
            showMessage(error.message, 'danger');
        });
}

// 渲染部门表格
function renderDeptTable(depts) {
    const tbody = $('#deptTableBody');
    tbody.empty();
    
    depts.forEach(dept => {
        const tr = $('<tr></tr>');
        tr.html(`
            <td>${dept.id}</td>
            <td>${dept.name}</td>
            <td>${formatDate(dept.createTime)}</td>
            <td>
                <button class="btn btn-sm btn-primary edit-dept" data-id="${dept.id}">
                    <i class="fa fa-edit"></i> 编辑
                </button>
                <button class="btn btn-sm btn-danger delete-dept" data-id="${dept.id}">
                    <i class="fa fa-trash"></i> 删除
                </button>
            </td>
        `);
        tbody.append(tr);
    });
    
    // 绑定编辑按钮事件
    $('.edit-dept').click(function() {
        const id = $(this).data('id');
        editDept(id);
    });
    
    // 绑定删除按钮事件
    $('.delete-dept').click(function() {
        const id = $(this).data('id');
        deleteDeptConfirm(id);
    });
}

// 编辑部门
function editDept(id) {
    getDeptById(id)
        .then(result => {
            const dept = result.data;
            currentDeptId = dept.id;
            $('#deptModalLabel').text('编辑部门');
            $('#deptName').val(dept.name);
            deptModal.show();
        })
        .catch(error => {
            showMessage(error.message, 'danger');
        });
}

// 删除部门确认
function deleteDeptConfirm(id) {
    showConfirm('确定要删除该部门吗？', function() {
        deleteDept(id)
            .then(() => {
                showMessage('部门删除成功');
                loadDepts();
            })
            .catch(error => {
                showMessage(error.message, 'danger');
            });
    });
}