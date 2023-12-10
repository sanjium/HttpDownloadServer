
const BASE_URL = "http://localhost:8080"

//TODO: tasks 页面 api, params是一个对象，包含了currentPage，optionValue（filter）和limit三个属性,  //用POST 方法
// TODO； /transfer/get_tasks 这个第一次获取和过滤是同一个接口，合并一下了，默认all的情况，如果optionValue是all，就是获取所有的任务，如果optionValue是filter，就是获取符合filter的任务
async function fetchTasks(params) {
    console.log(params)
    const resp = await fetch(`${BASE_URL}/transfer/get_tasks`, {
        // const resp = await fetch("https://v1.hitokoto.cn?c=i", {  //示例一言是可以的
        method: "POST",
        body: JSON.stringify({
            params
        })
    })
    console.log(resp, 'resp')
    const data = await resp.json()
    console.log(data, 'data json')
    return data
}


// 改变线程数
async function changeThreads(params) {
    let isSuccess = false
    await fetch(BASE_URL + "/transfer/update_thread", {
        method: "POST",
        body: JSON.stringify({
            params
        })
    })
    console.log(resp, 'resp')
    const data = await resp.json()
    console.log(data, 'data json')
    if (data.code === 200) {
        isSuccess = true
    } else {
        isSuccess = false
        console.log(data)
    }
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
    const resp = await fetch(BASE_URL + "/transfer/refresh", {
        method: "POST",
        body: JSON.stringify({
            ids
        })
    })
    const data = await resp.json()
    console.log(data, 'data json')
    if (resp.code === 200) {
        isRefresh = true
        location.reload()
    }
    return isRefresh
}

// 暂停下载任务,ids是一个数组，单个任务，就是一个元素的数组，多个任务就是多个元素的数组，实现同一个接口单量和多量的处理
async function pauseTask(ids) {
    console.log(ids, '刷新，重新获取任务的详细信息')
    const resp = await fetch(BASE_URL + "/transfer/pause", {
        method: "POST",
        body: JSON.stringify({
            ids
        })
    })
    // const data = await resp.json()
    // console.log(data, 'data json')
    if (resp.code === 200) {
        location.reload()
        return true
    }
   return false
}

// 恢复下载任务，ids是一个数组
async function resumeTask(ids) {
    console.log(ids, '刷新，重新获取任务的详细信息')

    const resp = await fetch(BASE_URL + "/transfer/pause", {
        method: "POST",
        body: JSON.stringify({
            ids
        })
    })
    console.log(resp, 'resp')
    if (resp.code === 200) {
        location.reload()
        return true
    }
   return false
}

// 删除下载任务，ids是一个数组
async function deleteTask(ids) {
    console.log(ids, '刷新，重新获取任务的详细信息')

    await fetch(BASE_URL + "/transfer/delete?ids=" + ids, {
        method: "POST",
        body: JSON.stringify({
            ids
        })
    })
    console.log(resp, 'resp')
    if (resp.code === 200) {
        location.reload()
        return true
    }
    return false
}

//TODO： 对任务进行过滤，可以调用上面的fetchTasks接口，传入的参数有过滤的条件
// async function fetchFilterTasks(params) {
//     console.log(params)
//     try {
//         await fetch(BASE_URL + "/transfer/get_tasks", {
//             method: "GET",

//         }).then(data => {
//             return data.json()
//         }).then(response => {
//             if (response.code === 200) {
//                 total = response.data.total
//                 items = response.data.items
//             }
//         })
//         return {
//             total: total,
//             items: items
//         }
//     } catch (e) {
//         console.log(e)
//     }
// }