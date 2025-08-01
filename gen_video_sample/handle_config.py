import os
import requests
import json
import BASE_VAR

def gen_video_finish(id, filename):
    url = BASE_VAR._WENAN_BASE_URL + "/plScriptgen/finish"
    params = {
        "id": id,  
        "filename": filename  
    }

    try:
        response = requests.get(url, params=params)
        
        if response.status_code == 200:
            print("[修改文案状态] 修改成功")
        else:
            print(f"[修改文案状态] 请求返回状态码: {response.status_code}")

    except requests.exceptions.RequestException as e:
        print(f"[修改文案状态] 请求发生异常: {e}")

class JsonPathReader:
    def __init__(self, url):
        self.url = url
        self.data = self._fetch_json_data()

    def _parse_nested_json(self, data):
        """解析嵌套的JSON字符串字段（如y1,y2等）"""
        for key in list(data.keys()):
            if key.startswith('y') and key[1:].isdigit():
                value = data[key]
                if isinstance(value, str):
                    if value.strip().lower() == 'null':
                        data[key] = None
                    else:
                        try:
                            data[key] = json.loads(value)
                        except json.JSONDecodeError:
                            pass  
        return data

    def _fetch_json_data(self):
        """从URL获取并解析JSON数据（含嵌套解析）"""
        try:
            response = requests.get(self.url)
            response.raise_for_status()
            content_start = response.text.find('{')
            json_str = response.text[content_start:]
            data = json.loads(json_str)
            return self._parse_nested_json(data)
        except requests.exceptions.RequestException as e:
            print(f"获取数据失败: {e}")
            return {}
        except json.JSONDecodeError as e:
            print(f"JSON解析失败: {e}")
            return {}

    def get_value_by_path(self, path):
        keys = path.strip('/').split('/')
        current = self.data
        for key in keys:
            if isinstance(current, dict) and key in current:
                current = current[key]
            else:
                return None
        return current
    
if __name__ == "__main__":
    url = BASE_VAR._WENAN_BASE_URL + "/plScriptgen/getone"
    reader = JsonPathReader(url)
    
    path1 = "/y1/service_info"
    path2 = "/all"
    path3 = "/y2/link_info"
    path4 = "/non_exist"
    
    print(f"路径 {path1} 的值: {reader.get_value_by_path(path1)}")
    print(f"路径 {path2} 的值: {reader.get_value_by_path(path2)[:50]}  # 截断显示")  
    print(f"路径 {path3} 的值: {reader.get_value_by_path(path3)}")
    print(f"路径 {path4} 的值: {reader.get_value_by_path(path4)}")
