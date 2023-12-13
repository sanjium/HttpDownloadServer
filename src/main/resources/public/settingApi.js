// const BASE_URL = "http://localhost:8080"
const BASE_URL = "http://43.248.191.29:8585"

// 设置页面 api,下面是需要返回给我的数据格式
async function fetchSettings() {
    try {
        const resp = await fetch(`${BASE_URL}/setting`, {
            // const resp = await fetch("https://v1.hitokoto.cn?c=i", {  //示例一言是可以的
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            }
        })
        console.log(resp, 'resp')
        const data = await resp.json()
        return data.data
    } catch (e) {
        console.log(e)
    }
}

// 保存设置
async function saveSettings(params) {
    console.log(params)
    const resp = await fetch(`${BASE_URL}/setting/save`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(params)
    })
    console.log(resp, 'resp')
    // const data = await resp.json()
    return true
}

// TODO: 获取文件列表，过滤可以一起做了，四个参数 path, type, sort，order（正序/倒序）
// 进行排序的时候，参数放在body里面，不要放在url里面
// 如果 没有path参数或者参数是/file，就是获取根目录下的文件列表,有path参数，就是获取path目录下的文件列表
async function fetchFileList(params) {
    console.log(params)

        const resp = await fetch(BASE_URL + "/file/file_list", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(params)
        })
            console.log(resp, 'resp')
            const data = await resp.json()
            console.log(data, 'data json')
            return data
}
    // const resp = await fetch(`${BASE_URL}/file/file_list`, {
    //     method: "POST",
    //     headers: {
    //         "Content-Type": "application/json"
    //     },
    //     body: JSON.stringify(params)
    // })
    // console.log(resp, 'resp')
    // const data = await resp.json()
    // console.log(data, 'data json')  // 看一下返回的数据格式，确定返回的内容
    // return data.data



// // TODO: 进行排序的时候，参数放在body里面，不要放在url里面.这个接口不要了，合并在上面了
// //点击排序按钮，对文件列表进行排序
// async function sortFileList(params) {
//     console.log(params, 'sort files')
//     const resp = await fetch(BASE_URL + "/file/sort_file_list", {
//         method: "POST",
//         headers: {
//             "Content-Type": "application/json"
//         },
//         body: JSON.stringify(params)
//     })
//     console.log(resp, 'resp')
//     const data = await resp.json()
//     console.log(data, 'data json')
//     return data.data
// }


// mock 的 api,需要的参数和返回的数据格式
// async function fetchFileList(params) {
//     console.log(params)
//     // if (filter === undefined) {
//         if (params.path === '' || params.path === '/file') {
//             return [
//                 {
//                     name: 'Test1',
//                     isDirectory: true,
//                     path: '/data/mock_dir/test1',
//                     createdAt: '2023-11-11',
//                     children: [
//                         {
//                             name: 'Test1-1',
//                             isDirectory: true,
//                             path: '/test1-1',
//                             size: null,
//                             createdAt: '2023-11-11',
//                             children: [{
//                                 name: 'Test1-1-1',
//                                 isDirectory: true,
//                                 path: '/test1-1-1',
//                                 size: '100KB',
//                                 createdAt: '2023-11-11',
//                                 children: [{
//                                     name: 'Test1-1-1-1',
//                                     isDirectory: false,
//                                     type: 'txt',
//                                     path: '/test1-1-1-1',
//                                     size: '100KB',
//                                     createdAt: '2023-11-11'
//                                 }]
//                             }]
//                         },
//                         {
//                             name: 'Test1-2.pdf',
//                             isDirectory: false,
//                             type: 'pdf',
//                             path: '/test1-2',
//                             size: '100KB',
//                             createdAt: '2023-11-11'
//                         }]
//                 },
//                 {
//                     name: 'Test2.txt',
//                     isDirectory: false,
//                     type: 'txt',
//                     path: '/data/mock_dir/test2',
//                     size: '20KB',
//                     createdAt: '2023-11-13'
//                 },
//                 {
//                     name: 'Test3.gif',
//                     isDirectory: false,
//                     type: 'gif',
//                     path: '/data/mock_dir/test3',
//                     size: '1MB',
//                     createdAt: '2023-11-12'
//                 },
//             ]
//         }
//         if (params.path === '/data/mock_dir/test1') {
//             return [
//                 {
//                     name: 'Test1-1',
//                     isDirectory: true,
//                     path: '/test1-1',
//                     size: null,
//                     createdAt: '2023-11-11',
//                     children: [{
//                         name: 'Test1-1-1',
//                         isDirectory: true,
//                         path: '/test1-1-1',
//                         size: '100KB',
//                         createdAt: '2023-11-11',
//                         children: [{
//                             name: 'Test1-1-1-1',
//                             isDirectory: false,
//                             type: 'txt',
//                             path: '/test1-1-1-1',
//                             size: '100KB',
//                             createdAt: '2023-11-11'
//                         }]
//                     }]
//                 },
//                 {
//                     name: 'Test1-2.pdf',
//                     isDirectory: false,
//                     type: 'pdf',
//                     path: '/test1-2',
//                     size: '100KB',
//                     createdAt: '2023-11-11'
//                 }]
//         }
//         if (params.path === '/data/mock_dir/test2') {
//             return [
//                 {
//                     name: 'Test2.txt',
//                     isDirectory: false,
//                     type: 'txt',
//                     path: '/data/mock_dir/test2',
//                     size: '20KB',
//                     createdAt: '2023-11-13'
//                 }
//             ]
//         }
// }
