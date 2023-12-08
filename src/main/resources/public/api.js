const BASE_URL = "http://localhost:8080"


/**
 * @returns {Array} 返回所有的设置项
 */

// 设置页面 api,下面是需要返回给我的数据格式
async function fetchSettings() {
    return {
        downloadPath: '/data/mock_dir',
        maxTasks: 4,
        maxDownloadSpeed: 10,
        maxUploadSpeed: 12
    }
}


// 保存设置
async function saveSettings(settings) {
    console.log(settings)
    return true
}

// file 页面 api
// TODO: 获取文件列表
// 如果 没有path参数或者参数是/file，就是获取根目录下的文件列表,有payt参数，就是获取path目录下的文件列表

async function fetchFileList(path, filter) {
    console.log(path, filter)
    if (filter === undefined) {
        if (path === undefined || path === '/file') {
            return [
                {
                    name: 'Test1',
                    isDirectory: true,
                    path: '/data/mock_dir/test1',
                    createdAt: '2023-11-11',
                    children: [
                        {
                            name: 'Test1-1',
                            isDirectory: true,
                            path: '/test1-1',
                            size: null,
                            createdAt: '2023-11-11',
                            children: [{
                                name: 'Test1-1-1',
                                isDirectory: true,
                                path: '/test1-1-1',
                                size: '100KB',
                                createdAt: '2023-11-11',
                                children: [{
                                    name: 'Test1-1-1-1',
                                    isDirectory: false,
                                    type: 'txt',
                                    path: '/test1-1-1-1',
                                    size: '100KB',
                                    createdAt: '2023-11-11'
                                }]
                            }]
                        },
                        {
                            name: 'Test1-2.pdf',
                            isDirectory: false,
                            type: 'pdf',
                            path: '/test1-2',
                            size: '100KB',
                            createdAt: '2023-11-11'
                        }]
                },
                {
                    name: 'Test2.txt',
                    isDirectory: false,
                    type: 'txt',
                    path: '/data/mock_dir/test2',
                    size: '20KB',
                    createdAt: '2023-11-13'
                },
                {
                    name: 'Test3.gif',
                    isDirectory: false,
                    type: 'gif',
                    path: '/data/mock_dir/test3',
                    size: '1MB',
                    createdAt: '2023-11-12'
                },
            ]
        }
        if (path === '/data/mock_dir/test1') {
            return [
                {
                    name: 'Test1-1',
                    isDirectory: true,
                    path: '/test1-1',
                    size: null,
                    createdAt: '2023-11-11',
                    children: [{
                        name: 'Test1-1-1',
                        isDirectory: true,
                        path: '/test1-1-1',
                        size: '100KB',
                        createdAt: '2023-11-11',
                        children: [{
                            name: 'Test1-1-1-1',
                            isDirectory: false,
                            type: 'txt',
                            path: '/test1-1-1-1',
                            size: '100KB',
                            createdAt: '2023-11-11'
                        }]
                    }]
                },
                {
                    name: 'Test1-2.pdf',
                    isDirectory: false,
                    type: 'pdf',
                    path: '/test1-2',
                    size: '100KB',
                    createdAt: '2023-11-11'
                }]
        }
        if (path === '/data/mock_dir/test2') {
            return [
                {
                    name: 'Test2.txt',
                    isDirectory: false,
                    type: 'txt',
                    path: '/data/mock_dir/test2',
                    size: '20KB',
                    createdAt: '2023-11-13'
                }
            ]
        }
        if (path === '/test1-1') {
            return [
                {
                    name: 'Test1-1-1',
                    isDirectory: true,
                    path: '/test1-1-1',
                    size: '100KB',
                    createdAt: '2023-11-11',
                    children: [{
                        name: 'Test1-1-1-1',
                        isDirectory: false,
                        type: 'txt',
                        path: '/test1-1-1-1',
                        size: '100KB',
                        createdAt: '2023-11-11'
                    }]
                }
            ]
        }
        if (path === '/test1-1-1') {
            return [
                {
                    name: 'Test1-1-1-1',
                    isDirectory: false,
                    type: 'txt',
                    path: '/test1-1-1-1',
                    size: '100KB',

                    createdAt: '2023-11-11'
                }
            ]
        }
    } else {
        return {
            name: 'test.测试用例类型',
            isDirectory: false,
            type: '测试用例类型',
            path: '/data/mock_dir/test3',
            size: '1MB',
            createdAt: '2023-11-12'
        }
    }
}

