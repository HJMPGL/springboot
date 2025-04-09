// 初始化界面
document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('app').innerHTML = `
        <button onclick="play()">播放</button>
        <button onclick="next()">下一首</button>
    `;
});

// 播放功能
function play() {
    fetch('/springboot/api/play')
        .then(response => console.log('播放命令已发送'))
        .catch(error => console.error('请求失败:', error));
}

// 切歌功能
function next() {
    fetch('/springboot/api/next')
        .then(response => console.log('切歌命令已发送'))
        .catch(error => console.error('请求失败:', error));
}