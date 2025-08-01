
#资源下载后存放的目录
_DOWN_LOAD_PATH = "downloads/"

# HEYGEM 数字人部署地址
_HEYGEM_BASE_URL = "http://127.0.0.1"

# 文案管理后台部署地址（换为你部署的文案管理后台地址）
_WENAN_BASE_URL = "http://192.168.2.104/"  

# 文案配置信息（换为你部署的文案管理后台地址）
_WENAN_CONFIG = "http://192.168.2.104/plScriptgen/getonebyfileid?genfileid="

# 根据克隆的数字人视频

_HEYGEM_FILE_MARK ="-r.mp4"

# 合成数字人视频存放目录
_HEYGEM_OUT_PUT_PATH = "D:/heygem_data/face2face/temp/"

# 视频剪辑存放目录
_VIDEO_EDIT_PATH = "D:/video_handle/"

# 语音训练目录
_AUDIO_TRAN_PATH = "D:/heygem_data/voice/data/"
# 相对路径，先对 D:/heygem_data/voice/data/ 下的，也就是docker容器下的路径
_AUDIO_TRAN_docker = "/my/"

_MY_AUDIO_TRAN_PATH = _AUDIO_TRAN_PATH + _AUDIO_TRAN_docker
FORMAT_ADDIO_FILE_NAME = "USE_THIS_format_denoise.wav"
FORMAT_ADDIO_FILE = '/code/data/my/' + FORMAT_ADDIO_FILE_NAME
FORMAT_REFERENCE_TEXT = 'food when it comes to relationships。 and when it comes to the gym， they start about foot。food is really something really principal。'
