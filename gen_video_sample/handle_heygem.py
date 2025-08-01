
import sys
import requests
import BASE_VAR
import handle_file
from datetime import datetime
from handle_config import JsonPathReader
 
def preprocess_and_tran(file) :
    base_url= BASE_VAR._HEYGEM_BASE_URL + ":18180/v1/preprocess_and_tran"
 
    headers = {
        'Content-Type': 'application/json'
    }
    data ={
        "format": "wav",
        "reference_audio": file , 
        "lang": "zh"
    }
    
    result = requests.post(url=base_url, json=data, headers=headers)
    
    print(f"[语音训练] 返回结果: {result.text}")
    return result

def speech_cloning(text, audio_file) :
    
    asr_audio_url = ''
    asr_audio_text = ''

    hadUseFormatFile = handle_file.check_file_existence(BASE_VAR._MY_AUDIO_TRAN_PATH + BASE_VAR.FORMAT_ADDIO_FILE_NAME)

    if(hadUseFormatFile == False) : 

        tran_result = preprocess_and_tran(audio_file)
        if "-1" in tran_result.text :
            print("[语音训练] 出现异常，终止程序执行")
            sys.exit()
        else :
            json_data = tran_result.json()
            asr_audio_url = json_data.get('asr_format_audio_url')
            print(f"asr_audio_url: {asr_audio_url}")
            asr_audio_text = json_data.get('reference_audio_text')
            print(f"code的值为: {asr_audio_text}")

    else: 
        print("[语音训练] 已有指定语音模型，不需要训练")
        asr_audio_url = BASE_VAR.FORMAT_ADDIO_FILE
        asr_audio_text =BASE_VAR.FORMAT_REFERENCE_TEXT

    base_url= BASE_VAR._HEYGEM_BASE_URL + ":18180/v1/invoke"
    
    headers = {
        'Content-Type': 'application/json'
    }

    data ={
    "speaker": "1", 
    "text": text, 
    "format": "wav", 
    "topP": 0.7, 
    "max_new_tokens": 1024, 
    "chunk_length": 100, 
    "repetition_penalty": 1.2, 
    "temperature": 0.7, 
    "need_asr": "false", 
    "streaming": "false",
    "is_fixed_seed": 0,
    "is_norm": 0,
    "reference_audio": asr_audio_url,
    "reference_text": asr_audio_text 
    }
    
    result = requests.post(url=base_url, json=data, headers=headers)
    current_time = datetime.now().strftime("%Y%m%d%H%M%S%f")[:-3]
    output_wav = current_time + ".wav"
    
    with open (output_wav, 'wb') as file:
            file.write(result.content)
    
    print(f"[语音克隆合成] 输出文件: {output_wav}")
    return output_wav

def video_cloning(videofile, audiofile) :
    base_url= BASE_VAR._HEYGEM_BASE_URL + ":8383/easy/submit"
    code = datetime.now().strftime("%Y%m%d%H%M%S%f")[:-3]
    print(code) 

    headers = {
        'Content-Type': 'application/json'
    }
    data =  {
        "audio_url": audiofile, 
        "video_url": videofile,
        "code": code, 
        "chaofen": 0,
        "watermark_switch": 0, 
        "pn": 1 
    }
    
    result = requests.post(url=base_url, json=data, headers=headers)
    print(f"[视频合成-生成数字人视频] 输出文件: {result.text}")

    return code
   

def check_gen_video(codeid) :
    base_url = BASE_VAR._HEYGEM_BASE_URL + ":8383/easy/query"
    params= {
        "code":f"{codeid}"
    }
    response = requests.get(url=base_url, params=params)
    return response.text


if __name__ == "__main__":
    url = "https://XXXX.com/plScriptgen/getone"     
    reader = JsonPathReader(url)
    print(reader.get_value_by_path("/all"))
    



