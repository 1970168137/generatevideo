
import os
import sys
import time
import handle_file
import handle_video
import handle_heygem
import handle_config
import BASE_VAR
from handle_config import JsonPathReader

_DOWN_LOAD_PATH = BASE_VAR._DOWN_LOAD_PATH
_AUDIO_TRAN_PATH = BASE_VAR._AUDIO_TRAN_PATH
_AUDIO_TRAN_docker = BASE_VAR._AUDIO_TRAN_docker

_VIDEO_GEN_PATH = BASE_VAR._HEYGEM_OUT_PUT_PATH

def gen_video() :
    try:
        # 获取文案配置
        config_url = BASE_VAR._WENAN_BASE_URL + "/plScriptgen/getone"     
        reader = JsonPathReader(config_url)

        if reader.data is None or reader.data == {}:
            print("[文案配置读取] 没有可执行的任务")
            time.sleep(5)
            return
        
        # 读取配置项
        id = reader.get_value_by_path("/id")
        video = reader.get_value_by_path("/video")
        audio = reader.get_value_by_path("/audio")
        all = reader.get_value_by_path("/all")

        print(f"video: {video}")  # 自动将任何类型转换为字符串
        print(f"audio: {audio}")
        print(f"all: {all}")

        down_url = BASE_VAR._WENAN_BASE_URL + "/file/download?filename="

        if(video != None) :
            handle_file.download_file(down_url + video, _DOWN_LOAD_PATH + video)
        if(audio != None) :    
            handle_file.download_file(down_url + audio, _DOWN_LOAD_PATH + audio)

        time.sleep(5)

        input_file = _DOWN_LOAD_PATH + video  # 
        success = handle_video.extract_media(input_file)
        
        if success:
            print("[视频分拆] 处理完成!")
        else:
            print("[视频分拆] 处理失败，请检查输入文件和 FFmpeg 安装。")
            sys.exit()
        
        base, ext = os.path.splitext(video)
        video_output = f"{base}_no_audio.mp4"
        audio_output = f"{base}.wav"
        
        handle_file.copy_file(_DOWN_LOAD_PATH + audio_output, _AUDIO_TRAN_PATH + _AUDIO_TRAN_docker + audio_output, overwrite=True)
        handle_file.copy_file(_DOWN_LOAD_PATH + video_output, _VIDEO_GEN_PATH + video_output, overwrite=True)
        
        cloning_audio = handle_heygem.speech_cloning(all, _AUDIO_TRAN_docker + audio_output)
        handle_file.copy_file(cloning_audio, _VIDEO_GEN_PATH + cloning_audio, overwrite=True)
        handle_file.force_delete(cloning_audio)

        code = handle_heygem.video_cloning(video_output, cloning_audio)
        time.sleep(10)

        handle_config.gen_video_finish(id, code)
        
        return code
    except TypeError as e:
        print(f"类型错误: {e}")
        time.sleep(5)
    except Exception as e:
        print(f"未知错误: {e}")
        time.sleep(5)

if __name__ == "__main__":

    code = gen_video()
    i = 0
    while True:
        time.sleep(3)
        if(code != None) :
            i= i+ 1
            print(f"第 {i} 次查询任务状态，CODE ： {code}")
            resultget = handle_heygem.check_gen_video(code)
            print(resultget)
            if("任务不存在" in resultget):
                code = gen_video()
        else :
            code = gen_video()
       

    
    
