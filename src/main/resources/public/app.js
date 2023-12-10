// const data = { url, method, data }
// async function sendReq(data) {
//     console.log(data)
//     await fetch(url = `http://47.102.203.113:8081/${url}`, {
//         method,
//         credentials: 'same-origin',
//         body: JSON.stringify(data),
//         headers: new Headers({
//             'Content-Type': 'application/json',
//         }),
//     })
// }

let BASE_URL = 'http://47.102.203.113:8081'
async function fetchData() {
    await fetch(`${BASE_URL}/tea-expo/command/list`, {
        method: "POST",
        // body: JSON.stringify({
        //     "page": 1,
        // }),
    })
}