//  TODO: 查找符合path的文件，然后根据filter进行过滤
// 如果有filter参数，那么就是获取path目录下的文件列表，然后根据filter进行过滤,filter的值有：all,video,archive,document，我这里只需要返回一个符合filter的文件即可（后端详细分类）
async function fetchFilterFile(path, filter) {
    console.log(path, filter)
    if (filter === 'all') {
        return [
            {
                name: 'Test1',
                isDirectory: true,
                path: '/data/mock_dir/test1',
                createdAt: '2023-11-11',
                children: [
                    {
                        name: 'Test1-1.txt',
                        isDirectory: false,
                        type: 'txt',
                        path: '/test1-1',
                        size: null,
                        createdAt: '2023-11-11'
                    },
                    {
                        name: 'Test1-2.pdf',
                        isDirectory: false,
                        type: 'pdf',
                        path: '/test1-2',
                        size: '100KB',
                        createdAt: '2023-11-11'
                    }]
            },
            {
                name: 'Test2.txt',
                isDirectory: false,
                type: 'txt',
                path: '/data/mock_dir/test2',
                size: '20KB',
                createdAt: '2023-11-13'
            },
            {
                name: 'Test3.gif',
                isDirectory: false,
                type: 'gif',
                path: '/data/mock_dir/test3',
                size: '1MB',
                createdAt: '2023-11-12'
            },
        ]
    } else
        return [
            {
                name: 'Test1',
                isDirectory: true,
                path: '/data/mock_dir/test1',
                createdAt: '2023-11-11',
                children: [
                    {
                        name: 'Test1-1.txt',
                        isDirectory: false,
                        type: 'txt',
                        path: '/test1-1',
                        size: null,
                        createdAt: '2023-11-11'
                    }]
            },
            {
                name: 'test.测试用例类型',
                isDirectory: false,
                type: '测试用例类型',
                path: '/data/mock_dir/test3',
                size: '1MB',
                createdAt: '2023-11-12'
            }]
}

//点击排序按钮，对文件列表进行排序
async function sortFileList(path, sort) {
    console.log(path, sort)
    return [{
        name: 'test.测试用例类型',
        isDirectory: false,
        type: '测试用例类型',
        path: '/data/test',
        size: '1MB',
        createdAt: '2023-11-12'
    }]
    // TODO: 根据sort的值，对path目录下的文件列表进行排序,sort的值有：name, size, createdAt,返回的数据格式和上面的一样，这里我就不写了
}

//file
async function fetchFilterFile(params) {
    let name = null
    let isDirectory = true
    let path = null
    let size = null
    let creatAt = null
    let children = null

    await fetch(BASE_URL + "/file/fetch_filter_file?path=" +
        "" + params.path + "&filter=" + params.filter, {
        method: "GET",
    }).then(data => {
        return data.json()
    }).then(response => {
        if (response.code === 200) {
            name = response.data.name
            isDirectory = response.data.isDirectory
            path = response.data.path
            size = response.data.size
            creatAt = response.data.creatAt
            children = response.data.children
        }
    })
    return {
        name: name,
        isDirectory: isDirectory,
        path: path,
        size: size,
        creatAt: creatAt,
        children: children
    }
}

