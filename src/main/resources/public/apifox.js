const data ={url, method, data}
async function sendReq(data) {
await fetch(url=`http://47.102.203.113:8081/${url}`, {
    method,
    credentials: 'same-origin',
    body: JSON.stringify(data),
    headers: new Headers({
        'Content-Type': 'application/json',
    }),
})
}

export default sendReq
