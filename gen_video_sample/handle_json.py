import requests
import json
from collections.abc import Mapping

def parse_nested_json(obj, parent_key=''):
    if isinstance(obj, dict):
        for k in list(obj.keys()):
            current_path = f"{parent_key}.{k}" if parent_key else k
            
            if k in {'type', 'gen_video_name', 'y31'} or current_path in {'y1.type', 'y2.type', 'y4.type', 'y5.type', 'y6.type', 'y7.type'}:
                if isinstance(obj[k], str) and obj[k].isdigit():
                    pass  
                elif obj[k] is None:
                    pass 
                else:
                    obj[k] = str(obj[k])  
                continue
            
            if k == 'y31' and obj[k] == "null":
                obj[k] = "null"
            
            obj[k] = parse_nested_json(obj[k], current_path)
        return obj
    
    elif isinstance(obj, list):
        return [parse_nested_json(item, parent_key) for item in obj]
    
    elif isinstance(obj, str):
        try:
            parsed = json.loads(obj)
            return parse_nested_json(parsed, parent_key)
        except json.JSONDecodeError:
            return obj.replace('\\u201c', '“').replace('\\u201d', '”')
    
    return obj

def url_to_json(url):
    try:
        response = requests.get(url, timeout=5)
        response.raise_for_status()
        
        raw_data = json.loads(
            response.text,
            parse_constant=lambda x: x if x == 'null' else None
        )
        
        processed = parse_nested_json(raw_data)
        
        if isinstance(processed.get('gen_video_name'), str) and processed['gen_video_name'].lower() == 'null':
            processed['gen_video_name'] = None
            
        return processed
    
    except Exception as e:
        print(f"处理失败: {str(e)}")
        return None

# 测试验证
if __name__ == "__main__":
    result = url_to_json("http://127.0.0.1/plScriptgen/getonebyfileid?genfileid=123123")
    print(json.dumps(result, indent=2, ensure_ascii=False, default=str))