//sort
async function sortFileList(params) {
    let path = null
    let sort = null
    await fetch(BASE_URL + "/file/sort_file_list?path=" + params.path + "&sort=" + params.sort, {
        method: "POST",
    }).then(data => {
        return data.json()
    }).then(response => {
        if (response.code === 200) {
            path = response.data.path
            sort = response.data.sort
        }
    })
    return {
        total: path,
        items: sort
    }
}
// tasks 页面 api
async function fetchTasks(params) {
    let total = 0
    let items = []
    await fetch(BASE_URL + "/transfer/get_tasks?pageNum=" + params.pos + "&pageSize=" + params.limit, {
        method: "GET",
    }).then(data => {
        return data.json()
    }).then(response => {
        if (response.code === 200) {
            total = response.data.total
            items = response.data.items
        }
    })
    return {
        total: total,
        items: items
    }
}


// 改变线程数
async function changeThreads(id, threads) {
    let isSuccess = false
    await fetch(BASE_URL + "/transfer/update_thread?id=" + id + "&count=" + threads, {
        method: "POST",
    }).then(data => {
        return data.json()
    }).then(response => {
        if (response.code === 200) {
            isSuccess = true
        }
    })
    return isSuccess
}

async function submitDownloadPath(path) {
    console.log(path)
    return true
}

// 重新下载任务的详细信息，ids是一个数组，单个任务，就是一个元素的数组，多个任务就是多个元素的数组，实现同一个接口单量和多量的处理
async function fetchTaskInfo(ids) {
    console.log(ids, '刷新，重新获取任务的详细信息')
    let isRefresh = false
    await fetch(BASE_URL + "/transfer/refresh?ids=" + ids, {
        method: "POST",
    }).then(data => {
        return data.json()
    }).then(response => {
        if (response.code === 200) {
            isRefresh = true
            location.reload()
        }
    })
    return isRefresh
}

// 暂停下载任务,ids是一个数组，单个任务，就是一个元素的数组，多个任务就是多个元素的数组，实现同一个接口单量和多量的处理
async function pauseTask(ids) {
    console.log(ids, '刷新，重新获取任务的详细信息')

    await fetch(BASE_URL + "/transfer/pause?ids=" + ids, {
        method: "POST",
    }).then(data => {
        return data.json()
    }).then(response => {
        if (response.code === 200) {
            location.reload()
        }
    })
    return true
}

// 恢复下载任务，ids是一个数组
async function resumeTask(ids) {
    console.log(ids, '刷新，重新获取任务的详细信息')

    await fetch(BASE_URL + "/transfer/pause?ids=" + ids, {
        method: "POST",
    }).then(data => {
        return data.json()
    }).then(response => {
        if (response.code === 200) {
            location.reload()
        }
    })
    return true
}

// 删除下载任务，ids是一个数组
async function deleteTask(ids) {
    console.log(ids, '刷新，重新获取任务的详细信息')

    await fetch(BASE_URL + "/transfer/delete?ids=" + ids, {
        method: "POST",
    }).then(data => {
        return data.json()
    }).then(response => {
        if (response.code === 200) {
            location.reload()
        }
    })
    return true
}

// 对任务的状态进行过滤选择,如果是all 的情况下，就返回所有的数据，默认是all 的情况
async function fetchFilterTasks(filter, pos, limit,currentPage) {
    console.log(filter)
    let total = 0
    let items = []
    if (filter === 'all') {
        await fetch(BASE_URL + "/transfer/get_tasks?pageNum=0&pageSize=5", {
            method: "GET",
        }).then(data => {
            return data.json()
        }).then(response => {
            if (response.code === 200) {
                total = response.data.total
                items = response.data.items
            }
        })
        return {
            total: total,
            items: items
        }
    } else {
        await fetch(BASE_URL + "/transfer/get_tasks?pageNum=0&pageSize=5", {
            method: "GET",
        }).then(data => {
            return data.json()
        }).then(response => {
            if (response.code === 200) {
                total = response.data.total
                items = response.data.items
            }
        })
        return {
            total: total,
            items: items
        }
    }
}
