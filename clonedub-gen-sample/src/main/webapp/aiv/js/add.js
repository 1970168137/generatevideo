
    // 打开样式选择窗口
    function openStyleWindow() {
        // 获取当前预览文本
        const _title = document.getElementById('_title').value;
        const styleWindow = window.open(
            '/selectStyle?title=' + _title,
            'styleWindow',
            'width=500,height=630,left=400,top=150'
        );
    }

    // 接收样式更新
    window.addEventListener('message', (e) => {
        if (e.origin !== window.location.origin) return;
        document.getElementById('_title_style').value = e.data;
        // 应用样式预览
        document.getElementById('stylePreview').innerHTML =
            `<span style="${e.data}">${document.getElementById('_title').value || "标题预览"}</span>`;
    });

    // 标题位置更新
    function updateTitlePosition() {
        const position = parseFloat(document.getElementById('titlePosition').value);
        const marker = document.getElementById('titlePositionMarker');
        const label = document.getElementById('titlePositionLabel');

        marker.style.top = `${position * 100}%`;
        label.style.top = `${position * 100 - 1}%`;
        label.textContent = `${Math.round(position * 100)}% 位置`;
    }

    // 标题文本更新
    document.getElementById('_title').addEventListener('input', function() {
        const preview = document.getElementById('stylePreview');
        const style = document.getElementById('_title_style').value;
        preview.innerHTML = `<span style="${style}">${this.value || "标题预览"}</span>`;
    });