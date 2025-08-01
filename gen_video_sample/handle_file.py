import os
import re
import time
from typing import Optional
import requests
from tqdm import tqdm  # 进度条库
import shutil
import pathlib
from typing import Optional
from datetime import datetime, timedelta

def copy_file(source_path, target_path, overwrite=False, show_progress=True):
    try:
        if not os.path.isfile(source_path):
            print(f"错误: 源文件不存在 - {source_path}")
            return False
        
        target_dir = os.path.dirname(target_path)
        if not os.path.exists(target_dir):
            os.makedirs(target_dir, exist_ok=True)
            if show_progress:
                print(f"创建目录: {target_dir}")
        
        if os.path.exists(target_path) and not overwrite:
            print(f"跳过: 目标文件已存在 - {target_path}")
            return False
        
        if show_progress:
            file_size = os.path.getsize(source_path)
            print(f"正在复制: {source_path} -> {target_path} ({file_size/1024:.1f} KB)")
        
        shutil.copy2(source_path, target_path)  
        
        if show_progress:
            print(f"[文件复制] 复制完成: {target_path}")
        return True
        
    except Exception as e:
        print(f"错误: 复制失败 - {e}")
        return False
    
def download_file(url, save_path):
    os.makedirs(os.path.dirname(save_path), exist_ok=True)

    file_size = 0
    if os.path.exists(save_path):
        file_size = os.path.getsize(save_path)

    headers = {'Range': f'bytes={file_size}-'} if file_size else {}

    try:
        response = requests.get(url, headers=headers, stream=True, timeout=30)
        response.raise_for_status() 
        total_size = int(response.headers.get('content-length', 0)) + file_size

        progress_bar = tqdm(
            total=total_size,
            unit='B',
            unit_scale=True,
            unit_divisor=1024,
            desc=f"下载 {os.path.basename(save_path)}",
            initial=file_size
        )

        with open(save_path, 'ab') as f:
            for chunk in response.iter_content(chunk_size=1024):
                if chunk:  
                    f.write(chunk)
                    progress_bar.update(len(chunk))

        progress_bar.close()

        if total_size != 0 and progress_bar.n != total_size:
            print("警告：下载文件可能不完整")
        
        print(f"文件已保存到：{save_path}")

    except requests.exceptions.RequestException as e:
        print(f"下载失败：{str(e)}")
    except Exception as e:
        print(f"发生未知错误：{str(e)}")
        if os.path.exists(save_path):
            os.remove(save_path) 

def force_delete(path: str, max_retries: int = 5, retry_delay: float = 0.5, ignore_errors: bool = False) -> bool:
   
    if not os.path.exists(path):
        if ignore_errors:
            return False
        raise FileNotFoundError(f"路径不存在: {path}")
    
    path = os.path.abspath(path)
    
    for attempt in range(max_retries):
        try:
            if os.path.isfile(path) or os.path.islink(path):
                if os.name == 'nt':
                    if not os.access(path, os.W_OK):
                        os.chmod(path, 0o666)
                os.remove(path)
                
            elif os.path.isdir(path):
                if os.name == 'nt':
                    for root, dirs, files in os.walk(path):
                        for dir in dirs:
                            os.chmod(os.path.join(root, dir), 0o777)
                        for file in files:
                            os.chmod(os.path.join(root, file), 0o666)
                shutil.rmtree(path, ignore_errors=False)
                
            if not os.path.exists(path):
                return True
                
        except Exception as e:
            if attempt == max_retries - 1:
                if ignore_errors:
                    return False
                raise e
                
            print(f"删除失败 (尝试 {attempt+1}/{max_retries}): {e}，{retry_delay}秒后重试")
    
    return False

def find_first_matching_file(directory: str) -> Optional[str]:
    """
    扫描指定目录，返回首个匹配条件的文件中间字符串
    
    Args:
        directory: 要扫描的目录路径
        
    Returns:
        匹配到的中间字符串，如果没有找到则返回None
    """
    if not os.path.exists(directory):
        print(f"错误: 目录不存在 - {directory}")
        return None
    

    min_age = timedelta(minutes=30) 
    
    for filename in os.listdir(directory):
        current_time = datetime.now()

        file_path = os.path.join(directory, filename)
        if os.path.isfile(file_path):
            match = re.search(r'(.+)-r\.mp4$', filename)
            if match:
                try:
                    create_time = datetime.fromtimestamp(os.path.getctime(file_path))
                    file_age = current_time - create_time
                    
                    if file_age >= min_age:
                        extracted = match.group(1)
                        print(f"找到匹配文件: {filename} -> 提取结果: {extracted}")
                        print(f"文件创建时间: {create_time.strftime('%Y-%m-%d %H:%M:%S')}，已存在 {file_age.total_seconds()//60:.0f} 分钟")
                        return extracted
                    else:
                        print(f"忽略文件 {filename}: 年龄不足30分钟（{file_age.total_seconds()//60:.0f} 分钟）")
                except Exception as e:
                    print(f"获取文件时间失败: {filename}, 错误: {e}")
                    
    return None

def check_file_existence(file_path: str) -> bool:

    try:
        normalized_path = os.path.normpath(file_path)
        path_obj = pathlib.Path(normalized_path)
        return path_obj.is_file()
        
    except Exception as e:
        print(f"检查文件存在性时出错: {e}")
        return False

if __name__ == "__main__":

     while True:
            result = find_first_matching_file(r"D:\video_handle\step1")
            if result:
                print(f"程序已成功提取: {result}")
            
            time.sleep(3)  
