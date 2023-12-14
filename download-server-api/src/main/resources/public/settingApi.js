// const BASE_URL = "http://localhost:8080"
const BASE_URL = "http://43.248.191.29:8585"

//setting api,
async function fetchSettings() {
    try {
        const resp = await fetch(`${BASE_URL}/setting`, {
            // const resp = await fetch("https://v1.hitokoto.cn?c=i", {  //示例一言是可以的
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            }
        })
        const data = await resp.json()
        console.log(data.data, 'data json')
        // TODO: 这里返回的是第一个数组，要是你换成了对象，这里就把data.data[0]改成data.data
        return data.data[0]
    } catch (e) {
        console.log(e)
    }
}

// save settings
async function saveSettings(params) {
    console.log(params)
    const resp = await fetch(`${BASE_URL}/setting/save`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(params)
    })
    return true
}

// TODO: 获取文件列表，过滤可以一起做了，四个参数 path, type, sort，order（正序/倒序）
//TODO：看一下返回的数据格式，确定返回的内容，我换成了data.data,不报错，但是没数据，你那边跑通就行，测试一下，各种筛选条件都要试一下，梅妃提就可以继续往下做了

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
            return data.data
}
