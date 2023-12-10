const BASE_URL = "http://localhost:8080"

// 设置页面 api,下面是需要返回给我的数据格式
async function fetchSettings() {
    const resp = await fetch(`${BASE_URL}/setting`, {
        // const resp = await fetch("https://v1.hitokoto.cn?c=i", {  //示例一言是可以的
        method: "GET",
        headers: {
            "Content-Type": "application/json"
        }
    })
    console.log(resp, 'resp')
    const data = await resp.json()
    console.log(data, 'data json')
    return data.data
    // return {
    //     downloadPath: '/data/mock_dir',
    //     maxTasks: 4,
    //     maxDownloadSpeed: 10,
    //     maxUploadSpeed: 12
    // }
}


// 保存设置
async function saveSettings(settings) {
    console.log(settings)
    const resp = await fetch(`${BASE_URL}/setting/savesettings`, {
        // const resp = await fetch("https://v1.hitokoto.cn?c=i", {  //示例一言是可以的
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(settings)
    })
    console.log(resp, 'resp')
    const data = await resp.json()
    console.log(data, 'data json')
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
    const resp = await fetch(BASE_URL+"/file/sort_file_list?path=" + path +"&sort=" +sort, {
        // const resp = await fetch("https://v1.hitokoto.cn?c=i", {  //示例一言是可以的
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(params)
    })
    console.log(resp, 'resp')
    const data = await resp.json()
    console.log(data, 'data json')
    return data.data
    // return [{
    //     name: 'test.测试用例类型',
    //     isDirectory: false,
    //     type: '测试用例类型',
    //     path: '/data/test',
    //     size: '1MB',
    //     createdAt: '2023-11-12'
    // }]
    // TODO: 根据sort的值，对path目录下的文件列表进行排序,sort的值有：name, size, createdAt,返回的数据格式和上面的一样，这里我就不写了
}

