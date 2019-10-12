package spa.lyh.cn.lib_audio.mediaplayer.events;


import spa.lyh.cn.lib_audio.mediaplayer.model.AudioBean;

public class AudioLoadEvent {
  public AudioBean mAudioBean;

  public AudioLoadEvent(AudioBean audioBean) {
    this.mAudioBean = audioBean;
  }
}
