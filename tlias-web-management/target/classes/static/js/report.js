// 报表统计页面逻辑

let jobChart = null;
let genderChart = null;

// 初始化报表统计
function initReportManagement() {
    // 加载岗位分布数据
    loadJobData();
    
    // 加载性别分布数据
    loadGenderData();
}

// 加载岗位分布数据
function loadJobData() {
    getEmpJobData()
        .then(result => {
            const jobData = result.data;
            renderJobChart(jobData);
        })
        .catch(error => {
            showMessage(error.message, 'danger');
        });
}

// 加载性别分布数据
function loadGenderData() {
    getEmpGenderData()
        .then(result => {
            const genderData = result.data;
            renderGenderChart(genderData);
        })
        .catch(error => {
            showMessage(error.message, 'danger');
        });
}

// 渲染岗位分布图表
function renderJobChart(jobData) {
    const ctx = document.getElementById('jobChart').getContext('2d');
    
    // 销毁之前的图表
    if (jobChart) {
        jobChart.destroy();
    }
    
    jobChart = new Chart(ctx, {
        type: 'pie',
        data: {
            labels: jobData.labels || [],
            datasets: [{
                data: jobData.data || [],
                backgroundColor: [
                    'rgba(255, 99, 132, 0.8)',
                    'rgba(54, 162, 235, 0.8)',
                    'rgba(255, 206, 86, 0.8)',
                    'rgba(75, 192, 192, 0.8)',
                    'rgba(153, 102, 255, 0.8)',
                    'rgba(255, 159, 64, 0.8)'
                ],
                borderColor: [
                    'rgba(255, 99, 132, 1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(75, 192, 192, 1)',
                    'rgba(153, 102, 255, 1)',
                    'rgba(255, 159, 64, 1)'
                ],
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: {
                    position: 'top',
                },
                title: {
                    display: true,
                    text: '员工岗位分布'
                }
            }
        }
    });
}

// 渲染性别分布图表
function renderGenderChart(genderData) {
    const ctx = document.getElementById('genderChart').getContext('2d');
    
    // 销毁之前的图表
    if (genderChart) {
        genderChart.destroy();
    }
    
    const labels = genderData.map(item => item.gender === 1 ? '男' : '女');
    const data = genderData.map(item => item.count);
    
    genderChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: '员工数量',
                data: data,
                backgroundColor: [
                    'rgba(54, 162, 235, 0.8)',
                    'rgba(255, 99, 132, 0.8)'
                ],
                borderColor: [
                    'rgba(54, 162, 235, 1)',
                    'rgba(255, 99, 132, 1)'
                ],
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            scales: {
                y: {
                    beginAtZero: true,
                    ticks: {
                        stepSize: 1
                    }
                }
            },
            plugins: {
                legend: {
                    display: false
                },
                title: {
                    display: true,
                    text: '员工性别分布'
                }
            }
        }
    });
}