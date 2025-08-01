import subprocess
import os

def extract_media(input_file, video_output=None, audio_output=None):

    if not video_output:
        base, ext = os.path.splitext(input_file)
        video_output = f"{base}_no_audio.mp4"
    
    if not audio_output:
        base, ext = os.path.splitext(input_file)
        audio_output = f"{base}.wav"
    
    try:
        video_cmd = [
            "ffmpeg",
            "-i", input_file,
            "-c:v", "copy",  # 直接复制视频流，不重新编码
            "-an",           # 移除音频
            "-y",            # 覆盖已存在文件
            video_output
        ]
        subprocess.run(video_cmd, check=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
        
        audio_cmd = [
            "ffmpeg",
            "-i", input_file,
            "-vn",           # 移除视频
            "-y",            # 覆盖已存在文件
            audio_output
        ]
        subprocess.run(audio_cmd, check=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
        
        print(f"成功提取:")
        print(f"  静音视频: {video_output}")
        print(f"  WAV 音频: {audio_output}")
        return True
        
    except subprocess.CalledProcessError as e:
        print(f"FFmpeg 执行失败: {e.stderr.decode()}")
        return False
    except Exception as e:
        print(f"发生错误: {e}")
        return False

def add_clip(reader):
    id = reader.get_value_by_path("/id")
    video = reader.get_value_by_path("/video")
    audio = reader.get_value_by_path("/audio")


if __name__ == "__main__":
    input_file = "input.mp4" 
    success = extract_media(input_file)
    
    if success:
        print("处理完成!")
    else:
        print("处理失败，请检查输入文件和 FFmpeg 安装。")